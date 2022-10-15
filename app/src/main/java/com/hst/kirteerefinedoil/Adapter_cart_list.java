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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hst.kirteerefinedoil.Utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_cart_list extends RecyclerView.Adapter<Adapter_cart_list.MyViewHolder> {
    private final Context context;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    CartCountChange cartCountChange; //listener custom
    private ArrayList<modelCart> data = new ArrayList<>();

    public Adapter_cart_list(Context context, ArrayList<modelCart> data, CartCountChange cartCountChange) {
        this.context = context;
        this.data = data;
        this.cartCountChange = cartCountChange;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_recycle, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        final modelCart d = data.get(i);
        //Toast.makeText(context, String.valueOf(d.getItemName()), Toast.LENGTH_SHORT).show();
        viewHolder.itemName.setText(d.getItemName());
        viewHolder.itemRate.setText(d.getItemRate());
        viewHolder.itemPrice.setText(d.getItemPrice());
        viewHolder.itemQty.setText(d.getItemQty());
        viewHolder.calculations.setText("(" + d.getItemRate() + " x " + d.getItemQty() + ")");
        Glide.with(context).load(d.getItemLogo()).into(viewHolder.itemLogo);
        viewHolder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(d.getCartUid());
            }
        });
        viewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(d.getCartUid());
            }
        });
        viewHolder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusItem(d.getCartUid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(String cartUid) {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(context);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ADD_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");
                    //    Cart_count = j.getString("cartCount");
                    //    cartCount.cartCount(Cart_count);

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                        cartCountChange.cartCountChange("Success");
                        /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Notice");
                        alert.setPositiveButton("OK", null);
                        alert.setMessage(j.getString("status"));
                        alert.show();*/

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                NetworkDialog(cartUid);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("cartUid", cartUid);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog(String cartUid) {
        final Dialog dialogs = new Dialog(context);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                addItem(cartUid);

            }
        });
        dialogs.show();
    }

    public void minusItem(String cartUid) {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(context);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.MINUS_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");
                    //    Cart_count = j.getString("cartCount");
                    //    cartCount.cartCount(Cart_count);

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                        cartCountChange.cartCountChange("Success");
                        /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Notice");
                        alert.setPositiveButton("OK", null);
                        alert.setMessage(j.getString("status"));
                        alert.show();*/

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                NetworkDialog1(cartUid);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("cartUid", cartUid);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog1(String cartUid) {
        final Dialog dialogs = new Dialog(context);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                minusItem(cartUid);

            }
        });
        dialogs.show();
    }

    public void removeItem(String cartUid) {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(context);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REMOVE_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");
                    //    Cart_count = j.getString("cartCount");
                    //    cartCount.cartCount(Cart_count);

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                        cartCountChange.cartCountChange("Success");
                        /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Notice");
                        alert.setPositiveButton("OK", null);
                        alert.setMessage(j.getString("status"));
                        alert.show();*/

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                NetworkDialog2(cartUid);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("cartUid", cartUid);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog2(String cartUid) {
        final Dialog dialogs = new Dialog(context);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                removeItem(cartUid);

            }
        });
        dialogs.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemRate, itemPrice, itemQty, addItem, minusItem, removeItem, calculations;
        ImageView itemLogo;


        public MyViewHolder(View view) {
            super(view);
            calculations = itemView.findViewById(R.id.calculations);
            addItem = itemView.findViewById(R.id.addItem);
            minusItem = itemView.findViewById(R.id.minusItem);
            removeItem = itemView.findViewById(R.id.removeItem);
            itemName = itemView.findViewById(R.id.itemName);
            itemRate = itemView.findViewById(R.id.itemRate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemLogo = itemView.findViewById(R.id.itemLogo);
            itemQty = itemView.findViewById(R.id.itemQty);


        }

    }
}



