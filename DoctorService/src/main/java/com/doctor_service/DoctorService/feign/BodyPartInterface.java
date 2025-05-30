package com.doctor_service.DoctorService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("BODYPARTSERVICE")
public interface BodyPartInterface {
    @GetMapping("/api/bodypart/{name}")
    ResponseEntity<Long> getBodyPartByName(@RequestParam  String name);
}
