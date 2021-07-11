package com.example.myapp.post.ui.message;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.databinding.FragmentMessageBinding;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.entity.*;

import com.example.myapp.post.ui.message.chat.entity.Msg;
import com.example.myapp.post.ui.message.chat.entity.MsgBox;
import com.example.myapp.post.ui.message.chat.util.MsgBoxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;
    private FragmentMessageBinding binding;
    private List<MsgBox> messageBoxes = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);

        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = root.findViewById(R.id.message_container);
        //初始化MsgBoxes数据
        initMsgBoxes();
        MsgBoxAdapter adapter = new MsgBoxAdapter(messageBoxes);
        recyclerView.setAdapter(adapter);
        //设置线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initMsgBoxes(){
        new Thread(()->{
            try {
                OkHttpClient client = new OkHttpClient(); //创建http客户端
                FormBody.Builder params = new FormBody.Builder();//创建参数列表
                params.add("userId", ApplicationStatus.getUserId().toString());

                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/chat/box/get")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute(); //执行请求
                String res = Objects.requireNonNull(response.body()).string();
                Result<List<MessageBox>> result = JSON.parseObject(res, new TypeReference<Result<List<MessageBox>>>() {});

                if (result.isSuccess()) {
                    for (MessageBox messageBox:result.get()) {
                        //获得某个MsgBox里的MsgList
                        client = new OkHttpClient(); //创建http客户端
                        params = new FormBody.Builder();//创建参数列表
                        params.add("msgBoxId", messageBox.getMessageBoxId().toString());

                        request = new Request.Builder()
                                .url(ApplicationStatus.HOST + "/chat/msg/getall")
                                .post(params.build())
                                .build();
                        response = client.newCall(request).execute(); //执行请求
                        res = Objects.requireNonNull(response.body()).string();
                        Result<List<Message>> messageListResult = JSON.parseObject(res, new TypeReference<Result<List<Message>>>() {});

                        List<Message> messageList = messageListResult.get();
                        List<Msg> msgList = new ArrayList<>();
                        for(Message message:messageList){
                            Msg msg = new Msg(message.getSendTime().toString(),message.getWord());
                            if (message.getUserId() == ApplicationStatus.getUserId()){
                                msg.setMsgType(Msg.TYPE_SENT);
                            }else{
                                msg.setMsgType(Msg.TYPE_RECEIVED);
                            }
                            msgList.add(msg);
                        }
                        MsgBox msgBox = new MsgBox(null,msgList);
                        messageBoxes.add(msgBox);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}