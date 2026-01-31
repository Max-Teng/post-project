package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.*;
import com.yushan.max_interview.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final UserService userService;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public PostDetailVo getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<Comment> commentList = commentService.findByPostId(postId);
        List<CommentVo> commentVoList = new ArrayList<>();

        User createUser = userService.findById(post.getUserId());
        PostVo postVo = new PostVo();
        postVo.setPostId(postId);
        postVo.setContent(post.getContent());
        postVo.setAuthor(createUser);
        postVo.setImage(post.getImage());

        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            User commentCreateUser = userService.findById(comment.getUserId());
            commentVo.setContent(comment.getContent());
            commentVo.setAuthor(commentCreateUser);
            commentVoList.add(commentVo);
        }

        PostDetailVo postDetailVo = new PostDetailVo();
        postDetailVo.setPostVo(postVo);
        postDetailVo.setCommentVoList(commentVoList);
        return postDetailVo;
    }

    @Transactional
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    public ApiResponse<String> createPost(PostCreateRequest postCreateRequest, User user) {
        ApiResponse<String> response = new ApiResponse<>();
        Post newPost = Post.builder()
                .content(postCreateRequest.getContent())
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .image(postCreateRequest.getImage())
                .build();
        postRepository.save(newPost);
        response.setStatus(ApiResponse.Status.SUCCESS);
        return response;
    }

    public ApiResponse<Post> editPost(PostEditRequest postEditRequest, User user) {
        ApiResponse<Post> response = new ApiResponse<>();
        Post post = postRepository.findById(postEditRequest.getPostId()).orElse(null);
        if (post == null) {
            response.setStatus(ApiResponse.Status.ERROR);
            response.setMessage(java.util.Collections.singletonList("Post not found"));
            return response;
        }
        if (!post.getUserId().equals(user.getId())) {
            response.setStatus(ApiResponse.Status.ERROR);
            response.setMessage(java.util.Collections.singletonList("You are not the owner of this post"));
            return response;
        }
        post.setContent(postEditRequest.getContent());
        post.setImage(postEditRequest.getImage());
        postRepository.save(post);
        response.setData(post);
        response.setStatus(ApiResponse.Status.SUCCESS);
        return response;
    }
}
