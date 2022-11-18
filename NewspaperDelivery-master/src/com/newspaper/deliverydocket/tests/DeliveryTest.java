package com.newspaper.deliverydocket.tests;

import com.newspaper.deliverydocket.Delivery;
import com.newspaper.deliverydocket.Utility;
import junit.framework.TestCase;

import java.time.LocalDate;
/**
 * @author  Yuliia Dovbak
 */
public class DeliveryTest extends TestCase {

    Delivery delivery;
    public DeliveryTest() {
        Utility ut = new Utility();

        LocalDate date = ut.convertDate("2021-02-01");
        delivery = new Delivery(1, 4, date ,"delivered");
    }

    public void testGetCustomerId() {
            assertEquals(1, delivery.getCustomerId());
    }

    public void testGetPublicationId() {
        assertEquals(4, delivery.getPublicationId());
    }

    public void testGetDate() {
        assertEquals("2021-02-01", delivery.getDate());
    }

    public void testGetDeliveryStatus() {
        assertEquals("delivered", delivery.getDeliveryStatus());
    }
}