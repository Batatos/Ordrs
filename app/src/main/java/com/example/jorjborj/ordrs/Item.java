package com.example.jorjborj.ordrs;

import android.graphics.Bitmap;

/**
 * Created by jorjborj on 4/7/2018.
 */

public class Item {

        String name;
        int amount;
        String type;
        String category;
        Bitmap img;
        String supplier;
        String supplierNumber;
        double price;
        int quantity = 0; //order quantity
        int sold = 0;


    //constructor without supplier
    public Item(String name, double price, int amount, String type, String category, Bitmap img){
        this.name=name;
        this.amount=amount;
        this.category=category;
        this.type=type;
        this.price=price;
        this.img=img;

    }

    public Item(String name, int amount){
        this.name=name;
        this.amount=amount;
    }

    public Item(String name, int amount, double price){
        this.name=name;
        this.amount=amount;
        this.price=price;
    }

    public Item(String name){
        this.name=name;
    }

    //constructor with supplier
    public Item(String name, double price, int amount, String type,String category, Bitmap img, String supplier, String supplierNumber){
        this.name=name;
        this.amount=amount;
        this.type=type;
        this.img=img;
        this.category=category;
        this.supplier=supplier;
        this.supplierNumber=supplierNumber;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
