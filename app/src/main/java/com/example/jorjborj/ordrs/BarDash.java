package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jorjborj on 7/1/2018.
 */

public class BarDash extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<OrderItem> orderItems;
    int i;
    AnimationDrawable anim;
    LinearLayout container;
    OrdersAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_dash);

        db = new DatabaseHelper(getBaseContext());
        db.getWritableDatabase();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        container = (LinearLayout) findViewById(R.id.container);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);
        anim.start();

        orderItems = new ArrayList<>();
        i=0;
        Cursor c = db.getBarAllOrderItemsStatusFalse();

        try {
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                int id = c.getInt(c.getColumnIndex("ID"));
                String name = c.getString(c.getColumnIndex("name"));
                String type = c.getString(c.getColumnIndex("type"));
                int tablenum = c.getInt(c.getColumnIndex("tablenum"));
                int quantity = c.getInt(c.getColumnIndex("quantity"));
                Double price = c.getDouble(c.getColumnIndex("price")/c.getInt(c.getColumnIndex("quantity")));
                String notes = c.getString(c.getColumnIndex("notes"));
                String status = c.getString(c.getColumnIndex("status"));

                OrderItem oi = new OrderItem(id,name,quantity,price,type,notes,status,tablenum);

                orderItems.add(oi);

            }
        } catch (Throwable t) {
            t.printStackTrace();
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lv = (ListView) findViewById(R.id.barlv);
        adapter = new OrdersAdapter(this,R.layout.barlv_row,orderItems);
        lv.setAdapter(adapter);

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

    public class OrdersAdapter extends ArrayAdapter<OrderItem> {

        private final Context context;
        private final ArrayList<OrderItem> orderItems;
        private int ctr;
        private int resource;
        TextView orderid,tablenum,itemname,quantity,notes;
        ImageButton check;

        public OrdersAdapter(Context context, int resource, ArrayList<OrderItem> modelsArrayList) {
            super(context, resource, modelsArrayList);
            this.context = context;
            this.orderItems = modelsArrayList;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.kitchelv_row, parent, false);

            tablenum = (TextView)customView.findViewById(R.id.tablenum);
            itemname = (TextView)customView.findViewById(R.id.itemname);
            quantity = (TextView)customView.findViewById(R.id.quantity);
            check = (ImageButton)customView.findViewById(R.id.checkBtn);
            notes = (TextView)customView.findViewById(R.id.notes);

            tablenum.setText(this.orderItems.get(position).getTableNum()+"");
            itemname.setText(this.orderItems.get(position).getTitle()+"");
            quantity.setText(this.orderItems.get(position).getCounter()+"");
            notes.setText(this.orderItems.get(position).getNotes()+"");

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderItem oi = orderItems.get(position);
                    db.setOrderItemStatusTrue(oi);
                    adapter.notifyDataSetChanged();
                    Intent i = new Intent(BarDash.this,BarDash.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    Toast.makeText(context, "Order "+oi.getCounter()+"x "+oi.getTitle()+" on table "+oi.getTableNum()+" is ready!", Toast.LENGTH_SHORT).show();

                }
            });

            return customView;
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(BarDash.this,PickOptionActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(BarDash.this,PickOptionActivity.class);
        startActivity(i);
        finish();
        return true;
    }

}
