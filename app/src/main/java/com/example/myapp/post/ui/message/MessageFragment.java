package com.example.myapp.post.ui.message;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentMessageBinding;
import com.example.myapp.databinding.FragmentNotificationsBinding;
import com.example.myapp.post.ui.message.chat.ChatUser;
import com.example.myapp.post.ui.message.chat.MessageBox;
import com.example.myapp.post.ui.message.chat.Msg;
import com.example.myapp.post.ui.message.chat.util.MsgBoxAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

        private MessageViewModel messageViewModel;
        private FragmentMessageBinding binding;
        private List<MessageBox> messageBoxes = new ArrayList<>();

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
            ChatUser chatUser1 = new ChatUser("uId1",R.mipmap.default_user_icon,"User1");
            Msg msg1 = new Msg("This is first Msg",1);
            Msg msg2 = new Msg("This is latest Msg 1316461616946456546156541651616616161651616516516",1);
            List<Msg> msgList = new ArrayList<>();
            msgList.add(msg1);
            msgList.add(msg2);
            MessageBox messageBox1 = new MessageBox(chatUser1,msgList);
            messageBoxes.add(messageBox1);

        }




}