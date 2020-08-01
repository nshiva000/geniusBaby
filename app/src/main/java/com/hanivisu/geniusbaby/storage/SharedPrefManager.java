package com.hanivisu.geniusbaby.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.hanivisu.geniusbaby.Models.LoginModel.LoginModel;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_name";

    private static SharedPrefManager mInstance;
    private Context mCtx;


    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager get_mInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);

        }
        return mInstance;
    }

    public void saveUser(LoginModel login) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", login.getData().getId());
        editor.putString("name", login.getData().getName());
        editor.putString("password", login.getData().getPassword());
        editor.putString("email", login.getData().getEmail());
        editor.putString("phone", login.getData().getPhone());
        editor.putString("address", login.getData().getAddress());
        editor.putBoolean("status", login.getStatus());


        editor.apply();
        editor.commit();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("status", false) == true;

    }

    public String getId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");

    }


    public Integer getOtp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("otp", 0);

    }

    public boolean setOtp(Integer otp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("otp", otp);
        editor.apply();
        return true;
    }

    public boolean setId(Integer id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id.toString());
        editor.apply();
        return true;
    }


    public boolean setDay(String day) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", day);
        editor.apply();
        return true;
    }

    public boolean setWeek(String week) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("week", week);
        editor.apply();
        return true;
    }

    public boolean setSession(String session) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", session);
        editor.apply();
        return true;
    }


    public String getDay() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("day", "");

    }

    public String getWeek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("week", "");

    }

    public String getSession() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("session", "");

    }









    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
