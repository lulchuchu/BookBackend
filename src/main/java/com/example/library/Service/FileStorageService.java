package com.example.library.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void save(MultipartFile file);

    Resource load(String filename);
}
