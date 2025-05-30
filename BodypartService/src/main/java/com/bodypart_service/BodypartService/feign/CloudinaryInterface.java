package com.bodypart_service.BodypartService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("CLOUDINARYSERVICE")
public interface CloudinaryInterface {
    @PostMapping("/upload/image")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file);
}
