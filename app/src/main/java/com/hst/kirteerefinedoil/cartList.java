package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cartList extends AppCompatActivity {
    public static int clickedCount;
    ActivityCartListBinding activityCartListBinding;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String paymentMethod;
    String grandPaidPrice, orderId, mid, txnToken, callBackUrl, paymentUrl, total, subTotal, discount, transactionId, gst, grandTotal, rounded;
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
        activityCartListBinding.address.setText(SplashScreen.address);
        activityCartListBinding.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Delivery Address");
                alert.setMessage("Address: " + SplashScreen.address + "\n" + "City: " + SplashScreen.city + "\n" + "Pincode: " + SplashScreen.pinCode + "\n" + "State: " + SplashScreen.state);
                alert.setPositiveButton("OK", null);
                alert.setNegativeButton("Change Address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        startActivity(new Intent(cartList.this, Change_Address.class));
                    }
                });
                alert.show();
            }
        });

        activityCartListBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCartList();
        activityCartListBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityCartListBinding.cod.isChecked()) {
                    paymentMethod = "COD";
                    orderPlaced();
                } else if (activityCartListBinding.online.isChecked()) {
                    paymentMethod = "Online";
                    clickedCount++;
                    StartPayment();
                } else {
                    Toast.makeText(cartList.this, "Select payment method", Toast.LENGTH_SHORT).show();
                }


            }
        });
        activityCartListBinding.applyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activityCartListBinding.coupon.getText().toString().isEmpty()) {
                    applyCoupon();
                } else {
                    Toast.makeText(cartList.this, "Enter coupon code", Toast.LENGTH_SHORT).show();
                }

            }
        });
        activityCartListBinding.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
            }
        });
    }

    public void getCartList() {
       /* progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Assigning Activity this to progress dialog.
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CART_LIST, new Response.Listener<String>() {
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
                        // progressDialog.dismiss();
                        rounded = j.getString("rounded");
                        subTotal = j.getString("subTotal");
                        gst = j.getString("gst");
                        discount = j.getString("discount");
                        grandPaidPrice = j.getString("grandTotalPrice");
                        grandTotal = j.getString("grandTotal");
                        total = j.getString("subTotal");

                        if (!j.getString("rounded").equals("0")) {
                            activityCartListBinding.rounded.setText("₹ " + j.getString("rounded") + "/-");
                            activityCartListBinding.grandTotal.setText("₹ " + j.getString("grandTotal") + "/-");
                        } else {
                            activityCartListBinding.roundedLayout.setVisibility(View.GONE);
                            activityCartListBinding.grandTotalLayout.setVisibility(View.GONE);
                        }
                        if (!j.getString("discount").equals("0")) {
                            activityCartListBinding.discount.setText("₹ " + j.getString("discount") + "/-");
                        } else {
                            activityCartListBinding.discountLayout.setVisibility(View.GONE);
                        }

                        activityCartListBinding.subTotal.setText(j.getString("subTotal"));

                        activityCartListBinding.total.setText("₹ " + j.getString("subTotal") + "/-");


                        activityCartListBinding.grandTotalPrice.setText("₹ " + j.getString("grandTotalPrice") + "/-");

                        activityCartListBinding.totalItems.setText(j.getString("totalItems"));
                        activityCartListBinding.totalQtys.setText(j.getString("totalQtys"));


                        activityCartListBinding.pay.setText("PAY ₹ " + j.getString("grandTotalPrice") + "/-");
                        activityCartListBinding.gst.setText("₹ " + j.getString("gst") + "/-");

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
                                    //Toast.makeText(cartList.this, text, Toast.LENGTH_SHORT).show();

                                    dm.clear();
                                    getCartList();
                                }


                            });

                            activityCartListBinding.rvCartList.setAdapter(adapter);
                            if (!activityCartListBinding.coupon.getText().toString().isEmpty()) {
                                applyCoupon();
                            }
                            activityCartListBinding.shimmerViewContainer.setVisibility(View.GONE);
                        }
                    }

                } catch (JSONException e) {
                    // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    activityCartListBinding.noCartList.setVisibility(View.VISIBLE);
                    activityCartListBinding.shimmerViewContainer.setVisibility(View.GONE);
                    activityCartListBinding.screenData.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
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
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                getCartList();

            }
        });
        dialogs.show();
    }

    public void StartPayment() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(cartList.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.START_TRANSACTION, new Response.Listener<String>() {
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
                        orderId = j.getString("orderId");
                        mid = j.getString("mid");
                        txnToken = j.getString("txnToken");
                        callBackUrl = j.getString("callBackUrl");
                        paymentUrl = j.getString("paymentUrl");
                        PaytmGateway();
                                /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();*/

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
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
                params.put("userUid", SplashScreen.Uid);
                params.put("grandPricePaid", grandPaidPrice);
                params.put("clicked", String.valueOf(clickedCount));

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(cartList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(cartList.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                clickedCount++;
                StartPayment();

            }
        });
        dialogs.show();
    }

    public void clearAll() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(cartList.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CLEAR_CART, new Response.Listener<String>() {
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
                        dm.clear();
                                /*AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();*/
                        getCartList();

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
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
                NetworkDialog5();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("userUid", SplashScreen.Uid);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(cartList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog5() {
        final Dialog dialogs = new Dialog(cartList.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                clearAll();

            }
        });
        dialogs.show();
    }

    public void applyCoupon() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(cartList.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHECK_COUPON, new Response.Listener<String>() {
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
                        rounded = j.getString("rounded");
                        subTotal = j.getString("subTotal");
                        gst = j.getString("gst");
                        discount = j.getString("discount");
                        grandPaidPrice = j.getString("grandTotalPrice");
                        grandTotal = j.getString("grandTotal");
                        grandPaidPrice = j.getString("grandTotalPrice");
                        total = j.getString("total");


                        if (!j.getString("rounded").equals("0")) {
                            activityCartListBinding.rounded.setText("₹ " + j.getString("rounded") + "/-");
                            activityCartListBinding.grandTotal.setText("₹ " + j.getString("grandTotal") + "/-");
                            activityCartListBinding.roundedLayout.setVisibility(View.VISIBLE);
                            activityCartListBinding.grandTotalLayout.setVisibility(View.VISIBLE);
                        } else {
                            activityCartListBinding.roundedLayout.setVisibility(View.GONE);
                            activityCartListBinding.grandTotalLayout.setVisibility(View.GONE);
                        }
                        if (!j.getString("discount").equals("0")) {
                            activityCartListBinding.discount.setText("₹ " + j.getString("discount") + "/-");
                            activityCartListBinding.discountLayout.setVisibility(View.VISIBLE);
                        } else {
                            activityCartListBinding.discountLayout.setVisibility(View.GONE);
                        }

                        activityCartListBinding.subTotal.setText(j.getString("subTotal"));

                        activityCartListBinding.total.setText("₹ " + j.getString("total") + "/-");


                        activityCartListBinding.grandTotalPrice.setText("₹ " + j.getString("grandTotalPrice") + "/-");

                        /*activityCartListBinding.totalItems.setText(j.getString("totalItems"));
                        activityCartListBinding.totalQtys.setText(j.getString("totalQtys"));
*/

                        activityCartListBinding.pay.setText("PAY ₹ " + j.getString("grandTotalPrice") + "/-");
                        activityCartListBinding.gst.setText("₹ " + j.getString("gst") + "/-");

                        activityCartListBinding.cgst.setText(j.getString("cgst"));
                        activityCartListBinding.sgst.setText(j.getString("sgst"));





                        /*activityCartListBinding.rounded.setText(j.getString("rounded"));
                        activityCartListBinding.subTotal.setText(j.getString("subTotal"));
                        activityCartListBinding.total.setText(j.getString("total"));

                        activityCartListBinding.grandTotal.setText(j.getString("grandTotal"));

                        activityCartListBinding.grandTotalPrice.setText(j.getString("grandTotalPrice"));
                        activityCartListBinding.totalItems.setText(j.getString("totalItems"));
                        activityCartListBinding.totalQtys.setText(j.getString("totalQtys"));
                        activityCartListBinding.discount.setText(j.getString("discount"));
                        activityCartListBinding.gst.setText(j.getString("gst"));
                        activityCartListBinding.cgst.setText(j.getString("cgst"));
                        activityCartListBinding.sgst.setText(j.getString("sgst"));*/
                        //PaytmGateway();
                                /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();*/

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
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
                NetworkDialog3();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("userUid", SplashScreen.Uid);
                params.put("coupon", activityCartListBinding.coupon.getText().toString());
                params.put("subTotal", total);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(cartList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog3() {
        final Dialog dialogs = new Dialog(cartList.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                applyCoupon();

            }
        });
        dialogs.show();
    }

    public void CheckPaymentStatus() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(cartList.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHECK_TRANSACTION, new Response.Listener<String>() {
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
                                /*orderId = j.getString("orderId");
                                mid = j.getString("mid");
                                txnToken = j.getString("txnToken");
                                callBackUrl = j.getString("callBackUrl");*/
                                /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();*/
                        transactionId = j.getString("txnId");
                        orderPlaced();
                                /*Intent intent = new Intent(cartList.this, PaymentSuccessScreen.class);
                                startActivity(intent);*/


                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
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
                NetworkDialog2();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("orderId", orderId);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(cartList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog2() {
        final Dialog dialogs = new Dialog(cartList.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                CheckPaymentStatus();

            }
        });
        dialogs.show();
    }

    public void orderPlaced() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(cartList.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(cartList.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PLACE_ORDER, new Response.Listener<String>() {
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
                                /*orderId = j.getString("orderId");
                                mid = j.getString("mid");
                                txnToken = j.getString("txnToken");
                                callBackUrl = j.getString("callBackUrl");*/
                                /*AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", null);
                                alert.setMessage(j.getString("status"));
                                alert.show();*/
                        clickedCount = 0;
                        Intent intent = new Intent(cartList.this, PaymentSuccessScreen.class);
                        intent.putExtra("currentDate", j.getString("currentDate"));
                        intent.putExtra("currentTime", j.getString("currentTime"));
                        intent.putExtra("transactionId", transactionId);
                        intent.putExtra("grandPaidPrice", grandPaidPrice);
                        intent.putExtra("paymentMode", paymentMethod);
                        startActivity(intent);


                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                        alert.setTitle("Notice");
                        alert.setMessage(j.getString("status"));
                        alert.setPositiveButton("OK", null);
                        alert.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                NetworkDialog4();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.

                params.put("userUid", SplashScreen.Uid);
                params.put("paymentType", paymentMethod);
                if (!activityCartListBinding.coupon.getText().toString().isEmpty()) {
                    params.put("coupon", activityCartListBinding.coupon.getText().toString());
                }
                params.put("subTotal", subTotal);
                params.put("total", total);
                params.put("discountAmount", discount);
                params.put("address", SplashScreen.address);
                params.put("city", SplashScreen.city);
                params.put("state", SplashScreen.state);
                params.put("pincode", SplashScreen.pinCode);
                if (paymentMethod.equals("Online")) {
                    params.put("transactionId", transactionId);
                    params.put("orderId", orderId);
                }
                params.put("gst", gst);
                params.put("grandTotal", grandTotal);
                params.put("rounded", rounded);
                params.put("grandPricePaid", grandPaidPrice);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(cartList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog4() {
        final Dialog dialogs = new Dialog(cartList.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                orderPlaced();

            }
        });
        dialogs.show();
    }

    public void PaytmGateway() {
        PaytmOrder paytmOrder = new PaytmOrder(orderId, mid, txnToken, grandPaidPrice, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {

            @Override
            public void onTransactionResponse(Bundle bundle) {
                //Toast.makeText(cartList.this, "Response (onTransactionResponse) : " + bundle.toString(), Toast.LENGTH_SHORT).show();
                CheckPaymentStatus();
            }

            @Override
            public void networkNotAvailable() {
                NetworkDialog();
            }

            @Override
            public void onErrorProceed(String s) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Notice");
                alert.setMessage(s);
                alert.setPositiveButton("OK", null);
                alert.show();
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Notice");
                alert.setMessage(s);
                alert.setPositiveButton("OK", null);
                alert.show();
            }

            @Override
            public void someUIErrorOccurred(String s) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Notice");
                alert.setMessage(s);
                alert.setPositiveButton("OK", null);
                alert.show();
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Notice");
                alert.setMessage(s + "\n" + s1);
                alert.setPositiveButton("OK", null);
                alert.show();
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Toast.makeText(cartList.this, "Transaction Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cartList.this);
                alert.setTitle("Notice");
                alert.setMessage(s);
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        });

        transactionManager.setShowPaymentUrl(paymentUrl + "/theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, 2);
    }
}