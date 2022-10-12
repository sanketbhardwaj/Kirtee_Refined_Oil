package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    public static String Uid, name, state, city, mobile_no, address, email, pinCode;
    private final int SPLASH_DISPLAY_LENGTH = 500;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager session;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(SplashScreen.this);
        HashMap<String, String> users = session.getUserDetails();
        //  id = users.get(session.KEY_ID);
        uid = users.get(SessionManager.KEY_UId);
        if (uid.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Uid = "N";
                    name = "N";
                    mobile_no = "N";
                    address = "N";
                    email = "N";
                    state = "N";
                    city = "N";
                    pinCode = "N";
                    Intent intent = new Intent(SplashScreen.this, homeScreen.class);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_DISPLAY_LENGTH);

        } else {
            getUserInfo();

        }

    }

    public void getUserInfo() {
        // Assigning Activity this to progress dialog.

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(SplashScreen.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                        Uid = j.getString("userUid");
                        name = j.getString("name");
                        mobile_no = j.getString("mobile");
                        address = j.getString("address");
                        email = j.getString("email");
                        state = j.getString("state");
                        city = j.getString("city");
                        pinCode = j.getString("pincode");
                        Intent intent = new Intent(SplashScreen.this, homeScreen.class);
                        startActivity(intent);
                        finish();


                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SplashScreen.this);
                        alert.setTitle("Notice");
                        alert.setMessage(j.getString("status"));
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
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
                params.put("userUid", uid);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(SplashScreen.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = (Button) dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                getUserInfo();

            }
        });
        dialogs.show();
    }

}
