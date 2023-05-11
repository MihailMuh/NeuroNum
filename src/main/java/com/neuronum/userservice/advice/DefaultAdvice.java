package com.neuronum.userservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.neuronum.userservice.exception.ClientAlreadyRegisteredException;
import com.neuronum.userservice.exception.IncorrectAccessKeyException;
import com.neuronum.userservice.exception.IncorrectPlatformException;

@ControllerAdvice
public class DefaultAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ClientAlreadyRegisteredException.class)
    public String handleUserRegisterException(ClientAlreadyRegisteredException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IncorrectAccessKeyException.class)
    public String handleIncorrectKeyException(IncorrectAccessKeyException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectPlatformException.class)
    public String handleIncorrectPlatformException(IncorrectPlatformException exception) {
        return exception.getMessage();
    }
}
