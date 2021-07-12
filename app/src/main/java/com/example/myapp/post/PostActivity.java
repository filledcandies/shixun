package com.example.myapp.post;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.MyPost;
import com.example.myapp.entity.Notice;
import com.example.myapp.entity.Result;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.ui.dashboard.NoticeActivity;
import com.example.myapp.post.ui.post.uitl.PostAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.ActivityPostBinding;

import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding binding;
    private List<MyPost> posts;
    private RecyclerView recyclerPost;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerPost = findViewById(R.id.post_recycle);
        recyclerPost.setLayoutManager(new LinearLayoutManager(PostActivity.this));
        adapter = new PostAdapter(posts);
        recyclerPost.setAdapter(adapter);
        getSupportActionBar().hide();

        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_post, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_message)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_post);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void getPost() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder params = new FormBody.Builder();
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/post/match?userId=" +
                                ApplicationStatus.getUserId())
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<List<MyPost>> result =
                        JSON.parseObject(res, new TypeReference<Result<List<MyPost>>>() {});
                List<MyPost> noticeList = result.get();
                if (noticeList != null && noticeList.size() > 0) {
                    for (int i = noticeList.size() - 1; i >= 0; i--) {
                        posts.add(0, noticeList.get(i));
                    }
                    runOnUiThread(() -> {
                        for (int i = 0; i < posts.size(); i++) {
                            adapter.notifyItemChanged(i);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("网络连接失败");
            }
        }).start();
    }

    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(PostActivity.this, msg,
                Toast.LENGTH_SHORT).show());
    }

}