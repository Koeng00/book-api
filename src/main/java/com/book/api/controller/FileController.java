package com.book.api.controller;

import com.book.api.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileService fileService;

    @Value("${project.images}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException
    {
        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("File upload is : " + uploadedFileName);
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void getResource(@PathVariable String fileName, HttpServletResponse response) throws IOException {
         InputStream resourceFile = fileService.getResource(path, fileName);
         response.setContentType(MediaType.IMAGE_PNG_VALUE);
         StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
