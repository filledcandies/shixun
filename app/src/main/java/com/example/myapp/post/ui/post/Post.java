package com.example.myapp.post.ui.post;

import java.sql.Time;

public class Post {
    private Integer ownerId;
    private Integer postId;
    //动态的文字内容
    private String message;
    //图片的地址
    private  String[] pictureUrl;
    //是否他人可见
    private  Boolean onlyOwner = false;

    private String createTime;

    public Post(){

    }

    public Post(Integer ownerId, String message, Boolean onlyOwner, String createTime) {
        this.ownerId = ownerId;
        this.message = message;
        this.onlyOwner = onlyOwner;
        this.createTime = createTime;
    }



    public Post(Integer ownerId, String message, String[] pictureUrl, Boolean onlyOwner) {
        this.ownerId = ownerId;
        this.message = message;
        this.pictureUrl = pictureUrl;
        this.onlyOwner = onlyOwner;
    }

    public void setPostId(Integer postId){
        this.postId = postId;
    }
    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getMessage() {
        return message;
    }

    public String[] getPictureUrl() {
        return pictureUrl;
    }

    public Boolean getOnlyOwner() {
        return onlyOwner;
    }

    public String getCreateTime() {
        return createTime;
    }
}
