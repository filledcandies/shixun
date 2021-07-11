package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapp.logIn.ui.LogInActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.sqliteuitil.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LogInActivity.class)));
        ApplicationStatus.setUserId(1234);

    }
}