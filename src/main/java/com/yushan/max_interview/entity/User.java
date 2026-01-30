package com.yushan.max_interview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 使用者ID (Primary Key)

    @Column(name = "user_name", nullable = false)
    private String userName; // 使用者名稱

    @Column(name = "email")
    private String email; // 使用者電子郵件

    @Column(name = "password", nullable = false)
    private String password; // 密碼 (已加鹽與雜湊)

    @Column(name = "cover_image")
    private String coverImage; // 封面照片 (非必要)

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography; // 使用者的自我介紹

    @Column(name = "created_at", columnDefinition = "TEXT")
    private LocalDateTime createdAt;
}
