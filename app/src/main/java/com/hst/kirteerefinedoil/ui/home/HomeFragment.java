package com.hst.kirteerefinedoil.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hst.kirteerefinedoil.Adapter_offer_slider;
import com.hst.kirteerefinedoil.Adapter_product_list;
import com.hst.kirteerefinedoil.CartCount;
import com.hst.kirteerefinedoil.LoginActivity;
import com.hst.kirteerefinedoil.R;
import com.hst.kirteerefinedoil.SplashScreen;
import com.hst.kirteerefinedoil.Utilities.Constant;
import com.hst.kirteerefinedoil.cartList;
import com.hst.kirteerefinedoil.databinding.FragmentHomeBinding;
import com.hst.kirteerefinedoil.modelOffer;
import com.hst.kirteerefinedoil.modelProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final long ANIM_VIEWPAGER_DELAY = 3000;
    private final boolean pagerMoved = false;
    private final Handler h = new Handler();
    ArrayList<modelProduct> dm1 = new ArrayList<modelProduct>();
    ArrayList<modelOffer> dm = new ArrayList<modelOffer>();
    RequestQueue requestQueue;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.categoryDetailRv.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.categoryDetailRv.setLayoutManager(mLayoutManager);
        binding.categoryDetailRv.setItemAnimator(new DefaultItemAnimator());
        //Login();
        binding.cartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SplashScreen.Uid.equals("N")) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), cartList.class));
                }

            }
        });
        binding.cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SplashScreen.Uid.equals("N")) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), cartList.class));
                }

            }
        });
        productList();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*public void Login() {
        // Assigning Activity this to progress dialog.
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getContext());
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PRODUCT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(ServerResponse);
                            if (ServerResponse.length() == 21) {

                            } else {
                                *//*   no_cutomers.setVisibility(View.GONE);
     *//*

                            }

                            // Finish the current Login activity.
                            // Opening the user profile activity using intent.

                            *//*else{
                                String msg = j.getString("msg");
                                // Showing Echo Response Message Coming From Server.
                                Toast.makeText(customer_names.this, msg, Toast.LENGTH_LONG).show();

                            }*//*
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //     Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        //  lottie.setVisibility(View.GONE);
                        NetworkDialog();
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

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(getContext());
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = (Button) dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                Login();

            }
        });
        dialogs.show();
    }*/

    @Override
    public void onPause() {
        super.onPause();
        if (h != null) {
            h.removeCallbacks(animateViewPager);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
    }

    public void productList() {
        // Assigning Activity this to progress dialog.
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getContext());
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PRODUCT_LIST, new Response.Listener<String>() {
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
                        binding.cartCount.setText(j.getString("cartCount"));
                        JSONArray applist = j.getJSONArray("offerList");
                        if (applist != null && applist.length() > 0) {
                            dm.clear();
                            for (int i = 0; i < applist.length(); i++) {
                                modelOffer ds = new modelOffer();
                                JSONObject jsonObject = applist.getJSONObject(i);
                                ds.setDate(jsonObject.getString("date"));
                                ds.setCover(jsonObject.getString("cover"));
                                ds.setOfferUid(jsonObject.getString("offerUid"));
                                dm.add(ds);
                            }

                            Adapter_offer_slider adapter = new Adapter_offer_slider(getContext(), dm);
                            binding.viewpage.setAdapter(adapter);

                        }
                        JSONArray applists = j.getJSONArray("productList");
                        if (applists != null && applists.length() > 0) {
                            dm1.clear();
                            for (int i = 0; i < applists.length(); i++) {
                                modelProduct ds = new modelProduct();
                                JSONObject jsonObject = applists.getJSONObject(i);
                                ds.setProductUid(jsonObject.getString("productUid"));
                                ds.setDate(jsonObject.getString("date"));
                                ds.setName(jsonObject.getString("name"));
                                ds.setDescription(jsonObject.getString("description"));
                                ds.setPrice(jsonObject.getString("price"));
                                ds.setImg(jsonObject.getString("img"));
                                dm1.add(ds);

                            }
                            Adapter_product_list adapter = new Adapter_product_list(getContext(), dm1, new CartCount() {
                                @Override
                                public void cartCount(String cartCount) {
                                    binding.cartCount.setText(cartCount);
                                }
                            });
                            binding.categoryDetailRv.setAdapter(adapter);
                            binding.shimmerViewContainer.setVisibility(View.GONE);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog1() {
        final Dialog dialogs = new Dialog(getContext());
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                productList();

            }
        });
        dialogs.show();
    }


    private final Runnable animateViewPager = new Runnable() {
        @Override
        public void run() {

            if (!pagerMoved) {

                if (binding.viewpage.getCurrentItem() == binding.viewpage.getChildCount()) {
                    binding.viewpage.setCurrentItem(0, true);
                } else {
                    binding.viewpage.setCurrentItem(binding.viewpage.getCurrentItem() + 1, true);
                }

                h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
            }
        }
    };


}