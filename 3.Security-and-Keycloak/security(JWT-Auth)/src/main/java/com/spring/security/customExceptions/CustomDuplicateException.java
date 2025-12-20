package com.spring.security.customExceptions;

public class CustomDuplicateException extends RuntimeException {

    public CustomDuplicateException(String message) {
        super(message);
    }

    public CustomDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomDuplicateException(Throwable cause) {
        super(cause);
    }
}

