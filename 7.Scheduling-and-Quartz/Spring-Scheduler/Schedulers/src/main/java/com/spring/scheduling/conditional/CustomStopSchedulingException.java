package com.spring.scheduling.conditional;

public class CustomStopSchedulingException extends RuntimeException {
    public CustomStopSchedulingException(String message) {
        super(message);
    }
}
