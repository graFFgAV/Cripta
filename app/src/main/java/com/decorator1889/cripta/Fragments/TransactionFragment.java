package com.decorator1889.cripta.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.decorator1889.cripta.Constant;
import com.decorator1889.cripta.MainActivity;
import com.decorator1889.cripta.Models.Task;
import com.decorator1889.cripta.R;
import com.decorator1889.cripta.Room.DatabaseClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class TransactionFragment extends Fragment {

    EditText name, amount;
    TextView date, price;
    Button AddTrans;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaction, container, false);

        AddTrans = v.findViewById(R.id.transaction_button);
        name = v.findViewById(R.id.Name);
        amount = v.findViewById(R.id.Amount);
        price = v.findViewById(R.id.Price);
        date = v.findViewById(R.id.Date);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");
        String time = df.format(Calendar.getInstance().getTime());

        date.setText(time);
        float Price = Float.parseFloat(Constant.PRICE);

        amount.setText("1");

        price.setText("$" + String.format("%.2f", Price));

        AddTrans.setOnClickListener(view -> {

            @SuppressLint("StaticFieldLeak")
            class SaveTask extends AsyncTask<Void, Void, Void> {

                final int Amount = Integer.parseInt(String.valueOf(amount.getText()));

                @Override
                protected Void doInBackground(Void... voids) {

                    Task task = new Task();
                    task.setTask(String.valueOf(name.getText()));
                    task.setDesc("$"+String.format("%.2f", Amount *Price));
                    
                    DatabaseClient.getInstance(getContext()).getAppDatabase()
                            .taskDao()
                            .insert(task);
                    return null;
                }
            }
            SaveTask st = new SaveTask();
            st.execute();

            PortfolioFragment fragment = new  PortfolioFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.commit();
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showUpButton();
    }
}