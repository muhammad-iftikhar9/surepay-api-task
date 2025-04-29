package com.surepay.model;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;
} 