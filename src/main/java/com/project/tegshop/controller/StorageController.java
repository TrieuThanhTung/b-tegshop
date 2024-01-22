package com.project.tegshop.controller;

import com.project.tegshop.service.image.StorageService;
import com.project.tegshop.shared.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class StorageController {
    @Autowired
    private StorageService storageService;
    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImageHandler(MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(uploadImage));
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> downloadImageHandler(@PathVariable String fileName) {
        byte[] imageData = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
