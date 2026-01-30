package com.yushan.max_interview.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostEditRequest {

    @NotNull(message = "id cant be null")
    private Long postId; // 貼文ID

    @NotBlank(message = "content cant be null")
    private String content; // 貼文內容

    private String image; // 貼文圖片路徑
}
