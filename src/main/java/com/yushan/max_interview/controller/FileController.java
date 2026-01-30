package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    private final String uploadDir = "image/";

    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        ApiResponse<String> response = new ApiResponse<>();
        if (file.isEmpty()) {
            response.setStatus(ApiResponse.Status.ERROR);
            return response;
        }

        try {
            // Create directory if not exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID() + extension;

            // Save file
            File dest = new File(directory.getAbsolutePath() + File.separator + newFilename);
            file.transferTo(dest);

            // Return URL
            // Assuming the app runs on localhost:8080, and we map /images/** to this folder
            String fileUrl = "http://localhost:8080/images/" + newFilename;

            response.setData(fileUrl);
            response.setStatus(ApiResponse.Status.SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(ApiResponse.Status.ERROR);
        }

        return response;
    }
}
