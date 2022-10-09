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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hst.kirteerefinedoil.Utilities.Constant;
import com.hst.kirteerefinedoil.databinding.ActivityChangeAddressBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Change_Address extends AppCompatActivity {
    ActivityChangeAddressBinding activityChangeAddressBinding;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangeAddressBinding = ActivityChangeAddressBinding.inflate(getLayoutInflater());
        View view = activityChangeAddressBinding.getRoot();
        setContentView(view);
        activityChangeAddressBinding.address.setText(SplashScreen.address);
        activityChangeAddressBinding.state.setText(SplashScreen.state);
        activityChangeAddressBinding.pincode.setText(SplashScreen.pinCode);
        activityChangeAddressBinding.city.setText(SplashScreen.city);
        activityChangeAddressBinding.updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAddress();
            }
        });
    }

    public void updateAddress() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(Change_Address.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Change_Address.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_ADDRESS,
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
                                SplashScreen.address = activityChangeAddressBinding.address.getText().toString();
                                SplashScreen.city = activityChangeAddressBinding.city.getText().toString();
                                SplashScreen.pinCode = activityChangeAddressBinding.pincode.getText().toString();
                                SplashScreen.state = activityChangeAddressBinding.state.getText().toString();
                                AlertDialog.Builder alert = new AlertDialog.Builder(Change_Address.this);
                                alert.setTitle("Notice");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                        startActivity(new Intent(Change_Address.this, cartList.class));
                                    }
                                });
                                alert.setMessage(j.getString("status"));
                                alert.show();

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(Change_Address.this);
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
                params.put("userUid", SplashScreen.Uid);
                params.put("address", activityChangeAddressBinding.address.getText().toString());
                params.put("city", activityChangeAddressBinding.city.getText().toString());
                params.put("pincode", activityChangeAddressBinding.pincode.getText().toString());
                params.put("state", activityChangeAddressBinding.state.getText().toString());


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Change_Address.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(Change_Address.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = (Button) dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                updateAddress();

            }
        });
        dialogs.show();
    }
}