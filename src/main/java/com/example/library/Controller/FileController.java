package com.example.library.Controller;

import com.example.library.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {
    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(
            value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@RequestParam String path) throws IOException {

        Resource imgFile = fileStorageService.load(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files) {
        String fileName = "";
        for (MultipartFile file : files) {
            fileStorageService.save(file);
            fileName += file.getOriginalFilename() + ",";
        }
        System.out.println(fileName.substring(0, fileName.length() - 1));
        return ResponseEntity.ok(fileName.substring(0, fileName.length() - 1));
    }
}

