package com.hst.kirteerefinedoil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.kirteerefinedoil.databinding.ActivityPaymentSuccessScreenBinding;

public class PaymentSuccessScreen extends AppCompatActivity {
    ActivityPaymentSuccessScreenBinding activityPaymentSuccessScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentSuccessScreenBinding = ActivityPaymentSuccessScreenBinding.inflate(getLayoutInflater());
        View view = activityPaymentSuccessScreenBinding.getRoot();
        setContentView(view);
        activityPaymentSuccessScreenBinding.dateSuccess.setText(getIntent().getStringExtra("currentDate"));
        activityPaymentSuccessScreenBinding.timeSuccess.setText(getIntent().getStringExtra("currentTime"));
        activityPaymentSuccessScreenBinding.amountPaid.setText(getIntent().getStringExtra("grandPaidPrice"));
        activityPaymentSuccessScreenBinding.transactionId.setText(getIntent().getStringExtra("transactionId"));
        activityPaymentSuccessScreenBinding.paymentMode.setText(getIntent().getStringExtra("paymentMode"));
        activityPaymentSuccessScreenBinding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccessScreen.this, homeScreen.class);
                startActivity(intent);
            }
        });
    }
}