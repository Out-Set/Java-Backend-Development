package com.savan.quartz.exceptionhandler;

public class CustomDuplicateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

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

