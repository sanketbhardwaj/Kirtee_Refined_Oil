package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
                    Toast.makeText(this, jsonObject.getString("itemName"), Toast.LENGTH_SHORT).show();
                }
                Adapter_history_details adapter = new Adapter_history_details(orderHistoryDetails.this, dm);

                activityOrderHistoryDetailsBinding.rvCartList.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        progressDialog = new ProgressDialog(orderHistoryDetails.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(orderHistoryDetails.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CANCEL_ORDER,
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
                            //Cart_count = j.getString("cartCount");
                            //cartCount.cartCount(j.getString("cartCount"));

                            if (result.equals("Success")) {
                                // If response matched then show the toast.
                                // Finish the current Login activity
                                //dm.clear();
                                AlertDialog.Builder alert = new AlertDialog.Builder(orderHistoryDetails.this);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();
                                //getCartList();

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(orderHistoryDetails.this);
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
        Button done = (Button) dialogs.findViewById(R.id.done);
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