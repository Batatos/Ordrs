package com.example.jorjborj.ordrs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorjborj on 4/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ordrs.db";
    public static final String ORDER_TABLE = "tblOrder";
    public static final String ORDER_ID_COL = "ID";
    public static final String ORDER_TABLENUM_COL = "TABLENUMBER";
    public static final String ORDER_STATUS_COL = "STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //STAM TESTING -> it works
        //Order (As a whole order that contains order items) table creation.
        db.execSQL("create table "+ORDER_TABLE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TABLENUMBER INTEGER,STATUS TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+ORDER_TABLE);
        onCreate(db);
    }
}
