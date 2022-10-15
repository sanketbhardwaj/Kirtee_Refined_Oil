package com.hst.kirteerefinedoil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpScreen extends AppCompatActivity {
    String otp, name, mobile, pincode, address, email, state, city;
    EditText first, second, third, fourth;
    RequestQueue requestQueue;
    SessionManager session;
    Button verify;
    TextView resend;
    ProgressDialog progressDialog;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        session = new SessionManager(getApplicationContext());

        first = findViewById(R.id.first_text);
        second = findViewById(R.id.second_text);
        third = findViewById(R.id.third_text);
        fourth = findViewById(R.id.fourth_text);
        resend = findViewById(R.id.resend);
        verify = findViewById(R.id.verify);
        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        state = getIntent().getStringExtra("state");
        city = getIntent().getStringExtra("city");
        pincode = getIntent().getStringExtra("pincode");
        address = getIntent().getStringExtra("address");
        otp = getIntent().getStringExtra("otp");
        uid = getIntent().getStringExtra("uid");
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend.setText("Seconds Remaining: " + millisUntilFinished / 1000);

                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                resend.setText("RESEND CODE");
            }

        }.start();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resend.getText().toString().equals("RESEND CODE")) {
                    resendOtp();
                    try {
                        new CountDownTimer(120000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                resend.setText("Seconds Remaining: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                resend.setText("RESEND CODE");
                            }

                        }.start();
                        //Toast.makeText(this, "out try", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        //Toast.makeText(this, "In Catch", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp_t = first.getText().toString() + second.getText().toString() + third.getText().toString() + fourth.getText().toString();
                if (otp_t.equals(otp)) {
                    //  Login();
                    session.createLoginSession(uid);
                    SplashScreen.Uid = getIntent().getStringExtra("uid");
                    SplashScreen.name = getIntent().getStringExtra("name");
                    SplashScreen.mobile_no = getIntent().getStringExtra("mobile");
                    SplashScreen.address = getIntent().getStringExtra("address");
                    SplashScreen.email = getIntent().getStringExtra("email");
                    SplashScreen.state = getIntent().getStringExtra("state");
                    SplashScreen.city = getIntent().getStringExtra("city");
                    SplashScreen.pinCode = getIntent().getStringExtra("pincode");
                    Intent intent = new Intent(OtpScreen.this, homeScreen.class);
                    startActivity(intent);


                } else {

                    Toast.makeText(OtpScreen.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();

                }
            }
        });
        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (first.getText().length() == 1) {
                    second.requestFocus();

                }
            }
        });
        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (second.getText().length() == 1) {
                    third.requestFocus();
                } else {
                    first.requestFocus();
                }
            }
        });
        third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (third.getText().length() == 1) {
                    fourth.requestFocus();
                } else {
                    second.requestFocus();
                }
            }
        });
        fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (first.getText().length() == 1) {
                    InputMethodManager imm = (InputMethodManager) OtpScreen.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = OtpScreen.this.getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(OtpScreen.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


            }
        });
    }

    public void resendOtp() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(OtpScreen.this, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(OtpScreen.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL, new Response.Listener<String>() {
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
                        uid = j.getString("userUid");
                        SplashScreen.Uid = j.getString("userUid");
                        SplashScreen.name = j.getString("name");
                        SplashScreen.mobile_no = j.getString("mobile");
                        SplashScreen.address = j.getString("address");
                        SplashScreen.email = j.getString("email");
                        SplashScreen.state = j.getString("state");
                        SplashScreen.city = j.getString("city");
                        SplashScreen.pinCode = j.getString("pincode");

                        otp = j.getString("otp");
                        Toast.makeText(OtpScreen.this, otp, Toast.LENGTH_SHORT).show();


                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(OtpScreen.this, AlertDialog.THEME_HOLO_LIGHT);
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
                params.put("mobile", mobile);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(OtpScreen.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(OtpScreen.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                resendOtp();

            }
        });
        dialogs.show();
    }
}
