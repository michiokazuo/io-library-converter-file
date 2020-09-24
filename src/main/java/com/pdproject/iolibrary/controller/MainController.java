package com.pdproject.iolibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping(value = {"/"})
    public String homePage() {
        return "redirect:/home";
    }

    @GetMapping(value = {"/home"})
    public String homePage2() {
        return "home";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin_page";
    }

    @GetMapping(value = "/convert-file")
    public String convertFile(){
        return "convert_file";
    }

    @GetMapping("/api/v1/public/my-file")
    public String myFile() {
        return "my_file";
    }
}
