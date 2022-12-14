package com.hst.kirteerefinedoil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_transaction extends RecyclerView.Adapter<Adapter_transaction.MyViewHolder> {
    private final Context context;
    private ArrayList<modelOrderHistory> data = new ArrayList<>();


    public Adapter_transaction(Context context, ArrayList<modelOrderHistory> data) {
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
        final modelOrderHistory d = data.get(i);
        viewHolder.date.setText(d.getDate());
        viewHolder.invoice_no.setText(d.getInvoiceNo());
        viewHolder.invoice_amt.setText(d.getGrandPricePaid());
        viewHolder.payment_mode.setText(d.getPaymentType());
        viewHolder.payment_status.setText(d.getPaymentStatus());
        viewHolder.transaction_id.setText(d.getTransactionId());
        if (d.getTransactionId().isEmpty()) {
            viewHolder.transaction_idLayout.setVisibility(View.GONE);
        }
        viewHolder.order_status.setText(d.getOrderStatus());
        if (d.getOrderStatus().equals("Cancelled")) {
            viewHolder.viewInvoice.setVisibility(View.INVISIBLE);
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, orderHistoryDetails.class);
                intent.putExtra("items", d.getItems());
                intent.putExtra("orderUid", d.getOrderUid());
                intent.putExtra("address", d.getAddress());
                intent.putExtra("city", d.getCity());
                intent.putExtra("state", d.getState());
                intent.putExtra("pincode", d.getPincode());
                intent.putExtra("orderStatus", d.getOrderStatus());
                intent.putExtra("paymentStatus", d.getPaymentStatus());
                intent.putExtra("paymentMode", d.getPaymentType());
                intent.putExtra("invoice", d.getInvoice());
                intent.putExtra("deliveredOn", d.getDeliveredTime());
                intent.putExtra("cancelledOn", d.getCancelledTime());
                intent.putExtra("refundOn", d.getRefundTime());
                intent.putExtra("transactionId", d.getTransactionId());
                intent.putExtra("coupon", d.getCoupon());
                intent.putExtra("invoiceNumber", d.getInvoiceNo());
                intent.putExtra("total", d.getTotal());
                intent.putExtra("discount", d.getDiscountAmount());
                intent.putExtra("gst", d.getGst());
                intent.putExtra("grandTotal", d.getGrandTotal());
                intent.putExtra("rounded", d.getRounded());
                intent.putExtra("paidAmount", d.getGrandPricePaid());
                context.startActivity(intent);
            }
        });
        viewHolder.viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(d.getInvoice()));
                context.startActivity(i);
            }
        });
        // Toast.makeText(context, d.getItems(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, viewInvoice, invoice_no, invoice_amt, payment_mode, payment_status, transaction_id, order_status;
        LinearLayout linearLayout, transaction_idLayout;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            transaction_idLayout = itemView.findViewById(R.id.transaction_idLayout);
            date = itemView.findViewById(R.id.date);
            invoice_no = itemView.findViewById(R.id.invoice_no);
            viewInvoice = itemView.findViewById(R.id.viewInvoice);
            invoice_amt = itemView.findViewById(R.id.invoice_amount);
            payment_mode = itemView.findViewById(R.id.payment_mode);
            payment_status = itemView.findViewById(R.id.payment_status);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            order_status = itemView.findViewById(R.id.order_status);

        }

    }

}



