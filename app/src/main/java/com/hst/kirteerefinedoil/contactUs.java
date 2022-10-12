package com.hst.kirteerefinedoil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.kirteerefinedoil.databinding.ActivityContactUsBinding;

public class contactUs extends AppCompatActivity {
    ActivityContactUsBinding activityContactUsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContactUsBinding = ActivityContactUsBinding.inflate(getLayoutInflater());
        View view = activityContactUsBinding.getRoot();
        setContentView(view);
        activityContactUsBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityContactUsBinding.callUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + activityContactUsBinding.mobileNumber.getText().toString());

                // Create the intent and set the data for the
                // intent as the phone number.
                Intent i = new Intent(Intent.ACTION_DIAL, u);

                try {
                    // Launch the Phone app's dialer with a phone
                    // number to dial a call.
                    startActivity(i);
                } catch (SecurityException s) {
                    // show() method display the toast with
                    // exception message.
                }

            }
        });
        activityContactUsBinding.emailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {activityContactUsBinding.emailId.getText().toString()};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
                } catch (ActivityNotFoundException e) {
                }
            }
        });
        activityContactUsBinding.whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("smsto:" + activityContactUsBinding.whatsappNumber.getText().toString());
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(i);
                } catch (Exception e) {
                    // Toast.makeText(contact_us.this, "whts not installed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}