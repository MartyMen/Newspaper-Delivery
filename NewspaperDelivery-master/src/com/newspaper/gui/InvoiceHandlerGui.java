package com.newspaper.gui;

import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import com.newspaper.deliverydocket.Utility;

public class InvoiceHandlerGui {
        Utility ut;

        InvoiceHandlerGui() {
            ut = new Utility();
        }

        public int validateDeliveryPersonId (String invoiceid) throws DeliveryDocketExceptionHandler {
            int id;

            try {
                // try parsing String into int
                id = Integer.parseInt( invoiceid);

                // if parse went correct, checking if Invoice exists
                if(!ut.ifInvoiceExists(id)) {
                    throw new DeliveryDocketExceptionHandler("Delivery person with id " + id + " does not exist");
                }
            }
            catch (NumberFormatException e) {
                throw new DeliveryDocketExceptionHandler("Delivery Person Id should be a number");
            }

            return id;
        }
}
