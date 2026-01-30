package com.yushan.max_interview.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

     public enum Status {
        SUCCESS,
        ERROR
    }

    private Status status;
    private List<String> message;
    private T data;
}
