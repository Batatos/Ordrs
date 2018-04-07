package com.example.jorjborj.ordrs;

/**
 * Created by Ahed on 4/7/2018.
 */

import java.util.ArrayList;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.counterlistitem,parent,false);
        //convertView = inflater.inflate(resource,parent,false);
        // 2. Get rowView from inflater



            // 3. Get icon,title & counter views from the rowView
       //     ImageView imgView = (ImageView) customView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) customView.findViewById(R.id.itemname);
            TextView counterView = (TextView) customView.findViewById(R.id.item_counter);
            ImageView addImage = (ImageView) customView.findViewById(R.id.add);
            ImageView removeImage = (ImageView) customView.findViewById(R.id.add);

            // 4. Set the text for textView

            titleView.setText(modelsArrayList.get(position).getTitle());
            //Toast.makeText(context, Integer.toString(modelsArrayList.get(position).getCounter()), Toast.LENGTH_SHORT).show();
           counterView.setText(Integer.toString(modelsArrayList.get(position).getCounter()));


             // 5. retrn rowView
              return customView;

    }
}