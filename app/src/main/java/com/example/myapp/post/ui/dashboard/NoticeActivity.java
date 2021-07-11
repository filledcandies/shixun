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

public class NoticeActivity extends AppCompatActivity {
    private Button button_back;
    private List<Message> lists;
    private notice_adapter adapter;
    private ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        button_back=(Button)findViewById(R.id.back_notice);
        button_back.setOnClickListener(v -> {
            finish();
        });
        listView = (ListView) findViewById(R.id.noticebox);
        lists = new ArrayList<>();
        lists.add(new Message());
        lists.add(new Message());
        adapter = new notice_adapter(lists, NoticeActivity.this);
        listView.setAdapter(adapter);
    }
}
