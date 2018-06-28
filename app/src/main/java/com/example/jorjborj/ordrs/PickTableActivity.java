package com.example.jorjborj.ordrs;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
                    getSupportActionBar().setTitle("Choose Table");

                    //RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);
                    initializeData();

//                    final TableCardAdapter adapter3 = new TableCardAdapter(this, tableItems);
//                    lv.setLayoutManager(new GridLayoutManager(this,7));
//                    lv.setHasFixedSize(true);
//                    lv.setAdapter(adapter3);
//                    adapter3.notifyDataSetChanged();


                    ConstraintLayout table1 = (ConstraintLayout)findViewById(R.id.table1);
                    ConstraintLayout table2 = (ConstraintLayout)findViewById(R.id.table2);
                    ConstraintLayout table3 = (ConstraintLayout)findViewById(R.id.table3);
                    ConstraintLayout table4 = (ConstraintLayout)findViewById(R.id.table4);
                    ConstraintLayout table5 = (ConstraintLayout)findViewById(R.id.table5);
                    ConstraintLayout table6 = (ConstraintLayout)findViewById(R.id.table6);
                    ConstraintLayout table7 = (ConstraintLayout)findViewById(R.id.table7);
                    ConstraintLayout table8 = (ConstraintLayout)findViewById(R.id.table8);
                    ConstraintLayout table9 = (ConstraintLayout)findViewById(R.id.table9);
                    ConstraintLayout table10 = (ConstraintLayout)findViewById(R.id.table10);
                    ConstraintLayout table11 = (ConstraintLayout)findViewById(R.id.table11);
                    ConstraintLayout table12 = (ConstraintLayout)findViewById(R.id.table12);
                    ConstraintLayout table13 = (ConstraintLayout)findViewById(R.id.table13);
                    ConstraintLayout table14 = (ConstraintLayout)findViewById(R.id.table14);
                    ConstraintLayout table15 = (ConstraintLayout)findViewById(R.id.table15);
                    ConstraintLayout table16 = (ConstraintLayout)findViewById(R.id.table16);
                    ConstraintLayout table17 = (ConstraintLayout)findViewById(R.id.table17);
                    ConstraintLayout table18 = (ConstraintLayout)findViewById(R.id.table18);

                    table1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(0).getTableNum());
                            startActivity(i);
                        }
                    });
                    table2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(1).getTableNum());
                            startActivity(i);
                        }
                    });
                    table3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(2).getTableNum());
                            startActivity(i);
                        }
                    });
                    table4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(3).getTableNum());
                            startActivity(i);
                        }
                    });
                    table5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(4).getTableNum());
                            startActivity(i);
                        }
                    });
                    table6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(5).getTableNum());
                            startActivity(i);
                        }
                    });
                    table7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(6).getTableNum());
                            startActivity(i);
                        }
                    });
                    table8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(7).getTableNum());
                            startActivity(i);
                        }
                    });
                    table9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(8).getTableNum());
                            startActivity(i);
                        }
                    });
                    table10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(9).getTableNum());
                            startActivity(i);
                        }
                    });
                    table11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(10).getTableNum());
                            startActivity(i);
                        }
                    });
                    table12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(11).getTableNum());
                            startActivity(i);
                        }
                    });
                    table13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(12).getTableNum());
                            startActivity(i);
                        }
                    });
                    table14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(13).getTableNum());
                            startActivity(i);
                        }
                    });
                    table15.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(14).getTableNum());
                            startActivity(i);
                        }
                    });
                    table16.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(15).getTableNum());
                            startActivity(i);
                        }
                    });
                    table17.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(16).getTableNum());
                            startActivity(i);
                        }
                    });
                    table18.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(17).getTableNum());
                            startActivity(i);
                        }
                    });


                }

                public void initializeData() {
                    for(int i=1 ; i<19; i++){
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
