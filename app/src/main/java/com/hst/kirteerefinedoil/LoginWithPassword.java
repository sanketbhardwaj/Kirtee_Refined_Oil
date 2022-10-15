package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.hst.kirteerefinedoil.databinding.ActivityLoginWithPasswordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginWithPassword extends AppCompatActivity {
    ActivityLoginWithPasswordBinding activityLoginWithPasswordBinding;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginWithPasswordBinding = ActivityLoginWithPasswordBinding.inflate(getLayoutInflater());
        View view = activityLoginWithPasswordBinding.getRoot();
        setContentView(view);
        session = new SessionManager(LoginWithPassword.this);
        activityLoginWithPasswordBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityLoginWithPasswordBinding.mobile.getText().toString().isEmpty()) {
                    activityLoginWithPasswordBinding.mobile.setError("Please Enter the Mobile Number");
                } else if (activityLoginWithPasswordBinding.password.getText().toString().isEmpty()) {
                    activityLoginWithPasswordBinding.password.setError("Please Enter the Password");
                } else {
                    Login();
                }
            }
        });

    }

    public void Login() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(LoginWithPassword.this, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LoginWithPassword.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_WITH_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                // Matching server responce message to our text.
                JSONObject j = null;
                try {
                    j = new JSONObject(ServerResponse);
                    String result = j.getString("result");

                    if (result.equals("Success")) {
                        // If response matched then show the toast.
                        // Finish the current Login activity
                               /* SplashScreen.Uid = j.getString("userUid");
                                SplashScreen.name = j.getString("name");
                                SplashScreen.mobile_no = j.getString("mobile");
                                SplashScreen.address = j.getString("address");
                                SplashScreen.email = j.getString("email");
                                SplashScreen.state = j.getString("state");
                                SplashScreen.city = j.getString("city");
                                SplashScreen.pinCode = j.getString("pincode");*/
                        session.createLoginSession(j.getString("userUid"));
                        SplashScreen.Uid = j.getString("userUid");
                        SplashScreen.name = j.getString("name");
                        SplashScreen.mobile_no = j.getString("mobile");
                        SplashScreen.address = j.getString("address");
                        SplashScreen.email = j.getString("email");
                        SplashScreen.state = j.getString("state");
                        SplashScreen.city = j.getString("city");
                        SplashScreen.pinCode = j.getString("pincode");

                        Intent intent = new Intent(LoginWithPassword.this, homeScreen.class);
                        startActivity(intent);
                        //  finish();

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginWithPassword.this, AlertDialog.THEME_HOLO_LIGHT);
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
                params.put("mobile", activityLoginWithPasswordBinding.mobile.getText().toString());
                params.put("password", activityLoginWithPasswordBinding.password.getText().toString());
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LoginWithPassword.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(LoginWithPassword.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                Login();

            }
        });
        dialogs.show();
    }


}