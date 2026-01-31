package com.yushan.max_interview.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostEditRequest {

    @NotNull(message = "id cant be null")
    private Long postId;

    @NotBlank(message = "content cant be null")
    private String content;

    private String image;
}
