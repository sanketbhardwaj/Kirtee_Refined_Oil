package com.hst.kirteerefinedoil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpScreen extends AppCompatActivity {
    String otp, name, mobile, passoword, email, state, city;
    EditText first, second, third, fourth;
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
        otp = getIntent().getStringExtra("otp");
        uid = getIntent().getStringExtra("uid");
        first = findViewById(R.id.first_text);
        second = findViewById(R.id.second_text);
        third = findViewById(R.id.third_text);
        fourth = findViewById(R.id.fourth_text);
        resend = findViewById(R.id.resend);
        verify = findViewById(R.id.verify);
        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        passoword = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        state = getIntent().getStringExtra("state");
        city = getIntent().getStringExtra("city");

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
                    // otp_send_api = spinnerss.getSelectedItem().toString().replace("{mobile}", mobile).replace("{page}", "CREATE_ACCOUNT").replace("{ReferalCode}", refer_code);
                    // otpsend();
                    // Login_resend();
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
}
