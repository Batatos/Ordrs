package com.example.jorjborj.ordrs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahed on 4/17/2018.
 */

public class TableCardAdapter extends RecyclerView.Adapter<CardviewAdapter.CardviewHolder> {
    Context context;
    ArrayList<TableItem> tableItems;

    public TableCardAdapter(Context context,ArrayList<TableItem> tableItems){
        this.context = context;
        this.tableItems = tableItems;
    }

    @Override
    public CardviewAdapter.CardviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.table_cardview_layout, null);
        CardviewAdapter.CardviewHolder cardholder = new CardviewAdapter.CardviewHolder(view);
        return cardholder;
    }

    @Override
    public void onBindViewHolder(CardviewAdapter.CardviewHolder holder, int position) {
        TableItem itemTable = tableItems.get(position);
        holder.title.setText(itemTable.getTableNum());

        if(itemTable.getImg()==null){
            holder.imageView.setImageResource(R.mipmap.ordrs_asset); //null picture
        }else{
            holder.imageView.setImageBitmap(itemTable.getImg());
        }


        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, menu.get(position).getName(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
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
