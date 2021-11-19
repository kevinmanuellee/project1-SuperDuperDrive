package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping()
    public String uploadFile (Model model, @RequestParam("fileUpload")MultipartFile file, Authentication authentication) {
        Integer userId = userService.getUserId(authentication.getName());
        String fileName = file.getOriginalFilename();
        if(!fileName.isEmpty()) {
            if (!fileService.isDuplicate(file, userId)) {
                if (file.getSize() < 10485760) {
                    fileService.uploadFile(file, userId);
                    model.addAttribute("isSuccessful", true);
                    model.addAttribute("successMessage", fileName + " has been successfully uploaded!");
                } else {
                    model.addAttribute("hasAnError", true);
                    model.addAttribute("errorMessage", "File size must be smaller than 10485760, current file size= (" + file.getSize() + ")");
                }
            } else {
                model.addAttribute("hasAnError", true);
                model.addAttribute("errorMessage", "File with " + fileName + " existed!");
            }
        } else {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Uploading empty file is not possible");
        }
        return "result";
    }

    @GetMapping("download/{fileId}")
    public ResponseEntity downloadFile (@PathVariable Integer fileId, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        File file = fileService.getFile(fileId, userId);

        final byte[] data = fileService.downloadFile(file.getFileName(), userId);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("delete/{fileId}")
    public String deleteFile (Model model, @PathVariable Integer fileId, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        File file = fileService.getFile(fileId, userId);
        Integer fileIsDeleted = fileService.deleteFile(fileId, userId, file.getFileName());

        if (fileIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "file has been successfully deleted!");
        } else {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "file deletion failed!");
        }
        return "result";
    }

}