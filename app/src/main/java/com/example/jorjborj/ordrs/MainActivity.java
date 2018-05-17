package com.example.jorjborj.ordrs;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jorjborj on 4/2/2018.
 */

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    ArrayList<Item> startersmenu = new ArrayList<Item>();
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

        DatabaseHelper myDb = new DatabaseHelper(this);
        myDb.getWritableDatabase();

        discounttext = (TextView)findViewById(R.id.discountAhoz);
        totalprice = (TextView)findViewById(R.id.totalPrice);

        final BottomNavigationView nav = (BottomNavigationView)findViewById(R.id.navbar);
        disableShiftMode(nav);

        tablenum = getIntent().getExtras().get("table_num").toString();
        getSupportActionBar().setTitle("Order on table #"+tablenum);

        // Large screen, LISTVIEW and adapters
        final View mainscreen = (View)findViewById(R.id.largeScreen);
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        final ListView counterlv = (ListView)findViewById(R.id.counterlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,5));
        //TextView price = (TextView) findViewById(R.id.sumPrice);
        initializeData();

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY)+1; // return the hour in 24 hrs format (ranging from 0-23)
        if(currentHour>14 && currentHour<=17){
            discounttext.setText("10");
        }else{
        }


        Button sendBtn = (Button)findViewById(R.id.sendBtn);
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        final Button payBtn = (Button)findViewById(R.id.payBtn);
        final Button discountBtn = (Button)findViewById(R.id.discountBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = "Table #"+tablenum+": ";
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
                if(output.equals("Table #"+tablenum+": ")){
                    Toast.makeText(MainActivity.this, "No items selected", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, output, Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if there are no items ordered.. there is no need to show dialog.
                if(orderItems.isEmpty()){
                    Intent i = new Intent(getBaseContext(),PickOptionActivity.class);
                    finish();
                    startActivity(i);
                }else {

                    showCancelAlert();

                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                payDialog payDialog = new payDialog(MainActivity.this);
//                payDialog.show();
                if(!orderItems.isEmpty()){
                Intent i = new Intent(MainActivity.this,PaymentProcess.class);
                i.putExtra("orderlv", getOrderItems());
                i.putExtra("ahoz",discounttext.getText().toString());
                i.putExtra("total", totalprice.getText().toString());
                i.putExtra("price",sumPrice);
                startActivity(i);}else{
                    Toast.makeText(MainActivity.this, "No Items Selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLoginDialog adminDialog = new AdminLoginDialog(MainActivity.this);
                adminDialog.show();
            }
        });

        orders = new ArrayList<String>();
        adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, orders);

        orderItems = new ArrayList<>();
        orderAdapter = new OrderItemAdapter(this,R.layout.counterlistitem,orderItems);

        final CardviewAdapter adapter0 = new CardviewAdapter(this,startersmenu);
        final CardviewAdapter adapter = new CardviewAdapter(this,foodmenu);
        final CardviewAdapter adapter1 = new CardviewAdapter(this,drinksmenu);
        final CardviewAdapter adapter2 = new CardviewAdapter(this,dessertsmenu);
        final CardviewAdapter adapter3 = new CardviewAdapter(this,alcoholmenu);

        rv.setAdapter(adapter0);

        counterlv.setAdapter(orderAdapter);
        orderAdapter.setNotifyOnChange(true);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                switch (item.getItemId()){
                    case R.id.starters:
                        rv.setAdapter(adapter0);
                        item.setChecked(true);
                        break;
                    case R.id.food:
                        rv.setAdapter(adapter);
                        item.setChecked(true);
                        break;
                    case R.id.alcohol:
                        rv.setAdapter(adapter3);
                        item.setChecked(true);

                        break;
                    case R.id.drinks:
                        rv.setAdapter(adapter1);
                        item.setChecked(true);

                        break;
                    case R.id.desserts:
                        rv.setAdapter(adapter2);
                        item.setChecked(true);

                        break;
                }
                return false;
            }
        });

    }

    private void showCancelAlert() {

        final AlertDialog.Builder cancelAlert = new AlertDialog.Builder(MainActivity.this);
        cancelAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getBaseContext(), PickOptionActivity.class);
                startActivity(i);
                finish();

            }
        });
        cancelAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        cancelAlert.setTitle("Cancel Order");
        cancelAlert.setMessage("Are you sure you want to cancel order on table " + tablenum + " ?");
        cancelAlert.show();


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


        Item starter = new Item("Lemon Shrimps",38.50,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.lemon_shrimps));
        Item starter1 = new Item("Tangri Kebabs",35.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.tangari_kebabs));
        Item starter2 = new Item("Cheese Balls",41.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.cheese_balls));
        Item starter3 = new Item("Italian Cuisine",28.50,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.italian_cuisine));
        Item starter4 = new Item("Lobster Legs",38.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.lobster_legs));
        Item starter5 = new Item("Smoked Salamon",31.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.smoked_salamon));
        Item starter6 = new Item("Adamami",34.50,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.adamami));
        Item starter7 = new Item("Falafel",35.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.falafel));
        Item starter8 = new Item("Seafood Salad",40.00,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.seafood_salad));
        Item starter9 = new Item("Baked Potato",28.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.baked_potato));
        Item starter10 = new Item("Baked Sweet Potato",28.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.baked_sweet_potato));
        Item starter11 = new Item("Corn Soup",29.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.corn_soup));
        Item starter12 = new Item("Fish Soup",34.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.fish_soup));
        Item starter13 = new Item("French Fries", 28.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.french_fries));


        startersmenu.add(starter);
        startersmenu.add(starter1);
        startersmenu.add(starter2);
        startersmenu.add(starter3);
        startersmenu.add(starter4);
        startersmenu.add(starter5);
        startersmenu.add(starter6);
        startersmenu.add(starter7);
        startersmenu.add(starter8);
        startersmenu.add(starter9);
        startersmenu.add(starter10);
        startersmenu.add(starter11);
        startersmenu.add(starter12);
        startersmenu.add(starter13);


        Item fooditem = new Item("Chicken Salad",49.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.chicken_salad));
        Item fooditem1 = new Item("Caesar Salad",53.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.caesar_salad));
        Item fooditem2 = new Item("Tuna Salad",51.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.tuna_salad));
        Item fooditem3 = new Item("Nazareth Breakfast",65.90,10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.nazareth_breakfast));
        Item fooditem4 = new Item("Beef Fillet", 109.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.beef_fillet));
        Item fooditem5 = new Item("English Breakfast", 54.50, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.english_breakfast));
        Item fooditem6 = new Item("Italian Pasta", 67.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.italian_pasta));
        Item fooditem7 = new Item("Cavatappi Pasta", 28.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.cavatappi_pasta));
        Item fooditem8 = new Item("Chicken Pie", 28.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.chicken_pie));
        Item fooditem9 = new Item("Fresco Shrimps", 28.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.fresco_shrimps));
        Item fooditem10 = new Item("Potato Tortilla", 57.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.potato_tortilla));
        Item fooditem11 = new Item("Mac and Cheese", 53.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.mac_cheese));
        Item fooditem12 = new Item("Sloppy Joe", 58.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.sloppy_joe));
        Item fooditem13 = new Item("Cheesy Cabbage", 56.90, 10,'k',BitmapFactory.decodeResource(getResources(),R.mipmap.cabbage_cheese));

        foodmenu.add(fooditem);
        foodmenu.add(fooditem1);
        foodmenu.add(fooditem2);
        foodmenu.add(fooditem3);
        foodmenu.add(fooditem4);
        foodmenu.add(fooditem5);
        foodmenu.add(fooditem6);
        foodmenu.add(fooditem7);
        foodmenu.add(fooditem8);
        foodmenu.add(fooditem9);
        foodmenu.add(fooditem10);
        foodmenu.add(fooditem11);
        foodmenu.add(fooditem12);
        foodmenu.add(fooditem13);

        Item drinkitem = new Item("Cola",11.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.cola));
        Item drinkitem1 = new Item("Sprite",11.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.sprite));
        Item drinkitem2 = new Item("Espresso",8.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.espresso));
        Item drinkitem3 = new Item("Americano",10.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.americano));
        Item drinkitem4 = new Item("Cappuccino",14.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.cappuccino));
        Item drinkitem5 = new Item("Orange Juice",15.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.orange_juice));
        Item drinkitem6 = new Item("Lemonade",14.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.lemonade));
        Item drinkitem7 = new Item("Summer Smoothie",19.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.summer_vibes));
        Item drinkitem8 = new Item("Fruits Smoothie",16.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.colorfull_smoothie));
        Item drinkitem9 = new Item("Diaster Milkshake",25.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.disaster_milkshake));
        Item drinkitem10 = new Item("Red Vanil Milkshake",21.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.vanil_strawberry_milkshake));
        Item drinkitem11 = new Item("Choco Milkshake",19.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.choco_milkshake));

        drinksmenu.add(drinkitem);
        drinksmenu.add(drinkitem1);
        drinksmenu.add(drinkitem2);
        drinksmenu.add(drinkitem3);
        drinksmenu.add(drinkitem4);
        drinksmenu.add(drinkitem5);
        drinksmenu.add(drinkitem6);
        drinksmenu.add(drinkitem7);
        drinksmenu.add(drinkitem8);
        drinksmenu.add(drinkitem9);
        drinksmenu.add(drinkitem10);
        drinksmenu.add(drinkitem11);

        Item dessertitem = new Item("Chocolate Cake", 42.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.chocolate_cake));
        Item dessertitem1 = new Item("Cheese Cake",42.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.cheese_cake));
        Item dessertitem2 = new Item("Truffle",35.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.truffle));
        Item dessertitem3 = new Item("Apple Pie",33.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.apple_pie));
        Item dessertitem4 = new Item("Chocolate Mousse",42.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.choco_mousse));
        Item dessertitem5 = new Item("Strawberry Cake",45.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.straw_cake));
        Item dessertitem6 = new Item("Lemon Sorbet",33.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.lemon_sorbet));
        Item dessertitem7 = new Item("Lemon Tart",42.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.lemon_tart));
        Item dessertitem8 = new Item("IceCream Cake",45.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.iceream_cake));
        Item dessertitem9 = new Item("Guinness Cake",34.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.guinness_cake));
        Item dessertitem10 = new Item("Bannoffee Whoopies",43.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.bannoffee_whoopies));

        dessertsmenu.add(dessertitem);
        dessertsmenu.add(dessertitem1);
        dessertsmenu.add(dessertitem2);
        dessertsmenu.add(dessertitem3);
        dessertsmenu.add(dessertitem4);
        dessertsmenu.add(dessertitem5);
        dessertsmenu.add(dessertitem6);
        dessertsmenu.add(dessertitem7);
        dessertsmenu.add(dessertitem8);
        dessertsmenu.add(dessertitem9);
        dessertsmenu.add(dessertitem10);


        Item alcoholitem = new Item("Pina Colada",35.20,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.pina_colada));
        Item alcoholitem1 = new Item("Jin",35.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.jin));
        Item alcoholitem2 = new Item("Arak",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.arak));
        Item alcoholitem3 = new Item("Carlsberg",26.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.carlsberg));
        Item alcoholitem4 = new Item("Heineken",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.heineken));
        Item alcoholitem5 = new Item("Green Tuborg",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.green_tuborg));
        Item alcoholitem6 = new Item("Red Tuborg",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.red_tuborg));
        Item alcoholitem7 = new Item("Corona",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.corona));
        Item alcoholitem8 = new Item("Red Label",22.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.red_label));
        Item alcoholitem9 = new Item("Jameson",25.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.jameson));
        Item alcoholitem10 = new Item("Royal Chivas",25.90,10,'b',BitmapFactory.decodeResource(getResources(),R.mipmap.royal_chivas));

        alcoholmenu.add(alcoholitem);
        alcoholmenu.add(alcoholitem1);
        alcoholmenu.add(alcoholitem2);
        alcoholmenu.add(alcoholitem3);
        alcoholmenu.add(alcoholitem4);
        alcoholmenu.add(alcoholitem5);
        alcoholmenu.add(alcoholitem6);
        alcoholmenu.add(alcoholitem7);
        alcoholmenu.add(alcoholitem8);
        alcoholmenu.add(alcoholitem9);
        alcoholmenu.add(alcoholitem10);

    }

    @Override
    public void onBackPressed() {
        if(orderItems.isEmpty()){
        super.onBackPressed();}else{
            showCancelAlert();
        }
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

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
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
        public void onBindViewHolder(com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public void onBindViewHolder(final com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder holder, final int position) {
            final Item item = menu.get(position);
            holder.title.setText(item.getName());

            if(item.getImg()==null){
                holder.imageView.setImageResource(R.mipmap.ordrs_asset); //null picture
            }else{
                holder.imageView.setImageBitmap(item.getImg());
            }

            holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                                                        @Override
                                                        public boolean onLongClick(View v) {
                                                            Toast.makeText(mCtx, "Price: "+Double.toString(item.getPrice()), Toast.LENGTH_SHORT).show();
                                                            return false;
                                                        }
                                                    });

            holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, menu.get(position).getName(), Toast.LENGTH_SHORT).show();
                if(!orders.contains(menu.get(position).getName())) {
                    OrderItem newItem = new OrderItem(menu.get(position).getName(), 1,menu.get(position).getPrice(),menu.get(position).getType());
                    orders.add(menu.get(position).getName());
                    orderItems.add(newItem);
                    // Toast.makeText(mCtx, orderItems.get(orderItems.size()).toString(), Toast.LENGTH_SHORT).show();
                    sumPrice += newItem.getPrice();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                    calculateTotalPrice();
                    orderAdapter.notifyDataSetChanged();
                }else{
                    OrderItem itemAdded = orderItems.get(orders.indexOf(menu.get(position).getName()));
                    itemAdded.setCounter(itemAdded.getCounter()+1);
                    sumPrice += itemAdded.getPrice();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));
                    calculateTotalPrice();
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


            //TODO: bind modelsArrayList to database.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.counterlistitem,parent,false);
            //convertView = inflater.inflate(resource,parent,false);
            // 2. Get rowView from inflater

            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(MainActivity.this, v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.order_item_menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            NotesDialog nd = new NotesDialog(context,position);
                            nd.show();
                            return true;
                        }
                    });

                    popup.show(); //showing popup menu

                }
            });

            //Did it with onClick instead of onLongClick. See above.
