package com.example.jorjborj.ordrs;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
    DatabaseHelper db;

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        initializeData();
        grabAndFillData();

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
        if(calc1==-0.0){
            calc1=0.0;
        }
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
    public void grabAndFillData(){

        Cursor cursorStarters = db.getAllStartersItems();
        Cursor cursorFood = db.getAllFoodItems();
        Cursor cursorDrinks = db.getAllDrinksItems();
        Cursor cursorDesserts = db.getAllDessertsItems();
        Cursor cursorAlcohol = db.getAllAlcoholItems();

        if(cursorStarters!=null){
            for(cursorStarters.moveToFirst(); !cursorStarters.isAfterLast(); cursorStarters.moveToNext()) {
                String column1 = cursorStarters.getString(cursorStarters.getColumnIndex("name"));
                double column2 = cursorStarters.getDouble(cursorStarters.getColumnIndex("price"));
                int column3 = cursorStarters.getInt(cursorStarters.getColumnIndex("amount"));
                String column4 = cursorStarters.getString(cursorStarters.getColumnIndex("type"));
                String column5 = cursorStarters.getString(cursorStarters.getColumnIndex("category"));

                byte[] column6 = cursorStarters.getBlob(cursorStarters.getColumnIndex("img"));
                Bitmap picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);

                String column7 = cursorStarters.getString(cursorStarters.getColumnIndex("supplier"));
                String column8 = cursorStarters.getString(cursorStarters.getColumnIndex("suppliernumber"));

                Item i = new Item(column1,column2,column3,column4,column5,picture,column7,column8);

                if(!startersmenu.contains(i)){
                    startersmenu.add(i);
                }
            }
        }

        if(cursorFood!=null){
            for(cursorFood.moveToFirst(); !cursorFood.isAfterLast(); cursorFood.moveToNext()) {
                String column1 = cursorFood.getString(cursorFood.getColumnIndex("name"));
                double column2 = cursorFood.getDouble(cursorFood.getColumnIndex("price"));
                int column3 = cursorFood.getInt(cursorFood.getColumnIndex("amount"));
                String column4 = cursorFood.getString(cursorFood.getColumnIndex("type"));
                String column5 = cursorFood.getString(cursorFood.getColumnIndex("category"));
                Bitmap picture=null;
                byte[] column6 = cursorFood.getBlob(cursorFood.getColumnIndex("img"));
                if (column6 != null && column6.length > 0) {
                    picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);
                }
                String column7 = cursorFood.getString(cursorFood.getColumnIndex("supplier"));
                String column8 = cursorFood.getString(cursorFood.getColumnIndex("suppliernumber"));

                Item i = new Item(column1,column2,column3,column4,column5,picture,column7,column8);

                if(!foodmenu.contains(i)){
                    foodmenu.add(i);
                }
            }
        }
        if(cursorDrinks!=null){
            for(cursorDrinks.moveToFirst(); !cursorDrinks.isAfterLast(); cursorDrinks.moveToNext()) {
                String column1 = cursorDrinks.getString(cursorDrinks.getColumnIndex("name"));
                double column2 = cursorDrinks.getDouble(cursorDrinks.getColumnIndex("price"));
                int column3 = cursorDrinks.getInt(cursorDrinks.getColumnIndex("amount"));
                String column4 = cursorDrinks.getString(cursorDrinks.getColumnIndex("type"));
                String column5 = cursorDrinks.getString(cursorDrinks.getColumnIndex("category"));
                Bitmap picture=null;
                byte[] column6 = cursorDrinks.getBlob(cursorDrinks.getColumnIndex("img"));
                if (column6 != null && column6.length > 0) {
                    picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);
                }
                String column7 = cursorDrinks.getString(cursorDrinks.getColumnIndex("supplier"));
                String column8 = cursorDrinks.getString(cursorDrinks.getColumnIndex("suppliernumber"));

                Item i = new Item(column1,column2,column3,column4,column5,picture,column7,column8);

                if(!drinksmenu.contains(i)){
                    drinksmenu.add(i);
                }
            }
        }
        if(cursorDesserts!=null){
            for(cursorDesserts.moveToFirst(); !cursorDesserts.isAfterLast(); cursorDesserts.moveToNext()) {
                String column1 = cursorDesserts.getString(cursorDesserts.getColumnIndex("name"));
                double column2 = cursorDesserts.getDouble(cursorDesserts.getColumnIndex("price"));
                int column3 = cursorDesserts.getInt(cursorDesserts.getColumnIndex("amount"));
                String column4 = cursorDesserts.getString(cursorDesserts.getColumnIndex("type"));
                String column5 = cursorDesserts.getString(cursorDesserts.getColumnIndex("category"));
                Bitmap picture=null;
                byte[] column6 = cursorDesserts.getBlob(cursorDesserts.getColumnIndex("img"));
                if (column6 != null && column6.length > 0) {
                    picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);
                }
                String column7 = cursorDesserts.getString(cursorDesserts.getColumnIndex("supplier"));
                String column8 = cursorDesserts.getString(cursorDesserts.getColumnIndex("suppliernumber"));

                Item i = new Item(column1,column2,column3,column4,column5,picture,column7,column8);

                if(!dessertsmenu.contains(i)){
                    dessertsmenu.add(i);
                }
            }
        }
        if(cursorAlcohol!=null){
            for(cursorAlcohol.moveToFirst(); !cursorAlcohol.isAfterLast(); cursorAlcohol.moveToNext()) {
                String column1 = cursorAlcohol.getString(cursorAlcohol.getColumnIndex("name"));
                double column2 = cursorAlcohol.getDouble(cursorAlcohol.getColumnIndex("price"));
                int column3 = cursorAlcohol.getInt(cursorAlcohol.getColumnIndex("amount"));
                String column4 = cursorAlcohol.getString(cursorAlcohol.getColumnIndex("type"));
                String column5 = cursorAlcohol.getString(cursorAlcohol.getColumnIndex("category"));
                Bitmap picture=null;
                byte[] column6 = cursorAlcohol.getBlob(cursorAlcohol.getColumnIndex("img"));
                if (column6 != null && column6.length > 0) {
                    picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);
                }
                String column7 = cursorAlcohol.getString(cursorAlcohol.getColumnIndex("supplier"));
                String column8 = cursorAlcohol.getString(cursorAlcohol.getColumnIndex("suppliernumber"));

                Item i = new Item(column1,column2,column3,column4,column5,picture,column7,column8);

                if(!alcoholmenu.contains(i)){
                    alcoholmenu.add(i);
                }
            }
        }
    }
    public void initializeData() {

        db.getWritableDatabase();

        if(db.getAllItems().getCount()<1){
            db.insertItem("Lemon Shrimps", 38.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_shrimps), "George", "0545983177");
            db.insertItem("Tangri Kebabs", 35.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.tangari_kebabs), "Supplier1", "0545983177");
            db.insertItem("Cheese Balls", 41.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.cheese_balls), "Supplier1", "0545983177");
            db.insertItem("Italian Cuisine", 28.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.italian_cuisine), "Supplier1", "0545983177");
            db.insertItem("Lobster Legs", 38.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.lobster_legs), "Supplier1", "0545983177");
            db.insertItem("Smoked Salamon", 31.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.smoked_salamon), "Supplier1", "0545983177");
            db.insertItem("Adamami", 34.50, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.adamami), "Supplier1", "0545983177");
            db.insertItem("Falafel", 35.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.falafel), "Supplier1", "0545983177");
            db.insertItem("Seafood Salad", 40.00, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.seafood_salad), "Supplier1", "0545983177");
            db.insertItem("Baked Potato", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.baked_potato), "Supplier1", "0545983177");
            db.insertItem("Baked Sweet Potato", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.baked_sweet_potato), "Supplier1", "0545983177");
            db.insertItem("Corn Soup", 29.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.corn_soup), "Supplier1", "0545983177");
            db.insertItem("Fish Soup", 34.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.fish_soup), "Supplier1", "0545983177");
            db.insertItem("French Fries", 28.90, 10, "k", "starters", BitmapFactory.decodeResource(getResources(), R.mipmap.french_fries), "Supplier1", "0545983177");


            //TODO: get item by category starters and insert into startsmenu
