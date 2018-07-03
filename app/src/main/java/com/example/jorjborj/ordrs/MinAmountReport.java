package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jorjborj on 7/2/2018.
 */

public class MinAmountReport extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_amount_report);

        ListView lv = (ListView) findViewById(R.id.lv);
        ArrayList<Item> items = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Minimum Amount Report");

        DatabaseHelper db = new DatabaseHelper(this);
        db.getWritableDatabase();

        Cursor c = db.getAllItemsOrderedByMinAmount();

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                Item i = new Item(c.getString(c.getColumnIndex("name")),c.getInt(c.getColumnIndex("amount")));
                items.add(i);
            }

            minAdapter adapter = new minAdapter(this, R.layout.min_amount_row, items);
            lv.setAdapter(adapter);

        }
    }

    public class minAdapter extends ArrayAdapter<Item> {

        private final Context context;
        private final ArrayList<Item> modelsArrayList;
        private int ctr;
        private int resource;

        public minAdapter(Context context, int resource, ArrayList<Item> modelsArrayList) {
            super(context, resource, modelsArrayList);
            this.context = context;
            this.modelsArrayList = modelsArrayList;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.min_amount_row, parent, false);

            TextView itemname,amount;
            LinearLayout bg;

            bg = (LinearLayout)customView.findViewById(R.id.bg);

            itemname = (TextView)customView.findViewById(R.id.itemname);
            amount = (TextView)customView.findViewById(R.id.itemamount);


            itemname.setText(modelsArrayList.get(position).getName()+"");
            amount.setText(modelsArrayList.get(position).getAmount()+"");

            int a = Integer.parseInt(amount.getText().toString());

            if(a>=0 && a <=4 ){
                bg.setBackgroundResource(R.mipmap.redrow);
            }

            if(a>4 && a<10) {
                bg.setBackgroundResource(R.mipmap.yellowrow);
            }

            if(a>=10){
                bg.setBackgroundResource(R.mipmap.greenrow);
            }

            return customView;
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(MinAmountReport.this,Reports.class);
        startActivity(i);
        finish();
    }

}
