package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.Post;
import com.yushan.max_interview.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPost()
    {
        return postRepository.findAll();
    }
}
