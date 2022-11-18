package com.newspaper.deliverydocket;
/**
 * @author  Yuliia Dovbak
 */
public class DeliveryDocketExceptionHandler extends Exception{
    private String message;

    public DeliveryDocketExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}
