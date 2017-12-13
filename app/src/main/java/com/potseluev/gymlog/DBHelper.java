package com.potseluev.gymlog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = DBSchema.DATABASE_NAME;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBSchema.UserInfo.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(DBSchema.Weight.SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DBSchema.UserInfo.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(DBSchema.Weight.SQL_DROP_TABLE);

        onCreate(sqLiteDatabase);

    }

    public long addWeight(String date, double value) {
        ContentValues cv = new ContentValues();
        cv.put(DBSchema.Weight.DATE, String.valueOf(date));
        cv.put(DBSchema.Weight.VALUE, value);
        SQLiteDatabase db = this.getWritableDatabase();
        long newRow = db.replace(DBSchema.Weight.TABLE_NAME, null, cv);
        return newRow;
    }

    public int getLastSavedWeight() {
        int value = 0;
        String date;

        String[] columns = {DBSchema.Weight.DATE, DBSchema.Weight.VALUE};
        Cursor c = this.getReadableDatabase().query(DBSchema.Weight.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToLast()) {
            date = c.getString(c.getColumnIndexOrThrow(DBSchema.Weight.DATE));
            value = c.getInt(c.getColumnIndexOrThrow(DBSchema.Weight.VALUE));
        }
        c.close();


        return value;
    }

    public ArrayList<Entry> getAllWeight() {

        ArrayList<Entry> values = new ArrayList<Entry>();
//        String[] columns = {DBSchema.Weight.DATE, DBSchema.Weight.VALUE};
        String[] columns = {DBSchema.Weight.VALUE};
        Cursor c = this.getReadableDatabase().query(DBSchema.Weight.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
//                String date = c.getString(c.getColumnIndexOrThrow(DBSchema.Weight.DATE));
                int value = c.getInt(c.getColumnIndexOrThrow(DBSchema.Weight.VALUE));
                values.add(new Entry(c.getPosition(), value));
                c.moveToNext();
                Log.d("mytag", String.valueOf(value));
            }
        }


        return values;
    }
}
