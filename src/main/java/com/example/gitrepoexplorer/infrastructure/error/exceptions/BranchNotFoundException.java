package com.example.gitrepoexplorer.infrastructure.error.exceptions;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String message) {
        super(message);
    }
}
