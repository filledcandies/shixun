package com.example.myapp.post.ui.message.chat.entity;

import java.util.List;

public class MsgBox {
    //聊天对象
    private ChatUser chatUser;
    //消息记录
    private List<Msg> msgList;
    //聊天对象
    private String title;
    public MsgBox(){

    }
    public MsgBox(ChatUser cUser, List<Msg> mList){
        chatUser = cUser;
        msgList  = mList;
        if(null!=cUser.getName()){
            title = cUser.getName();
        }else title = " ";
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public List<Msg> getMsgList() {
        return msgList;
    }

    public String getTitle() {
        return title;
    }
}
