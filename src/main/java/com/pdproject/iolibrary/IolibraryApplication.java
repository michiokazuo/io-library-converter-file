package com.pdproject.iolibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pdproject.iolibrary")
public class IolibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(IolibraryApplication.class, args);
    }

}
