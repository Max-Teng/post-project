package com.yushan.max_interview.entity;

import lombok.Data;

@Data
public class PostVo {

    private Long postId;

    private String content; // 貼文內容

    private String image; // 貼文圖片路徑

    private User author;
}
