package com.example.myapp.post.ui.notifications;


import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class information extends AppCompatActivity {
    private List<Message> lists;
    private comment_adapter adapter;
    private ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url()
                            .post(RequestBody.create(), json)
                            .build();
                    Response response = client.newCall(request).execute();
                    String res =  response.body().string();
                    //JSONObject resData = new JSONObject(res);
                    //JSONArray jsonArray = new JSONArray(resData);
                    Message msg1 = new Message() ;
                    //for (int i =0;i<jsonArray.length();i++) {
                    //    JSONObject jsonObject = jsonArray.getJSONObject(i); }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("1",res);
                    message.setData(bundle);

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(root.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();*/

        //listView = (ListView) findViewById(R.id.comment_container);
        //lists = new ArrayList<>();
        //lists.add(new Message());
        //lists.add(new Message());
        //adapter = new comment_adapter(lists, information.this);
        //listView.setAdapter(adapter);
    }
}
