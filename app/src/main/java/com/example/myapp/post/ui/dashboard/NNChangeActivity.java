package com.example.myapp.post.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.logIn.ui.LogInActivity;
import com.example.myapp.logIn.ui.ResetPwdActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NNChangeActivity extends AppCompatActivity {
    private EditText mEtChangeUsername;
    private Button button_back,button_save;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnchange);
        mEtChangeUsername = findViewById(R.id.change_username);
        button_back = findViewById(R.id.back_nn);
        button_back.setOnClickListener(v -> finish());
        button_save = findViewById(R.id.save_nn);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    try {
                        User user = new User();
                        user.setUserId(ApplicationStatus.getUserId());
                        user.setUserName(mEtChangeUsername.getText().toString());
                        String json = JSON.toJSONString(user);
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(ApplicationStatus.HOST + "/user/change_username")
                                .post(RequestBody.create(MediaType.parse("application/json"), json))
                                .build();
                        Response response = client.newCall(request).execute();
                        showToast("修改成功");
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("网络连接失败");
                    }
                }).start();
            }
        });
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(NNChangeActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}