package com.example.myapp.post.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentNotificationsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {
    private FloatingActionButton button_add;
    private Button button_search;
    private Button shoucang;
    private Button jubao;
    private NotificationsViewModel notificationsViewModel;

    private List<Message> lists;
    private topicbox_adapter adapter;
    private ListView listView;
    private Spinner spinner;
    private EditText editText;
    private TextView textView1;
    public int num=2;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        shoucang = (Button)root.findViewById(R.id.shoucang);
        jubao = (Button)root.findViewById(R.id.jubao);
        spinner = (Spinner)root.findViewById(R.id._dynamic);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                           @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                String[] banner = getResources().getStringArray(R.array.spingarr);
                                Toast.makeText(root.getContext(), "你点击的是:" +banner[pos], Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }

                        });



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


        listView = (ListView) root.findViewById(R.id.notification_container);
        lists = new ArrayList<>();
        adapter = new com.example.myapp.post.ui.notifications.topicbox_adapter(lists, root.getContext());
        listView.setAdapter(adapter);
        lists.add(new Message());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(root.getContext(),information.class);
                startActivity(intent);
            }
        });

        button_add = (FloatingActionButton) root.findViewById(R.id.add_notification);
        button_add.setOnClickListener(v -> {
          startActivity(new Intent(root.getContext(), AddNotificationActivity.class));});
        button_search = (Button) root.findViewById(R.id.search);
        button_search.setOnClickListener(v -> {
            onCreate(null);});
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s){
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public int getNum(){
        return num;
    }

}