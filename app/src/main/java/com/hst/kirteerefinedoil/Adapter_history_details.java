package com.hst.kirteerefinedoil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_history_details extends RecyclerView.Adapter<Adapter_history_details.MyViewHolder> {
    private final Context context;
    //CartCountChange cartCountChange; //listener custom
    private ArrayList<modelOrderHistoryDetails> data = new ArrayList<>();

    public Adapter_history_details(Context context, ArrayList<modelOrderHistoryDetails> data) {
        this.context = context;
        this.data = data;
        //this.cartCountChange = cartCountChange;
        //Toast.makeText(context, String.valueOf(data.get(0).getItemName()), Toast.LENGTH_SHORT).show();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_details_recycle, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        final modelOrderHistoryDetails d = data.get(i);
        Toast.makeText(context, String.valueOf(d.getItemName()), Toast.LENGTH_SHORT).show();
        viewHolder.itemName.setText(d.getItemName());
        viewHolder.itemRate.setText(d.getItemRate());
        viewHolder.itemPrice.setText(d.getItemPrice());
        viewHolder.itemQty.setText(d.getItemQty());
        Glide.with(context).load(d.getItemLogo()).into(viewHolder.itemLogo);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemRate, itemPrice, itemQty;
        ImageView itemLogo;


        public MyViewHolder(View view) {
            super(view);
            itemName = itemView.findViewById(R.id.itemName);
            itemRate = itemView.findViewById(R.id.itemRate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemLogo = itemView.findViewById(R.id.itemLogo);
            itemQty = itemView.findViewById(R.id.itemQty);


        }

    }
}



