package com.example.myapp.post.ui.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.MyPost;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.logIn.ui.RegisterActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.util.RelativeDateHandler;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowPostActivity extends AppCompatActivity {

    private TextView mPostUsername;
    private TextView mPostText;
    private TextView mTimeShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);
        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", 1);
        mPostUsername = findViewById(R.id.post_user_name);
        mPostText = findViewById(R.id.post_text);
        mTimeShow = findViewById(R.id.post_time_show);
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("postId", postId + "");
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/post/getPost")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<MyPost> result = JSON.parseObject(res, new TypeReference<Result<MyPost>>() {});
                if (result.isSuccess()) {
                    MyPost post = result.get();
                    setTitle(post);
                    runOnUiThread(() -> {
                        mPostText.setText(post.getMessage());
                        mTimeShow.setText(RelativeDateHandler.format(post.getCreateTime()));
                    });
                } else {
                    showToast(result.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(ShowPostActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }

    private void setTitle(MyPost post) {
        new Thread(() -> {
            try {
                OkHttpClient client1 = new OkHttpClient();
                FormBody.Builder params1 = new FormBody.Builder();
                params1.add("userId",  post.getUserId()+ "");
                Request request1 = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/finduser")
                        .post(params1.build())
                        .build();
                Response response1 = client1.newCall(request1).execute();
                String res1 = Objects.requireNonNull(response1.body()).string();
                Result<User> userResult = JSON.parseObject(res1,
                        new TypeReference<Result<User>>() {});
                User user = userResult.get();
                runOnUiThread(() -> {
                    mPostUsername.setText(user == null ? "User" : user.getUserName());
                });
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }
}