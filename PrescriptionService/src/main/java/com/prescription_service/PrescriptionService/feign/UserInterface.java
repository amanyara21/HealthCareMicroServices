package com.prescription_service.PrescriptionService.feign;

import com.prescription_service.PrescriptionService.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;


@FeignClient("USERSERVICE")
public interface UserInterface {
    @GetMapping("/auth/getuser")
    ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String token);

    @GetMapping("/auth/getuser/${id}")
    ResponseEntity<UserDTO> getUserById(@RequestParam UUID id);
}
