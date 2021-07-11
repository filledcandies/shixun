package com.example.myapp.post.ui.message.chat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.post.ui.message.chat.entity.Msg;
import com.example.myapp.post.ui.message.chat.util.MsgAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    //存放消息
    private List<Msg> msgList=new ArrayList<>();
    //输入框
    private EditText InputText;
    //发送按钮
    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
//                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initMsg();
        InputText = (EditText) findViewById(R.id.edit_chat_1);
        send = (Button) findViewById(R.id.send_test_1);
        msgRecyclerView = (RecyclerView) findViewById(R.id.chat_content_1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = InputText.getText().toString();
                if(!"".equals(input)){
                    Msg msg = new Msg(input,Msg.TYPE_SENT);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    InputText.setText("");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void initMsg() {

        Intent intent = getIntent();
        msgList = intent.getParcelableArrayListExtra("msgList");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_navigation_back) {
            finish();
        }
    }
}