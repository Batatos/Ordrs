package com.example.jorjborj.ordrs;

import java.util.ArrayList;

/**
 * Created by jorjborj on 6/29/2018.
 */

public class Order {

    int id,tableNum;
    ArrayList<Item> orderItems;

    public Order(int id, int tableNum, ArrayList<Item> orderItems){

        this.id=id;
        this.tableNum=tableNum;
        this.orderItems=orderItems;

    }


}
