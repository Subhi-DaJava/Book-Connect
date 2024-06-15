package com.uyghurjava.book.file;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
@RequiredArgsConstructor
public class IFileStorageService implements FileStorageService {

    @Value("${application.file.upload.cover-images-output-path}")
    private String fileUploadPath;

    @Override
    public String saveFile (
            @Nonnull MultipartFile sourceFile,
            @Nonnull Integer userId
    ) {

        // sub path for every/each user
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String fileUploadSubPath) {

        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;

        File targetFile = new File(finalUploadPath);

        if (!targetFile.exists()) {
            boolean folderCreated = targetFile.mkdirs();
            if (!folderCreated) {
                log.error("Failed to create folder: {}", finalUploadPath);
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        // target file path, e.g. /home/user/cover-images/123456.jpg, 123456 is the current time in milliseconds
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;

        Path targetPath = Paths.get(targetFilePath);

        // import java.nio.file.Path; IO Exception
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved successfully: {}", targetFilePath);
        } catch (IOException e) {
            log.error("Failed to save file", e);
        }
        return targetFilePath;
    }

    private String getFileExtension(String fileName) {
        if(fileName == null || fileName.isEmpty()) {
            return "";
        }
        // get the last dot index, if not found return -1
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex == -1) {
            return "";
        }
        // get the file extension, convert to a lower case, JPG -> jpg
        return fileName.substring(lastDotIndex + 1 ).toLowerCase();
    }
}
