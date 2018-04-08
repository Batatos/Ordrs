package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PickOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_option);

        Button order = (Button)findViewById(R.id.order);
        Button table_order = (Button)findViewById(R.id.tableorder);
        Button supplies_management = (Button)findViewById(R.id.supplies);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),PickTableActivity.class);
                startActivity(i);
            }
        });

        table_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PickOptionActivity.this, "Under construction", Toast.LENGTH_SHORT).show();
                //Intent i1= new Intent(this,TableOrder.class);
                //startActivity(i1);
            }
        });

        supplies_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PickOptionActivity.this, "Under construction", Toast.LENGTH_SHORT).show();
                //Intent i2 = new Intent(this,SuppliesManagement.class);
               // startActivity(i2);
            }
        });

    }
}
