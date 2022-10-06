package com.hst.kirteerefinedoil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hst.kirteerefinedoil.Utilities.Constant;
import com.hst.kirteerefinedoil.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;
    SessionManager session;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        init();
        setContentView(view);

    }

    //This function is use to initialize
    private void init() {

        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityLoginBinding.mobile.getText().toString().isEmpty()) {
                    activityLoginBinding.mobile.setError("Please Enter the Mobile Number");
                } else {
                    Login();
                }
            }
        });
    }

    public void Login() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(LoginActivity.this);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
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

                            if (result.equals("Success")) {
                                // If response matched then show the toast.
                                // Finish the current Login activity
                                SplashScreen.Uid = j.getString("userUid");
                                SplashScreen.name = j.getString("name");
                                SplashScreen.mobile_no = j.getString("mobile");
                                SplashScreen.address = j.getString("address");
                                SplashScreen.email = j.getString("email");
                                SplashScreen.state = j.getString("state");
                                SplashScreen.city = j.getString("city");
                                SplashScreen.pinCode = j.getString("pincode");

                                String otp = j.getString("otp");
                                Toast.makeText(LoginActivity.this, otp, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, OtpScreen.class);
                                intent.putExtra("uid", j.getString("userUid"));
                                intent.putExtra("otp", otp);
                                intent.putExtra("otp", otp);
                                intent.putExtra("otp", otp);
                                intent.putExtra("otp", otp);
                                intent.putExtra("otp", otp);

                                startActivity(intent);
                                finish();

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
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
                params.put("mobile", activityLoginBinding.mobile.getText().toString());
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(LoginActivity.this);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}