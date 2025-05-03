package org.attractor.java_share_hub.util;

import lombok.SneakyThrows;

import org.attractor.java_share_hub.exception.BadRequestException;
import org.attractor.java_share_hub.exception.ResourceNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileUtil {
    private final static String UPLOAD_DIR = "data/";

    public FileUtil() {

    }
    @SneakyThrows
    public String saveUploadFile(MultipartFile file, String subDir) {
        String uuidFile = java.util.UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(UPLOAD_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + resultFileName);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }

        return resultFileName;
    }

    public ResponseEntity<Resource> getOutputFile(String fileName, String subDir, MediaType mediaType) {
        Path filePath = Paths.get(UPLOAD_DIR + subDir + fileName);
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("File not found: " + fileName);
        }

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(fileBytes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Could not read file: " + fileName);
        }
    }

}
