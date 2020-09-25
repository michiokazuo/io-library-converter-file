package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public/file/*")
public class UploadFileController {

    private FileUtils fileUtils;

    @PostMapping(value = "/upload-avatar")
    public ResponseEntity<FileUtils> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            fileUtils.storeFile(avatar);
            return ResponseEntity.ok(fileUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
