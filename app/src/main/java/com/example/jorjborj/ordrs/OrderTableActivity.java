package com.example.jorjborj.ordrs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorjborj on 4/21/2018.
 */

public class OrderTableActivity extends AppCompatActivity {

    private List<TableItem> tableItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_table_layout);

        RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeData();

        final OrderTableCardViewAdapter adapter3 = new OrderTableCardViewAdapter(this, tableItems);
        lv.setLayoutManager(new GridLayoutManager(this,6));
        lv.setHasFixedSize(true);
        lv.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
    }

    public void initializeData() {
        for(int i=1 ; i<18; i++){
            TableItem item = new TableItem(null, ""+i+"");
            tableItems.add(item);
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}