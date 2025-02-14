package com.example.myapp.post.ui.message;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.databinding.FragmentMessageBinding;
import com.example.myapp.entity.Message;
import com.example.myapp.entity.MessageBox;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.ui.message.chat.entity.ChatUser;
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
    private RecyclerView recyclerView;
    private View root;
    private MsgBoxAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);

        binding = FragmentMessageBinding.inflate(inflater, container, false);
         root = binding.getRoot();


         recyclerView = root.findViewById(R.id.message_container);
        //初始化MsgBoxes数据
        //initMsgBoxes();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initMsgBoxes(){

        messageBoxes.clear();
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
                        //获得某个MessageBox里的MessageList
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

                        //将 Message转化为 Msg 并存放在 MsgList中
                        List<Message> messageList = messageListResult.get();
                        List<Msg> msgList = new ArrayList<>();
                        for(Message message:messageList){
                            Msg msg = new Msg(message.getSendTime().toString(),message.getWord());
                            msg.setMsgId(message.getMessageId());
                            if (message.getUserId() == ApplicationStatus.getUserId()){
                                msg.setMsgType(Msg.TYPE_SENT);
                            }else{
                                msg.setMsgType(Msg.TYPE_RECEIVED);
                            }
                            msgList.add(msg);
                        }

                        //获取聊天对象的各个属性
                        ChatUser cUser = new ChatUser();
                        client = new OkHttpClient(); //创建http客户端
                        params = new FormBody.Builder();//创建参数列表
                        Integer cUserId ;
                        if(messageBox.getUser1Id()==ApplicationStatus.getUserId()){
                            cUserId = messageBox.getUser2Id();
                        }else{
                            cUserId = messageBox.getUser1Id();
                        }
                        params.add("userId", cUserId.toString());



                        request = new Request.Builder()
                                .url(ApplicationStatus.HOST + "/user/find?userId="+cUserId)
                                .get()
                                .build();
                        response = client.newCall(request).execute(); //执行请求
                        res = Objects.requireNonNull(response.body()).string();
                        Result<User> userResult = JSON.parseObject(res, new TypeReference<Result<User>>() {});
                        User user = userResult.get();
                        Log.d("Uname", "initMsgBoxes: "+user);
                        cUser.setuId(cUserId.toString());
                        cUser.setuIconId(R.mipmap.default_user_icon);

                        cUser.setuName(user.getUserName());

                        //将聊天对象和历史信息存入MsgBox
                        MsgBox msgBox = new MsgBox(cUser,msgList,messageBox.getMessageBoxId());
                        Log.d("MsgBox", "initMsgBoxes: "+cUser.getName());
                        messageBoxes.add(msgBox);
                        getActivity().runOnUiThread(()->{adapter = new MsgBoxAdapter(messageBoxes);
                        recyclerView.setAdapter(adapter);
                        //设置线性布局
                        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                        recyclerView.setLayoutManager(layoutManager);});

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onStart() {
       initMsgBoxes();
        super.onStart();
    }
}