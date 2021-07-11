package com.example.myapp.post.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.MainActivity;
import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class PPChangeActivity extends AppCompatActivity {
    private List<Message> lists;
    private pp_adapter adapter;
    private ListView listView;
    private Button button_back,button_save;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppchange);

        button_back = (Button) findViewById(R.id.back_pp);
        button_back.setOnClickListener(v -> {
            finish();
        });
        button_save = (Button) findViewById(R.id.save_pp);
        button_save.setOnClickListener(v -> {
            finish();
        });

        listView = (ListView) findViewById(R.id.ppchangebox);
        lists = new ArrayList<>();
        lists.add(new Message());
        lists.add(new Message());
        lists.add(new Message());
        adapter = new pp_adapter(lists,PPChangeActivity.this);
        listView.setAdapter(adapter);
    }
}
