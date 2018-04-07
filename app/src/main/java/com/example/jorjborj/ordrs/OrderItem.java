package com.example.jorjborj.ordrs;

/**
 * Created by Ahed on 4/7/2018.
 */
public class OrderItem {



    private String title;
    private int counter;



    public OrderItem(String title) {
        this(title,0);
    }
    public OrderItem( String title, int counter) {
        super();
        this.title = title;
        this.counter = counter;
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




//gettters & setters...
}


