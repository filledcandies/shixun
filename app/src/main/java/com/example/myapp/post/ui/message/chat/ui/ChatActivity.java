package com.example.myapp.post.ui.message.chat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Message;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.logIn.ui.RegisterActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;
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
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    //对话 Box的 Id
    private Integer msgBoxId;
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
                    //创建本地Msg对象，并更新显示
                    Msg msg = new Msg(input,Msg.TYPE_SENT);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    InputText.setText("");

                    //创建 Message对象，用于上传
                    Message message = new Message();
                    message.setMessageBoxId(msgBoxId);
                    message.setUserId(ApplicationStatus.getUserId());
                    message.setWord(input);

                    //将信息上传至服务器
                    new Thread(()->{
                        try {
                            String json = JSON.toJSONString(message);
                            OkHttpClient client = new OkHttpClient(); //创建http客户端
                            Request request = new Request.Builder()
                                    .url(ApplicationStatus.HOST + "/chat/msg/upload/msg")
                                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                                    .build();  //编写请求的

                            Response response = client.newCall(request).execute();
                            String res = Objects.requireNonNull(response.body()).string();
                            Result<User> result = JSON.parseObject(res, new TypeReference<Result<User>>() {});
                        } catch (Exception e) {

                        }
                    }).start();
                }
            }
        });

    }

    private void initMsg() {

        Intent intent = getIntent();
        msgList = intent.getParcelableArrayListExtra("msgList");
        msgBoxId =  intent.getParcelableExtra("MsgBoxId");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_navigation_back) {
            finish();
        }
    }
}