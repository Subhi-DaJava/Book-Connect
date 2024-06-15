package com.uyghurjava.book.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
     String saveFile(MultipartFile file, Integer userId);
}
