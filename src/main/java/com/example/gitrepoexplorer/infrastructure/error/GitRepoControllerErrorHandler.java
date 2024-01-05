package com.example.gitrepoexplorer.infrastructure.error;

import com.example.gitrepoexplorer.infrastructure.controller.GitRepoController;
import com.example.gitrepoexplorer.infrastructure.error.dto.UnsupportedMediaTypeErrorDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UnsupportedMediaTypeException;
import com.example.gitrepoexplorer.infrastructure.error.dto.UserNotFoundErrorDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = GitRepoController.class)
@Log4j2
public class GitRepoControllerErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundErrorDto> handleUserNotFoundException(UserNotFoundException exception) {
        log.warn("UserNotFoundException thrown while fetching given user github data");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UserNotFoundErrorDto(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<UnsupportedMediaTypeErrorDto> handleUnsupportedMediaTypeException(UnsupportedMediaTypeException exception) {
        log.warn("UnsupportedMediaTypeException thrown while performing request");
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new UnsupportedMediaTypeErrorDto(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<UnsupportedMediaTypeErrorDto> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
        log.warn("HttpMediaTypeNotAcceptableException thrown while performing request");
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new UnsupportedMediaTypeErrorDto(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage()));
    }
}
