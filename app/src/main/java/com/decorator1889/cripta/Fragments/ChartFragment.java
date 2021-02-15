package com.decorator1889.cripta.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bestseller.coupons.sam.Network.RetrofitApi;
import com.decorator1889.cripta.Constant;
import com.decorator1889.cripta.CustomMarkerView;
import com.decorator1889.cripta.LandscapeChart;
import com.decorator1889.cripta.MainActivity;
import com.decorator1889.cripta.Models.GraphModel;
import com.decorator1889.cripta.Models.GraphModelResponce;
import com.decorator1889.cripta.Models.MarketsModel;
import com.decorator1889.cripta.Models.MarketsModelResponce;
import com.decorator1889.cripta.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ChartFragment extends Fragment implements
        OnChartValueSelectedListener {


    int id;
    String name;
    SeekBar seekBar;
    private LineChart chart;
    private TextView tvY, tvTime, supply, maxSupply, marketCap, changePercent24Hr;
    private int ButtonValue = 365;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    Button year, halfYear, month,week, day, hour, landScape, transaction;
    ArrayList<Float> ss = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showUpButton();
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, container, false);


        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            fragmentActivity.findViewById(R.id.navigation).setVisibility(View.GONE);
        }


        Bundle bundle = this.getArguments();
        assert bundle != null;
        id = Integer.parseInt(bundle.getString("id")) -1;
        name = bundle.getString("name");
        //Init
        {
            seekBar = v.findViewById(R.id.SeeeeekBar);
            transaction = v.findViewById(R.id.transaction_button);
            landScape = v.findViewById(R.id.buttonLandscape);
            changePercent24Hr = v.findViewById(R.id.changePercent24Hr);
            tvY = v.findViewById(R.id.tvY);
            marketCap = v.findViewById(R.id.tvMarketCap);
            tvTime = v.findViewById(R.id.tvTime);
            supply = v.findViewById(R.id.tvsupply);
            maxSupply = v.findViewById(R.id.tvMaxSluppy);
            year = v.findViewById(R.id.buttonYear);
            halfYear = v.findViewById(R.id.buttonSixMonth);
            month = v.findViewById(R.id.buttonOneMonth);
            week = v.findViewById(R.id.buttonOneWeek);
            day = v.findViewById(R.id.buttonOneDay);
            hour = v.findViewById(R.id.buttonOneHour);
            {   // // Chart Style // //
                chart = v.findViewById(R.id.chart1);
                chart.setBackgroundColor(Color.TRANSPARENT);
                chart.getDescription().setEnabled(false);
                chart.setTouchEnabled(true);
                chart.getLegend().setEnabled(false);
                //chart.setOnChartValueSelectedListener(getContext());
                chart.setDrawGridBackground(false);
                chart.getXAxis().setDrawLabels(false);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setPinchZoom(true);
                chart.setDrawMarkers(true);
                chart.getAxisLeft().setTextColor(Color.WHITE);
                chart.getXAxis().setTextColor(Color.WHITE);
            }
            CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.custom_maker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);
        }

        seekBar.setProgress(37);
        loadYear();
        loadReview();
        ColorDefault();
        chart.setNoDataText("Loading");
        chart.animateX(1600);
        year.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));

        @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
        View.OnClickListener onClickListener = view -> {

            switch (view.getId()){
                case R.id.buttonYear:
                    ButtonValue = 365;
                    ColorDefault ();
                    year.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadYear();
                    break;
                case R.id.buttonSixMonth:
                    ButtonValue = 548;
                    ColorDefault ();
                    halfYear.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadYear();
                    break;
                case R.id.buttonOneMonth:
                    ButtonValue = 1;
                    ColorDefault ();
                    month.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadMonth();
                    break;
                case R.id.buttonOneWeek:
                    ButtonValue = 562;
                    ColorDefault ();
                    week.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadMonth();
                    break;
                case R.id.buttonOneDay:
                    ButtonValue = 729;
                    ColorDefault ();
                    day.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadDay();

                    break;
                case R.id.buttonOneHour:
                    ButtonValue = 1380;
                    ColorDefault ();
                    hour.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background));
                    loadDay();
                    break;
            }
            chart.clear();
            chart.animateX(1600);
            loadReview();
        };


        transaction.setOnClickListener(view -> {
            TransactionFragment fragment = new TransactionFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.commit();
                });


        landScape.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), LandscapeChart.class);
            Constant.NAME = name;
            startActivity(i);
        });

        year.setOnClickListener(onClickListener);
        halfYear.setOnClickListener(onClickListener);
        month.setOnClickListener(onClickListener);
        week.setOnClickListener(onClickListener);
        day.setOnClickListener(onClickListener);
        hour.setOnClickListener(onClickListener);

        return v;
    }




    @SuppressLint("UseCompatLoadingForDrawables")
    private void ColorDefault (){
        year.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));//default специально с ошибкой написано. Для большего стиля.
        halfYear.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));
        month.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));
        week.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));
        day.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));
        hour.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.button_background_defult));
    }

    private void loadYear() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getMarketsType(name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }

    private void loadMonth() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getDay(name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }

    private void loadDay() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getHour(name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setData, this::initError)
        );
    }



    private void loadReview() {
        mCompositeDisposable.add(
                Objects.requireNonNull(RetrofitApi.getRetrofit()).getMarketsAll()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::setReview, this::initError)
        );
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setReview(MarketsModelResponce mm) {

        Constant.PRICE = mm.getData().get(id).getPriceUsd();

      if (mm.getData().get(id).getMaxSupply() !=null)
        {
        String sb = prettyCount(Float.parseFloat(mm.getData().get(id).getMaxSupply()));
            maxSupply.setText(sb);
        }

        else {
            String sb = "null";
            maxSupply.setText(sb);
        }

        String sb1 = prettyCount(Float.parseFloat(mm.getData().get(id).getSupply()));
        String sb2 = prettyCount(Float.parseFloat(mm.getData().get(id).getMarketCapUsd()));
        float sb3 = Float.parseFloat(mm.getData().get(id).getPriceUsd());
        float sb4 =  Float.parseFloat(mm.getData().get(id).getChangePercent24Hr());


        if (sb4>0) {
            changePercent24Hr.setTextColor(Color.GREEN);
        }
        else {
            changePercent24Hr.setTextColor(Color.RED);
        }

        String ssb4 = String.format("%.2f", sb4);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");
        String time = df.format(Calendar.getInstance().getTime());

        tvTime.setText(time);
        marketCap.setText("$"+sb2);
        supply.setText(sb1);
        //rank.setText(mm.getData().get(id).getRank());
        changePercent24Hr.setText(ssb4+"%");
        tvY.setText("$" + sb3);
    }

    public String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
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
            //long time = Long.parseLong(mm.get(i - 1).getTime());

            values.add(new Entry(i, val));
        }

        Collections.sort(ss);
        if (ss.size() != 0) {
            float sss = ss.get(ss.size() - 1);
            yAxis.setAxisMaximum(sss + (sss * 0.01f));

            // yAxis.setAxisMinimum((Float.parseFloat(mm.get(mm.size()-1).getPriceUsd())) -500);
            yAxis.setAxisMinimum(ss.get(0) - (ss.get(0) * 0.01f));

            ss.clear();
        } else Toast.makeText(getContext(), "No information for this period", Toast.LENGTH_LONG).show();


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
                Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.gradient);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.WHITE);
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