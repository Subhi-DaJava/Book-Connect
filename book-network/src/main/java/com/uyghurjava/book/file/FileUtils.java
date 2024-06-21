package com.uyghurjava.book.file;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    public static byte[] readFileFromLocation(String fileURL) {
        if(StringUtils.isBlank(fileURL)) {
            return new byte[0];
        }
        try {
            Path filePath = new File(fileURL).toPath();
            return Files.readAllBytes(filePath);

        } catch (IOException e) {
            log.warn("Failed to read file from location:{}", fileURL);
        }
        return new byte[0];
    }

    private FileUtils() {
        throw new IllegalStateException("FileUtiles class should not be instantiated!");
    }
}