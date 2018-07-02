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

        happyhour = (LinearLayout)findViewById(R.id.bar);
        anim = (AnimationDrawable) happyhour.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        initializeData();

        if(db.getReportsCount()==0){
            db.initReportTable();
        }

        if(db.getTablesCount()==0) {
            db.initTables();
        }
        if(db.getAdmin()==0){
            db.initAdminPassword();
        }

        db.close();
        //HAPPY HOUR!@#!@#!@#!@#!@#

        Button order = (Button)findViewById(R.id.order);
        Button table_order = (Button)findViewById(R.id.tableorder);
        Button supplies_management = (Button)findViewById(R.id.supplies);
        Button reportsBtn = (Button)findViewById(R.id.reportsBtn);

        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PickOptionActivity.this,Reports.class);
                startActivity(i);
                finish();
            }
        });

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)


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
                Intent i = new Intent(PickOptionActivity.this,KitchenDash.class);
                startActivity(i);
                break;
            case R.id.dashboard1:
                Intent i1 = new Intent(PickOptionActivity.this,BarDash.class);
                startActivity(i1);
                break;
            case R.id.events:
                Intent iStam = new Intent(PickOptionActivity.this,UpcomingEventsActivity.class);
                startActivity(iStam);
                //Toast.makeText(this, "Upcoming Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.passwordreset:
                Intent x = new Intent(PickOptionActivity.this,ResetPassword.class);
                startActivity(x);
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
                    db.getWritableDatabase();
                    if(password.getText().toString().equals(db.getAdminPassword())){
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
                    db.getWritableDatabase();
                    if(password.getText().toString().equals(db.getAdminPassword())){
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
    public void initializeData() {

        db.getWritableDatabase();

        if(db.getAllItems().getCount()<1){
            db.insertItem("Lemon Shrimps", 38.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_shrimps), "George", "0545983177",0);
            db.insertItem("Tangri Kebabs", 35.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.tangari_kebabs), "Supplier1", "0545983177",0);
            db.insertItem("Cheese Balls", 41.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.cheese_balls), "Supplier1", "0545983177",0);
            db.insertItem("Italian Cuisine", 28.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.italian_cuisine), "Supplier1", "0545983177",0);
            db.insertItem("Lobster Legs", 38.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.lobster_legs), "Supplier1", "0545983177",0);
            db.insertItem("Smoked Salamon", 31.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.smoked_salamon), "Supplier1", "0545983177",0);
            db.insertItem("Adamami", 34.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.adamami), "Supplier1", "0545983177",0);
            db.insertItem("Falafel", 35.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.falafel), "Supplier1", "0545983177",0);
            db.insertItem("Seafood Salad", 40.00, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.seafood_salad), "Supplier1", "0545983177",0);
            db.insertItem("Baked Potato", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.baked_potato), "Supplier1", "0545983177",0);
            db.insertItem("Baked Sweet Potato", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.baked_sweet_potato), "Supplier1", "0545983177",0);
            db.insertItem("Corn Soup", 29.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.corn_soup), "Supplier1", "0545983177",0);
            db.insertItem("Fish Soup", 34.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.fish_soup), "Supplier1", "0545983177",0);
            db.insertItem("French Fries", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.french_fries), "Supplier1", "0545983177",0);

            db.insertItem("Chicken Salad", 49.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.chicken_salad), "George", "0545983177",0);
            db.insertItem("Caesar Salad", 53.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.caesar_salad), "George", "0545983177",0);
            db.insertItem("Tuna Salad", 51.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.tuna_salad), "George", "0545983177",0);
            db.insertItem("Nazareth Breakfast", 65.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.nazareth_breakfast), "George", "0545983177",0);
            db.insertItem("Beef Fillet", 109.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.beef_fillet), "George", "0545983177",0);
            db.insertItem("English Breakfast", 54.50, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.english_breakfast), "George", "0545983177",0);
            db.insertItem("Italian Pasta", 67.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.italian_pasta), "George", "0545983177",0);
            db.insertItem("Cavatappi Pasta", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.cavatappi_pasta), "George", "0545983177",0);
            db.insertItem("Chicken Pie", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.chicken_pie), "George", "0545983177",0);
            db.insertItem("Fresco Shrimps", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.fresco_shrimps), "George", "0545983177",0);
            db.insertItem("Potato Tortilla", 57.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.potato_tortilla), "George", "0545983177",0);
            db.insertItem("Mac and Cheese", 53.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.mac_cheese), "George", "0545983177",0);
            db.insertItem("Sloppy Joe", 58.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.sloppy_joe), "George", "0545983177",0);
            db.insertItem("Cheesy Cabbage", 56.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.cabbage_cheese), "George", "0545983177",0);

            db.insertItem("Cola", 11.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.cola), "George", "0545983177",0);
            db.insertItem("Sprite", 11.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.sprite), "George", "0545983177",0);
            db.insertItem("Espresso", 8.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.espresso), "George", "0545983177",0);
            db.insertItem("Americano", 10.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.americano), "George", "0545983177",0);
            db.insertItem("Cappuccino", 14.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.cappuccino), "George", "0545983177",0);
            db.insertItem("Orange Juice", 15.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.orange_juice), "George", "0545983177",0);
            db.insertItem("Lemonade", 14.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.lemonade), "George", "0545983177",0);
            db.insertItem("Summer Smoothie", 19.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.summer_vibes), "George", "0545983177",0);
            db.insertItem("Fruits Smoothie", 16.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.colorfull_smoothie), "George", "0545983177",0);
            db.insertItem("Diaster Milkshake", 25.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.disaster_milkshake), "George", "0545983177",0);
            db.insertItem("Red Vanil Milkshake", 21.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.vanil_strawberry_milkshake), "George", "0545983177",0);
            db.insertItem("Choco Milkshake", 19.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.choco_milkshake), "George", "0545983177",0);

            db.insertItem("Chocolate Cake", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.chocolate_cake), "George", "0545983177",0);
            db.insertItem("Cheese Cake", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.cheese_cake), "George", "0545983177",0);
            db.insertItem("Truffle", 35.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.truffle), "George", "0545983177",0);
            db.insertItem("Apple Pie", 33.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.apple_pie), "George", "0545983177",0);
            db.insertItem("Chocolate Mousse", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.choco_mousse), "George", "0545983177",0);
            db.insertItem("Strawberry Cake", 45.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.straw_cake), "George", "0545983177",0);
            db.insertItem("Lemon Sorbet", 33.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_sorbet), "George", "0545983177",0);
            db.insertItem("Lemon Tart", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_tart), "George", "0545983177",0);
            db.insertItem("IceCream Cake", 45.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.iceream_cake), "George", "0545983177",0);
            db.insertItem("Guinness Cake", 34.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.guinness_cake), "George", "0545983177",0);
            db.insertItem("B Whoopies", 43.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.bannoffee_whoopies), "George", "0545983177",0);

            db.insertItem("Pina Colada", 35.20, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.pina_colada), "George", "0545983177",0);
            db.insertItem("Jin", 35.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.jin), "George", "0545983177",0);
            db.insertItem("Arak", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.arak), "George", "0545983177",0);
            db.insertItem("Carlsberg", 26.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.carlsberg), "George", "0545983177",0);
            db.insertItem("Heineken", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.heineken), "George", "0545983177",0);
            db.insertItem("Green Tuborg", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.green_tuborg), "George", "0545983177",0);
            db.insertItem("Red Tuborg", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.red_tuborg), "George", "0545983177",0);
            db.insertItem("Corona", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.corona), "George", "0545983177",0);
            db.insertItem("Red Label", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.red_label), "George", "0545983177",0);
            db.insertItem("Jameson", 25.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.jameson), "George", "0545983177",0);
            db.insertItem("Royal Chivas", 25.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.royal_chivas), "George", "0545983177",0);

        }

    }

}
