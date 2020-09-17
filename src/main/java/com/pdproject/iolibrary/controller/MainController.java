package com.pdproject.iolibrary.controller;

import com.pdproject.iolibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
//@RequestMapping(value = {"/home"})
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/home"})
    public String homePage() {
        return "index";
    }

    @GetMapping(value = "/test")
    public String testPage(){
        return "test";
    }

    @GetMapping(value = "/login")
    public String loginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null && error.equals("true")) {
            model.addAttribute("error", "true");
        }
        return "login";
    }

    @GetMapping(value = "/user/user-info")
    public String userInfoPage(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("email", user.getUsername());
        model.addAttribute("role", user.getAuthorities().stream().findFirst().get().getAuthority());
        return "user_info";
    }

    @GetMapping(value = "/admin/admin-page")
    public String adminPage(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("email", user.getUsername());
        model.addAttribute("role", user.getAuthorities().stream().findFirst().get().getAuthority());
        return "admin_page";
    }

    @GetMapping(value = "/403")
    public String page403() {
        return "403page";
    }
}
