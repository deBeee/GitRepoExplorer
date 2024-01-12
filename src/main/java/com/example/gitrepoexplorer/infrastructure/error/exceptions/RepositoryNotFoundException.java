package com.example.gitrepoexplorer.infrastructure.error.exceptions;

public class RepositoryNotFoundException extends RuntimeException{
    public RepositoryNotFoundException(String message){
        super(message);
    }
}
