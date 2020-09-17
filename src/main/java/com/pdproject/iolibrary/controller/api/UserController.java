package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.ResponseMessage;
import com.pdproject.iolibrary.model.User;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/public/user/*")
public class UserController {

    private UserService userService;

    private ResponseMessage responseMessage;

    @GetMapping(value = "/find-all")
    public ResponseMessage findAllUsers(){
        try {
            responseMessage = responseMessage.successResponse("success",userService.findAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Get all user faild");
        }
        return responseMessage;
    }

    @GetMapping(value = "/find-by-email/{email}")
    public ResponseMessage findByEmail(@PathVariable(name = "email") String email){
        try {
            responseMessage = responseMessage.successResponse("success",userService.findByEmail(email));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Don't exists email : " + email);
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
        } catch (SQLException throwables) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            responseMessage = responseMessage.faildResponse("Insert user faild");
        }
        return userResult != null ? responseMessage.successResponse("success", userResult) : responseMessage.faildResponse("Exists email");
    }

    @PostMapping(value = "/upload-avatar")
    public ResponseMessage uploadAvatar(@RequestParam("avatar") MultipartFile avatar){
        return responseMessage.successResponse("success", avatar.getOriginalFilename());
    }

}
