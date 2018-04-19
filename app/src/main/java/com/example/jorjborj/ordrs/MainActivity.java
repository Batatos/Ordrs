package com.example.jorjborj.ordrs;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by jorjborj on 4/2/2018.
 */

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> foodmenu = new ArrayList<Item>();
    ArrayList<Item> drinksmenu = new ArrayList<Item>();
    ArrayList<Item> dessertsmenu = new ArrayList<Item>();
    ArrayList<Item> alcoholmenu = new ArrayList<Item>();
    ArrayAdapter<String> adapter4 = null;
    ArrayList<String> orders = null;
    ArrayList<OrderItem> orderItems = null;
    ArrayAdapter orderAdapter = null;
    double sumPrice = 0.0;
    String tablenum = "waiting for data";
    TextView discounttext,totalprice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        discounttext = (TextView)findViewById(R.id.discountAhoz);
        totalprice = (TextView)findViewById(R.id.totalPrice);


        final BottomNavigationView nav = (BottomNavigationView)findViewById(R.id.navbar);
        disableShiftMode(nav);

        tablenum = getIntent().getExtras().get("table_num").toString();
        getSupportActionBar().setTitle("Order for table #"+tablenum);

        // Large screen, LISTVIEW and adapters
        final View mainscreen = (View)findViewById(R.id.largeScreen);
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        final ListView counterlv = (ListView)findViewById(R.id.counterlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,5));
        //TextView price = (TextView) findViewById(R.id.sumPrice);
        initializeData();


        Button sendBtn = (Button)findViewById(R.id.sendBtn);
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        Button payBtn = (Button)findViewById(R.id.payBtn);
        final Button discountBtn = (Button)findViewById(R.id.discountBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = "";
                for (int x=0;x<orderItems.size();x++){
                    output+=orderItems.get(x).getTitle();
                    output+=" x";
                    output+=orderItems.get(x).getCounter();

                    if(x+1==orderItems.size()){
                        output += ".";
                    }else {
                        output += ", ";
                    }
                }
                Toast.makeText(MainActivity.this,output, Toast.LENGTH_SHORT).show();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),PickOptionActivity.class);
                startActivity(i);
                finish();
            }
        });
        
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Todo Dialog", Toast.LENGTH_SHORT).show();
            }
        });

        discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Todo Dialog", Toast.LENGTH_SHORT).show();

                DiscountDialog dg = new DiscountDialog(MainActivity.this);


                dg.show();

            }
        });

        orders = new ArrayList<String>();
        adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, orders);


        orderItems = new ArrayList<>();
        orderAdapter = new OrderItemAdapter(this,R.layout.counterlistitem,orderItems);


        final CardviewAdapter adapter = new CardviewAdapter(this,foodmenu);
        final CardviewAdapter adapter1 = new CardviewAdapter(this,drinksmenu);
        final CardviewAdapter adapter2 = new CardviewAdapter(this,dessertsmenu);
        final CardviewAdapter adapter3 = new CardviewAdapter(this,alcoholmenu);


        rv.setAdapter(adapter);
        counterlv.setAdapter(orderAdapter);
        orderAdapter.setNotifyOnChange(true);


        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                switch (item.getItemId()){
                    case R.id.food:
                        rv.setAdapter(adapter);
                        break;
                    case R.id.alcohol:
                        rv.setAdapter(adapter3);
                        break;
                    case R.id.drinks:
                        rv.setAdapter(adapter1);
                        break;
                    case R.id.desserts:
                        rv.setAdapter(adapter2);
                        break;
                }
                return false;
            }
        });

    }



    /**
     *     Disable bottomnavbar animation function
     */
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }


    //Initialize dummy data function

    public void initializeData (){

        Item item = new Item("Chicken Salad",49.90,10,'f',null);
        Item item1 = new Item("Nazareth Breakfast",65.90,10,'f',null);
        Item item2 = new Item("Beef Fillet", 109.90, 10,'f',null);

        foodmenu.add(item);
        foodmenu.add(item1);
        foodmenu.add(item2);

        Item item3 = new Item("Cola",11.90,10,'f',null);
        Item item4 = new Item("Sprite",11.90,10,'f',null);
        Item item5 = new Item("Espresso",8.90,10,'f',null);

        drinksmenu.add(item3);
        drinksmenu.add(item4);
        drinksmenu.add(item5);

        Item item6 = new Item("Chocolate Cake", 42.90,10,'d',null);
        Item item7 = new Item("Cheese Cake",42.90,10,'d',null);
        Item item8 = new Item("Truffle",35.90,10,'d',null);

        dessertsmenu.add(item6);
        dessertsmenu.add(item7);
        dessertsmenu.add(item8);

        Item item9 = new Item("Sex on the beach",35.20,10,'d',null);
        Item item10 = new Item("Beer",26.90,10,'d',null);
        Item item11 = new Item("Whiskey",25.90,10,'d',null);

        alcoholmenu.add(item9);
        alcoholmenu.add(item10);
        alcoholmenu.add(item11);


    }




    //GETTERS AND SETTERS

    public ArrayAdapter<String> getAdapter4() {
        return adapter4;
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setAdapter4(ArrayAdapter<String> adapter4) {
        this.adapter4 = adapter4;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public ArrayList<Item> getFoodmenu() {
        return foodmenu;
    }

    public void setFoodmenu(ArrayList<Item> foodmenu) {
        this.foodmenu = foodmenu;
    }

    public ArrayList<Item> getDrinksmenu() {
        return drinksmenu;
    }

    public void setDrinksmenu(ArrayList<Item> drinksmenu) {
        this.drinksmenu = drinksmenu;
    }

    public ArrayList<Item> getDessertsmenu() {
        return dessertsmenu;
    }

    public void setDessertsmenu(ArrayList<Item> dessertsmenu) {
        this.dessertsmenu = dessertsmenu;
    }

    public ArrayList<Item> getAlcoholmenu() {
        return alcoholmenu;
    }

    public void setAlcoholmenu(ArrayList<Item> alcoholmenu) {
        this.alcoholmenu = alcoholmenu;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayAdapter getOrderAdapter() {
        return orderAdapter;
    }

    public void setOrderAdapter(ArrayAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    //INNER CLASS CardViewAdapter - menu card view adapter.
    class CardviewAdapter extends RecyclerView.Adapter<com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder>{

        private Context mCtx;
        private List<Item> menu;

        public CardviewAdapter(Context mCtx, List<Item> menu) {
            this.mCtx = mCtx;
            this.menu = menu;
        }

        @Override
        public com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.cardview_layout, null);
            com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder cardholder = new com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder(view);
            return cardholder;
        }

        @Override
        public void onBindViewHolder(final com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder holder, final int position) {
            Item item = menu.get(position);
            holder.title.setText(item.getName());

            if(item.getImg()==null){
                holder.imageView.setImageResource(R.mipmap.ordrs_asset); //null picture
            }else{
                holder.imageView.setImageBitmap(item.getImg());
            }

            holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, menu.get(position).getName(), Toast.LENGTH_SHORT).show();
                if(!orders.contains(menu.get(position).getName())) {
                    OrderItem newItem = new OrderItem(menu.get(position).getName(), 1,menu.get(position).getPrice());
                    orders.add(menu.get(position).getName());
                    orderItems.add(newItem);
                    // Toast.makeText(mCtx, orderItems.get(orderItems.size()).toString(), Toast.LENGTH_SHORT).show();
                    sumPrice += newItem.getPrice();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                    orderAdapter.notifyDataSetChanged();
                }else{
                    OrderItem itemAdded = orderItems.get(orders.indexOf(menu.get(position).getName()));
                    itemAdded.setCounter(itemAdded.getCounter()+1);
                    sumPrice += itemAdded.getPrice();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

        @Override
        public int getItemCount() {
            return menu.size();
        }

        class CardviewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title;
            protected View mRootView;

            public CardviewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView)itemView.findViewById(R.id.item_img);
                title = (TextView)itemView.findViewById(R.id.item_name);
                mRootView = itemView;

            }
        }
    }

    //INNER CLASS : OrderItemAdapter - counter listview customized adapter
    public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

        private final Context context;
        private final ArrayList<OrderItem> modelsArrayList;
        private int ctr;
        private int resource;

        public OrderItemAdapter(Context context, int resource, ArrayList<OrderItem> modelsArrayList) {
            super(context, resource, modelsArrayList);


            this.context = context;
            this.modelsArrayList = modelsArrayList;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.counterlistitem,parent,false);
            //convertView = inflater.inflate(resource,parent,false);
            // 2. Get rowView from inflater

            // 3. Get icon,title & counter views from the rowView
            //     ImageView imgView = (ImageView) customView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) customView.findViewById(R.id.itemname);
            TextView counterView = (TextView) customView.findViewById(R.id.item_counter);

            // 4. Set the text for textView

            titleView.setText(modelsArrayList.get(position).getTitle());
            //Toast.makeText(context, Integer.toString(modelsArrayList.get(position).getCounter()), Toast.LENGTH_SHORT).show();
            counterView.setText(Integer.toString(modelsArrayList.get(position).getCounter()));

            ImageButton add = (ImageButton)customView.findViewById(R.id.add);
            ImageButton remove = (ImageButton)customView.findViewById(R.id.remove);
            ImageButton delete = (ImageButton)customView.findViewById(R.id.delete);
            TextView sum = (TextView)customView.findViewById(R.id.sum);

            double suma = modelsArrayList.get(position).getPrice()*((double)modelsArrayList.get(position).getCounter());
            double sumb = Double.parseDouble(new DecimalFormat("##.##").format(suma));

            sum.setText(Double.toString(sumb));

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modelsArrayList.get(position).setCounter(modelsArrayList.get(position).getCounter()+1);
//                                Toast.makeText(context, Double.toString(modelsArrayList.get(position).getPrice()), Toast.LENGTH_SHORT).show();

                                sumPrice += modelsArrayList.get(position).getPrice();
                                TextView tv = (TextView)findViewById(R.id.sumPrice);
                                tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                                orderAdapter.notifyDataSetChanged();


                            }
                        });

                        remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(modelsArrayList.get(position).getCounter()<2){
                                    Toast.makeText(context, "Minimum order reached.", Toast.LENGTH_SHORT).show();

                                }else {
                                    double toLess = modelsArrayList.get(position).getPrice();
                                    sumPrice -= toLess;
                                    modelsArrayList.get(position).setCounter(modelsArrayList.get(position).getCounter() - 1);
                                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                                    orderAdapter.notifyDataSetChanged();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumPrice -= modelsArrayList.get(position).getPrice()*modelsArrayList.get(position).getCounter();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));

                    modelsArrayList.remove(modelsArrayList.get(position));
                    orders.remove(position);
                    orderAdapter.notifyDataSetChanged();

                }
            });


            // 5. retrn rowView
            return customView;

        }
    }

/**
 *     Discount Dialog Class
 */

    public class DiscountDialog extends Dialog {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.discount_dialog);
            TextView tv = (TextView)findViewById(R.id.discountTitle);
            final TextInputEditText ahoz = (TextInputEditText)findViewById(R.id.ahoz);
            Button ok = (Button)findViewById(R.id.okBtn);
            Button cancel = (Button)findViewById(R.id.cancelBtn);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    discounttext.setText(ahoz.getText().toString());
                    double calc = sumPrice*(Double.parseDouble(discounttext.getText().toString())/100);
                    double calc1 = sumPrice-calc;
                    totalprice.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(calc1))));
                    dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        public DiscountDialog(@NonNull Context context) {
            super(context);

        }

        public DiscountDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
        }

        protected DiscountDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }


    }


}