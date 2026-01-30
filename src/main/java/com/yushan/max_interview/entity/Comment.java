package com.yushan.max_interview.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 留言ID (Primary Key)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 留言者 (Foreign Key)

    @Column(name = "post_id", nullable = false)
    private Long postId; // 所屬貼文 (Foreign Key)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 留言內容

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt; // 建立時間
}
