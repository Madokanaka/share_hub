package org.attractor.java_share_hub.controller;

import lombok.RequiredArgsConstructor;
import org.attractor.java_share_hub.dto.FileDto;
import org.attractor.java_share_hub.dto.UserDto;
import org.attractor.java_share_hub.service.FileService;
import org.attractor.java_share_hub.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileService fileService;

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
        return "profile/profile";
    }

}
