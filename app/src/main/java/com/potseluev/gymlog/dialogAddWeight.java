package com.potseluev.gymlog;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
    SQLiteDatabase db;
    ContentValues cv;
    Date date;
    InputFilter inputFilter;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_weight_add, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);

        editWeightValue = v.findViewById(R.id.editWeightValue);
        btnMinus05 = v.findViewById(R.id.btnMinus05);
        btnPlus05 = v.findViewById(R.id.btnPlus05);

        dbhelper = new DBHelper(v.getContext());
        db = dbhelper.getWritableDatabase();
        cv = new ContentValues(1);

        editWeightValue.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 1)});
//        editWeightValue.setSelectAllOnFocus(true);

        double value = dbhelper.getLastSavedWeight();
        if (value > 0) {
            editWeightValue.setText(String.valueOf(value));
        }

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                double currentValue = 0;
                try {
                    currentValue = Double.parseDouble(String.valueOf(editWeightValue.getText()));
                } catch (Exception e) {
                }

                double newValue = currentValue;
                switch (view.getId()) {
                    case R.id.btnPlus05:
                        newValue = (currentValue + 0.5);
                        break;
                    case R.id.btnMinus05:
                        newValue = (currentValue - 0.5);
                        break;
                }
                editWeightValue.setText(String.valueOf(newValue));
            }
        };

        btnMinus05.setOnClickListener(onClickListener);
        btnPlus05.setOnClickListener(onClickListener);

        return v;
    }

    public void onClick(View v) {
        Log.d("mytag", "Dialog 1: " + ((Button) v).getText());
        saveWeightToDB(v);
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("mytag", "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("mytag", "Dialog 1: onCancel");
    }


    private void saveWeightToDB(View v) {
        double previousValue = 0;
        try {
            previousValue = dbhelper.getLastSavedWeight();
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        double value = Double.parseDouble(String.valueOf(editWeightValue.getText()));
        date = Calendar.getInstance().getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String Date = fmt.format(date);

        long newRow = dbhelper.addWeight(Date, value);
        if (newRow > 0) {

            Toast.makeText(v.getContext(), "Разница: " + String.valueOf(value - previousValue), Toast.LENGTH_SHORT).show();
        }
    }


}