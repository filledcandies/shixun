package com.example.myapp.myapplication;

import java.io.FileOutputStream;

public class ApplicationStatus {

    private static final ApplicationStatus instance = new ApplicationStatus();

    public static final String HOST = "http://10.19.129.210:8000";

    public static final String USER_FILE_NAME = "userId.txt";

    private Integer userId;

    private ApplicationStatus() {
        // 尝试获取sqlite中的userId
    }

    public static void setUserId(Integer userId) {
        instance.userId = userId;
    }

    public static Integer getUserId() {
        return instance.userId;
    }


}
