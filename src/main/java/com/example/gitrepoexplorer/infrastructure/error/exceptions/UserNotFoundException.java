package com.example.gitrepoexplorer.infrastructure.error.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }
}
