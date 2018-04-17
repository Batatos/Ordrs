package com.example.jorjborj.ordrs;

import android.graphics.Bitmap;

/**
 * Created by Ahed on 4/17/2018.
 */

public class TableItem {
    Bitmap img;
    int tableNum;

    public TableItem(Bitmap img, int tableNum) {
        this.img = img;
        this.tableNum = tableNum;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}
