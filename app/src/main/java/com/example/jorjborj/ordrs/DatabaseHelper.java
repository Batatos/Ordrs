package com.example.jorjborj.ordrs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by jorjborj on 4/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
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

    // Items table
    public static final String ITEM_TABLE = "items";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_AMOUNT = "amount";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_CATEGORY = "category";
    public static final String ITEM_IMG = "img";
    public static final String ITEM_SUPPLIER = "supplier";
    public static final String ITEM_SUPPLIERNUMBER = "suppliernumber";


    // orders table
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID_COL = "ID";
    public static final String ORDER_TABLENUM_COL = "tablenum";

    // orderItems table
    public static final String ORDERITEM_TABLE = "orderitem";
    public static final String ORDERITEM_NAME_COL = "name";
    public static final String ORDERITEM_ID_COL = "ID"; //FOREIGN KEY
    public static final String ORDERITEM_QUANTITY_COL = "quantity";
    public static final String ORDERITEM_NOTES_COL = "notes";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
        db = this.getWritableDatabase();
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

        db.execSQL("CREATE TABLE " + ITEM_TABLE + " ("
                + ITEM_NAME + " TEXT PRIMARY KEY,"
                + ITEM_PRICE + " REAL,"
                + ITEM_AMOUNT + " INTEGER,"
                + ITEM_TYPE + " TEXT,"
                + ITEM_CATEGORY + " TEXT,"
                + ITEM_IMG + " BLOB,"
                + ITEM_SUPPLIER + " TEXT,"
                + ITEM_SUPPLIERNUMBER + " TEXT"
                + ")");

        //TODO: order, orderitem creation
    }

    public boolean insertOrder(){



        return false;
    }

    //########################ITEM###################################

    public boolean deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ITEM_TABLE +" WHERE "+ ITEM_NAME +" = '"+name+"'";
        db.execSQL(query);
        return true;
    }
    public boolean insertItem(String name, double price, int amount, String type, String category, Bitmap img, String supplier, String supplierNumber){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME,name);
        contentValues.put(ITEM_PRICE,price);
        contentValues.put(ITEM_AMOUNT,amount);
        contentValues.put(ITEM_TYPE,type);
        contentValues.put(ITEM_CATEGORY,category);

        byte[] bArray=null;
        if(img!=null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bArray = bos.toByteArray();
        }
        contentValues.put(ITEM_IMG,bArray);
        contentValues.put(ITEM_SUPPLIER,supplier);
        contentValues.put(ITEM_SUPPLIERNUMBER,supplierNumber);

        long result = db.insert(ITEM_TABLE, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ITEM_TABLE, null);
        return data;

        //TODO: should geta items by category.
    }

    public Cursor getAllStartersItems(){

        SQLiteDatabase db = this.getWritableDatabase();
        String s = "starters";
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE +
                " WHERE " + ITEM_CATEGORY + " = '"+s+"'", null);
        return data;
    }

    public Cursor getAllFoodItems(){

        SQLiteDatabase db = this.getWritableDatabase();
        String s = "food";
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE +
                " WHERE " + ITEM_CATEGORY + " = '"+s+"'", null);
        return data;
    }

    public Cursor getAllDrinksItems(){

        SQLiteDatabase db = this.getWritableDatabase();
        String s = "drinks";
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE +
                " WHERE " + ITEM_CATEGORY + " = '"+s+"'", null);
        return data;
    }

    public Cursor getAllDessertsItems(){

        SQLiteDatabase db = this.getWritableDatabase();
        String s = "desserts";
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE +
                " WHERE " + ITEM_CATEGORY + " = '"+s+"'", null);
        return data;
    }
    public Cursor getAllAlcoholItems(){

        SQLiteDatabase db = this.getWritableDatabase();
        String s = "alcohol";
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE +
                " WHERE " + ITEM_CATEGORY + " = '"+s+"'", null);
        return data;
    }

    //########################EVENTS###################################

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

    public int  updateEvent(int id, int tableNum,String contactName, int phoneNum, int numOfPeople, String notes, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_NUMBER,tableNum);
            contentValues.put(COLUMN_CONTACT_NAME,contactName);
            contentValues.put(COLUMN_PHONENUM,phoneNum);
            contentValues.put(COLUMN_NUMOFPEOPLE,numOfPeople);
            contentValues.put(COLUMN_NOTES,notes);
            contentValues.put(COLUMN_DATE,date);

            // update
            cnt = db.update(EVENT_TABLE, contentValues,ITEM_NAME+ " = ?", new String[]{Integer.toString(id)});
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
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


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: to other tables
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE);

        onCreate(db);
    }

    public int  updateItem(Item editedItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME,editedItem.getName().toString());
            contentValues.put(ITEM_PRICE,editedItem.getPrice());
            contentValues.put(ITEM_AMOUNT,editedItem.getAmount());
            contentValues.put(ITEM_TYPE,editedItem.getType().toString());
            contentValues.put(ITEM_CATEGORY,editedItem.getCategory().toString());

            byte[] bArray=null;
            if(editedItem.getImg()!=null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                editedItem.getImg().compress(Bitmap.CompressFormat.PNG, 100, bos);
                bArray = bos.toByteArray();
            }
            contentValues.put(ITEM_IMG,bArray);
            contentValues.put(ITEM_SUPPLIER,editedItem.getSupplier().toString());
            contentValues.put(ITEM_SUPPLIERNUMBER,editedItem.getSupplierNumber().toString());

            // update
            cnt = db.update(ITEM_TABLE, contentValues,ITEM_NAME+ " = ?", new String[]{editedItem.getName().toString()});
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }
}
