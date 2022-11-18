package com.newspaper.gui;

import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import com.newspaper.deliverydocket.Utility;

public class DeliveryDocketHandler {
    Utility ut;

    DeliveryDocketHandler() {
        ut = new Utility();
    }

    public int validateDeliveryPersonId (String deliveryPersonId) throws DeliveryDocketExceptionHandler {
        int id;

        try {
            // try parsing String into int
            id = Integer.parseInt( deliveryPersonId);

            // if parse went correct, checking if delivery person exists
            if(!ut.deliveryPersonExists(id)) {
                throw new DeliveryDocketExceptionHandler("Delivery person with id " + id + " does not exist");
            }
            else if(!ut.deliveryPersonActive(id)) {
                throw new DeliveryDocketExceptionHandler("Delivery person with id " + id + " is not available for work");
            }
        }
        catch (NumberFormatException e) {
            throw new DeliveryDocketExceptionHandler("Delivery Person Id should be a number");
        }

        return id;
    }

    public boolean validateDelivery(int deliveryId) {
        String query = "select count(*) as total from delivery where delivery_id = " + deliveryId + ";";

        return ut.exists(query);
    }
}
