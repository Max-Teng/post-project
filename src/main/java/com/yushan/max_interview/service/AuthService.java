package com.yushan.max_interview.service;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.User;
import com.yushan.max_interview.entity.UserLoginRequest;
import com.yushan.max_interview.repository.UserRepository;
import com.yushan.max_interview.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public ApiResponse<String> register(User user) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        if(userRepository.findByUserName(user.getUserName()) != null) {
            apiResponse.setStatus(ApiResponse.Status.ERROR);
            apiResponse.setMessage(List.of("手機已註冊"));
            return apiResponse;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        apiResponse.setStatus(ApiResponse.Status.SUCCESS);
        apiResponse.setMessage(List.of("註冊成功"));
        return apiResponse;
    }

    public ApiResponse<String> login(UserLoginRequest request) {

        ApiResponse<String> apiResponse = new ApiResponse<>();
        User user = userRepository.findByUserName(request.getUserName());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtTokenProvider.createToken(user);
            apiResponse.setStatus(ApiResponse.Status.SUCCESS);
            apiResponse.setData(token);
            return apiResponse;
        }

        apiResponse.setStatus(ApiResponse.Status.ERROR);
        apiResponse.setMessage(List.of("帳號或密碼錯誤"));
        return apiResponse;
    }
}
