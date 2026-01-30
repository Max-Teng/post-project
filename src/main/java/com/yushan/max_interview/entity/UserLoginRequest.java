package com.yushan.max_interview.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank(message = "使用者名稱不能為空")
    private String userName;

    @NotBlank(message = "密碼不能為空")
    private String password;
}
