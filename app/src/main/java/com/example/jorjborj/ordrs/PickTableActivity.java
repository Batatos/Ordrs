package com.example.jorjborj.ordrs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * Created by Ahed on 4/8/2018.
 */

            public class PickTableActivity extends AppCompatActivity {

                private List<TableItem> tableItems = new ArrayList<>();
                ProgressDialog progress;

    @Override
                protected void onCreate(@Nullable Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.pick_table_layout);

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle("Choose Table");

                    //RecyclerView lv = (RecyclerView) findViewById(R.id.tables_lv);
                    initializeData();

                    final DatabaseHelper db = new DatabaseHelper(this);
                    db.getWritableDatabase();

                    final ConstraintLayout table1 = (ConstraintLayout)findViewById(R.id.table1);
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
                    ImageView icon1 = (ImageView)findViewById(R.id.icon1);
                    ImageView icon2 = (ImageView)findViewById(R.id.icon2);
                    ImageView icon3 = (ImageView)findViewById(R.id.icon3);
                    ImageView icon4 = (ImageView)findViewById(R.id.icon4);
                    ImageView icon5 = (ImageView)findViewById(R.id.icon5);
                    ImageView icon6 = (ImageView)findViewById(R.id.icon6);
                    ImageView icon7 = (ImageView)findViewById(R.id.icon7);
                    ImageView icon8 = (ImageView)findViewById(R.id.icon8);
                    ImageView icon9 = (ImageView)findViewById(R.id.icon9);
                    ImageView icon10 = (ImageView)findViewById(R.id.icon10);
                    ImageView icon11 = (ImageView)findViewById(R.id.icon11);
                    ImageView icon12 = (ImageView)findViewById(R.id.icon12);
                    ImageView icon13 = (ImageView)findViewById(R.id.icon13);
                    ImageView icon14 = (ImageView)findViewById(R.id.icon14);
                    ImageView icon15 = (ImageView)findViewById(R.id.icon15);
                    ImageView icon16 = (ImageView)findViewById(R.id.icon16);
                    ImageView icon17 = (ImageView)findViewById(R.id.icon17);
                    ImageView icon18 = (ImageView)findViewById(R.id.icon18);

                    if(db.getOrderByTableNum(1).getCount()>0)
                        icon1.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(2).getCount()>0)
                        icon2.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(3).getCount()>0)
                        icon3.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(4).getCount()>0)
                        icon4.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(5).getCount()>0)
                        icon5.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(6).getCount()>0)
                        icon6.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(7).getCount()>0)
                        icon7.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(8).getCount()>0)
                        icon8.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(9).getCount()>0)
                        icon9.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(10).getCount()>0)
                        icon10.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(11).getCount()>0)
                        icon11.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(12).getCount()>0)
                        icon12.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(13).getCount()>0)
                        icon13.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(14).getCount()>0)
                        icon14.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(15).getCount()>0)
                        icon15.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(16).getCount()>0)
                        icon16.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(17).getCount()>0)
                        icon17.setVisibility(View.VISIBLE);
                    if(db.getOrderByTableNum(18).getCount()>0)
                        icon18.setVisibility(View.VISIBLE);

                    table1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(0).getTableNum());
                            startProgressBar();
                            startActivity(i);

                        }
                    });
                    table2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(1).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(2).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(3).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(4).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(5).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(6).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(7).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(8).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(9).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(10).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(11).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(12).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(13).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table15.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(14).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table16.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(15).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table17.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(16).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });
                    table18.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PickTableActivity.this, MainActivity.class);
                            i.putExtra("table_num", tableItems.get(17).getTableNum());
                            startProgressBar();
                            startActivity(i);
                        }
                    });


                }

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            progress.incrementProgressBy(20);
        }
    };

    private void startProgressBar() {
        progress = new ProgressDialog(PickTableActivity.this);
        progress.setTitle("Loading items from Database");
        progress.setMessage("Please wait...");
        progress.setMax(100);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (progress.getProgress() <= progress.getMax()) {
                        Thread.sleep(300);
                        myHandler.sendMessage(myHandler.obtainMessage());
                        if(progress.getProgress() == progress.getMax()){
                            progress.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    @Override
    public void onBackPressed(){
        finish();
    }
}
