package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.PostCreateRequest;
import com.yushan.max_interview.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/createComment")
    public ApiResponse<String> createPost(@RequestBody PostCreateRequest postCreateRequest) {


    }
}
