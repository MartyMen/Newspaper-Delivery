package com.newspaper.deliverydocket;

import java.time.LocalDate;
/**
 * @author  Yuliia Dovbak
 */
public class Delivery {
    private int customerId;
    private int publicationId;
    private LocalDate date;
    private  String deliveryStatus;

    public Delivery(int customer_id, int publication_id, LocalDate date, String deliveryStatus) {
        this.customerId = customer_id;
        this.publicationId = publication_id;
        this.date = date;
        this.deliveryStatus = deliveryStatus;
    }

    public void print() {
        System.out.printf("\n%-10d %-10d %-20s %-35s", customerId, publicationId, date, deliveryStatus);
    }

    public void printInserts() {
        System.out.println("insert into delivery values (null, " + customerId + ", " + publicationId + ", '" + date + "', 'not delivered');");
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public String getDate() {
        return date.toString();
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
