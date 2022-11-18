package com.newspaper.order;

public class OrderExceptionHandler extends Exception {

    private String message;

    public OrderExceptionHandler(String errMessage) {
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}