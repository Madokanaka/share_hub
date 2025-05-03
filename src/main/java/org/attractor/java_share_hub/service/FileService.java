package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface FileService {

    void saveFile(FileEntity file);

    Page<FileDto> getAllPublicFiles(String pageStr);

    Page<FileEntity> getUserFiles(String userEmail, String pageStr);

    FileDto getFileByPrivateKey(String privateKey);

    Page<FileDto> getFilesByCategory(Long categoryId, String pageStr);

    Optional<FileEntity> getById(Long id);

    FileDto getFileById(Long fileId);

    ResponseEntity<Resource> downloadFile(Long fileId);
}