//        startersmenu.add(starter);
//        startersmenu.add(starter1);
//        startersmenu.add(starter2);
//        startersmenu.add(starter3);
//        startersmenu.add(starter4);
//        startersmenu.add(starter5);
//        startersmenu.add(starter6);
//        startersmenu.add(starter7);
//        startersmenu.add(starter8);
//        startersmenu.add(starter9);
//        startersmenu.add(starter10);
//        startersmenu.add(starter11);
//        startersmenu.add(starter12);
//        startersmenu.add(starter13);

            db.insertItem("Chicken Salad", 49.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.chicken_salad), "George", "0545983177");
            db.insertItem("Caesar Salad", 53.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.caesar_salad), "George", "0545983177");
            db.insertItem("Tuna Salad", 51.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.tuna_salad), "George", "0545983177");
            db.insertItem("Nazareth Breakfast", 65.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.nazareth_breakfast), "George", "0545983177");
            db.insertItem("Beef Fillet", 109.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.beef_fillet), "George", "0545983177");
            db.insertItem("English Breakfast", 54.50, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.english_breakfast), "George", "0545983177");
            db.insertItem("Italian Pasta", 67.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.italian_pasta), "George", "0545983177");
            db.insertItem("Cavatappi Pasta", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.cavatappi_pasta), "George", "0545983177");
            db.insertItem("Chicken Pie", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.chicken_pie), "George", "0545983177");
            db.insertItem("Fresco Shrimps", 28.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.fresco_shrimps), "George", "0545983177");
            db.insertItem("Potato Tortilla", 57.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.potato_tortilla), "George", "0545983177");
            db.insertItem("Mac and Cheese", 53.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.mac_cheese), "George", "0545983177");
            db.insertItem("Sloppy Joe", 58.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.sloppy_joe), "George", "0545983177");
            db.insertItem("Cheesy Cabbage", 56.90, 10, "k", "food", BitmapFactory.decodeResource(getResources(), R.mipmap.cabbage_cheese), "George", "0545983177");


