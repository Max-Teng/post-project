package com.yushan.max_interview.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "手機號碼不能為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤")
    private String phoneNumber;

    @Email(message = "Email 格式錯誤")
    private String email;

    @NotBlank(message = "密碼不能為空")
    private String password;

    private String coverImage;

    private String biography;
}
