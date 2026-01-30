package com.yushan.max_interview.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 貼文ID (Primary Key)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 發文者 (Foreign Key)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 貼文內容

    @Column(name = "image")
    private String image; // 貼文圖片路徑

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt; // 建立時間
}
