package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorjborj on 5/5/2018.
 */

public class PaymentProcess extends AppCompatActivity implements CashFragment.OnFragmentInteractionListener, CardFragment.OnFragmentInteractionListener {

    ArrayList<OrderItem> orderItems;
    OrderItemAdapter orderAdapter;
    DatabaseHelper db;
    int orderId;
    int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_process);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        ListView orderslv = (ListView)findViewById(R.id.lv);
        TextView price, ahoz, total;
        final ViewPager view_pager = (ViewPager) findViewById(R.id.view_pager);
        final TabLayout tablayout = (TabLayout) findViewById(R.id.tabs);
        tablayout.addTab(tablayout.newTab().setText("Cash"), true);
        tablayout.addTab(tablayout.newTab().setText("Credit Card"));
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());

        price = (TextView)findViewById(R.id.price);
        ahoz = (TextView)findViewById(R.id.ahoz);
        total = (TextView)findViewById(R.id.total);

        Double totald = Double.parseDouble(getIntent().getExtras().get("total").toString());
        Double priced = Double.parseDouble(getIntent().getExtras().get("price").toString());
        a = Integer.parseInt(getIntent().getExtras().get("ahoz").toString());

        price.setText(new DecimalFormat("##.##").format(priced));
        ahoz.setText(getIntent().getExtras().get("ahoz").toString());
        total.setText(new DecimalFormat("##.##").format(totald));

        orderItems = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderlv");
        orderAdapter = new OrderItemAdapter(this, R.layout.payment_lv_row, orderItems);
        orderId = orderItems.get(0).getOrderId();

        orderslv.setAdapter(orderAdapter);

        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setScrollPosition(position, 0, true);
                tablayout.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                tablayout.setSelected(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

        double sumPrice = 0.0;
        private final Context context;
        private final ArrayList<OrderItem> modelsArrayList;
        private int ctr;
        private int resource;

        public OrderItemAdapter(Context context, int resource, ArrayList<OrderItem> modelsArrayList) {
            super(context, resource, modelsArrayList);


            this.context = context;
            this.modelsArrayList = modelsArrayList;
            this.resource = resource;


            //TODO: bind modelsArrayList to database.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.payment_lv_row, parent, false);
            //convertView = inflater.inflate(resource,parent,false);
            // 2. Get rowView from inflater

            // 3. Get icon,title & counter views from the rowView
            //     ImageView imgView = (ImageView) customView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) customView.findViewById(R.id.itemname);
            TextView counterView = (TextView) customView.findViewById(R.id.item_counter);


            titleView.setText(modelsArrayList.get(position).getTitle());
            //Toast.makeText(context, Integer.toString(modelsArrayList.get(position).getCounter()), Toast.LENGTH_SHORT).show();
            counterView.setText(Integer.toString(modelsArrayList.get(position).getCounter()));


            TextView sum = (TextView) customView.findViewById(R.id.sum);

            double suma = modelsArrayList.get(position).getPrice() * ((double) modelsArrayList.get(position).getCounter());
            double sumb = Double.parseDouble(new DecimalFormat("##.##").format(suma));

            sum.setText(Double.toString(sumb));


            // 5. retrn rowView
            return customView;

        }

    }
}


