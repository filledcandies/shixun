package com.example.myapp.post.ui.message.chat;

import java.io.Serializable;
import java.util.Date;


public class sMsgBox implements Serializable {
    private Integer messageBoxId;
    private Integer user1Id;

    public sMsgBox() {
    }

    private Integer user2Id;
    private Date createTime;
    private Integer messageBoxStatus;

    public sMsgBox(Integer messageBoxId, Integer user1Id, Integer user2Id,
                   Date createTime, Integer messageBoxStatus) {
        this.messageBoxId = messageBoxId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createTime = createTime;
        this.messageBoxStatus = messageBoxStatus;
    }

    public Integer getMessageBoxId() {
        return messageBoxId;
    }

    public void setMessageBoxId(Integer messageBoxId) {
        this.messageBoxId = messageBoxId;
    }

    public Integer getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Integer user1Id) {
        this.user1Id = user1Id;
    }

    public Integer getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Integer user2Id) {
        this.user2Id = user2Id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMessageBoxStatus() {
        return messageBoxStatus;
    }

    public void setMessageBoxStatus(Integer messageBoxStatus) {
        this.messageBoxStatus = messageBoxStatus;
    }
}
