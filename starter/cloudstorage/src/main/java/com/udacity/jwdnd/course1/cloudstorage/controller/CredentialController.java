package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String insertOrUpdateCredential (Model model, Credential credential, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        credential.setUserId(userId);
        try{
            credentialService.insertOrUpdateCredential(credential);
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage",  "credential has been successfully inserted!");
        } catch (Exception e){
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in adding credential");
            e.printStackTrace();
        }
        return "result";
    }

    @DeleteMapping("/{credentialId}")
    public String deleteNote (Model model, @PathVariable Integer credentialId, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        Integer credentialIsDeleted = credentialService.deleteCredential(credentialId, userId);

        if (credentialIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "credential has been successfully deleted!");
        } else {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "credential deletion failed!");
        }
        return "result";
    }
}
