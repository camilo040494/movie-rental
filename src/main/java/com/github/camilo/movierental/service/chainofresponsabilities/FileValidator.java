package com.github.camilo.movierental.service.chainofresponsabilities;

import java.io.File;
import java.util.Objects;

public abstract class FileValidator {

    protected FileValidator next;

    public void setNext(FileValidator nextHandler) {
        next = nextHandler;
    }

    public abstract void validate(File file);

    protected void checkNext(File file) {
        if (Objects.nonNull(next)) {
            next.validate(file);
        }
    }
    
}
