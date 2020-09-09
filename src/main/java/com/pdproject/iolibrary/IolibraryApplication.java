package com.pdproject.iolibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.pdproject.iolibrary", exclude = {SecurityAutoConfiguration.class})
public class IolibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(IolibraryApplication.class, args);
    }

}
