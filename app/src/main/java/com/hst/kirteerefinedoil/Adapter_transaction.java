package com.hst.kirteerefinedoil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_transaction extends RecyclerView.Adapter<Adapter_transaction.MyViewHolder> {
    private final Context context;
    private ArrayList<OrderHistory> data = new ArrayList<>();


    public Adapter_transaction(Context context, ArrayList<OrderHistory> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_recycle, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        final OrderHistory d = data.get(i);
        viewHolder.date.setText(d.getDate());
        viewHolder.invoice_no.setText(d.getInvoiceNo());
        viewHolder.invoice_amt.setText(d.getGrandPricePaid());
        viewHolder.payment_mode.setText(d.getPaymentType());
        viewHolder.payment_status.setText(d.getPaymentStatus());
        viewHolder.transaction_id.setText(d.getTransactionId());
        viewHolder.order_status.setText(d.getOrderStatus());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, invoice_no, invoice_amt, payment_mode, payment_status, transaction_id, order_status;

        public MyViewHolder(View view) {
            super(view);
            date = itemView.findViewById(R.id.date);
            invoice_no = itemView.findViewById(R.id.invoice_no);
            invoice_amt = itemView.findViewById(R.id.invoice_amount);
            payment_mode = itemView.findViewById(R.id.payment_mode);
            payment_status = itemView.findViewById(R.id.payment_status);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            order_status = itemView.findViewById(R.id.order_status);

        }

    }

}



