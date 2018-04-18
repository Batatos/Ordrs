package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahed on 4/17/2018.
 */

public class TableCardAdapter extends RecyclerView.Adapter<TableCardAdapter.TableCardviewHolder> {
    Context context;
    List<TableItem> tableItems = new ArrayList<>();
    int pos;

    public TableCardAdapter(Context context,List<TableItem> tableItems){
        this.context = context;
        this.tableItems = tableItems;
    }

    @Override
    public TableCardviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.table_cardview_layout, parent, false);
        TableCardviewHolder cardholder = new TableCardviewHolder(view);
        return cardholder;
    }

    @Override
    public void onBindViewHolder(TableCardAdapter.TableCardviewHolder holder, final int position) {
        TableItem itemTable = tableItems.get(position);
        holder.title.setText(itemTable.getTableNum());
        this.pos = position;
        if(itemTable.getImg()==null){
            holder.imageView.setImageResource(R.mipmap.ordrs_asset); //null picture
        }else{
            holder.imageView.setImageBitmap(itemTable.getImg());
        }


        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("table_num", tableItems.get(position).getTableNum());
                context.startActivity(i);
                notifyDataSetChanged();
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


    static class TableCardviewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        protected View mRootView;

        public TableCardviewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.tableItem_img);
            title = (TextView)itemView.findViewById(R.id.tableItem_num);

            mRootView = itemView;

        }
    }
}
