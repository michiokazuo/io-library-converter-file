package com.pdproject.iolibrary.config;

import com.pdproject.iolibrary.model.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ResponseMessage responseMessage(){
        return new ResponseMessage();
    }

}
