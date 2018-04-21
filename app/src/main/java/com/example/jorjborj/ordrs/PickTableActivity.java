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

                    RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);

                    initializeData();

                    //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, tables);
                    final TableCardAdapter adapter3 = new TableCardAdapter(this, tableItems);

//                    LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());
//                    lv.setLayoutManager(llm);
                    lv.setLayoutManager(new GridLayoutManager(this,7));
                    lv.setHasFixedSize(true);
                    lv.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();

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
                    for(int i=1 ; i<18; i++){
                     TableItem item = new TableItem(null, ""+i+"");
                     tableItems.add(item);
                    }



                }
            }
