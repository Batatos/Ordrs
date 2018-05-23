package com.example.jorjborj.ordrs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jorjborj on 5/1/2018.
 */

public class BarDashboard extends AppCompatActivity {

    ArrayList<OrderItem> list;
    LinearLayout container;
    AnimationDrawable anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_dashboard);

        container = (LinearLayout) findViewById(R.id.container);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bar Dashboard");
        RecyclerView rv = (RecyclerView) findViewById(R.id.dashboard);

        list = new ArrayList<>();
        initializeData();

        final BarDashboardAdapter adapter = new BarDashboardAdapter(this,list);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,3));

        rv.setAdapter(adapter);

    }

    private void initializeData() {

        OrderItem item = new OrderItem("Chicken Salad",2,50.90,'k');
        OrderItem item2 = new OrderItem("Cheese Cake",1,42.90,'b');
        OrderItem item3 = new OrderItem("Chocolate Cake",1,42.90,'b');
        OrderItem item4 = new OrderItem("Chocolate Cake",1,42.90,'b');
        OrderItem item5 = new OrderItem("Chocolate Cake",1,42.90,'b');

        list.add(item);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

class BarDashboardAdapter extends RecyclerView.Adapter<BarDashboardAdapter.BarDashboardHolder>{

    private Context mCtx;
    private List<OrderItem> menu;

    public BarDashboardAdapter(Context mCtx, List<OrderItem> menu) {
        this.mCtx = mCtx;
        this.menu = menu;
    }

    @Override
    public BarDashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.order_list_item_card, null);

        BarDashboardHolder cardholder = new BarDashboardHolder(view);
        return cardholder;


    }


    @Override
    public void onBindViewHolder(final BarDashboardHolder holder, final int position) {
        OrderItem item = menu.get(position);
        holder.tablenum.setText(Integer.toString(position+1)); //should be table number.

        List<String> order1 = new ArrayList<>();
        List<String> order2 = new ArrayList<>();
        List<String> order3 = new ArrayList<>();
        List<String> order4 = new ArrayList<>();
        List<String> order5 = new ArrayList<>();
        List<String> order6 = new ArrayList<>();

        order1.add("Coca-Cola");
        order1.add("Sprite");
        order1.add("Espresso");
        order1.add("Orange Juice");
        order1.add("Cappuccino");

        order2.add("Sprite");
        order2.add("Cheese Cake");
        order2.add("Espresso");

        order3.add("Espresso");
        order3.add("Soda");
        order3.add("Water");

        order4.add("Chocolate Cake");
        order4.add("Truffle");
        order4.add("Cappuccino");

        order5.add("Espresso");
        order5.add("Water");

        order6.add("Lemonade");
        order6.add("Orange Juice");
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
                //Toast.makeText(mCtx, Integer.toString(position+1) , Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }



    static class BarDashboardHolder extends RecyclerView.ViewHolder {

        TextView tablenum, timer;
        ListView lv;
        protected View mRootView;
        Button done, dismiss;
        public int seconds = 59;
        public int minutes = 9;


        public BarDashboardHolder(final View itemView) {
            super(itemView);

            lv = (ListView) itemView.findViewById(R.id.orderitems);
            tablenum = (TextView) itemView.findViewById(R.id.tblnumber);
            timer = (TextView) itemView.findViewById(R.id.timer);
            done = (Button) itemView.findViewById(R.id.done);
            dismiss = (Button) itemView.findViewById(R.id.dismiss);

            new CountDownTimer(6000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timer.setText(minutes+":"+ checkDigit(seconds));
                    seconds--;

                    if(minutes==8 && seconds==58){
                        timer.setTextColor(Color.RED);
                    }
                }

                public String checkDigit(int number) {
                    if (number==0) {
                        minutes--;
                        seconds = 60;
                        return "00";
                    }
                    if(number<=10){
                        String s = "0"+--number;
                        return s;
                    }
                    return Integer.toString(--number);
                }


                public void onFinish() {
                    timer.setText("HURRY UP!");
                }

            }.start();



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
                    //Toast.makeText(itemView.getContext(),lv.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "DONE", Toast.LENGTH_SHORT).show();
                }
            });
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "DISMISS", Toast.LENGTH_SHORT).show();
                }
            });


            mRootView = itemView;

        }


    }

}

