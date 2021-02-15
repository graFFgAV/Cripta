package com.decorator1889.cripta;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bestseller.coupons.sam.Network.RetrofitApi;
import com.decorator1889.cripta.Models.GraphModel;
import com.decorator1889.cripta.Models.GraphModelResponce;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LandscapeChart extends AppCompatActivity implements
        OnChartValueSelectedListener {

    String ID = Constant.NAME;

    private LineChart chart;
    private int ButtonValue = 365;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    Button year, halfYear, month,week, day, hour, close;
    ArrayList<Float> ss = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_land_scape);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        init();
        chart.setNoDataText("Loading...");
        year.setBackground(this.getDrawable(R.drawable.button_background));

        @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
        View.OnClickListener onClickListener = view -> {

            switch (view.getId()){
                case R.id.buttonYear:
                    ButtonValue = 365;
                    ColorDefault ();
                    year.setBackground(this.getDrawable(R.drawable.button_background));
                    loadYear();
                    break;
                case R.id.buttonSixMonth:
                    ButtonValue = 548;
                    ColorDefault ();
                    halfYear.setBackground(this.getDrawable(R.drawable.button_background));
                    loadYear();
                    break;
                case R.id.buttonOneMonth:
                    ButtonValue = 1;
                    ColorDefault ();
                    month.setBackground(this.getDrawable(R.drawable.button_background));
                    loadMonth();
                    break;
                case R.id.buttonOneWeek:
                    ButtonValue = 562;
                    ColorDefault ();
                    week.setBackground(this.getDrawable(R.drawable.button_background));
                    loadMonth();
                    break;
                case R.id.buttonOneDay:
                    ButtonValue = 729;
                    ColorDefault ();
                    day.setBackground(this.getDrawable(R.drawable.button_background));
                    loadDay();

                    break;
                case R.id.buttonOneHour:
                    ButtonValue = 1380;
                    ColorDefault();
                    hour.setBackground(this.getDrawable(R.drawable.button_background));
                    loadDay();
                    break;
            }

            chart.clear();
            chart.animateX(1400);
        };


        close.setOnClickListener(view -> { finish(); });

        year.setOnClickListener(onClickListener);
        halfYear.setOnClickListener(onClickListener);
        month.setOnClickListener(onClickListener);
        week.setOnClickListener(onClickListener);
        day.setOnClickListener(onClickListener);
        hour.setOnClickListener(onClickListener);
        chart.clear();

        loadYear();
        chart.animateX(1400);
    }

    public void init() {
        close = findViewById(R.id.close_btn);
        year = findViewById(R.id.buttonYear);
        halfYear = findViewById(R.id.buttonSixMonth);
        month = findViewById(R.id.buttonOneMonth);
        week = findViewById(R.id.buttonOneWeek);
        day = findViewById(R.id.buttonOneDay);
        hour = findViewById(R.id.buttonOneHour);
        {   // // Chart Style // //
            chart = findViewById(R.id.chart2);
            chart.setBackgroundColor(Color.TRANSPARENT);
            chart.getDescription().setEnabled(false);
            chart.setTouchEnabled(true);
            chart.getLegend().setEnabled(false);
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);
            chart.getXAxis().setDrawLabels(false);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setPinchZoom(true);
            chart.setDrawMarkers(true);
            chart.setNoDataText("Loading...");
            chart.getAxisLeft().setTextColor(Color.WHITE);
            chart.getXAxis().setTextColor(Color.WHITE);
        }
        CustomMarkerView mv = new CustomMarkerView(this, R.layout.custom_maker_view);
        mv.setChartView(chart);
        chart.setMarker(mv);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void ColorDefault (){
        year.setBackground(this.getDrawable(R.drawable.button_background_defult));//default специально с ошибкой написано. Для большего стиля.
        halfYear.setBackground(this.getDrawable(R.drawable.button_background_defult));
        month.setBackground(this.getDrawable(R.drawable.button_background_defult));
        week.setBackground(this.getDrawable(R.drawable.button_background_defult));
        day.setBackground(this.getDrawable(R.drawable.button_background_defult));
        hour.setBackground(this.getDrawable(R.drawable.button_background_defult));
    }

    private void loadYear() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getMarketsType(ID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }

    private void loadMonth() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getDay(ID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }

    private void loadDay() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getHour(ID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }


    public void initError(Throwable error){
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setData(GraphModelResponce company) {
        ArrayList<GraphModel> mm = company.getData();

        YAxis yAxis;
        {
            yAxis = chart.getAxisLeft();
            chart.getAxisRight().setEnabled(false);
        }

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = ButtonValue; i < mm.size(); i++) {

            float val = Float.parseFloat(mm.get(i).getPriceUsd());

            ss.add(i-ButtonValue, val);

            values.add(new Entry(i, val));
        }

        if (ss.size() != 0) {

            Collections.sort(ss);
            float sss = ss.get(ss.size() - 1);
            yAxis.setAxisMaximum(sss + (sss * 0.01f));

            yAxis.setAxisMinimum(ss.get(0) - (ss.get(0) * 0.01f));

            ss.clear();
        } else Toast.makeText(this, "No information for this period", Toast.LENGTH_LONG).show();

        LineDataSet set1;


        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type

            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            // draw dashed line
            //set1.enableDashedLine(10f, 5f, 0f);
            // black lines and points
            set1.setColor(Color.BLUE);
            set1.setCircleColor(Color.TRANSPARENT);
            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            // draw points as solid circles
            set1.setDrawCircleHole(false);
            // customize legend entry
            // text size of values
            set1.setValueTextSize(0f);
            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter((dataSet, dataProvider) -> chart.getAxisLeft().getAxisMinimum());
            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.gradient);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            // create a data object with the data sets
            LineData data = new LineData(dataSets);
            // set data
            chart.setData(data);
        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

}
