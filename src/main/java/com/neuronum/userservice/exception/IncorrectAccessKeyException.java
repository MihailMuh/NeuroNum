package com.neuronum.userservice.exception;

public class IncorrectAccessKeyException extends RuntimeException {
    public IncorrectAccessKeyException() {
        super("Client access key is not in whitelist");
    }
}
