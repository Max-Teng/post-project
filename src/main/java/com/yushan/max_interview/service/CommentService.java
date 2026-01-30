package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.Comment;
import com.yushan.max_interview.repository.CommentRepository;
import com.yushan.max_interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findByPostId(Long postId)
    {
        return commentRepository.findByPostId(postId);
    }


    public ApiResponse<String> createPost()
    {

    }
}
