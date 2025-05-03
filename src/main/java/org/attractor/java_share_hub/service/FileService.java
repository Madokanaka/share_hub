package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.model.FileEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FileService {

    void saveFile(FileEntity file);

    Page<FileEntity> getAllPublicFiles(String pageStr);

    List<FileDto> getAllPublicFileDtos(String pageStr);

    Page<FileEntity> getUserFiles(String userEmail, String pageStr);

    List<FileDto> getUserFileDtos(String userEmail, String pageStr);

    List<FileDto> getFileDtosByCategory(Long categoryId, String pageStr);

    FileDto getFileByPrivateKey(String privateKey);

    Page<FileEntity> getFilesByCategory(Long categoryId, String pageStr);

    Optional<FileEntity> getById(Long id);
}
