package com.github.camilo.movierental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.camilo.movierental.service.chainofresponsabilities.FileValidator;

@Configuration
public class FileValidatorConfig {
    
    @Bean({"fileValidator"})
    public FileValidator buildChainOfResponsabilities(@Autowired @Qualifier(value = "fileNameValidator") FileValidator nameValidator, 
            @Autowired @Qualifier(value = "fileSizeValidator") FileValidator sizeValidator){
        sizeValidator.setNext(nameValidator);
        return sizeValidator;
    }
    
}
