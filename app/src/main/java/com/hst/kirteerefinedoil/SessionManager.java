package com.hst.kirteerefinedoil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManager {

    public static final String KEY_name = "name";
    public static final String KEY_mobile = "mobile";
    public static final String KEY_UId = "uid";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_state = "state";
    public static final String KEY_city = "city";
    private static final String Pref_Name = "Login_admin";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context1) {
        this.context = context1;
        pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String Uid) {

        editor.putBoolean(IS_LOGIN, true);
        //editor.putString(KEY_password, password);
        editor.putString(KEY_UId, Uid);


        editor.commit();

    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_UId, pref.getString(KEY_UId, ""));

        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}



