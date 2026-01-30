package com.yushan.max_interview.entity;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private Long postId;
    private String content;
}
