package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.service.UserService;
import com.pdproject.iolibrary.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/public/user/*")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/my-info")
    public ResponseEntity<UserDTO> getUser(Authentication authentication){
        UserDTO userDTO = null;
        try {
            User user = (User) authentication.getPrincipal();
            userDTO = userService.findByEmail(user.getUsername());
        } catch (Exception e) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            try {
                userDTO = userService.findByEmail(token.getPrincipal().getAttribute("email"));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UserDTO> insertUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        UserDTO userResult = null;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            userResult = userService.insert(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return userResult != null ? ResponseEntity.ok(userResult) : ResponseEntity.badRequest().build();
    }
  
    @PutMapping(value = "/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        UserDTO userResult = null;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            userResult = userService.update(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return userResult != null ? ResponseEntity.ok(userResult) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<UserDTO> updatePassword(@RequestParam("email") String email,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("passwordConfirm") String passwordConfirm){
        UserDTO userDTO = null;
        try {
            if(password.equals(passwordConfirm)){
                userDTO = userService.updatePassword(email, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.badRequest().build();
    }

}
