package com.example.jorjborj.ordrs;

/**
 * Created by Ahed on 4/7/2018.
 */
public class OrderItem {


    private String title;
    private int counter;
    double price;
    char type;
    String notes=null;


    public OrderItem( String title, int counter, double price, char type) {
        super();
        this.title = title;
        this.counter = counter;
        this.price=price;
        this.type=type;
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


