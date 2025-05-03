package org.attractor.java_share_hub.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.service.CategoryService;
import org.attractor.java_share_hub.service.FileService;
import org.attractor.java_share_hub.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final FileService fileService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") String page,
                        @RequestParam(required = false, defaultValue = "0") Long categoryId,
                        Model model) {
        Page<FileDto> publicFiles = fileService.getFilesByCategory(categoryId, page);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("files", publicFiles.getContent());
        model.addAttribute("currentPage", publicFiles.getNumber());
        model.addAttribute("totalPages", publicFiles.getTotalPages());
        model.addAttribute("selectedCategoryId", categoryId);
        return "main/main";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@AuthenticationPrincipal User principal, @PathVariable Long fileId) {
        return fileService.downloadFile(principal, fileId);
    }

}