//        foodmenu.add(fooditem);
//        foodmenu.add(fooditem1);
//        foodmenu.add(fooditem2);
//        foodmenu.add(fooditem3);
//        foodmenu.add(fooditem4);
//        foodmenu.add(fooditem5);
//        foodmenu.add(fooditem6);
//        foodmenu.add(fooditem7);
//        foodmenu.add(fooditem8);
//        foodmenu.add(fooditem9);
//        foodmenu.add(fooditem10);
//        foodmenu.add(fooditem11);
//        foodmenu.add(fooditem12);
//        foodmenu.add(fooditem13);

            db.insertItem("Cola", 11.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.cola), "George", "0545983177");
            db.insertItem("Sprite", 11.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.sprite), "George", "0545983177");
            db.insertItem("Espresso", 8.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.espresso), "George", "0545983177");
            db.insertItem("Americano", 10.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.americano), "George", "0545983177");
            db.insertItem("Cappuccino", 14.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.cappuccino), "George", "0545983177");
            db.insertItem("Orange Juice", 15.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.orange_juice), "George", "0545983177");
            db.insertItem("Lemonade", 14.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.lemonade), "George", "0545983177");
            db.insertItem("Summer Smoothie", 19.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.summer_vibes), "George", "0545983177");
            db.insertItem("Fruits Smoothie", 16.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.colorfull_smoothie), "George", "0545983177");
            db.insertItem("Diaster Milkshake", 25.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.disaster_milkshake), "George", "0545983177");
            db.insertItem("Red Vanil Milkshake", 21.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.vanil_strawberry_milkshake), "George", "0545983177");
            db.insertItem("Choco Milkshake", 19.90, 10, "b", "drinks", BitmapFactory.decodeResource(getResources(), R.mipmap.choco_milkshake), "George", "0545983177");

