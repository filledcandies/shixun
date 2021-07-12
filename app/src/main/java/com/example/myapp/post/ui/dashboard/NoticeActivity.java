package com.example.myapp.post.ui.dashboard;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Notice;
import com.example.myapp.entity.Result;
import com.example.myapp.myapplication.ApplicationStatus;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoticeActivity extends AppCompatActivity {
    private Button button_back;
    private RecyclerView mRvNotice;
    private AlarmManager manager;
    private List<Notice> notices;
    private Handler handler;

    Logger logger = Logger.getLogger("logger");

    protected void onCreate(Bundle savedInstanceState) {
        notices = new ArrayList<>();
        notices.addAll(LitePal.findAll(Notice.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        button_back= findViewById(R.id.back_notice);
        button_back.setOnClickListener(v -> finish());
        mRvNotice = findViewById(R.id.rv_notice);
        mRvNotice.setLayoutManager(new LinearLayoutManager(NoticeActivity.this));
        NoticeAdapter adapter = new NoticeAdapter(NoticeActivity.this, notices);
        mRvNotice.setAdapter(adapter);
        new Thread(() -> {
            try {
                while (true) {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("userId", ApplicationStatus.getUserId().toString());
                    Request request = new Request.Builder()
                            .url(ApplicationStatus.HOST + "/community/notice/get")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String res = Objects.requireNonNull(response.body()).string();
                    logger.info(res);
                    Result<List<Notice>> result =
                            JSON.parseObject(res, new TypeReference<Result<List<Notice>>>() {});
                    List<Notice> noticeList = result.get();
                    logger.info(noticeList.size() + "");
                    if (noticeList != null && noticeList.size() > 0) {
                        for (int i = noticeList.size() - 1; i >= 0; i--) {
                            notices.add(0, noticeList.get(i));
                            noticeList.get(i).save();
                        }
                        runOnUiThread(() -> {
                            for (int i = 0; i < notices.size(); i++) {
                                adapter.notifyItemChanged(i);
                            }
                        });
                    }
                    Thread.sleep(10000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                showToast("网络连接失败");
            }
        }).start();
    }



    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(NoticeActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }


}
