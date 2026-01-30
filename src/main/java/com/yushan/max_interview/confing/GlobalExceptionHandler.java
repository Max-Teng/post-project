package com.yushan.max_interview.confing;

import com.yushan.max_interview.entity.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String> > handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            messages.add(errorMessage);
        });
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
            .status(ApiResponse.Status.ERROR)
            .message(messages)
            .data(null)
            .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
