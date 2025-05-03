package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileService {

    void saveFile(FileEntity file);

    Page<FileDto> getAllPublicFiles(String pageStr);

    Page<FileDto> getUserFiles(String userEmail, String pageStr, Long userId);

    FileDto getFileByPrivateKey(String privateKey);

    Page<FileDto> getFilesByCategory(Long categoryId, String pageStr);

    Optional<FileEntity> getById(Long id);

    FileDto getFileById(Long fileId);

    FileEntity findById(Long fileId);

    void save(FileEntity file);

    ResponseEntity<Resource> downloadFile(User principal, Long fileId);

    ResponseEntity<Resource> downloadFileByKey(String downloadKey);

    String generateDownloadLink(Long fileId);

    void uploadFile(String userEmail, MultipartFile file, Long categoryId, boolean isPublic);

    Page<FileDto> getAllFiles(String pageNumber, Long categoryId);

    void deleteFile(Long fileId);
}
