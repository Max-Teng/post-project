package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.*;
import com.yushan.max_interview.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findByPostId(Long postId)
    {
        return commentRepository.findByPostId(postId);
    }

    public ApiResponse<String> createComment(CommentCreateRequest commentVo, User user)
    {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        Comment newComment = Comment.builder()
            .content(commentVo.getContent())
            .postId(commentVo.getPostId())
            .userId(user.getId())
            .createdAt(LocalDateTime.now())
            .build();
        commentRepository.save(newComment);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        return apiResponse;
    }
}
