package com.example.jorjborj.ordrs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OrderTableMain extends AppCompatActivity {

    String tablenum = "waiting for data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table_main);


        tablenum = getIntent().getExtras().get("table_num").toString();
        getSupportActionBar().setTitle("Order for table #"+tablenum);
    }
}
