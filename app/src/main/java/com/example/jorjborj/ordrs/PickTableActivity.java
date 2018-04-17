package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ahed on 4/8/2018.
 */

            public class PickTableActivity extends AppCompatActivity {

                private ArrayList<TableItem> tableItems;


                @Override
                protected void onCreate(@Nullable Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.pick_table_layout);

                    RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);
//        final ArrayList<String> tables = new ArrayList<String>();
//        tables.add("1");
//        tables.add("2");
//        tables.add("3");
//        tables.add("4");
//        tables.add("5");
//        tables.add("6");
//        tables.add("7");

                    initializeData();

                    //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, tables);
                    final TableCardAdapter adapter3 = new TableCardAdapter(this, tableItems);


                    lv.setAdapter(adapter3);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getBaseContext(), MainActivity.class);
//                i.putExtra("table", tableItems.get(position).getTableNum());
//                startActivity(i);
//            }
//        });
                }

                public void initializeData() {
//                    for(int i=0 ; i<8; i++){
//                    TableItem item = new TableItem(null, i);
//                     tableItems.add(item);
//                    }

                    TableItem item = new TableItem(null, 1);
                    tableItems.add(item);
//                    TableItem item1 = new TableItem(null, 2);
//                    tableItems.add(item1);
//                    TableItem item2 = new TableItem(null, 3);
//                    tableItems.add(item2);
//                    TableItem item3 = new TableItem(null, 4);
//                    tableItems.add(item3);

                }
            }
