package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.ResponseMessage;
import com.pdproject.iolibrary.service.UserService;
import com.pdproject.iolibrary.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/public/user/*")
public class UserController {

    private UserService userService;

    private ResponseMessage responseMessage;

    private FileUtils fileUtils;

    @GetMapping(value = "/find-all")
    public ResponseMessage findAllUsers(){
        try {
            responseMessage = responseMessage.successResponse("success",userService.findAll());
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Get all user faild");
        }
        return responseMessage;
    }

    @GetMapping(value = "/find-by-email/{email}")
    public ResponseMessage findByEmail(@PathVariable(name = "email") String email){
        try {
            responseMessage = responseMessage.successResponse("success",userService.findByEmail(email));
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Don't exists email : " + email);
        }
        return responseMessage;
    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseMessage findById(@PathVariable(name = "id") int id){
        try {
            responseMessage = responseMessage.successResponse("success",userService.findById(id));
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Don't exists user with id : " + id);
        }
        return responseMessage;
    }

    @PostMapping(value = "/register")
    public ResponseMessage registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult result){
        UserDTO userResult = null;
        if (result.hasErrors()){
            return responseMessage.faildResponse("Error validate value");
        }
        try {
            userResult = userService.insert(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Insert user faild");
        }
        return userResult != null ? responseMessage.successResponse("success", userResult) : responseMessage.faildResponse("Exists email");
    }

    @PutMapping(value = "/update")
    public ResponseMessage updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult result){
        UserDTO userResult = null;
        if (result.hasErrors()){
            return responseMessage.faildResponse("Error validate value");
        }
        try {
            userResult = userService.update(userDTO);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Insert user faild");
        }
        return userResult != null ? responseMessage.successResponse("success", userResult) : responseMessage.faildResponse("Exists email");
    }

    @PostMapping(value = "/upload-avatar")
    public ResponseMessage uploadAvatar(@RequestParam("avatar") MultipartFile avatar){
        try {
            fileUtils.storeFile(avatar);
        } catch (IOException e) {
            e.printStackTrace();
            return responseMessage.faildResponse("Store file error");
        }
        return responseMessage.successResponse("success", fileUtils);
    }

    @GetMapping(value = "/login-success")
    public ResponseMessage loginSuccess(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserDTO userDTO = null;
        try {
            userDTO = userService.findByEmail(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMessage.successResponse("login success", userDTO);
    }

    @GetMapping(value = "/login-fail")
    public ResponseMessage loginFail(){
        return responseMessage.faildResponse("Username or Password incorrect");
    }

}
