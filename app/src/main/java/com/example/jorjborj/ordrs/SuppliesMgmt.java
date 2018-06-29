package com.example.jorjborj.ordrs;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorjborj on 6/27/2018.
 */

public class SuppliesMgmt extends AppCompatActivity {


    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE2 = 2;

    ImageButton img;
    ImageButton img1;

    DatabaseHelper db;
    ArrayList<Item> startersmenu = new ArrayList<Item>();
    ArrayList<Item> foodmenu = new ArrayList<Item>();
    ArrayList<Item> drinksmenu = new ArrayList<Item>();
    ArrayList<Item> dessertsmenu = new ArrayList<Item>();
    ArrayList<Item> alcoholmenu = new ArrayList<Item>();
    RecyclerView rv;
    CardviewAdapter adapter0,adapter,adapter1,adapter2,adapter3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplies_mgmt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Supplies Management");

        db = new DatabaseHelper(getBaseContext());
        db.getWritableDatabase();


        final BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navbar);
        rv = (RecyclerView) findViewById(R.id.rv);
        final Button addBtn = (Button) findViewById(R.id.add);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this, 7));
        initializeData();
        grabAndFillData();

        adapter0 = new CardviewAdapter(this, startersmenu);
        adapter = new CardviewAdapter(this, foodmenu);
        adapter1 = new CardviewAdapter(this, drinksmenu);
        adapter2 = new CardviewAdapter(this, dessertsmenu);
        adapter3 = new CardviewAdapter(this, alcoholmenu);

        rv.setAdapter(adapter0);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                switch (item.getItemId()) {
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


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Intent i = new Intent(SuppliesMgmt.this,SuppliesMgmt.class);
                        startActivity(i);

                        // Explain to the user why we need to read the contacts
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }

              AddItemDialog addItemDialog = new AddItemDialog(SuppliesMgmt.this);
                addItemDialog.show();
            }
        });

    }

    class CardviewAdapter extends RecyclerView.Adapter<com.example.jorjborj.ordrs.CardviewAdapter.CardviewHolder> {

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

            if (item.getImg() == null) {
                holder.imageView.setImageResource(R.mipmap.ordrs_asset); //null picture
            } else {
                holder.imageView.setImageBitmap(item.getImg());
            }

            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDetailsDialog idd = new ItemDetailsDialog(SuppliesMgmt.this,menu.get(position));
                    idd.show();
                }
            });

            holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(SuppliesMgmt.this, v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.notes_settings_menu, popup.getMenu());


                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().toString().equals("Delete")) {
                                if(db.deleteItem(holder.title.getText().toString())) {
                                    Intent i = new Intent(SuppliesMgmt.this,SuppliesMgmt.class);
                                    startActivity(i);
                                    Toast.makeText(SuppliesMgmt.this, "Successfully Deleted from Database", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(SuppliesMgmt.this, "Couldn't Delete from Database", Toast.LENGTH_SHORT).show();

                            }

                            if (item.getTitle().toString().equals("Edit")) {

                                EditItemDialog eid = new EditItemDialog(SuppliesMgmt.this,menu.get(position));
                                eid.show();
                            }
                            return true;
                        }
                    });

                    popup.show(); //showing popup menu

                return false;}

            });




        }

        @Override
        public int getItemCount() {
            return menu.size();
        }

        class CardviewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView title;
            protected View mRootView;

            public CardviewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.item_img);
                title = (TextView) itemView.findViewById(R.id.item_name);
                mRootView = itemView;

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

                Bitmap picture=null;
                byte[] column6 = cursorStarters.getBlob(cursorStarters.getColumnIndex("img"));
                if (column6 != null && column6.length > 0) {
                    picture = BitmapFactory.decodeByteArray(column6, 0, column6.length);
                }

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


    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(SuppliesMgmt.this,PickOptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
    }


    public class AddItemDialog extends Dialog {

        public AddItemDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.new_item_dialog);

            final TextView name,price,amount,supplier,suppliernumber;
            final RadioGroup type,category;
            final Button add,cancel;

            name = (TextView)findViewById(R.id.itemname);
            price = (TextView)findViewById(R.id.itemprice);
            amount = (TextView)findViewById(R.id.itemamount);
            supplier = (TextView)findViewById(R.id.itemsupplier);
            img =  (ImageButton) findViewById(R.id.addimg);
            suppliernumber = (TextView)findViewById(R.id.itemsuppliernumber);
            type = (RadioGroup)findViewById(R.id.type);
            category = (RadioGroup)findViewById(R.id.category);
            add = (Button)findViewById(R.id.addBtn);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, PICK_IMAGE);
                }
            });


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String t,c="";

                    //validate data input for new item
                    if(type.getCheckedRadioButtonId()==-1 || category.getCheckedRadioButtonId()==-1 || name.getText().equals("") || price.getText().toString()=="" ||
                            amount.getText().toString().equals("") || supplier.getText().toString().equals("") ||
                            suppliernumber.getText().toString().equals("") || img.getDrawable()==null){
                        Toast.makeText(SuppliesMgmt.this, "Enter Correct Data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(type.getCheckedRadioButtonId()==R.id.kitchen) t="k"; else t="b";

                    if(category.getCheckedRadioButtonId()==R.id.starters) c="starters";
                    if(category.getCheckedRadioButtonId()==R.id.food) c="food";
                    if(category.getCheckedRadioButtonId()==R.id.drinks) c="drinks";
                    if(category.getCheckedRadioButtonId()==R.id.desserts) c="desserts";
                    if(category.getCheckedRadioButtonId()==R.id.alcohol) c="alcohol";

                    //TODO: add picture from library and add it to the item.

                    Drawable drawable = img.getDrawable();
                    Bitmap pic = ((BitmapDrawable)drawable).getBitmap();

                    db.insertItem(name.getText().toString(),Double.parseDouble(price.getText().toString()),Integer.parseInt(amount.getText().toString()),t,c,pic,supplier.getText().toString(),suppliernumber.getText().toString());
                    dismiss();
                    Intent i = new Intent(SuppliesMgmt.this,SuppliesMgmt.class);
                    startActivity(i);
                    Toast.makeText(SuppliesMgmt.this, "Successfully Added to Database", Toast.LENGTH_SHORT).show();

                }
            });


            cancel = (Button)findViewById(R.id.cancelBtn);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


    }

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }

            if (requestCode == PICK_IMAGE2 && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                img1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    public class ItemDetailsDialog extends Dialog {

        Item i;
        public ItemDetailsDialog(@NonNull Context context,Item i) {
            super(context);
            this.i=i;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.item_details);

            setCanceledOnTouchOutside(true);
            final TextView name,price,amount,supplier,suppliernumber,type,category;
            final ImageView image;

            name = (TextView)findViewById(R.id.itemname);
            type = (TextView)findViewById(R.id.itemtype);
            category = (TextView)findViewById(R.id.itemcategory);
            price = (TextView)findViewById(R.id.itemprice);
            amount = (TextView)findViewById(R.id.itemamount);
            supplier = (TextView)findViewById(R.id.itemsupplier);
            image =  (ImageView) findViewById(R.id.itemimg);
            suppliernumber = (TextView)findViewById(R.id.itemsuppliernumber);


            name.setText(i.getName());
            price.setText(Double.toString(i.getPrice()));
            amount.setText(Integer.toString(i.getAmount()));
            supplier.setText(i.getSupplier().toString());
            suppliernumber.setText(i.getSupplierNumber().toString());
            type.setText(i.getType().toString());
            category.setText(i.getCategory().toString());
            image.setImageBitmap(i.getImg());


        }

    }

    public class EditItemDialog extends Dialog {

        Item i;
        public EditItemDialog(@NonNull Context context,Item i) {
            super(context);
            this.i=i;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.edit_item_dialog);

            final TextView title,price,amount,supplier,suppliernumber;
            final RadioGroup type,category;
            final Button edit,cancel;


            title = (TextView)findViewById(R.id.itemname1);
            price = (TextView)findViewById(R.id.itemprice);
            amount = (TextView)findViewById(R.id.itemamount);
            supplier = (TextView)findViewById(R.id.itemsupplier);
            img1 =  (ImageButton) findViewById(R.id.editimg);
            suppliernumber = (TextView)findViewById(R.id.itemsuppliernumber);
            type = (RadioGroup)findViewById(R.id.type);
            category = (RadioGroup)findViewById(R.id.category);

            edit = (Button)findViewById(R.id.editBtn);

            title.setText(i.getName().toString());
            price.setText(Double.toString(i.getPrice()));
            amount.setText(Integer.toString(i.getAmount()));
            supplier.setText(i.getSupplier().toString());
            suppliernumber.setText(i.getSupplierNumber().toString());

            if(i.getType().equals("k")){
                type.check(R.id.kitchen);
            }else
                type.check(R.id.bar);

            if(i.getCategory().equals("starters")){
                category.check(R.id.starters);
            }else if(i.getCategory().equals("food"))
                category.check(R.id.food);
            else if(i.getCategory().equals("drinks"))
                category.check(R.id.drinks);
            else if(i.getCategory().equals("desserts"))
                category.check(R.id.desserts);
            else if(i.getCategory().equals("alcohol"))
                category.check(R.id.alcohol);

            img1.setImageBitmap(i.getImg());

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, PICK_IMAGE2);
                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String t,c="";

                    //validate data input for new item
                    if(type.getCheckedRadioButtonId()==-1 || category.getCheckedRadioButtonId()==-1 || price.getText().toString()=="" ||
                            amount.getText().toString().equals("") || supplier.getText().toString().equals("") ||
                            suppliernumber.getText().toString().equals("") || img1.getDrawable()==null){
                        Toast.makeText(SuppliesMgmt.this, "Enter Correct Data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(type.getCheckedRadioButtonId()==R.id.kitchen) t="k"; else t="b";
                    if(category.getCheckedRadioButtonId()==R.id.starters) c="starters";
                    if(category.getCheckedRadioButtonId()==R.id.food) c="food";
                    if(category.getCheckedRadioButtonId()==R.id.drinks) c="drinks";
                    if(category.getCheckedRadioButtonId()==R.id.desserts) c="desserts";
                    if(category.getCheckedRadioButtonId()==R.id.alcohol) c="alcohol";

                    //TODO: add picture from library and add it to the item.

                    Drawable drawable = img1.getDrawable();
                    Bitmap pic = ((BitmapDrawable)drawable).getBitmap();

                    Item editedItem = new Item(title.getText().toString(),Double.parseDouble(price.getText().toString()),Integer.parseInt(amount.getText().toString()),t,c,pic,supplier.getText().toString(),suppliernumber.getText().toString());
                    db.updateItem(editedItem);
                    //db.insertItem(name.getText().toString(),Double.parseDouble(price.getText().toString()),Integer.parseInt(amount.getText().toString()),t,c,pic,supplier.getText().toString(),suppliernumber.getText().toString());
                    dismiss();
                    Intent i = new Intent(SuppliesMgmt.this,SuppliesMgmt.class);
                    startActivity(i);
                    Toast.makeText(SuppliesMgmt.this, "Successfully Added to Database", Toast.LENGTH_SHORT).show();

                }
            });


            cancel = (Button)findViewById(R.id.cancelBtn);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


        }

    }

}