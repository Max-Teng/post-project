package com.yushan.max_interview.repository;

import com.yushan.max_interview.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
