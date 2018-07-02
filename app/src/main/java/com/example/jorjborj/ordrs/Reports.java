package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jorjborj on 7/2/2018.
 */

public class Reports extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Button mostSold,monthlySales,minAmount;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mostSold = (Button)findViewById(R.id.mostsoldLayout);
        monthlySales = (Button)findViewById(R.id.monthlySalesLayout);
        minAmount = (Button)findViewById(R.id.minAmountLayout);


        mostSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reports.this,MostSoldReport.class);
                startActivity(i);
                finish();
            }
        });

        monthlySales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reports.this,MonthlyReport.class);
                startActivity(i);
                finish();
            }
        });

        minAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reports.this,MinAmountReport.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Reports.this,PickOptionActivity.class);
        startActivity(i);
        finish();
    }

    }
