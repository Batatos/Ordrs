package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorjborj on 4/21/2018.
 */

public class OrderTableCardViewAdapter extends RecyclerView.Adapter<OrderTableCardViewAdapter.OrderTableCardViewHolder> {
    Context context;
    List<TableItem> tableItems = new ArrayList<>();
    int pos;

    public OrderTableCardViewAdapter(Context context,List<TableItem> tableItems){
        this.context = context;
        this.tableItems = tableItems;
    }

    @Override
    public OrderTableCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.table_cardview_layout, parent, false);
        OrderTableCardViewHolder cardholder = new OrderTableCardViewHolder(view);
        return cardholder;
    }

    @Override
    public void onBindViewHolder(OrderTableCardViewAdapter.OrderTableCardViewHolder holder, final int position) {
        TableItem itemTable = tableItems.get(position);
        holder.title.setText(itemTable.getTableNum());
        this.pos = position;
        if(itemTable.getImg()==null){
            holder.imageView.setImageResource(R.mipmap.table_asset); //null picture
        }else{
            holder.imageView.setImageBitmap(itemTable.getImg());
        }


        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(context, TableOrderDetails.class);
//                i.putExtra("table_num", tableItems.get(position).getTableNum());
//                context.startActivity(i);
//                notifyDataSetChanged();
                Toast.makeText(context, "Insert Table Order Details...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return tableItems.size();
    }


    static class OrderTableCardViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        protected View mRootView;

        public OrderTableCardViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.tableItem_img);
            title = (TextView)itemView.findViewById(R.id.tableItem_num);

            mRootView = itemView;

        }
    }
}
