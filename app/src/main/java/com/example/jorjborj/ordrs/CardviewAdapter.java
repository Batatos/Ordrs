package com.example.jorjborj.ordrs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by jorjborj on 4/7/2018.
 */
public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.CardviewHolder>{


    private Context mCtx;
    private List<Item> menu;

    public CardviewAdapter(Context mCtx, List<Item> menu) {
        this.mCtx = mCtx;
        this.menu = menu;
    }

    @Override
    public CardviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardview_layout, null);
        CardviewHolder cardholder = new CardviewHolder(view);
        return cardholder;

    }

    @Override
    public void onBindViewHolder(final CardviewHolder holder, final int position) {
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
                Toast.makeText(mCtx, menu.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }



    static class CardviewHolder extends RecyclerView.ViewHolder{

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
