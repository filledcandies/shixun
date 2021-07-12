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

    public static Integer getUserId() {
        return instance.userId;
    }


}
