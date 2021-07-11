package com.example.myapp.post.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.example.myapp.entity.Result;
import com.example.myapp.logIn.ui.ResetPwdActivity;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordChangeActivity extends AppCompatActivity {
    private Button button_back,button_save;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtNewPasswordRepeat;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        mEtOldPassword = findViewById(R.id.et_old_password);
        mEtNewPassword = findViewById(R.id.et_new_password);
        mEtNewPasswordRepeat = findViewById(R.id.et_new_password_repeat);
        button_back= findViewById(R.id.back_pass);
        button_back.setOnClickListener(v -> finish());
        button_save= findViewById(R.id.save_pass);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mEtOldPassword.getText().toString();
                String newPassword = mEtNewPassword.getText().toString();
                String newPasswordRepeat = mEtNewPasswordRepeat.getText().toString();
                if (!newPassword.equals(newPasswordRepeat)) {
                    showToast("两次密码不相同");
                } else {
                    new Thread(() -> {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("userId", ApplicationStatus.getUserId().toString());
                            params.add("old", oldPassword);
                            params.add("new", newPassword);
                            Request request = new Request.Builder()
                                    .url(ApplicationStatus.HOST + "/user/change_password")
                                    .post(params.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            String res = Objects.requireNonNull(response.body()).string();
                            Result<Object> result = JSON.parseObject(res, new TypeReference<Result<Object>>() {});
                            showToast(result.getMessage());
                            if (result.isSuccess()) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast("网络连接失败");
                        }
                    }).start();
                }
            }
        });
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(PasswordChangeActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }
}
