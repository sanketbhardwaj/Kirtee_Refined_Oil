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
import com.hst.kirteerefinedoil.databinding.ActivityChangePasswordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changePassword extends AppCompatActivity {
    ActivityChangePasswordBinding activityChangePasswordBinding;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        View view = activityChangePasswordBinding.getRoot();
        setContentView(view);
        session = new SessionManager(changePassword.this);
        activityChangePasswordBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityChangePasswordBinding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityChangePasswordBinding.currentPassword.getText().toString().isEmpty()) {
                    activityChangePasswordBinding.currentPassword.setError("Please Enter Current Password");
                } else if (activityChangePasswordBinding.newPassword.getText().toString().isEmpty()) {
                    activityChangePasswordBinding.newPassword.setError("Please Enter New Password");
                } else if (activityChangePasswordBinding.confirmPassword.getText().toString().isEmpty()) {
                    activityChangePasswordBinding.confirmPassword.setError("Please Enter Again New Password");
                } else if (!activityChangePasswordBinding.newPassword.getText().equals(activityChangePasswordBinding.confirmPassword.getText())) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(changePassword.this, AlertDialog.THEME_HOLO_LIGHT);
                    alert.setTitle("Notice");
                    alert.setMessage("New Password and Confirm Password should be same!");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    updatePassword();
                }
            }
        });
    }

    public void updatePassword() {
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(changePassword.this, AlertDialog.THEME_HOLO_LIGHT);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(changePassword.this);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_PASSWORD, new Response.Listener<String>() {
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

                        AlertDialog.Builder alert = new AlertDialog.Builder(changePassword.this, AlertDialog.THEME_HOLO_LIGHT);
                        alert.setTitle("Notice");
                        alert.setMessage(j.getString("status"));
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                session.logoutUser();
                                startActivity(new Intent(changePassword.this, LoginWithBackground.class));
                            }
                        });
                        alert.show();
                        //  finish();

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(changePassword.this, AlertDialog.THEME_HOLO_LIGHT);
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
                params.put("password", activityChangePasswordBinding.newPassword.getText().toString());
                params.put("currentPassword", activityChangePasswordBinding.currentPassword.getText().toString());
                params.put("userUid", SplashScreen.Uid);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(changePassword.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void NetworkDialog() {
        final Dialog dialogs = new Dialog(changePassword.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.networkdialog);
        dialogs.setCanceledOnTouchOutside(false);
        Button done = dialogs.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                updatePassword();

            }
        });
        dialogs.show();
    }
}