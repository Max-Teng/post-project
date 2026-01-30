package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.*;
import com.yushan.max_interview.service.PostService;
import com.yushan.max_interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final UserService userService;

    @PostMapping("/getAllPost")
    public ApiResponse<List<Post>> getAllPost() {
        ApiResponse<List<Post>> apiResponse = new ApiResponse<>();
        List<Post> allPost = postService.getAllPost();
        apiResponse.setData(allPost);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        return apiResponse;
    }

    @PostMapping("/getPostDetail")
    public ApiResponse<PostDetailVo> getPostDetail(@RequestParam Long postId) {
        ApiResponse<PostDetailVo> apiResponse = new ApiResponse<>();
        PostDetailVo postDetail = postService.getPostDetail(postId);
        apiResponse.setData(postDetail);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        return apiResponse;
    }

    @PostMapping("/deletePost")
    public ApiResponse<Post> deletePost(@RequestParam Long postId) {
        ApiResponse<Post> apiResponse = new ApiResponse<>();
        postService.deletePostById(postId);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        return apiResponse;
    }

    @PostMapping("/createPost")
    public ApiResponse<String> createPost(@RequestBody PostCreateRequest postCreateRequest,
            java.security.Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        return postService.createPost(postCreateRequest, user);
    }

    @PostMapping("/editPost")
    public ApiResponse<Post> editPost(@RequestBody PostEditRequest postEditRequest,
            java.security.Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        return postService.editPost(postEditRequest, user);
    }
}
