package com.example.jorjborj.ordrs;

import android.graphics.Bitmap;

/**
 * Created by Ahed on 4/17/2018.
 */

public class TableItem {
    Bitmap img;
    String tableNum;

    public TableItem(Bitmap img, String tableNum) {
        this.img = img;
        this.tableNum = tableNum;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }
}
