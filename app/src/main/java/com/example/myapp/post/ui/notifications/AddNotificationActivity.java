package com.example.myapp.post.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.myapp.post.ui.dashboard.DashboardFragment;
import com.example.myapp.post.ui.dashboard.EmailChangeActivity;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddNotificationActivity extends AppCompatActivity {
    private Button button_push;
    private EditText notification_text;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notification);
        notification_text = findViewById(R.id.notification_add);
        button_push=(Button)findViewById(R.id.push);
        button_push.setOnClickListener(v -> {
            finish();
            /*new Thread(() -> {
                try {
                    String json = JSON.toJSONString(new User(notification_text.getText().toString(),
                            notification_text.getText().toString()));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(ApplicationStatus.HOST + "/user/login")
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String res = Objects.requireNonNull(response.body()).string();
                    Result result = JSON.parseObject(res, new TypeReference<Result>() {});
                    if (result.isSuccess()) {
                        startActivity(new Intent(AddNotificationActivity.this, com.example.myapp.post.ui.Notifications.NotificationsFragment.class));
                        finish();
                    } else {
                        runOnUiThread(() -> Toast.makeText(AddNotificationActivity.this, "修改失败",
                                Toast.LENGTH_SHORT).show());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(AddNotificationActivity.this, "网络连接失败",
                            Toast.LENGTH_SHORT).show());
                }
            }).start();*/
        });
    }
}
