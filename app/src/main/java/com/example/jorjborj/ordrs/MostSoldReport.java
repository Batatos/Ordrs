package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jorjborj on 7/2/2018.
 */

public class MostSoldReport extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.most_sold_report);

        ListView lv = (ListView) findViewById(R.id.lv);
        ArrayList<Item> items = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Most Sold Items Report");

        DatabaseHelper db = new DatabaseHelper(this);
        db.getWritableDatabase();

        Cursor c = db.getAllItemsOrderedByMaxSold();

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                Item i = new Item(c.getString(c.getColumnIndex("name")), c.getInt(c.getColumnIndex("amount")), c.getDouble(c.getColumnIndex("price")));
                items.add(i);
            }

            minAdapter adapter = new minAdapter(this, R.layout.most_sold_row, items);
            lv.setAdapter(adapter);

        }
    }

    class minAdapter extends ArrayAdapter<Item> {

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
            View customView = inflater.inflate(R.layout.most_sold_row, parent, false);

            TextView itemname, amount, totalPrice;
            LinearLayout bg;

            itemname = (TextView) customView.findViewById(R.id.itemname);
            amount = (TextView) customView.findViewById(R.id.itemamount);
            totalPrice = (TextView) customView.findViewById(R.id.itemtotalprice);
            bg = (LinearLayout)customView.findViewById(R.id.bg);

            DatabaseHelper db = new DatabaseHelper(getBaseContext());
            db.getWritableDatabase();
            Cursor c = db.getItemByName(modelsArrayList.get(position).getName()+"");
            c.moveToFirst();
            int y = c.getInt(c.getColumnIndex("sold"));
            double x = y * modelsArrayList.get(position).getPrice();
            itemname.setText(modelsArrayList.get(position).getName() + "");
            amount.setText(y + "");
            totalPrice.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(x))));

            if(y>=0 && y <=2 ){
                bg.setBackgroundResource(R.mipmap.redrow);
            }

            if(y>2 && y<6) {
                bg.setBackgroundResource(R.mipmap.yellowrow);
            }

            if(y>=6){
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
        Intent i = new Intent(MostSoldReport.this,Reports.class);
        startActivity(i);
        finish();
    }

}
