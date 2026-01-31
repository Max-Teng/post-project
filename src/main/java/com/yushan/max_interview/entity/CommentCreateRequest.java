package com.yushan.max_interview.entity;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CommentCreateRequest {
    @NotNull(message = "postId cant be null")
    private Long postId;

    @NotBlank(message = "content cant be null")
    private String content;
}
