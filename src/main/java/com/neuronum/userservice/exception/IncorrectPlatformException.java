package com.neuronum.userservice.exception;

public class IncorrectPlatformException extends RuntimeException {
    public IncorrectPlatformException() {
        super("No such platform!");
    }
}
