package com.example.myapp.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


public class ServerPost implements Serializable {
    private Integer postId;
    private Integer userId;
    private String picture;
    private String message;
    private Boolean visibility;
    private Date createTime;

    public ServerPost() {
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServerPost{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", picture='" + picture + '\'' +
                ", message='" + message + '\'' +
                ", isVisibility=" + visibility +
                ", createTime=" + createTime +
                '}';
    }
}
