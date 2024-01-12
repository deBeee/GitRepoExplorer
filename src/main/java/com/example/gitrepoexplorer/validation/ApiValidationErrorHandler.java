package com.example.gitrepoexplorer.validation;

import com.example.gitrepoexplorer.validation.dto.ApiValidationErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApiValidationErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiValidationErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> messages = getErrorsFromException(exception);
        ApiValidationErrorResponseDto response = new ApiValidationErrorResponseDto(messages, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    private List<String> getErrorsFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
