package com.hst.kirteerefinedoil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.kirteerefinedoil.databinding.ActivityLoginWithBackgroundBinding;

public class LoginWithBackground extends AppCompatActivity {
    ActivityLoginWithBackgroundBinding activityLoginWithBackgroundBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginWithBackgroundBinding = ActivityLoginWithBackgroundBinding.inflate(getLayoutInflater());
        View view = activityLoginWithBackgroundBinding.getRoot();
        setContentView(view);
        activityLoginWithBackgroundBinding.loginWithOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginWithBackground.this, LoginActivityWithOtp.class));
            }
        });
        activityLoginWithBackgroundBinding.loginWithPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginWithBackground.this, LoginWithPassword.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}