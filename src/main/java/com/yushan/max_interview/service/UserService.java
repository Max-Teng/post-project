package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.User;
import com.yushan.max_interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService   {
    private final UserRepository userRepository;

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    public User findById(Long id)
    {
        return userRepository.findById(id).orElse(null);
    }
}
