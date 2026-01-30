package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.*;
import com.yushan.max_interview.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final UserService userService;

    public List<Post> getAllPost()
    {
        return postRepository.findAll();
    }

    public PostDetailVo getPostDetail(Long postId)
    {
        Post post = postRepository.findById(postId).orElse(null);
        List<Comment> commentList = commentService.findByPostId(postId);
        List<CommentVo> commentVoList = new ArrayList<>();

        User createUser = userService.findById(post.getUserId());
        PostVo postVo = new PostVo();
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

    public void deletePostById(Long postId)
    {
        postRepository.deleteById(postId);
    }

    public ApiResponse<String> createPost(PostCreateRequest postCreateRequest,User user)
    {
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
}
