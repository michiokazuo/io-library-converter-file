package com.pdproject.iolibrary.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/convert/*")
public class FileController {
    //

    @PostMapping("to/{toFormat}")
    public void toEncrypt(@PathVariable(name = "toFormat",required = false) String toFormat){
    }

}
