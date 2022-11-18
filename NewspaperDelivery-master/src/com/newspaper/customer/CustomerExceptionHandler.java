package com.newspaper.customer;
/**
 * @author  Yuliia Dovbak
 */
public class CustomerExceptionHandler  extends Exception{

    private String message;

    public CustomerExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}
