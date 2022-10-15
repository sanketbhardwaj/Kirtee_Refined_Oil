package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hst.kirteerefinedoil.Utilities.Constant;
import com.hst.kirteerefinedoil.databinding.ActivityOrderHistoryDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class orderHistoryDetails extends AppCompatActivity {
    ActivityOrderHistoryDetailsBinding activityOrderHistoryDetailsBinding;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ArrayList<modelOrderHistoryDetails> dm = new ArrayList<modelOrderHistoryDetails>();
    String order_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderHistoryDetailsBinding = ActivityOrderHistoryDetailsBinding.inflate(getLayoutInflater());
        View view = activityOrderHistoryDetailsBinding.getRoot();
        setContentView(view);
        activityOrderHistoryDetailsBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityOrderHistoryDetailsBinding.address.setText(getIntent().getStringExtra("address") + ", " + getIntent().getStringExtra("city") + ", " + getIntent().getStringExtra("pincode") + ", " + getIntent().getStringExtra("state") + ".");
        activityOrderHistoryDetailsBinding.orderStatus.setText(getIntent().getStringExtra("orderStatus"));
        if (getIntent().getStringExtra("orderStatus").equals("Pending") || getIntent().getStringExtra("orderStatus").equals("InProgress")) {
            activityOrderHistoryDetailsBinding.orderStatus.setTextColor(ContextCompat.getColor(orderHistoryDetails.this, R.color.orange_800));
        }
        if (getIntent().getStringExtra("orderStatus").equals("Delivered")) {
            activityOrderHistoryDetailsBinding.orderStatus.setTextColor(ContextCompat.getColor(orderHistoryDetails.this, R.color.green_800));
        }
        if (getIntent().getStringExtra("orderStatus").equals("Cancelled")) {
            activityOrderHistoryDetailsBinding.cancelButtonLayout.setVisibility(View.GONE);
            activityOrderHistoryDetailsBinding.orderStatus.setTextColor(ContextCompat.getColor(orderHistoryDetails.this, R.color.red_800));
        }

        activityOrderHistoryDetailsBinding.paymentStatus.setText(getIntent().getStringExtra("paymentStatus"));
        if (getIntent().getStringExtra("paymentStatus").equals("Paid")) {
            activityOrderHistoryDetailsBinding.paymentStatus.setTextColor(ContextCompat.getColor(orderHistoryDetails.this, R.color.green_800));
        }
        if (getIntent().getStringExtra("paymentStatus").equals("Pending")) {
            activityOrderHistoryDetailsBinding.paymentStatus.setTextColor(ContextCompat.getColor(orderHistoryDetails.this, R.color.orange_800));
        }
        activityOrderHistoryDetailsBinding.paymentMethod.setText(getIntent().getStringExtra("paymentMode"));
        if (getIntent().getStringExtra("deliveredOn").isEmpty()) {
            activityOrderHistoryDetailsBinding.deliveredOnLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.deliveredOn.setText(getIntent().getStringExtra("deliveredOn"));
        }
        if (getIntent().getStringExtra("cancelledOn").isEmpty()) {
            activityOrderHistoryDetailsBinding.cancelledOnLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.cancelledOn.setText(getIntent().getStringExtra("cancelledOn"));
        }
        if (getIntent().getStringExtra("refundOn").isEmpty()) {
            activityOrderHistoryDetailsBinding.refundOnLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.refundOn.setText(getIntent().getStringExtra("refundOn"));
        }
        if (getIntent().getStringExtra("transactionId").isEmpty()) {
            activityOrderHistoryDetailsBinding.transactionIdLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.transactionId.setText(getIntent().getStringExtra("transactionId"));
        }
        if (getIntent().getStringExtra("coupon").isEmpty()) {
            activityOrderHistoryDetailsBinding.couponLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.coupon.setText(getIntent().getStringExtra("coupon"));
        }
        activityOrderHistoryDetailsBinding.invoiceNumber.setText(getIntent().getStringExtra("invoiceNumber"));
        activityOrderHistoryDetailsBinding.total.setText(getIntent().getStringExtra("total"));
        if (getIntent().getStringExtra("discount").equals("₹ 0/-")) {
            activityOrderHistoryDetailsBinding.discountLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.discount.setText(getIntent().getStringExtra("discount"));//0
        }

        activityOrderHistoryDetailsBinding.gst.setText(getIntent().getStringExtra("gst"));
        if (getIntent().getStringExtra("rounded").equals("₹ 0/-")) {
            activityOrderHistoryDetailsBinding.grandTotalLayout.setVisibility(View.GONE);
            activityOrderHistoryDetailsBinding.roundedLayout.setVisibility(View.GONE);
        } else {
            activityOrderHistoryDetailsBinding.grandTotal.setText(getIntent().getStringExtra("grandTotal"));
            activityOrderHistoryDetailsBinding.rounded.setText(getIntent().getStringExtra("rounded"));//0
        }

        activityOrderHistoryDetailsBinding.paidAmount.setText(getIntent().getStringExtra("paidAmount"));

        activityOrderHistoryDetailsBinding.viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getIntent().getStringExtra("invoice")));
                startActivity(i);
            }
        });
        activityOrderHistoryDetailsBinding.rvCartList.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        activityOrderHistoryDetailsBinding.rvCartList.setLayoutManager(mLayoutManager);
        activityOrderHistoryDetailsBinding.rvCartList.setItemAnimator(new DefaultItemAnimator());
        order_list = "{ 'items':" + getIntent().getStringExtra("items") + "}";
        JSONObject j = null;
        try {
            j = new JSONObject(order_list);
            //Toast.makeText(this, j.toString(), Toast.LENGTH_SHORT).show();
            JSONArray applists = j.getJSONArray("items");

            if (applists != null && applists.length() > 0) {
                for (int i = 0; i < applists.length(); i++) {
                    modelOrderHistoryDetails ds = new modelOrderHistoryDetails();
                    JSONObject jsonObject = applists.getJSONObject(i);
                    ds.setDate(jsonObject.getString("date"));
                    ds.setItemUid(jsonObject.getString("itemUid"));
                    ds.setItemName(jsonObject.getString("itemName"));
                    //    Toast.makeText(cartList.this, jsonObject.getString("itemName").toString(), Toast.LENGTH_SHORT).show();
                    ds.setItemRate(jsonObject.getString("itemRate"));
                    ds.setItemPrice(jsonObject.getString("itemPrice"));
                    ds.setItemQty(jsonObject.getString("itemQty"));
                    ds.setItemLogo(jsonObject.getString("itemLogo"));
                    dm.add(ds);
                }
                Adapter_history_details adapter = new Adapter_history_details(orderHistoryDetails.this, dm);

                activityOrderHistoryDetailsBinding.rvCartList.setAdapter(adapter);
            }
        } catch (Exception e) {
        }

        activityOrderHistoryDetailsBinding.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder();
            }
        });
    }

    public void cancelOrder() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(orderHistoryDetails.this, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(orderHistoryDetails.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CANCEL_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");
                    //Cart_count = j.getString("cartCount");
                    //cartCount.cartCount(j.getString("cartCount"));

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                        //dm.clear();
                        AlertDialog.Builder alert = new AlertDialog.Builder(orderHistoryDetails.this, AlertDialog.THEME_HOLO_LIGHT);
                        alert.setTitle("Notice");
                        alert.setPositiveButton("OK", null);
                        alert.setMessage(j.getString("status"));
                        alert.show();
                        activityOrderHistoryDetailsBinding.cancelOrder.setVisibility(View.GONE);
                        //getCartList();

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(orderHistoryDetails.this, AlertDialog.THEME_HOLO_LIGHT);
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
                NetworkDialog();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("orderUid", getIntent().getStringExtra("orderUid"));


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(orderHistoryDetails.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(orderHistoryDetails.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                cancelOrder();

            }
        });
        dialogs.show();
    }
}