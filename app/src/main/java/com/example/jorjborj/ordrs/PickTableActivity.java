package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ahed on 4/8/2018.
 */

public class PickTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_table_layout);

        ListView lv = (ListView)findViewById(R.id.tables_lv);
        final ArrayList<String> tables = new ArrayList<String>();
        tables.add("1");
        tables.add("2");
        tables.add("3");
        tables.add("4");
        tables.add("5");
        tables.add("6");
        tables.add("7");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, tables);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                i.putExtra("table",tables.get(position).toString());
                startActivity(i);
            }
        });
    }
}
