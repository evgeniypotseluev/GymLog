package com.potseluev.gymlog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.potseluev.gymlog.Helpers.DecimalDigitsInputFilter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dialogAddWeight extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    EditText editWeightValue;
    Button btnPlus05;
    Button btnMinus05;

    DBHelper dbhelper;
    Date date;
    InputFilter inputFilter;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_weight_add, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);

        //Элементы экрана
        editWeightValue = v.findViewById(R.id.editWeightValue);
        btnMinus05 = v.findViewById(R.id.btnMinus05);
        btnPlus05 = v.findViewById(R.id.btnPlus05);
        dbhelper = new DBHelper(v.getContext());
        //Только цифровой ввод веса пользователя
        editWeightValue.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 1)});

        //Покажем последнее сохраненное значение веса
        try {
            editWeightValue.setText(String.valueOf(dbhelper.getLastSavedWeight()));
        } catch (Exception ignored) {
        }

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                double currentValue = 0;
                try {
                    currentValue = Double.parseDouble(String.valueOf(editWeightValue.getText()));
                } catch (Exception ignored) {
                }

                switch (view.getId()) {
                    case R.id.btnPlus05:
                        editWeightValue.setText(String.valueOf(currentValue + 0.5));
                        break;
                    case R.id.btnMinus05:
                        editWeightValue.setText(String.valueOf(currentValue - 0.5));
                        break;
                }
            }
        };

        btnMinus05.setOnClickListener(onClickListener);
        btnPlus05.setOnClickListener(onClickListener);
        return v;
    }

    public void onClick(View v) {
        saveWeightToDB(v);
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    private void saveWeightToDB(View v) {
        double previousValue = 0;
        try {
            previousValue = dbhelper.getLastSavedWeight();
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        double value = Double.parseDouble(String.valueOf(editWeightValue.getText()));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String Date = fmt.format(Calendar.getInstance().getTime());

        long newRow = dbhelper.addWeight(Date, value);
        if (newRow > 0) {
            Toast.makeText(v.getContext(), "Разница: " + String.valueOf(value - previousValue), Toast.LENGTH_SHORT).show();
        }
    }


}