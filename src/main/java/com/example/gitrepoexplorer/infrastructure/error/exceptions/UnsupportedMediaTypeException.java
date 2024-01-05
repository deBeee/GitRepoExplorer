package com.example.gitrepoexplorer.infrastructure.error.exceptions;

public class UnsupportedMediaTypeException extends RuntimeException {
    public UnsupportedMediaTypeException(String message){
        super(message);
    }
}
