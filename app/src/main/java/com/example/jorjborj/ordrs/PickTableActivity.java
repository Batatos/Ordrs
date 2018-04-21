package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahed on 4/8/2018.
 */

            public class PickTableActivity extends AppCompatActivity {

                private List<TableItem> tableItems = new ArrayList<>();

                @Override
                protected void onCreate(@Nullable Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.pick_table_layout);

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                    RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);
                    initializeData();

                    final TableCardAdapter adapter3 = new TableCardAdapter(this, tableItems);
                    lv.setLayoutManager(new GridLayoutManager(this,7));
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
