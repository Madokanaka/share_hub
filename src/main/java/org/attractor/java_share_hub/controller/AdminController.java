package org.attractor.java_share_hub.controller;

import lombok.RequiredArgsConstructor;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.service.CategoryService;
import org.attractor.java_share_hub.service.FileService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileService fileService;
    private final CategoryService categoryService;
    @GetMapping
    public String adminHome(@RequestParam(required = false) String page,
                            @RequestParam(required = false) Long categoryId,Model model) {
        Page<FileDto> publicFiles = fileService.getAllFiles(page, categoryId);
        model.addAttribute("title", "Админ-панель");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("files", publicFiles.getContent());
        model.addAttribute("currentPage", publicFiles.getNumber());
        model.addAttribute("totalPages", publicFiles.getTotalPages());
        model.addAttribute("selectedCategoryId", categoryId);
        return "admin/admin";
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("success", "Файл успешно удален.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении файла: " + e.getMessage());
        }
        return "redirect:/admin";
    }

}
