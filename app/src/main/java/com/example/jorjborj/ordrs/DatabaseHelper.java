package com.example.jorjborj.ordrs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
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

    //tables table
    public static final String TABLES_TABLE = "tables";
    public static final String TABLE_NUMBER_COL = "ID";
    public static final String TABLE_LOCATION_COL = "location";

    // orders table
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID_COL = "ID";
    public static final String ORDER_TABLENUM_COL = "tablenum";
    public static final String ORDER_DISCOUNT_COL = "discount";


    //report table
    public static final String INCOME_STATISTICS_TABLE = "report";
    public static final String MONTH_COL = "month";
    public static final String INCOME_COL = "income";

    //admin table
    public static final String ADMIN_TABLE = "admin";
    public static final String ADMIN_PASS = "password";


    // orderItems table
    public static final String ORDERITEMS_TABLE = "orderitems";
    public static final String ORDERITEM_NAME_COL = "name"; // FOREIGN KEY -- together with orderid is PRIMARY KEY
    public static final String ORDERITEM_ID_COL = "ID"; //FOREIGN KEY
    public static final String ORDERITEM_TABLENUM_COL = "tablenum"; //FOREIGN KEY
    public static final String ORDERITEM_TYPE_COL = "type"; //FOREIGN KEY
    public static final String ORDERITEM_QUANTITY_COL = "quantity";
    public static final String ORDERITEM_NOTES_COL = "notes";
    public static final String ORDERITEM_PRICE_COL = "price";
    public static final String ORDERITEM_STATUS_COL = "status";


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

        db.execSQL("CREATE TABLE " + TABLES_TABLE + " ("
        + TABLE_NUMBER_COL + " INTEGER PRIMARY KEY,"
                + TABLE_LOCATION_COL + " TEXT"
        +")");

        db.execSQL("CREATE TABLE " + ADMIN_TABLE + " ("
                + ADMIN_PASS + " TEXT PRIMARY KEY"
                +")");


        db.execSQL("CREATE TABLE " + INCOME_STATISTICS_TABLE + " ("
                + MONTH_COL + " INTEGER PRIMARY KEY,"
                + INCOME_COL + " REAL"
                +")");

        db.execSQL("CREATE TABLE "+ ORDER_TABLE + " ("
        + ORDER_ID_COL + " INTEGER PRIMARY KEY,"
        + ORDER_TABLENUM_COL + " INTEGER,"
                + ORDER_DISCOUNT_COL + " INTEGER,"
                + "FOREIGN KEY ("+ORDER_TABLENUM_COL+")" + "REFERENCES "+ TABLES_TABLE +")");

        db.execSQL("CREATE TABLE "+ ORDERITEMS_TABLE + " ("
                + ORDERITEM_ID_COL + " INTEGER,"
                + ORDERITEM_NAME_COL + " TEXT,"
                + ORDERITEM_TABLENUM_COL + " INTEGER,"
                + ORDERITEM_TYPE_COL + " TEXT,"
                + ORDERITEM_QUANTITY_COL + " INTEGER,"
                + ORDERITEM_PRICE_COL + " REAL,"
                + ORDERITEM_NOTES_COL + " TEXT,"
                + ORDERITEM_STATUS_COL + " TEXT,"
                + "PRIMARY KEY ("+ORDERITEM_ID_COL+","+ORDERITEM_NAME_COL+"))");

        //TODO: order, orderitem creation
    }


    //########################ITEM###################################

    public Cursor getOrderItemsByTableNum( int tableNum){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ ORDERITEMS_TABLE +
                " WHERE "+ORDERITEM_TABLENUM_COL+" = "+ tableNum, null);
        return data;
    }


    public boolean insertOrderItem(int orderId,String itemName,int tableNum, String type, int quantity,double price,String notes,String status){
        if (notes==null || notes.equals("")){
            notes="";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ORDERITEM_ID_COL,orderId);
        contentValues.put(ORDERITEM_NAME_COL,itemName);
        contentValues.put(ORDERITEM_TABLENUM_COL,tableNum);
        contentValues.put(ORDERITEM_TYPE_COL,type);
        contentValues.put(ORDERITEM_QUANTITY_COL,quantity);
        contentValues.put(ORDERITEM_PRICE_COL,price);
        contentValues.put(ORDERITEM_NOTES_COL,notes);
        contentValues.put(ORDERITEM_STATUS_COL,status);
        long result = db.insert(ORDERITEMS_TABLE, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean deleteOrderItems(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ORDERITEMS_TABLE +" WHERE "+ ORDERITEM_ID_COL +" = "+id;
        db.execSQL(query);
        return true;

    }

    public boolean deleteOrderItemsByTable(int tableNum){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ORDERITEMS_TABLE +" WHERE "+ ORDERITEM_TABLENUM_COL +" = "+tableNum;
        db.execSQL(query);
        return true;

    }




    public boolean deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ORDER_TABLE +" WHERE "+ ORDER_ID_COL +" = "+id;
        db.execSQL(query);
        return true;
    }
    public boolean deleteOrderByTableNum(int tableNum){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ORDER_TABLE +" WHERE "+ ORDER_TABLENUM_COL +" = "+tableNum;
        db.execSQL(query);
        return true;
    }

    public Cursor getOrderById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String s = Integer.toString(id);
        Cursor data = db.rawQuery("SELECT * FROM "+ ORDER_TABLE +
                " WHERE " + ORDER_ID_COL + " = "+s, null);
        return data;

    }

    public Cursor getOrderByTableNum(int tableNum){
        SQLiteDatabase db = this.getWritableDatabase();
        String s = Integer.toString(tableNum);
        Cursor data = db.rawQuery("SELECT * FROM "+ ORDER_TABLE +
                " WHERE " + ORDER_TABLENUM_COL + " = "+s, null);
        return data;

    }
    public boolean insertOrder(int id, int tableNum,int discount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(getOrderByTableNum(tableNum)!=null && getOrderByTableNum(tableNum).getCount()>0){
            Log.i("##Database##: ","There's another order on this table.");
        }

        contentValues.put(ORDER_ID_COL,id);
        contentValues.put(ORDER_TABLENUM_COL,tableNum);
        contentValues.put(ORDER_DISCOUNT_COL,discount);

        long result = db.insert(ORDER_TABLE, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }

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

    public boolean deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ ITEM_TABLE +" WHERE "+ ITEM_NAME +" = '"+name+"'";
        db.execSQL(query);
        return true;
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ITEM_TABLE, null);
        return data;
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

    public int setOrderItemStatusTrue(OrderItem oi){

        SQLiteDatabase db = this.getWritableDatabase();

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(ORDERITEM_ID_COL,oi.getOrderId());
            contentValues.put(ORDERITEM_NAME_COL,oi.getTitle());
            contentValues.put(ORDERITEM_TABLENUM_COL,oi.getTableNum());
            contentValues.put(ORDERITEM_TYPE_COL,oi.getType());
            contentValues.put(ORDERITEM_QUANTITY_COL,oi.getCounter());
            contentValues.put(ORDERITEM_PRICE_COL,oi.getPrice());
            contentValues.put(ORDERITEM_NOTES_COL,oi.getNotes());
            contentValues.put(ORDERITEM_STATUS_COL,"true");

            // update
            cnt = db.update(ORDERITEMS_TABLE, contentValues,ORDERITEM_ID_COL+ " = ? AND "+ORDERITEM_NAME_COL+" = ?", new String[]{Integer.toString(oi.getOrderId()),oi.getTitle().toString()});
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }

    public Cursor getKitchenAllOrderItemsStatusFalse(){
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "false";
        Cursor data = db.rawQuery("SELECT * FROM "+ ORDERITEMS_TABLE +
                " WHERE " + ORDERITEM_STATUS_COL + " = '"+s+"' AND "+ ORDERITEM_TYPE_COL + " = 'k'", null);
        return data;
    }

    public Cursor getBarAllOrderItemsStatusFalse(){
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "false";
        Cursor data = db.rawQuery("SELECT * FROM "+ ORDERITEMS_TABLE +
                " WHERE " + ORDERITEM_STATUS_COL + " = '"+s+"' AND "+ ORDERITEM_TYPE_COL + " = 'b'", null);
        return data;
    }

    public boolean insertEvent(int tableNum, String contactName, int phoneNum, int numOfPeople, String notes, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_NUMBER,tableNum);
        contentValues.put(COLUMN_CONTACT_NAME,contactName);
        contentValues.put(COLUMN_PHONENUM,phoneNum);
        contentValues.put(COLUMN_NUMOFPEOPLE,numOfPeople);
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

    public int getTablesCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLES_TABLE, null);
        return data.getCount();

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
            cnt = db.update(EVENT_TABLE, contentValues, EVENT_ID_COL + " = "+id, null);
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


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: to other tables
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ORDERITEMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+INCOME_STATISTICS_TABLE);

        onCreate(db);
    }

    public double getMonthIncome(int month){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ INCOME_STATISTICS_TABLE +
                " WHERE " + MONTH_COL + " = "+month, null);

        data.moveToFirst();
        double i = data.getDouble(data.getColumnIndex("income"));

        return i;
    }

    public int getReportsCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ INCOME_STATISTICS_TABLE, null);

        return data.getCount();

    }
    public int updateReport(int month, double addedValue){
        double current = getMonthIncome(month);

        SQLiteDatabase db = this.getWritableDatabase();

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(MONTH_COL,month);
            contentValues.put(INCOME_COL,current+addedValue);

            // update
            cnt = db.update(INCOME_STATISTICS_TABLE, contentValues,MONTH_COL+ " = ?", new String[]{Integer.toString(month)});
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;


    }

    public void initTables() {

        for(int i=1;i<19;i++){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_NUMBER_COL,i);
            if(i<12){
                contentValues.put(TABLE_LOCATION_COL,"inside");
            }else
                contentValues.put(TABLE_LOCATION_COL,"outside");

            db.insert(TABLES_TABLE, null, contentValues);
            db.close();
        }

    }
    public void initReportTable() {

        for(int i=1;i<13;i++){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MONTH_COL,i);
            contentValues.put(INCOME_COL,0);

            db.insert(INCOME_STATISTICS_TABLE, null, contentValues);
            db.close();
        }

    }

    public void initAdminPassword() {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ADMIN_PASS,"0000");

            db.insert(ADMIN_TABLE, null, contentValues);
            db.close();

    }

    public int getAdmin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ ADMIN_TABLE, null);

        return data.getCount();

    }

    public int updatePassword(String oldPass,String newPassword){

        SQLiteDatabase db = this.getWritableDatabase();

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(ADMIN_PASS,newPassword);

            // update
            cnt = db.update(ADMIN_TABLE, contentValues,ADMIN_PASS+ " = ?", new String[]{oldPass});
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }

    public String getAdminPassword(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ ADMIN_TABLE, null);
        data.moveToFirst();

        return data.getString(data.getColumnIndex("password"));
    }


    public Cursor getItemByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE + " WHERE name='"+name+"'" , null);
        return data;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ ITEM_TABLE , null);
        return data;
    }

    public int updateItemQuantity(String name,int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = getItemByName(name);
        x.moveToFirst();
        int currentAmount = x.getInt(x.getColumnIndex("amount"));
        int newAmount = currentAmount-quantity;
        if (newAmount<0){
            newAmount=0;
        }
        String query = "UPDATE "+ITEM_TABLE+" SET amount = "+newAmount+" WHERE name = '"+name+"';";
        Cursor data = db.rawQuery(query , null);
        return data.getCount();
    }
}
