package org.attractor.java_share_hub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.model.Category;
import org.attractor.java_share_hub.model.FileEntity;
import org.attractor.java_share_hub.model.User;
import org.attractor.java_share_hub.repository.FileRepository;
import org.attractor.java_share_hub.service.CategoryService;
import org.attractor.java_share_hub.service.FileService;
import org.attractor.java_share_hub.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserService userService;
    private final CategoryService categoryService;


    @Override
    public void saveFile(FileEntity file) {
        log.info("Saving file: {}", file.getFilename());
        fileRepository.save(file);
    }

    @Override
    public Page<FileEntity> getAllPublicFiles(String pageStr) {
        int page = parsePageParameter(pageStr);
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FileEntity> filesPage = fileRepository.findByPublicTrue(pageable);

        if (filesPage.getTotalPages() > 0 && page >= filesPage.getTotalPages()) {
            log.warn("Запрашиваемая страница {} больше допустимой, возвращаем последнюю", page);
            pageable = PageRequest.of(filesPage.getTotalPages() - 1, size);
            filesPage = fileRepository.findByPublicTrue(pageable);
        }

        return filesPage;
    }

    @Override
    public List<FileDto> getAllPublicFileDtos(String pageStr) {
        return getAllPublicFiles(pageStr)
                .stream()
                .map(this::mapToDto)
                .toList();
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
    public List<FileDto> getUserFileDtos(String userEmail, String pageStr) {
        return getUserFiles(userEmail, pageStr)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<FileDto> getFileDtosByCategory(Long categoryId, String pageStr) {
        return getFilesByCategory(categoryId, pageStr)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    @Override
    public FileDto getFileByPrivateKey(String privateKey) {
        FileEntity file = fileRepository.findByDownloadKey(privateKey)
                .orElseThrow(() -> new IllegalArgumentException("Файл с данным ключом не найден"));

        return mapToDto(file);
    }


    @Override
    public Page<FileEntity> getFilesByCategory(Long categoryId, String pageStr) {
        Category categoryOpt = categoryService.findById(categoryId);

        int page = parsePageParameter(pageStr);
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FileEntity> filesPage = fileRepository.findByCategory(categoryOpt, pageable);

        if (filesPage.getTotalPages() > 0 && page >= filesPage.getTotalPages()) {
            log.warn("Запрашиваемая страница {} больше допустимой, возвращаем последнюю", page);
            pageable = PageRequest.of(filesPage.getTotalPages() - 1, size);
            filesPage = fileRepository.findByCategory(categoryOpt, pageable);
        }

        return filesPage;
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

}
