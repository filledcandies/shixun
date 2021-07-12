package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.example.myapp.logIn.ui.LogInActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;
import com.example.myapp.post.ui.dashboard.NoticeActivity;
import com.example.myapp.post.ui.post.Post;
import com.example.myapp.post.ui.post.ShowPostActivity;
import com.example.myapp.service.NoticeService;
import com.example.myapp.sqliteuitil.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Integer userId = ApplicationStatus.getUserId();
//        startActivity(new Intent(MainActivity.this, ShowPostActivity.class));
        new Handler().postDelayed(() -> {
            if (userId == null || userId <= 0) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, PostActivity.class));
            }
        }, 2000);
    }
}