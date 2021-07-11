package com.example.myapp.post.ui.post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.myapplication.ApplicationStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                if(msg!=""&&msg!=null){
                    Log.i("post", msg+"  "+time+" "+onlyOwner.isChecked()+" "+ApplicationStatus.getUserId());
                    Post post = new Post(ApplicationStatus.getUserId(),msg,onlyOwner.isChecked(), time);
                    sendPost(post);

                }
            }
        });
    }
    //将post上传到服务端
    public int sendPost(Post post){
        return 0;
    }
}