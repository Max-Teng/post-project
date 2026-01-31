package com.yushan.max_interview.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequest {

    @NotBlank(message = "content cant be null")
    private String content;

    private String image;
}
