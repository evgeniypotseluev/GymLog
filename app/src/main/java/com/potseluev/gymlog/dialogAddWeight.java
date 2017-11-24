package com.potseluev.gymlog;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class dialogAddWeight extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    EditText editWeightValue;
    Button btnPlus05;

    DBHelper dbhelper;
    SQLiteDatabase db;
    ContentValues cv;
    Date date;
    InputFilter inputFilter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_weight_add, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);

        editWeightValue = v.findViewById(R.id.editWeightValue);
        btnPlus05 = v.findViewById(R.id.btnPlus05);

        dbhelper = new DBHelper(v.getContext());
        db = dbhelper.getWritableDatabase();
        cv = new ContentValues(1);

        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int i2, int i3) {

                return null;
            }
        };

        editWeightValue.setFilters(new InputFilter[] {
                inputFilter
        });

        double value = dbhelper.getLastSavedWeight();
        Log.d("mytag", "lastSavedValue: " + value);
        if (value > 0) {
            editWeightValue.setText(String.valueOf(value));
        }

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                double currentValue = Double.parseDouble(String.valueOf(editWeightValue.getText()));
                double newValue = (currentValue + 0.5);
                editWeightValue.setText(String.valueOf(newValue));
            }
        };

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
        double value = Double.parseDouble(String.valueOf(editWeightValue.getText()));
        date = Calendar.getInstance().getTime();
        long newRow = dbhelper.addWeight(date, value);
        if (newRow > 0) {
            Toast.makeText(v.getContext(), "Saved date: " + date + "\n Value: " + value, Toast.LENGTH_SHORT).show();
        }
    }


}