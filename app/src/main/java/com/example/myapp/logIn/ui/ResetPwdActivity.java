package com.example.myapp.logIn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtResetPwd;
    private Button mBtResetSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_reset_pwd);

        initView();

    }

    private void initView() {
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        mEtResetPwd = findViewById(R.id.et_reset_pwd);
        mEtResetPwd.setOnClickListener(this);
        mBtResetSubmit = findViewById(R.id.bt_reset_submit);
        mBtResetSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_navigation_back) {
            finish();
        } else if (id == R.id.bt_reset_submit) {
            resetRequest();
        }
    }

    private void resetRequest() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("userId", ApplicationStatus.getUserId().toString());
                params.add("password", mEtResetPwd.getText().toString());
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/reset_password")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<Object> result = JSON.parseObject(res, new TypeReference<Result<Object>>() {});
                showToast(result.getMessage());
                if (result.isSuccess()) {
                    startActivity(new Intent(ResetPwdActivity.this, PostActivity.class));
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(ResetPwdActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}
