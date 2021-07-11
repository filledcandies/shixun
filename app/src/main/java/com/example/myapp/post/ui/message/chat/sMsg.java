package com.example.myapp.post.ui.message.chat;


import java.io.Serializable;
import java.util.Date;


public class sMsg implements Serializable {
    private Integer messageId;
    private Integer messageBoxId;
    private Integer userId;
    private String picture;
    private String word;
    private Date sendTime;
    private Integer messageStatus;

    public sMsg(Integer messageId, Integer messageBoxId, Integer userId,
                String picture, String word, Date sendTime, Integer messageStatus) {
        this.messageId = messageId;
        this.messageBoxId = messageBoxId;
        this.userId = userId;
        this.picture = picture;
        this.word = word;
        this.sendTime = sendTime;
        this.messageStatus = messageStatus;
    }

    public sMsg() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageBoxId() {
        return messageBoxId;
    }

    public void setMessageBoxId(Integer messageBoxId) {
        this.messageBoxId = messageBoxId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }
}

