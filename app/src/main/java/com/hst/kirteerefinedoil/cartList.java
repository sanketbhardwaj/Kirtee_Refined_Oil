package com.hst.kirteerefinedoil;

import android.app.Dialog;
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
import com.hst.kirteerefinedoil.databinding.ActivityCartListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cartList extends AppCompatActivity {
    ActivityCartListBinding activityCartListBinding;
    RequestQueue requestQueue;
    ArrayList<modelCart> dm = new ArrayList<modelCart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cart_list);
        activityCartListBinding = ActivityCartListBinding.inflate(getLayoutInflater());
        View view = activityCartListBinding.getRoot();
        setContentView(view);
        activityCartListBinding.rvCartList.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        activityCartListBinding.rvCartList.setLayoutManager(mLayoutManager);
        activityCartListBinding.rvCartList.setItemAnimator(new DefaultItemAnimator());
        getCartList();
    }

    public void getCartList() {
        // Assigning Activity this to progress dialog.
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CART_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(ServerResponse);

                            if (ServerResponse.length() == 21) {

                            } else {
                                /*
                                 no_cutomers.setVisibility(View.GONE);
                                 */
                                //Toast.makeText(getApplicationContext(), j.getString("cartList"), Toast.LENGTH_SHORT).show();
                                // binding.cartCount.setText(j.getString("cartCount"));
                                /*JSONArray applist = j.getJSONArray("cartList");
                                if (applist != null && applist.length() > 0) {
                                    for (int i = 0; i < applist.length(); i++) {
                                        Offer ds = new Offer();
                                        JSONObject jsonObject = applist.getJSONObject(i);
                                        ds.setDate(jsonObject.getString("date"));
                                        ds.setCover(jsonObject.getString("cover"));
                                        ds.setOfferUid(jsonObject.getString("offerUid"));
                                        dm.add(ds);
                                    }
                                    Adapter_offer_slider adapter = new Adapter_offer_slider(getApplicationContext(), dm);
                                    binding.viewpage.setAdapter(adapter);

                                }*/
                                activityCartListBinding.rounded.setText(j.getString("rounded"));
                                activityCartListBinding.subTotal.setText(j.getString("subTotal"));
                                activityCartListBinding.grandTotal.setText(j.getString("grandTotal"));
                                activityCartListBinding.grandTotalPrice.setText(j.getString("grandTotalPrice"));
                                activityCartListBinding.totalItems.setText(j.getString("totalItems"));
                                activityCartListBinding.totalQtys.setText(j.getString("totalQtys"));
                                activityCartListBinding.discount.setText(j.getString("discount"));
                                activityCartListBinding.gst.setText(j.getString("gst"));
                                activityCartListBinding.cgst.setText(j.getString("cgst"));
                                activityCartListBinding.sgst.setText(j.getString("sgst"));
                                JSONArray applists = j.getJSONArray("cartList");

                                if (applists != null && applists.length() > 0) {
                                    for (int i = 0; i < applists.length(); i++) {
                                        modelCart ds = new modelCart();
                                        JSONObject jsonObject = applists.getJSONObject(i);
                                        ds.setCartUid(jsonObject.getString("cartUid"));
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
                                    Adapter_cart_list adapter = new Adapter_cart_list(cartList.this, dm, new CartCountChange() {
                                        @Override
                                        public void cartCountChange(String text) {
                                            Toast.makeText(cartList.this, text, Toast.LENGTH_SHORT).show();
                                            dm.clear();
                                            getCartList();
                                        }


                                    });
                                    activityCartListBinding.rvCartList.setAdapter(adapter);

                                }
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        NetworkDialog1();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
//                params.put("latitude", String.valueOf(Home_screen.currentLatitude));
//                params.put("longitude", String.valueOf(Home_screen.currentLongitude));
                params.put("userUid", SplashScreen.Uid);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog1() {
        final Dialog dialogs = new Dialog(getApplicationContext());
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = (Button) dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                getCartList();

            }
        });
        dialogs.show();
    }
}