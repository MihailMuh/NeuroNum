package com.neuronum.userservice.exception;

public class ClientAlreadyRegisteredException extends RuntimeException {
    public ClientAlreadyRegisteredException() {
        super("Client already registered!");
    }
}