//        drinksmenu.add(drinkitem);
//        drinksmenu.add(drinkitem1);
//        drinksmenu.add(drinkitem2);
//        drinksmenu.add(drinkitem3);
//        drinksmenu.add(drinkitem4);
//        drinksmenu.add(drinkitem5);
//        drinksmenu.add(drinkitem6);
//        drinksmenu.add(drinkitem7);
//        drinksmenu.add(drinkitem8);
//        drinksmenu.add(drinkitem9);
//        drinksmenu.add(drinkitem10);
//        drinksmenu.add(drinkitem11);

            db.insertItem("Chocolate Cake", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.chocolate_cake), "George", "0545983177");
            db.insertItem("Cheese Cake", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.cheese_cake), "George", "0545983177");
            db.insertItem("Truffle", 35.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.truffle), "George", "0545983177");
            db.insertItem("Apple Pie", 33.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.apple_pie), "George", "0545983177");
            db.insertItem("Chocolate Mousse", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.choco_mousse), "George", "0545983177");
            db.insertItem("Strawberry Cake", 45.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.straw_cake), "George", "0545983177");
            db.insertItem("Lemon Sorbet", 33.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_sorbet), "George", "0545983177");
            db.insertItem("Lemon Tart", 42.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.lemon_tart), "George", "0545983177");
            db.insertItem("IceCream Cake", 45.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.iceream_cake), "George", "0545983177");
            db.insertItem("Guinness Cake", 34.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.guinness_cake), "George", "0545983177");
            db.insertItem("B Whoopies", 43.90, 10, "b", "desserts", BitmapFactory.decodeResource(getResources(), R.mipmap.bannoffee_whoopies), "George", "0545983177");

//        dessertsmenu.add(dessertitem);
//        dessertsmenu.add(dessertitem1);
//        dessertsmenu.add(dessertitem2);
//        dessertsmenu.add(dessertitem3);
//        dessertsmenu.add(dessertitem4);
//        dessertsmenu.add(dessertitem5);
//        dessertsmenu.add(dessertitem6);
//        dessertsmenu.add(dessertitem7);
//        dessertsmenu.add(dessertitem8);
//        dessertsmenu.add(dessertitem9);
//        dessertsmenu.add(dessertitem10);


            db.insertItem("Pina Colada", 35.20, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.pina_colada), "George", "0545983177");
            db.insertItem("Jin", 35.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.jin), "George", "0545983177");
            db.insertItem("Arak", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.arak), "George", "0545983177");
            db.insertItem("Carlsberg", 26.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.carlsberg), "George", "0545983177");
            db.insertItem("Heineken", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.heineken), "George", "0545983177");
            db.insertItem("Green Tuborg", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.green_tuborg), "George", "0545983177");
            db.insertItem("Red Tuborg", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.red_tuborg), "George", "0545983177");
            db.insertItem("Corona", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.corona), "George", "0545983177");
            db.insertItem("Red Label", 22.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.red_label), "George", "0545983177");
            db.insertItem("Jameson", 25.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.jameson), "George", "0545983177");
            db.insertItem("Royal Chivas", 25.90, 10, "b", "alcohol", BitmapFactory.decodeResource(getResources(), R.mipmap.royal_chivas), "George", "0545983177");

//        alcoholmenu.add(alcoholitem);
//        alcoholmenu.add(alcoholitem1);
//        alcoholmenu.add(alcoholitem2);
//        alcoholmenu.add(alcoholitem3);
//        alcoholmenu.add(alcoholitem4);
//        alcoholmenu.add(alcoholitem5);
//        alcoholmenu.add(alcoholitem6);
//        alcoholmenu.add(alcoholitem7);
//        alcoholmenu.add(alcoholitem8);
//        alcoholmenu.add(alcoholitem9);
//        alcoholmenu.add(alcoholitem10);
        }

    }

}