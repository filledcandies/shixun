package com.example.myapp.logIn.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener, TextWatcher {

    private EditText mEtRegisterUsername;
    private EditText mEtRegisterPwdInput;
    private EditText mEtRegisterAuthCode;
    private TextView mTvRegisterSMSCall;
    private Button mBtRegisterSubmit;
    private LinearLayout mLlRegisterTwoUsername;
    private LinearLayout mLlRegisterTwoPwd;
    private LinearLayout mLlRegisterSMSCode;

    private void initView() {
        mEtRegisterUsername = findViewById(R.id.et_register_username);
        mEtRegisterPwdInput = findViewById(R.id.et_register_pwd_input);
        mEtRegisterAuthCode = findViewById(R.id.et_register_auth_code);
        mTvRegisterSMSCall = findViewById(R.id.tv_register_sms_call);
        mBtRegisterSubmit = findViewById(R.id.bt_register_submit);
        mLlRegisterTwoUsername = findViewById(R.id.ll_register_two_username);
        mLlRegisterTwoPwd = findViewById(R.id.ll_register_two_pwd);
        mLlRegisterSMSCode = findViewById(R.id.ll_register_sms_code);

        mEtRegisterUsername.setOnClickListener(this);
        mEtRegisterAuthCode.setOnClickListener(this);
        mEtRegisterPwdInput.setOnClickListener(this);
        mTvRegisterSMSCall.setOnClickListener(this);
        mBtRegisterSubmit.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register_step_two);

        initView();

        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_navigation_back) {
            finish();
        } else if (id == R.id.et_register_username) {
            mEtRegisterPwdInput.clearFocus();
            mEtRegisterAuthCode.clearFocus();
            mEtRegisterUsername.setFocusableInTouchMode(true);
            mEtRegisterUsername.requestFocus();
        } else if (id == R.id.et_register_pwd_input) {
            mEtRegisterUsername.clearFocus();
            mEtRegisterAuthCode.clearFocus();
            mEtRegisterPwdInput.setFocusableInTouchMode(true);
            mEtRegisterPwdInput.requestFocus();
        } else if (id == R.id.et_register_auth_code) {
            mEtRegisterUsername.clearFocus();
            mEtRegisterPwdInput.clearFocus();
            mEtRegisterAuthCode.setFocusableInTouchMode(true);
            mEtRegisterAuthCode.requestFocus();
        } else if (id == R.id.tv_register_sms_call) {
            codeRequest();
        } else if (id == R.id.bt_register_submit) {
            registerRequest();
        }
    }

    private void codeRequest() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("email", mEtRegisterUsername.getText().toString());
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

    private void registerRequest() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                params.add("email", mEtRegisterUsername.getText().toString());
                params.add("password", mEtRegisterPwdInput.getText().toString());
                params.add("code", mEtRegisterAuthCode.getText().toString());
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/register")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<User> result = JSON.parseObject(res, new TypeReference<Result<User>>() {});
                if (result.isSuccess()) {
                    showToast("注册成功");
                    ApplicationStatus.setUserId(result.get().getUserId());
                    startActivity(new Intent(RegisterActivity.this, PostActivity.class));
                } else {
                    showToast(result.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.et_register_username) {
            if (hasFocus) {
                mLlRegisterTwoUsername.setActivated(true);
                mLlRegisterTwoPwd.setActivated(false);
                mLlRegisterSMSCode.setActivated(false);
            }
        } else if (id == R.id.et_register_pwd_input) {
            if (hasFocus) {
                mLlRegisterTwoUsername.setActivated(false);
                mLlRegisterTwoPwd.setActivated(true);
                mLlRegisterSMSCode.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlRegisterTwoUsername.setActivated(false);
                mLlRegisterTwoPwd.setActivated(false);
                mLlRegisterSMSCode.setActivated(true);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String email = mEtRegisterUsername.getText().toString().trim();
        String password = mEtRegisterPwdInput.getText().toString().trim();
        String code = mEtRegisterAuthCode.getText().toString().trim();

        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            if (!TextUtils.isEmpty(code)) {
                mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
            }
            mTvRegisterSMSCall.setBackgroundResource(R.drawable.bg_login_submit);
            mTvRegisterSMSCall.setTextColor(getResources().getColor(R.color.white));
        } else {
            mTvRegisterSMSCall.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mTvRegisterSMSCall.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}