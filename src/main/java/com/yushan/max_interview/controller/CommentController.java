package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.CommentCreateRequest;
import com.yushan.max_interview.entity.User;
import com.yushan.max_interview.service.CommentService;
import com.yushan.max_interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final UserService userService;

    private final CommentService commentService;

    @PostMapping("/createComment")
    public ApiResponse<String> createComment(@RequestBody CommentCreateRequest commentCreateRequest, java.security.Principal principal) {
        principal.getName();
        String username = principal.getName();
        User user = userService.findByUserName(username);
        return commentService.createComment(commentCreateRequest, user);
    }
}
