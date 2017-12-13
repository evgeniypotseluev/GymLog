package com.potseluev.gymlog;


import android.provider.BaseColumns;


//Database Schema
final class DBSchema {
    static final String DATABASE_NAME = "db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA = ", ";

    static class UserInfo implements  BaseColumns {
        static final String TABLE_NAME = "userinfo";
        static final String AGE = "age";
        static final String SEX = "sex";
        static final String GROWTH = "growth";
        static final String WEIGHT = "weight";

        static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY , " +
                    AGE + TEXT_TYPE + COMMA +
                    SEX + TEXT_TYPE + COMMA +
                    GROWTH + TEXT_TYPE + COMMA +
                    WEIGHT + TEXT_TYPE +   " )";

        static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static class Weight implements BaseColumns {

        static final String TABLE_NAME = "weight";
        static final String DATE = "date";
        static final String VALUE = "value";

        static final String SQL_CREATE_TABLE = "" +
                "CREATE TABLE " + TABLE_NAME + " (" +
                DATE + " TEXT PRIMARY KEY , " +
                VALUE + " NUMERIC )";
        static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

