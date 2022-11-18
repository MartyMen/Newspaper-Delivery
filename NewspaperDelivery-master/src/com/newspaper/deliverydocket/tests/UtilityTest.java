package com.newspaper.deliverydocket.tests;

import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryArea;
import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import com.newspaper.deliverydocket.Utility;
import com.newspaper.order.Order;
import com.newspaper.order.OrderExceptionHandler;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author  Yuliia Dovbak
 */

public class UtilityTest extends TestCase {

    Utility utility = new Utility();
    public UtilityTest() {
        DBconnection.init_db();
    }

    public void testGetPublicationByID001() {

        try {
            String pub = "Sunday World";
            assertEquals(pub, utility.getPublicationByID(13));
        }
        catch (DeliveryDocketExceptionHandler e) {

        }

    }

    public void testDeliveryPersonExists001() {
        assertEquals(utility.deliveryPersonExists(1), true);
    }

    public void testDeliveryPersonExists002() {
        assertEquals(utility.deliveryPersonExists(20), false);
    }

    public void testDeliveryAreaExists001() {
        assertEquals(utility.deliveryAreaExists(20), false);
    }

    public void testDeliveryAreaExists002() {
        assertEquals(utility.deliveryAreaExists(2), true);
    }

    public void testDeliveryPersonActive001() {
        assertEquals(utility.deliveryPersonActive(2), true);
    }

    public void testDeliveryPersonActive002() {
        assertEquals(utility.deliveryPersonActive(6), false);
    }

    public void testPublicationExists001() {
        assertEquals(utility.publicationExists(6), true);
    }

    public void testPublicationExists002() {
        assertEquals(utility.publicationExists(15), false);
    }

    public void testCustomerDeliveryExists() {
        assertEquals(utility.customerDeliveryExists(15), true);
    }

    public void testPublicationDeliveryExists001() {
        assertEquals(utility.publicationDeliveryExists(13), true);
    }

    public void testPublicationDeliveryExists002() {
        assertEquals(utility.publicationDeliveryExists(15), false);
    }

    public void testIfCustomerExists001() {
        assertEquals(utility.ifCustomerExists(15), true);
    }

    public void testIfCustomerExists002() {
        assertEquals(utility.ifCustomerExists(45), false);
    }

    public void testGetOrders() {
        try {
            utility.getOrders();
        }
        catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGetOrder() {
        try {
            Order order = new Order(43, 11, 5);
            Order orderTest = utility.getOrder(43, 11);
            assertEquals(order.getCustomer_id(), orderTest.getCustomer_id());
            assertEquals(order.getFrequency(), orderTest.getFrequency());
            assertEquals(order.getPublication_id(), orderTest.getPublication_id());
        }
        catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testGetDayNumber() {
        LocalDate testDate = utility.convertDate("2021-03-08");
        int dayNum = 1; // monday
        assertEquals(dayNum, utility.getDayNumber(testDate));
    }

    public void testGetMonths() {

        ArrayList<Integer> months = new ArrayList<>();
        months.add(1);
        months.add(2);
        months.add(3);

        ArrayList<Integer> testMonths = utility.getMonths();
        assertEquals(3, testMonths.size());

        for (int i = 0; i < months.size(); i++) {
            assertEquals(months.get(i), testMonths.get(i));
        }
    }

    public void testGetDeliveryPersonName() {
        try {
            assertEquals("Rey Secretan", utility.getDeliveryPersonName(13));
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    public void testConvertDate() {
        LocalDate date = LocalDate.now();
        assertEquals(date, utility.convertDate("2021-03-11"));
    }

    public void testIsInFuture001() {
        // future date
        assertEquals(true, utility.isInFuture("2021-11-11"));
    }

    public void testIsInFuture002() {
        // future date
        assertEquals(false, utility.isInFuture("2021-1-11"));
    }


    public void testGetDeliveryArea() {

        DeliveryArea deliveryArea = new DeliveryArea(2, "Coosan", "Temporary Text", 2);

        try {
            DeliveryArea testDarea = utility.getDeliveryArea(2);

            assertEquals(deliveryArea.getId(), testDarea.getId());
            assertEquals(deliveryArea.getDAreaName(), testDarea.getDAreaName());
            assertEquals(deliveryArea.getDeliveryPersonId(), testDarea.getDeliveryPersonId());
        }
        catch (DeliveryDocketExceptionHandler e) {

        }


    }
}