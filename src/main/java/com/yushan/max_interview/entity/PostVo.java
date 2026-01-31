package com.yushan.max_interview.entity;

import lombok.Data;

@Data
public class PostVo {

    private Long postId;

    private String content;

    private String image;

    private User author;
}
