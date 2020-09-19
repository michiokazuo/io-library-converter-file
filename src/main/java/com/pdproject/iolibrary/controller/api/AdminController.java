package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.model.ResponseMessage;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/private/user/*")
@AllArgsConstructor
public class AdminController {

    private UserService userService;
    private ResponseMessage responseMessage;

    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteUser(@PathVariable(name = "id") int id){
        try {
            if(userService.delete(id)){
                return responseMessage.successResponse("success",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMessage.faildResponse("Dont exist user with id : " + id);
    }
}
