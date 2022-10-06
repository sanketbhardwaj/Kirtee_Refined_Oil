package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hst.kirteerefinedoil.Utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_product_list extends RecyclerView.Adapter<Adapter_product_list.MyViewHolder> {
    public static String Cart_count;
    private final Context context;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    CartCount cartCount; //listener custom
    private ArrayList<Product> data = new ArrayList<>();

    public Adapter_product_list(Context context, ArrayList<Product> data, CartCount cartCount) {
        this.context = context;
        this.data = data;
        this.cartCount = cartCount;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_recycle, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        final Product d = data.get(i);
        viewHolder.date.setText(d.getDate());
        viewHolder.invoice_no.setText(d.getDescription());
        viewHolder.invoice_amt.setText(d.getDate());
        viewHolder.invoice_amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(d.getProductUid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void Login(String productUid) {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(context);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(context);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Add_Cart,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        JSONObject j = null;
                        try {
                            j = new JSONObject(ServerResponse);
                            String result = j.getString("result");
                            Cart_count = j.getString("cartCount");
                            cartCount.cartCount(Cart_count);

                            if (result.equals("Success")) {
                                // If response matched then show the toast.
                                // Finish the current Login activity

                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setMessage(j.getString("status"));
                                alert.setPositiveButton("OK", null);
                                alert.show();

                            }
                        } catch (JSONException e) {
                            //    Toast.makeText(Login_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        NetworkDialog(productUid);
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("userUid", SplashScreen.Uid);
                params.put("productUid", productUid);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog(String productUid) {
        final Dialog dialogs = new Dialog(context);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = (Button) dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                Login(productUid);

            }
        });
        dialogs.show();
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



