package com.newspaper.deliveryperson;
// Not used currently, may be implemented for use with test validation

public class DeliveryPersonExceptionHandler extends Throwable {

        private String message;

        public DeliveryPersonExceptionHandler(String errMessage){
            message = errMessage;
        }

        public String getMessage() {
            return message;
        }
    }

