package com.example.jorjborj.ordrs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by jorjborj on 4/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ordrs.db";

    // event table
    public static final String EVENT_TABLE = "events";
    public static final String EVENT_ID_COL = "ID";
    public static final String TABLE_NUMBER = "number";
    public static final String COLUMN_CONTACT_NAME = "contactname";
    public static final String COLUMN_PHONENUM = "phonenum";
    public static final String COLUMN_NUMOFPEOPLE = "numofpeople";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_DATE = "date";

    // event table
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID_COL = "ID";
    public static final String ORDER_TABLENUM_COL = "tablenum";

    // event table
    public static final String ORDERITEM_TABLE = "orderitem";
    public static final String ORDERITEM_NAME_COL = "name";
    public static final String ORDERITEM_ID_COL = "ID"; //FOREIGN KEY
    public static final String ORDERITEM_QUANTITY_COL = "quantity";
    public static final String ORDERITEM_NOTES_COL = "notes";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + EVENT_TABLE + " ("
                + EVENT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TABLE_NUMBER + " INTEGER,"
                + COLUMN_CONTACT_NAME + " TEXT,"
                + COLUMN_PHONENUM + " INTEGER,"
                + COLUMN_NUMOFPEOPLE + " INTEGER,"
                + COLUMN_NOTES + " TEXT,"
                + COLUMN_DATE + " TEXT"
                + ")");

        //TODO: order, orderitem creation
    }

    public boolean insertOrder(){



        return false;
    }

    public boolean insertEvent(int tableNum, String contactName, int phoneNum, int numOfPeope, String notes, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_NUMBER,tableNum);
        contentValues.put(COLUMN_CONTACT_NAME,contactName);
        contentValues.put(COLUMN_PHONENUM,phoneNum);
        contentValues.put(COLUMN_NUMOFPEOPLE,numOfPeope);
        contentValues.put(COLUMN_NOTES,notes);
        contentValues.put(COLUMN_DATE,date);

        long result = db.insert(EVENT_TABLE, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getEventData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + EVENT_TABLE, null);
        return data;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: to other tables
        db.execSQL("DROP TABLE IF EXISTS"+EVENT_TABLE);
        onCreate(db);
    }

    public boolean deleteEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ EVENT_TABLE +" WHERE "+ EVENT_ID_COL +" = '"+id+"'";
        db.execSQL(query);
        return true;
    }

    public int getEventId(String contactName, int phoneNum, int numOfPeople, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+EVENT_ID_COL+" FROM "+EVENT_TABLE+" " +
                "WHERE "+COLUMN_CONTACT_NAME+" = '"+contactName+"'"+
                " AND "+COLUMN_PHONENUM+" = '"+phoneNum+"'"+
                " AND "+COLUMN_NUMOFPEOPLE+" = '"+numOfPeople+"'"+
                " AND "+COLUMN_DATE+" = '"+date+"'", null);
        data.moveToFirst();
        return data.getInt(0);
    }

}
