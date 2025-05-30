package com.healtcare.CloudinaryService.controller;

import com.healtcare.CloudinaryService.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class CloudinaryController {
    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadImage(file);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadPdf(file);
        return ResponseEntity.ok(url);
    }
}
