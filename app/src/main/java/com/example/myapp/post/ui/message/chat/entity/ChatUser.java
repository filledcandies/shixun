package com.example.myapp.post.ui.message.chat.entity;

public class ChatUser {

    private String uId;
    private int uIconId;
    private String uName;

    public ChatUser() {
    }

    public ChatUser(String id,int icon,String name){

        uId = id;
        uIconId = icon;
        uName = name;

    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getId() {
        return uId;
    }

    public void setuIconId(int uIconId) {
        this.uIconId = uIconId;
    }

    public int getIcon() {
        return uIconId;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getName() {
        return uName;
    }
}
