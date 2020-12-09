package com.github.camilo.movierental.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.github.camilo.movierental.exception.InvalidFileExtension;
import com.github.camilo.movierental.service.chainofresponsabilities.FileValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSizeValidator extends FileValidator{

    private static final String LOG_VALIDATING_FILE_SIZE = "Validating file size";
    private static final String LOG_ATTACHED_FILE_TOO_LONG = "Attached file too long";
    
    private static final int FILE_SIZE_IN_MB = 2;

    @Override
    public void validate(File file) {
        log.info(LOG_VALIDATING_FILE_SIZE);
        long size = 0L;
        size = file.length() / (1024 * 1024);
        if (size >= FILE_SIZE_IN_MB) {
            log.error(LOG_ATTACHED_FILE_TOO_LONG);
            throw new InvalidFileExtension(LOG_ATTACHED_FILE_TOO_LONG);
        }

        checkNext(file);
    }
    
}
