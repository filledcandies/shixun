package com.example.myapp.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class ApplicationStatus {

    private static final ApplicationStatus instance = new ApplicationStatus();

    public static final String HOST = "http://10.17.39.110:8000";

    private static final String FILE_NAME = "data";

    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private Integer userId;

    private ApplicationStatus() {
        userId = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getInt(USER_ID, 0);
    }

    public static void setUserId(Integer userId) {
        setUserId(userId, true);
    }

    public static void setUserId(Integer userId, boolean saveToLocal) {
        instance.userId = userId;
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(USER_ID, saveToLocal ? userId : 0);
        editor.apply();
    }

    public static void saveUser(String email, String password) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String getEmail() {
        return MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(EMAIL, "");
    }

    public static String getPassword() {
        return MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(PASSWORD, "");
    }

    public static Integer getUserId() {
        return instance.userId;
    }


}
