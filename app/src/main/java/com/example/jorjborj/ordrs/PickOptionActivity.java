package com.example.jorjborj.ordrs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class PickOptionActivity extends AppCompatActivity {

//    LinearLayout container;
        AnimationDrawable anim;
        LinearLayout happyhour;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_option);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        db.initTables();

        //HAPPY HOUR!@#!@#!@#!@#!@#
        happyhour = (LinearLayout)findViewById(R.id.happyHourBar);

        anim = (AnimationDrawable) happyhour.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);

        Button order = (Button)findViewById(R.id.order);
        Button table_order = (Button)findViewById(R.id.tableorder);
        Button supplies_management = (Button)findViewById(R.id.supplies);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)

        if(currentHour>14 && currentHour<17){
            happyhour.setVisibility(LinearLayout.GONE);
        }else{
        }


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
                //Toast.makeText(PickOptionActivity.this, "Under construction", Toast.LENGTH_SHORT).show();
                Intent i1= new Intent(getBaseContext(),OrderTableActivity.class);
                startActivity(i1);
            }
        });

        supplies_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLoginDialog dialog = new AdminLoginDialog(PickOptionActivity.this);
                dialog.setTitle("Admin Area");
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                Intent i = new Intent(PickOptionActivity.this,OrdersDashboard.class);
                startActivity(i);
                break;
            case R.id.dashboard1:
                Intent i1 = new Intent(PickOptionActivity.this,BarDashboard.class);
                startActivity(i1);

                break;
            case R.id.events:
                Intent iStam = new Intent(PickOptionActivity.this,UpcomingEventsActivity.class);
                startActivity(iStam);
                //Toast.makeText(this, "Upcoming Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reports:

                ReportsAdminDialog rad = new ReportsAdminDialog(PickOptionActivity.this);
                rad.show();
                break;

        }
        return true;
    }

    public class AdminLoginDialog extends Dialog {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.admin_login);
            final EditText password = (EditText)findViewById(R.id.inputPassword);
            Button submit = (Button)findViewById(R.id.submit);
            Button cancel = (Button)findViewById(R.id.cancel);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(password.getText().toString().equals("0000")){
                        dismiss();
                    Intent i = new Intent(PickOptionActivity.this, SuppliesMgmt.class);
                    startActivity(i);
                        ProgressDialog progress;
                        progress = new ProgressDialog(getContext());
                        progress.setTitle("Loading Data from database");
                        progress.setMessage("Please wait...");
                        progress.setCancelable(false);
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.show();
                    }else{
                        Toast.makeText(PickOptionActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }


        public AdminLoginDialog(@NonNull Context context) {
            super(context);
        }

        public AdminLoginDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
        }

        protected AdminLoginDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    public class ReportsAdminDialog extends Dialog {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.admin_login);
            final EditText password = (EditText)findViewById(R.id.inputPassword);
            Button submit = (Button)findViewById(R.id.submit);
            Button cancel = (Button)findViewById(R.id.cancel);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(password.getText().toString().equals("0000")){
                        dismiss();
                        Intent i4 = new Intent(PickOptionActivity.this,MonthlyReport.class);
                        startActivity(i4);
                        finish();
                    }else{
                        Toast.makeText(PickOptionActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }


        public ReportsAdminDialog(@NonNull Context context) {
            super(context);
        }

        public ReportsAdminDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
        }

        protected ReportsAdminDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }
    }

}
