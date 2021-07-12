package com.example.myapp.post.ui.message.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Message;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.ui.message.chat.entity.Msg;
import com.example.myapp.post.ui.message.chat.util.MsgAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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

        //绑定一个定时器，处于当前界面的时候，可以定期拉取对方发来的新消息
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            /**
             * 获得某个MessageBox里的MessageList
             */
            @Override
            public void run() {
                try {
                    //创建连接对象
                    OkHttpClient client = new OkHttpClient(); //创建http客户端
                    FormBody.Builder params = new FormBody.Builder();//创建参数列表
                    params.add("msgBoxId", msgBoxId.toString());
                    Integer lastMsgId = 0;
                    if(msgList.size() != 0){
                        lastMsgId = msgList.get(msgList.size()).getMsgId();
                    }
                    params.add("userId", ApplicationStatus.getUserId().toString());
                    params.add("msgId",lastMsgId.toString());
                    //先检测是否有新消息，
                    Request checkRequest = new Request.Builder()
                            .url(ApplicationStatus.HOST + "/chat/box/check")
                            .post(params.build())
                            .build();
                    Response checkRespose = client.newCall(checkRequest).execute();
                    String res = Objects.requireNonNull(checkRespose.body()).string();
                    Result<Boolean> result = JSON.parseObject(res, new TypeReference<Result<Boolean>>() {});

                    if(result.get()==Boolean.TRUE){//若有结果，则执行消息拉取
                        //拉取请求sync
                        Request syncRequest = new Request.Builder()
                                .url(ApplicationStatus.HOST + "/chat/msg/getsync")
                                .post(params.build())
                                .build();
                        Response syncResponse = client.newCall(syncRequest).execute(); //执行请求
                        String res2 = Objects.requireNonNull(syncResponse.body()).string();
                        Result<List<Message>> messageListResult = JSON.parseObject(res, new TypeReference<Result<List<Message>>>() {});
                        List<Message> messageList = messageListResult.get();
                        List<Msg> msgList = new ArrayList<>();
                        for(Message message:messageList){
                            Msg msg = new Msg(message.getSendTime().toString(),message.getWord());
                            if (message.getUserId() == ApplicationStatus.getUserId()){
                                msg.setMsgType(Msg.TYPE_SENT);
                            }else{
                                msg.setMsgType(Msg.TYPE_RECEIVED);
                            }
                            msgList.add(msg);
                        }
                        //TODO 这里已经获得MsgList，根据需求，将他们展示在页面内

                    }

                }catch (Exception e){

                }
            }
        };

        timer.schedule(timerTask,0,1000); //启动定时器

        //TODO: timer的关闭为如下调用，应该放在这个页面的退出逻辑里
        //timer.cancel();
    }




    private void initMsg() {

        Intent intent = getIntent();
        msgList = intent.getParcelableArrayListExtra("msgList");
        msgBoxId =  intent.getIntExtra("MsgBoxId",-1);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_navigation_back) {
            finish();
        }
    }
}