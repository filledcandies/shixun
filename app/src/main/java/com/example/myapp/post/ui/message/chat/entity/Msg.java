package com.example.myapp.post.ui.message.chat.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapp.entity.Message;
import com.example.myapp.myapplication.ApplicationStatus;

public class Msg implements Parcelable {


    public static int TYPE_SENT = 0;
    public static int TYPE_RECEIVED = 1;
    private String mTime;
    private String content;
    private Integer msgId;
    //信息的类型，1为收到的信息，0为发送的信息
    private int msgType=Msg.TYPE_RECEIVED;


    public Msg() {
    }

    public Msg(String mTime, String content) {
        this.mTime = mTime;
        this.content = content;
    }
    public Msg(String content,int msgType){
        this.content = content;
        this.msgType = msgType;
    }
    public Msg(String time,String content,int msgType){
        this.mTime = time;
        this.content = content;
        this.msgType = msgType;
    }

    protected Msg(Parcel in) {
        mTime = in.readString();
        content = in.readString();
        msgType = in.readInt();
    }

    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel in) {
            return new Msg(in);
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    public String getTime() {
        return mTime;
    }

    public void setTime(String Time) {
        this.mTime = Time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTime);
        dest.writeString(content);
        dest.writeInt(msgType);

    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }
}
