package org.attractor.java_share_hub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.exception.NoAccessException;
import org.attractor.java_share_hub.exception.ResourceNotFoundException;
import org.attractor.java_share_hub.model.Category;
import org.attractor.java_share_hub.model.FileEntity;
import org.attractor.java_share_hub.model.User;
import org.attractor.java_share_hub.repository.FileRepository;
import org.attractor.java_share_hub.service.CategoryService;
import org.attractor.java_share_hub.service.FileService;
import org.attractor.java_share_hub.service.UserService;
import org.attractor.java_share_hub.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;


    @Override
    public void saveFile(FileEntity file) {
        log.info("Saving file: {}", file.getFilename());
        fileRepository.save(file);
    }

    @Override
    public Page<FileDto> getAllPublicFiles(String pageStr) {
        int page = parsePageParameter(pageStr);
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FileEntity> filesPage = fileRepository.findByIsPublicTrue(pageable);

        if (filesPage.getTotalPages() > 0 && page >= filesPage.getTotalPages()) {
            log.warn("Запрашиваемая страница {} больше допустимой, возвращаем последнюю", page);
            pageable = PageRequest.of(filesPage.getTotalPages() - 1, size);
            filesPage = fileRepository.findByIsPublicTrue(pageable);
        }

        return filesPage.map(this::mapToDto);
    }


    @Override
    public Page<FileEntity> getUserFiles(String userEmail, String pageStr) {
        User user = userService.findUserByEmail(userEmail);
        int page = parsePageParameter(pageStr);
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FileEntity> filesPage = fileRepository.findByOwner(user, pageable);

        if (filesPage.getTotalPages() > 0 && page >= filesPage.getTotalPages()) {
            log.warn("Запрашиваемая страница {} больше допустимой, возвращаем последнюю", page);
            pageable = PageRequest.of(filesPage.getTotalPages() - 1, size);
            filesPage = fileRepository.findByOwner(user, pageable);
        }

        return filesPage;
    }


    @Override
    public FileDto getFileByPrivateKey(String privateKey) {
        FileEntity file = fileRepository.findByDownloadKey(privateKey)
                .orElseThrow(() -> new ResourceNotFoundException("Файл с данным ключом не найден"));

        return mapToDto(file);
    }


    @Override
    public Page<FileDto> getFilesByCategory(Long categoryId, String pageStr) {
        if (categoryId == null || categoryId == 0) {
            return getAllPublicFiles(pageStr);
        }
        Category categoryOpt = categoryService.findById(categoryId);

        int page = parsePageParameter(pageStr);
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FileEntity> filesPage = fileRepository.findByCategoryAndIsPublicTrue(categoryOpt, pageable);

        if (filesPage.getTotalPages() > 0 && page >= filesPage.getTotalPages()) {
            log.warn("Запрашиваемая страница {} больше допустимой, возвращаем последнюю", page);
            pageable = PageRequest.of(filesPage.getTotalPages() - 1, size);
            filesPage = fileRepository.findByCategoryAndIsPublicTrue(categoryOpt, pageable);
        }

        return filesPage.map(this::mapToDto);
    }


    @Override
    public Optional<FileEntity> getById(Long id) {
        return fileRepository.findById(id);
    }

    private int parsePageParameter(String page) {
        try {
            int pageNumber = Integer.parseInt(page);
            if (pageNumber < 0) {
                log.warn("Page index less than 0, setting to 0");
                return 0;
            }
            return pageNumber;
        } catch (NumberFormatException e) {
            log.warn("Invalid page parameter: {}. Setting to default 0", page);
            return 0;
        }
    }

    private FileDto mapToDto(FileEntity file) {
        return FileDto.builder()
                .id(file.getId())
                .filename(file.getFilename())
                .isPublic(file.isPublic())
                .downloadKey(file.getDownloadKey())
                .downloadCount(file.getDownloadCount())
                .uploadDate(file.getUploadDate())
                .ownerEmail(file.getOwner().getEmail())
                .categoryName(file.getCategory() != null ? file.getCategory().getName() : null)
                .build();
    }

    @Override
    public FileDto getFileById(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        return mapToDto(fileEntity);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long fileId) {
        FileDto fileDto = getFileById(fileId);

        if (!fileDto.isPublic()) {
            log.warn("Attempt to download non-public file with ID: {}", fileId);
            throw new NoAccessException("This file is private");
        }

        log.info("Downloading file: {}", fileDto.getFilename());
        ResponseEntity<Resource> response = fileUtil.getOutputFile(fileDto.getFilename(), "upload/", MediaType.APPLICATION_OCTET_STREAM);
        FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow();
        fileEntity.setDownloadCount(fileEntity.getDownloadCount() + 1);
        fileRepository.save(fileEntity);
        return response;
    }


}
