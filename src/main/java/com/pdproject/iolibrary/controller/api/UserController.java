package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.ResponseMessage;
import com.pdproject.iolibrary.service.UserService;
import com.pdproject.iolibrary.utils.FileUtils;
import lombok.AllArgsConstructor;
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

    private ResponseMessage responseMessage;

    private final FileUtils fileUtils;

    @GetMapping(value = "/find-all")
    public ResponseMessage findAllUsers() {
        try {
            responseMessage = responseMessage.successResponse("success", userService.findAll());
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.failResponse("Get all user fail");
        }
        return responseMessage;
    }

    @GetMapping(value = "/find-by-email/{email}")
    public ResponseMessage findByEmail(@PathVariable(name = "email") String email) {
        try {
            responseMessage = responseMessage.successResponse("success", userService.findByEmail(email));
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.failResponse("Don't exists email : " + email);
        }
        return responseMessage;
    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseMessage findById(@PathVariable(name = "id") int id) {
        try {
            responseMessage = responseMessage.successResponse("success", userService.findById(id));
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.failResponse("Don't exists user with id : " + id);
        }
        return responseMessage;
    }

    @PostMapping(value = "/register")
    public ResponseMessage registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        UserDTO userResult = null;
        if (result.hasErrors()) {
            return responseMessage.failResponse("Error validate value");
        }
        try {
            userResult = userService.insert(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.failResponse("Insert user fail");
        }
        return userResult != null ? responseMessage.successResponse("success", userResult) : responseMessage.failResponse("Exists email");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteUser(@PathVariable(name = "id") int id) {
        try {
            if (userService.delete(id)) {
                return responseMessage.successResponse("success", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMessage.failResponse("Dont exist user with id : " + id);
    }

    @PutMapping(value = "/update")
    public ResponseMessage updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        UserDTO userResult = null;
        if (result.hasErrors()) {
            return responseMessage.failResponse("Error validate value");
        }
        try {
            userResult = userService.update(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.failResponse("Insert user fail");
        }
        return userResult != null ? responseMessage.successResponse("success", userResult) : responseMessage.failResponse("Exists email");
    }

    @PostMapping(value = "/upload-avatar")
    public ResponseMessage uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            fileUtils.storeFile(avatar);
        } catch (IOException e) {
            e.printStackTrace();
            return responseMessage.failResponse("Store file error");
        }
        return responseMessage.successResponse("success", fileUtils);
    }

}
