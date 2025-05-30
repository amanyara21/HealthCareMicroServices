package com.bodypart_service.BodypartService.controller;

import com.bodypart_service.BodypartService.model.BodyPart;
import com.bodypart_service.BodypartService.service.BodyPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class BodyPartController {
    private final BodyPartService bodyPartService;

    public BodyPartController(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    @GetMapping("/users/body-parts")
    public ResponseEntity<List<BodyPart>> getBodyParts() {
        return ResponseEntity.ok(bodyPartService.getAllParts());
    }
    @GetMapping("/api/bodypart/{name}")
    public ResponseEntity<Long> getBodyPart(@RequestParam String name) {
        return ResponseEntity.ok(bodyPartService.getBodyPartByName(name));
    }
    @PostMapping("/admin/add-body-part")
    public ResponseEntity<String> addBodyPart(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {
        bodyPartService.saveBodyPart(file, name);
        return ResponseEntity.ok("Image uploaded and saved successfully!");
    }
}

