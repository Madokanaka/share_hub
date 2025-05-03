package org.attractor.java_share_hub.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.dto.UserDto;
import org.attractor.java_share_hub.service.CategoryService;
import org.attractor.java_share_hub.service.FileService;
import org.attractor.java_share_hub.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final CategoryService categoryService;

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User principal,
                              @RequestParam(defaultValue = "0") String page,
                              Model model) {
        Page<FileDto> userFiles = fileService.getUserFiles(principal.getUsername(), page);
        UserDto userProfileDto = userService.getUserProfileAuth(principal);
        model.addAttribute("userProfile", userProfileDto);
        model.addAttribute("files", userFiles.getContent());
        model.addAttribute("currentPage", userFiles.getNumber());
        model.addAttribute("totalPages", userFiles.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        return "profile/profile";
    }

    @PostMapping("/profile/upload")
    public String uploadFile(@AuthenticationPrincipal User principal,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("categoryId") Long categoryId,
                             @RequestParam(value = "isPublic", defaultValue = "false") boolean isPublic) {
        fileService.uploadFile(principal.getUsername(), file, categoryId, isPublic);
        return "redirect:/profile";
    }

    @PostMapping("/profile/createDownloadLink")
    public String createDownloadLink(@RequestParam("fileId") Long fileId,
                                     @AuthenticationPrincipal User principal,
                                     @RequestParam(defaultValue = "0") String page,
                                     HttpServletRequest request,
                                     Model model) {
        FileDto fileDto = fileService.getFileById(fileId);

        if (!fileDto.isPublic() && fileDto.getOwnerEmail().equals(principal.getUsername())) {
            String downloadKey = fileService.generateDownloadLink(fileId);
            String protocol = request.getScheme();
            String host = request.getServerName();
            int port = request.getServerPort();
            Page<FileDto> userFiles = fileService.getUserFiles(principal.getUsername(), page);
            UserDto userProfileDto = userService.getUserProfileAuth(principal);
            model.addAttribute("userProfile", userProfileDto);
            model.addAttribute("files", userFiles.getContent());
            model.addAttribute("currentPage", userFiles.getNumber());
            model.addAttribute("totalPages", userFiles.getTotalPages());
            model.addAttribute("categories", categoryService.findAll());

            String downloadLink = protocol + "://" + host + ":" + port + "/download/key/" + downloadKey;
            model.addAttribute("downloadLink", downloadLink);
        } else {
            model.addAttribute("error", "Only file owners can create a download link for private files.");
        }

        return "profile/profile";
    }


}
