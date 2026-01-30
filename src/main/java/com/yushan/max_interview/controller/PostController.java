package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.Post;
import com.yushan.max_interview.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/getAllPost")
    public ApiResponse<List<Post>> getAllPost() {
        ApiResponse<List<Post>> apiResponse = new ApiResponse<>();
        List<Post> allPost = postService.getAllPost();
        apiResponse.setData(allPost);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        return apiResponse;
    }
}
