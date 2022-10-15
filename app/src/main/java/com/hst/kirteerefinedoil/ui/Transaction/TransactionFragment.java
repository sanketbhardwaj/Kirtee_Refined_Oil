package com.hst.kirteerefinedoil.ui.Transaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

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
import com.hst.kirteerefinedoil.Adapter_transaction;
import com.hst.kirteerefinedoil.LoginActivityWithOtp;
import com.hst.kirteerefinedoil.R;
import com.hst.kirteerefinedoil.SplashScreen;
import com.hst.kirteerefinedoil.Utilities.Constant;
import com.hst.kirteerefinedoil.databinding.FragmentTransactionBinding;
import com.hst.kirteerefinedoil.modelOrderHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionFragment extends Fragment {
    Adapter_transaction adapter;
    RequestQueue requestQueue;
    ArrayList<modelOrderHistory> dm = new ArrayList<modelOrderHistory>();
    String uid, client_name;
    int flag = 0;
    String mobile, client_uid;
    String phNumber;
    RelativeLayout arrowback3;
    private FragmentTransactionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        if (SplashScreen.Uid.equals("N")) {
            startActivity(new Intent(getContext(), LoginActivityWithOtp.class));
        }
        /*binding.arrowback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });*/
        binding.rvFollowUpsList.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvFollowUpsList.setLayoutManager(mLayoutManager);
        binding.rvFollowUpsList.setItemAnimator(new DefaultItemAnimator());
        orderHistory();

        return binding.getRoot();
    }

    public void orderHistory() {
        // Assigning Activity this to progress dialog.
        // Creating Volley newRequestQueue .

        requestQueue = Volley.newRequestQueue(getActivity());
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ORDER_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                //    lottie.setVisibility(View.GONE);
                // dialogs.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);


                    JSONArray applist = j.getJSONArray("orderHistory");

                    if (applist != null && applist.length() > 0) {
                        for (int i = 0; i < applist.length(); i++) {
                            modelOrderHistory ds = new modelOrderHistory();
                            JSONObject jsonObject = applist.getJSONObject(i);

                            ds.setOrderUid(jsonObject.getString("orderUid"));
                            ds.setDate(jsonObject.getString("date"));
                            ds.setInvoiceNo(jsonObject.getString("invoiceNo"));
                            ds.setPaymentType(jsonObject.getString("paymentType"));
                            ds.setCoupon(jsonObject.getString("coupon"));
                            ds.setSubTotal(jsonObject.getString("subTotal"));
                            ds.setDiscountAmount(jsonObject.getString("discountAmount"));
                            ds.setGrandPricePaid(jsonObject.getString("grandPricePaid"));
                            ds.setRounded(jsonObject.getString("rounded"));
                            ds.setGrandTotal(jsonObject.getString("grandTotal"));
                            ds.setGst(jsonObject.getString("gst"));
                            ds.setCgst(jsonObject.getString("cgst"));
                            ds.setSgst(jsonObject.getString("sgst"));
                            ds.setOrderStatus(jsonObject.getString("orderStatus"));
                            ds.setDeliveredTime(jsonObject.getString("deliveredTime"));
                            ds.setCancelledTime(jsonObject.getString("cancelledTime"));
                            ds.setRefundTime(jsonObject.getString("refundTime"));
                            ds.setAddress(jsonObject.getString("address"));
                            ds.setCity(jsonObject.getString("city"));
                            ds.setPincode(jsonObject.getString("pincode"));
                            ds.setState(jsonObject.getString("state"));
                            ds.setTransactionId(jsonObject.getString("transactionId"));
                            ds.setPaymentStatus(jsonObject.getString("paymentStatus"));
                            ds.setInvoice(jsonObject.getString("invoice"));
                            ds.setTotal(jsonObject.getString("total"));
                            ds.setItems(jsonObject.getString("items"));
                            dm.add(ds);

                        }
                        adapter = new Adapter_transaction(getActivity(), dm);      //ds=model       d=Data
                        binding.rvFollowUpsList.setAdapter(adapter);
                        binding.shimmerViewContainer.setVisibility(View.GONE);

                    }


                    // Finish the current Login activity.
                    // Opening the user profile activity using intent.

                            /*else{
                                String msg = j.getString("msg");
                                // Showing Echo Response Message Coming From Server.
                                Toast.makeText(customer_names.this, msg, Toast.LENGTH_LONG).show();

                            }*/
                } catch (JSONException e) {
                    // Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.noOrderHistory.setVisibility(View.VISIBLE);
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.logout13211.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Hiding the progress dialog after all task complete.
                //     Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                //      lottie.setVisibility(View.GONE);
                //  dialogs.dismiss();

                NetworkDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("userUid", "1");

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(getActivity());
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                orderHistory();

            }
        });
        dialogs.show();
    }

}