package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private UserService userService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, UserService userService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(Model model, Authentication authentication){
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);
        model.addAttribute("fileList", this.fileService.getAllFiles(userId));
        return "home";
    }


}
