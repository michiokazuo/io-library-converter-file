package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/private/*")
@AllArgsConstructor
public class AdminController {

    private UserService userService;

    @GetMapping(value = "/user/find-all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        try {
            List<UserDTO> userDTOList = userService.findAll();
            return userDTOList != null ? ResponseEntity.ok(userDTOList) : ResponseEntity.noContent().build();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/user/find-by-email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable(name = "email") String email) {
        try {
            UserDTO userDTO = userService.findByEmail(email);
            return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.noContent().build();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/user/find-by-id/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") int id) {
        try {
            UserDTO userDTO = userService.findById(id);
            return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.noContent().build();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") int id){
        try {
            if(userService.delete(id)){
                return ResponseEntity.ok("Delete user success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Delete user fail");
    }
}
