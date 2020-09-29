package com.pdproject.iolibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(value = {"/convert-file", "/convert-file/{fromFormat}/to/{toFormat}"})
    public String convertFile(Model model,
                              @PathVariable(name = "fromFormat", required = false) String fromFormat,
                              @PathVariable(name = "toFormat", required = false) String toFormat){
        model.addAttribute("fromFormat", fromFormat);
        model.addAttribute("toFormat", toFormat);
        return "convert_file";
    }

    @GetMapping("/your-file")
    public String myFile() {
        return "your_file";
    }
}