//            customView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    PopupMenu popup = new PopupMenu(MainActivity.this, v);
//                    //Inflating the Popup using xml file
//                    popup.getMenuInflater()
//                            .inflate(R.menu.order_item_menu, popup.getMenu());
//                    //registering popup with OnMenuItemClickListener
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        public boolean onMenuItemClick(MenuItem item) {
//                            NotesDialog nd = new NotesDialog(context,position);
//                            nd.show();
//                            return true;
//                        }
//                    });
//
//                    popup.show(); //showing popup menu
//
//
//                    return false;
//                }
//            });


            // 3. Get icon,title & counter views from the rowView
            //     ImageView imgView = (ImageView) customView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) customView.findViewById(R.id.itemname);
            TextView counterView = (TextView) customView.findViewById(R.id.item_counter);
            LinearLayout notesLayout = (LinearLayout)customView.findViewById(R.id.NotesLayout);

            ImageButton add = (ImageButton)customView.findViewById(R.id.add);
            ImageButton remove = (ImageButton)customView.findViewById(R.id.remove);
            ImageButton delete = (ImageButton)customView.findViewById(R.id.delete);
            ImageButton clearNotes = (ImageButton)customView.findViewById(R.id.clearnote);
            final TextView notesData = (TextView)customView.findViewById(R.id.notesData);

            // 4. Set the text for textView
            if(modelsArrayList.get(position).getNotes()!=null){
                notesData.setText(modelsArrayList.get(position).getNotes().toString());
                orderAdapter.notifyDataSetChanged();
            }else{
                notesLayout.setVisibility(LinearLayout.GONE);
                clearNotes.setVisibility(ImageButton.GONE);
            }

            clearNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modelsArrayList.get(position).getNotes()!=null){
                        modelsArrayList.get(position).setNotes(null);
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            });

            titleView.setText(modelsArrayList.get(position).getTitle());
            //Toast.makeText(context, Integer.toString(modelsArrayList.get(position).getCounter()), Toast.LENGTH_SHORT).show();
            counterView.setText(Integer.toString(modelsArrayList.get(position).getCounter()));


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
                                calculateTotalPrice();
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
                                    calculateTotalPrice();
                                    orderAdapter.notifyDataSetChanged();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Alert dialog --> are you sure you want to delete items? (IF quantity>1)

                    sumPrice -= modelsArrayList.get(position).getPrice()*modelsArrayList.get(position).getCounter();
                    TextView tv = (TextView)findViewById(R.id.sumPrice);
                    tv.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(sumPrice))));

                    modelsArrayList.remove(modelsArrayList.get(position));
                    orders.remove(position);
                    calculateTotalPrice();
                    orderAdapter.notifyDataSetChanged();

                }
            });


            // 5. retrn rowView
            return customView;

        }

        class NotesDialog extends Dialog {

            int position;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.notes_dialog);

                final EditText notes = (EditText)findViewById(R.id.notesText);
                Button ok = (Button)findViewById(R.id.submitBtn);
                final Button cancel = (Button)findViewById(R.id.cancelBtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(notes.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Insert Notes", Toast.LENGTH_SHORT).show();
                        }else{
                            //Toast.makeText(MainActivity.this, notes.getText().toString(), Toast.LENGTH_SHORT).show();
                            modelsArrayList.get(position).setNotes(notes.getText().toString());
                            getOrderAdapter().notifyDataSetChanged();
                            dismiss();
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

            public NotesDialog(@NonNull Context context) {
                super(context);
            }

            public NotesDialog(@NonNull Context context, @StyleRes int themeResId) {
                super(context);
                this.position = themeResId;

            }


        }

    }

    public void calculateTotalPrice(){
        double calc = sumPrice*(Double.parseDouble(discounttext.getText().toString())/100);
        double calc1 = sumPrice-calc;
        totalprice.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(calc1))));
    }

    private void showError() {
        final EditText t = (EditText) findViewById(R.id.ahoz);
        t.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                t.setError("Incorrect Input ..");
            }
        });
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

            final EditText ahoz = (EditText)findViewById(R.id.ahoz);
            Button ok = (Button)findViewById(R.id.okBtn);
            Button cancel = (Button)findViewById(R.id.cancelBtn);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ahoz.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Insert %", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(Double.parseDouble(ahoz.getText().toString()) <= 100 && Double.parseDouble(ahoz.getText().toString()) >= 0) {
                        discounttext.setText(Integer.toString(Integer.parseInt(ahoz.getText().toString())/1));
                        double calc = sumPrice * (Double.parseDouble(discounttext.getText().toString()) / 100);
                        double calc1 = sumPrice - calc;
                        totalprice.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(calc1))));
                        dismiss();
                    } else{
                        ahoz.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                ahoz.requestFocus();
                            }
                        });
                        Toast.makeText(MainActivity.this, "Incorrect Input", Toast.LENGTH_SHORT).show();

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



    public class payDialog extends Dialog {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.paydialog);


            TextView price,ahoz,total,returnCash;
            EditText received;
            Button pay,cancel;

            price = (TextView)findViewById(R.id.price);
            ahoz = (TextView)findViewById(R.id.ahoz);
            total = (TextView)findViewById(R.id.total);
            returnCash = (TextView)findViewById(R.id.returncash);
            received = (EditText)findViewById(R.id.amountreceived);
            pay = (Button)findViewById(R.id.payBtn);
            cancel = (Button)findViewById(R.id.cancelBtn);


            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Pay", Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public payDialog(@NonNull Context context) {
            super(context);
        }

        public payDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context,themeResId);

        }


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
                        DiscountDialog dg = new DiscountDialog(MainActivity.this);
                        dg.show();
                    }else{
                        Toast.makeText(MainActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
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

}