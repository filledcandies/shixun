package com.example.myapp.post.ui.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.ServerPost;
import com.example.myapp.entity.User;
import com.example.myapp.logIn.ui.LogInActivity;
import com.example.myapp.logIn.ui.ResetPwdActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_post);
        ImageView goBack = findViewById(R.id.add_post_back);
        CheckBox onlyOwner = (CheckBox) findViewById(R.id.checkBox);
        goBack.setOnClickListener(v -> finish());
        Button send = (Button) findViewById(R.id.add_post_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText postMsg = (EditText) findViewById(R.id.post_msg);
                String msg = String.valueOf(postMsg.getText());
                String time;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                time = format.format(new Date());
                if(!msg.equals("") &&msg!=null){
                    Log.i("post", msg+"  "+time+" "+onlyOwner.isChecked()+" "+ApplicationStatus.getUserId());
                    Post post = new Post(ApplicationStatus.getUserId(),msg, onlyOwner.isChecked(), time);
                    System.out.println(post.toString());
                    sendPost(post);
                }
            }
        });
    }
    //将post上传到服务端
    public void sendPost(Post post){
        new Thread(() -> {
            try {
                ServerPost post1 = new ServerPost();
                post1.setUserId(post.getOwnerId());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                post1.setCreateTime(format.parse(post.getCreateTime()));
                post1.setMessage(post.getMessage());
                post1.setVisibility(!post.getOnlyOwner());
                System.out.println(post1.toString());
                String json = JSON.toJSONString(post1);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/post/upload/post")
                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<Integer> result = JSON.parseObject(res, new TypeReference<Result<Integer>>() {});
                showToast("发送成功");
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(AddPostActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}