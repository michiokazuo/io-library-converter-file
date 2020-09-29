package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.service.FileService;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/mail/*")
@AllArgsConstructor
public class MailController {

    private final JavaMailSender mailSender;

    private final UserService userService;

    private final FileService fileService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        UserDTO userDTO = null;
        try {
            String newPassword = RandomString.make(17);
            userDTO = userService.updatePassword(email, newPassword);
            sendEmail(email, "RESET YOUR PASSWORD", "Your new password : " + newPassword);
        } catch (Exception e) {
            userDTO = null;
            e.printStackTrace();
        }
        return userDTO != null ? ResponseEntity.ok("Get your password form your email") : ResponseEntity.badRequest().build();
    }

    @PostMapping("/share-file/{id}")
    public ResponseEntity<String> shareFiles(@PathVariable("id") Integer id,
                                             @RequestParam("email") String email) {
        try {
            FileDTO fileDTO = fileService.findById(id);
            if (fileDTO == null) return ResponseEntity.badRequest().build();
            sendEmail(email, "LINK_SHARE_FILE", "http://localhost:8080" + fileDTO.getUrlDownload());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Share Complete");
    }

    private void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
