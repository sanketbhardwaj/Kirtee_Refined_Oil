package com.hst.kirteerefinedoil.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hst.kirteerefinedoil.SessionManager;
import com.hst.kirteerefinedoil.SplashScreen;
import com.hst.kirteerefinedoil.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    SessionManager session;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        session = new SessionManager(getContext());
        binding.name.setText(SplashScreen.name);
        binding.mobile.setText(SplashScreen.mobile_no);
        binding.email.setText(SplashScreen.email);
        binding.address.setText(SplashScreen.address);
        binding.city.setText(SplashScreen.city);
        binding.pincode.setText(SplashScreen.pinCode);
        binding.state.setText(SplashScreen.state);
        binding.name.setText(SplashScreen.name);
        binding.name.setText(SplashScreen.name);
        binding.name.setText(SplashScreen.name);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                session.logoutUser();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}