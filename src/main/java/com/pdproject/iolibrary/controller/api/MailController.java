package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    private final JavaMailSender mailSender;

    private final UserService userService;

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

    @PostMapping("/share-file")
    public ResponseEntity<String> shareFiles(@RequestParam("email") String email,
                                             @RequestParam("link_share") String linkShare) {
        try {

            sendEmail(email, "LINK_SHARE_FILE", linkShare);

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
