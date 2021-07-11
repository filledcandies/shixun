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

    public String getId() {
        return uId;
    }

    public int getIcon() {
        return uIconId;
    }

    public String getName() {
        return uName;
    }
}
