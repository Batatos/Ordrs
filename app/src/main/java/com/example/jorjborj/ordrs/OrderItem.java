package com.example.jorjborj.ordrs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ahed on 4/7/2018.
 */
public class OrderItem implements Serializable{


    private String title;
    private int counter;
    double price;
    String type;
    String notes=null;
    int orderId;
    String status = "false";
    int tableNum;

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderItem() {
        super();
    }

    public OrderItem(String title, int counter, double price, String type, String notes) {
        super();
        this.title = title;
        this.counter = counter;
        this.price=price;
        this.type=type;
        this.notes=notes;
    }
    public OrderItem(int orderId, String title, int counter, double price, String type, String notes,String status,int tableNum) {
        super();
        this.status=status;
        this.orderId=orderId;
        this.tableNum=tableNum;
        this.title = title;
        this.counter = counter;
        this.price=price;
        this.type=type;
        this.notes=notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getPrice() {
        return price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//gettters & setters...
}


