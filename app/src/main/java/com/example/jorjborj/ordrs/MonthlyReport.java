package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.jorjborj.ordrs.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jorjborj on 5/2/2018.
 */

public class MonthlyReport extends AppCompatActivity {

    private BarChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monthly Sales Report");

        mChart = (BarChart)findViewById(R.id.monthly_sales);
        mChart.getDescription().setEnabled(false);
        setData(12);

        //mChart.setFitBars(true);
        mChart.disableScroll();
        mChart.setDoubleTapToZoomEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MonthlyReport.this,PickOptionActivity.class);
        startActivity(i);
        finish();
    }

    private void setData(int count) {

        ArrayList<BarEntry> yVals = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(getBaseContext());
        db.getWritableDatabase();

        for(int i=1;i<13;i++){
            values.add((int)db.getMonthIncome(i));
        }
        db.close();

        for(int i=0;i<count;i++){
            float value;
            Calendar c = Calendar.getInstance();
            if(i<c.get(Calendar.MONTH)+1) {
                value = values.get(i);
            }else{
                value = (float) (0);
            }

            BarEntry bar = new BarEntry(i+1,(int)value);
            yVals.add(bar);
        }

        final BarDataSet set = new BarDataSet(yVals,"Months");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        mChart.animate();
        set.setValueTextSize(15);
        mChart.getXAxis().setTextSize(11);
        mChart.getAxisLeft().setTextSize(13);
        mChart.getAxisRight().setTextSize(13);
        BarData data = new BarData(set);
        mChart.setData(data);
        mChart.invalidate();

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(MonthlyReport.this,PickOptionActivity.class);
        startActivity(i);
        finish();
        return true;
    }
}
