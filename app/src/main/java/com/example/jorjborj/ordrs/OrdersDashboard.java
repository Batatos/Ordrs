package com.example.jorjborj.ordrs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorjborj on 5/1/2018.
 */

public class OrdersDashboard extends AppCompatActivity {

    ArrayList<OrderItem> list;
    LinearLayout container;
    AnimationDrawable anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_dashboard);

        container = (LinearLayout) findViewById(R.id.container);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = (RecyclerView) findViewById(R.id.dashboard);

        list = new ArrayList<>();
        initializeData();

        final DashboardAdapter adapter = new DashboardAdapter(this,list);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,3));

        rv.setAdapter(adapter);

    }

    private void initializeData() {

        OrderItem item = new OrderItem("Chicken Salad",2,50.90);
        OrderItem item1 = new OrderItem("Espresso",2,9.90);
        OrderItem item2 = new OrderItem("Cheese Cake",1,42.90);
        OrderItem item3 = new OrderItem("Chocolate Cake",1,42.90);
        OrderItem item4 = new OrderItem("Chocolate Cake",1,42.90);
        OrderItem item5 = new OrderItem("Chocolate Cake",1,42.90);

        list.add(item);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);

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
}

class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder>{

    private Context mCtx;
    private List<OrderItem> menu;

    public DashboardAdapter(Context mCtx, List<OrderItem> menu) {
        this.mCtx = mCtx;
        this.menu = menu;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.order_list_item_card, null);

        DashboardHolder cardholder = new DashboardHolder(view);
        return cardholder;


    }


    @Override
    public void onBindViewHolder(final DashboardHolder holder, final int position) {
        OrderItem item = menu.get(position);
        holder.tablenum.setText(Integer.toString(position+1)); //should be table number.

        List<String> order1 = new ArrayList<>();
        List<String> order2 = new ArrayList<>();
        List<String> order3 = new ArrayList<>();
        List<String> order4 = new ArrayList<>();
        List<String> order5 = new ArrayList<>();
        List<String> order6 = new ArrayList<>();

        order1.add("Caesar Salad");
        order1.add("Hummus and Beef");
        order1.add("Chicken Breast");
        order1.add("Cordon Blu");
        order1.add("Apple Pie");

        order2.add("Chicken Salad");
        order2.add("Beef Fillet");
        order2.add("Nazareth Breakfast");
        order2.add("Margarita Pizza");
        order2.add("Chicken Breast");

        order3.add("Margarita Pizza");
        order3.add("Beef Fillet");

        order4.add("Coca-Cola");
        order4.add("Orange Juice");
        order4.add("Chocolate Cake");
        order4.add("Apple Pie");

        order5.add("Cordon Blu");
        order5.add("Chicken Salad");

        order6.add("Orange Juice");
        order6.add("Whiskey");
        order6.add("Truffle");

        if(position==0)
        holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order1));
        if(position==1)
            holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order2));
        if(position==2)
            holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order3));
        if(position==3)
            holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order4));
        if(position==4)
            holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order5));
        if(position==5)
            holder.lv.setAdapter(new ArrayAdapter<String>(mCtx,android.R.layout.simple_list_item_1,order6));

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, Integer.toString(position+1) , Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }



    static class DashboardHolder extends RecyclerView.ViewHolder{

        TextView tablenum;
        ListView lv;
        protected View mRootView;

        public DashboardHolder(final View itemView) {
            super(itemView);

            lv = (ListView)itemView.findViewById(R.id.orderitems);
            tablenum = (TextView)itemView.findViewById(R.id.tblnumber);

            lv.setOnTouchListener(new ListView.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
            break;
        }

        // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
    }
});

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Toast.makeText(itemView.getContext(),lv.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
                });


        mRootView = itemView;

        }

    }
}

