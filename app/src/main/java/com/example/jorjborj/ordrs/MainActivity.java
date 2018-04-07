package com.example.jorjborj.ordrs;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

 /**
 * Created by jorjborj on 4/2/2018.
 */

 public class MainActivity extends AppCompatActivity {

     ArrayList<Item> foodmenu = new ArrayList<Item>();
     ArrayList<Item> drinksmenu = new ArrayList<Item>();
     ArrayList<Item> dessertsmenu = new ArrayList<Item>();
     ArrayList<Item> alcoholmenu = new ArrayList<Item>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final BottomNavigationView nav = (BottomNavigationView)findViewById(R.id.navbar);
        disableShiftMode(nav);

        // Large screen, LISTVIEW and adapters
        final View mainscreen = (View)findViewById(R.id.largeScreen);
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        final ListView counterlv = (ListView)findViewById(R.id.counterlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,5));
        initializeData();

        //dummy data for now
        final String[] FoodValues = new String[] {"Chicken Salad", "Nazareth Breakfast", "Antricot" };
        final String[] DrinksValues = new String[] { "Cola","Sprite","Fanta","Water" };
        final String[] DessertsValues = new String[] { "Chocolate Cake", "Truffle", "Donuts" };
        final String[] AlcoholValues = new String[] { "Carlsberg", "Tuborg", "Corona", "Whiskey", "Arak" };

        final ArrayList<String> orders = new ArrayList<String>();

        final CardviewAdapter adapter = new CardviewAdapter(this,foodmenu);
        final CardviewAdapter adapter1 = new CardviewAdapter(this,drinksmenu);
        final CardviewAdapter adapter2 = new CardviewAdapter(this,dessertsmenu);
        final CardviewAdapter adapter3 = new CardviewAdapter(this,alcoholmenu);

//        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, DrinksValues);
//        rv.setAdapter(adapter);
//        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, DessertsValues);
//        rv.setAdapter(adapter);
//        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, AlcoholValues);

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, orders);

        rv.setAdapter(adapter);
        counterlv.setAdapter(adapter4);

//        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                orders.add(FoodValues[position]);
//                adapter4.notifyDataSetChanged();
//            }
//        });
//
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                nav.setSelectedItemId(item.getItemId());
                switch (item.getItemId()){
                    case R.id.food:
                        rv.setAdapter(adapter);
                        rv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orders.add(item.getTitle().toString());
                                adapter4.notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.alcohol:
                        rv.setAdapter(adapter3);
                        rv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orders.add(item.getTitle().toString());
                                adapter4.notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.drinks:
                        rv.setAdapter(adapter1);
                        rv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orders.add(item.getTitle().toString());
                                adapter4.notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.desserts:
                        rv.setAdapter(adapter2);
                        rv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orders.add(item.getTitle().toString());
                                adapter4.notifyDataSetChanged();
                            }
                        });
                        break;
                }
                return false;
            }
        });

    }

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


    public void initializeData (){

        Item item = new Item("Chicken Salad",10,'f',null);
        Item item1 = new Item("Nazareth Breakfast",10,'f',null);
        Item item2 = new Item("Beef Fillet",10,'f',null);

        foodmenu.add(item);
        foodmenu.add(item1);
        foodmenu.add(item2);

        Item item3 = new Item("Cola",10,'f',null);
        Item item4 = new Item("Sprite",10,'f',null);
        Item item5 = new Item("Espresso",10,'f',null);

        drinksmenu.add(item3);
        drinksmenu.add(item4);
        drinksmenu.add(item5);

        Item item6 = new Item("Chocolate Cake",10,'d',null);
        Item item7 = new Item("Cheese Cake",10,'d',null);
        Item item8 = new Item("Truffle",10,'d',null);

        dessertsmenu.add(item6);
        dessertsmenu.add(item7);
        dessertsmenu.add(item8);

        Item item9 = new Item("Sex on the beach",10,'d',null);
        Item item10 = new Item("Beer",10,'d',null);
        Item item11 = new Item("Whiskey",10,'d',null);

        alcoholmenu.add(item9);
        alcoholmenu.add(item10);
        alcoholmenu.add(item11);


    }

}
