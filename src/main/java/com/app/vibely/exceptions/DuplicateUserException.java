package com.app.vibely.exceptions;

public class DuplicateUserException extends  RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
