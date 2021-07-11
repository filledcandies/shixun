package com.example.myapp.logIn.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mLlRetrieveTel;
    private EditText mEtRetrieveTel;

    private LinearLayout mLlRetrieveCode;
    private EditText mEtRetrieveCodeInput;
    private TextView mRetrieveSMSCall;

    private Button mBtRetrieveSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forget_pwd);

        initView();

    }

    private void initView() {
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        mLlRetrieveTel = findViewById(R.id.ll_retrieve_tel);
        mEtRetrieveTel = findViewById(R.id.et_retrieve_tel);

        mLlRetrieveCode = findViewById(R.id.ll_retrieve_code);
        mEtRetrieveCodeInput = findViewById(R.id.et_retrieve_code_input);
        mRetrieveSMSCall = findViewById(R.id.retrieve_sms_call);

        mBtRetrieveSubmit = findViewById(R.id.bt_retrieve_submit);

        mEtRetrieveTel.setOnClickListener(this);
        mEtRetrieveCodeInput.setOnClickListener(this);
        mRetrieveSMSCall.setOnClickListener(this);
        mBtRetrieveSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_navigation_back) {
            finish();
        } else if (id == R.id.retrieve_sms_call) {
            codeRequest();
        } else if (id == R.id.bt_retrieve_submit) {
            resetRequest();
        }
    }

    private void codeRequest() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("email", mEtRetrieveTel.getText().toString());
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/verification")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result result = JSON.parseObject(res, new TypeReference<Result>() {});
                if (result.isSuccess()) {
                    showToast("获取验证码成功，请在120秒内填写");
                } else {
                    showToast("获取验证码失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    private void resetRequest() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("email", mEtRetrieveTel.getText().toString());
                params.add("code", mEtRetrieveCodeInput.getText().toString());
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/reset_try")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<Integer> result = JSON.parseObject(res, new TypeReference<Result<Integer>>() {});
                if (result.isSuccess()) {
                    ApplicationStatus.setUserId(result.get());
                    startActivity(new Intent(ForgetPwdActivity.this, ResetPwdActivity.class));
                    finish();
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
        runOnUiThread(() -> Toast.makeText(ForgetPwdActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}
