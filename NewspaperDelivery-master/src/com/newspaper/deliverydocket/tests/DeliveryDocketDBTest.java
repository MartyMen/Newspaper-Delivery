package com.newspaper.deliverydocket.tests;

import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.*;
import junit.framework.TestCase;

import java.time.LocalDate;
/**
 * @author  Yuliia Dovbak
 */
public class DeliveryDocketDBTest extends TestCase {

    DeliveryDocketDB deliveryDocketDB = new DeliveryDocketDB();
    Utility utility = new Utility();

    public DeliveryDocketDBTest() {
        DBconnection.init_db();
    }

    public void testCreateDeliveryDocketFor001() {
        try {
            DeliveryDocket docket = deliveryDocketDB.createDeliveryDocketFor(10, "2021-01-01");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery person with id 10 is inactive", e.getMessage());
        }

    }

    public void testCreateDeliveryDocketFor002() {
        try {
            DeliveryDocket docket = deliveryDocketDB.createDeliveryDocketFor(2, "2021-01-01");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }

    }

    public void testGetAllDeliveryItemsFor() {

        try {
            deliveryDocketDB.getAllDeliveryItemsFor(10, "2021-01-01");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGetInvoicesForDeliveryArea() {

        try {
            deliveryDocketDB.getInvoicesForDeliveryArea(10, 2);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGetPublicationDeliveriesForDeliveryArea() {

        try {
            deliveryDocketDB.getPublicationDeliveriesForDeliveryArea(10, "2021-02-01");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGenerateDeliveriesIfNeeded() {
        try {
            deliveryDocketDB.getPublicationDeliveriesForDeliveryArea(10, "2021-02-01");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }




    public void testSaveDelivery() {

        try {
            Delivery delivery = new Delivery(3, 7, LocalDate.now(), "not delivered");
            deliveryDocketDB.saveDelivery(delivery);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testDeliveriesForThisMonthExist001() {
        assertEquals(deliveryDocketDB.deliveriesForThisMonthExist(1), true);
    }

    public void testDeliveriesForThisMonthExist002() {
        assertEquals(deliveryDocketDB.deliveriesForThisMonthExist(12), false);
    }

    public void testGetDeliveryArea001() {
        try {
            deliveryDocketDB.getDeliveryArea(2);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGetDeliveryArea002() {
        try {
            deliveryDocketDB.getDeliveryArea(7);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery person with id 7 is inactive", e.getMessage());
        }
    }

    public void testIsFullyDelivered001() {
        try {
            int deliveryPersonId = 1;
            String date = "2021-03-8";
            int index = deliveryDocketDB.isFullyDelivered(deliveryPersonId, date);
            assertEquals(1, index);
        }
        catch (DeliveryDocketExceptionHandler e) {

        }
    }

    public void testIsFullyDelivered002() {
        try {
            int deliveryPersonId = 1;
            String date = "2021-03-9";
            int index = deliveryDocketDB.isFullyDelivered(deliveryPersonId, date);
            assertEquals(-1, index);
        }
        catch (DeliveryDocketExceptionHandler e) {

        }
    }

    public void testIsFullyDelivered003() {
        try {
            int deliveryPersonId = 1;
            String date = "2021-03-10";
            int index = deliveryDocketDB.isFullyDelivered(deliveryPersonId, date);
            assertEquals(0, index);
        }
        catch (DeliveryDocketExceptionHandler e) {

        }
    }

    public void testUpdateDeliveriesStatus() {

        try {
            deliveryDocketDB.updateDeliveriesStatus(2, "2021-03-02");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }




}