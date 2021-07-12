package com.example.myapp.post.ui.message.chat.entity;

import java.util.List;

public class MsgBox {
    //MsgBoxId
    private Integer msgBoxId;
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
    public MsgBox(ChatUser cUser,List<Msg> mList,Integer msgBoxId){
        chatUser = cUser;
        msgList  = mList;
        if(null!=cUser.getName()){
            title = cUser.getName();
        }else title = " ";
        this.setMsgBoxId(msgBoxId);
    }


    public Integer getMsgBoxId() {
        return msgBoxId;
    }

    public void setMsgBoxId(Integer msgBoxId) {
        this.msgBoxId = msgBoxId;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }

    public List<Msg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Msg> msgList) {
        this.msgList = msgList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
