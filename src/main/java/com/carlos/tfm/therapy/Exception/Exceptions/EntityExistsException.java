package com.carlos.tfm.therapy.Exception.Exceptions;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String message) {
        super(message);
    }
}