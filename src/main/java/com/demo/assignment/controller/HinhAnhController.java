package com.demo.assignment.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@CrossOrigin("*")
@RequestMapping("/hinh-anh")
public class HinhAnhController {
    private final String uploadDir = "D:\\assignment\\src\\main\\resources\\static\\";
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> hinhAnh(@PathVariable String fileName) {
        File file = new File(uploadDir + fileName);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
