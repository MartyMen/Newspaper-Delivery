package com.newspaper.deliverydocket.tests;

import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.DeliveryDocket;
import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import junit.framework.TestCase;
/**
 * @author  Yuliia Dovbak
 */
public class DeliveryDocketTest extends TestCase {

    public DeliveryDocketTest() {

        // needed because some of the validation methods need the connection to DB
        DBconnection.init_db();
    }

    //Test #: 1
    //Test Objective: To create a DeliveryDocket Record with the valid data
    //Inputs: ArrayList<DeliveryItem> deliveryItems = null,
    //                                date = '2021-03-20',
    //                                deliveryAreaId = 1,
    //                                deliveryAreaName = Willow Park,
    //                                deliveryPersonName = "Cherey Parren"
    //Expected Output: DeliveryDocket object created with no exceptions
    public void testDeliveryDocket001() {
        try {

            DeliveryDocket d = new DeliveryDocket(null, "2021-03-20", 1, "Willow Park", "Cherey Parren");
            // checking object creation
            assertEquals(null, d.getDeliveryItems());
            assertEquals("2021-03-20", d.getDate());
            assertEquals(1, d.getDeliveryAreaId());
            assertEquals("Willow Park", d.getDeliveryAreaName());
            assertEquals("Cherey Parren", d.getDeliveryPersonName());
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 2
    //Test Objective: To test creation of a DeliveryDocket Record with the invalid data
    //Inputs: ArrayList<DeliveryItem> deliveryItems = null,
    //                                date = '2021-03-20',
    //                                deliveryAreaId = -1,
    //                                deliveryAreaName = Willow Park,
    //                                deliveryPersonName = "Cherey Parren"
    //Expected Output: Exception Message: "Delivery Area name with id -1 does not exist"
    public void testDeliveryDocket002() {
        try {

            DeliveryDocket d = new DeliveryDocket(null, "2021-03-20", -1, "Willow Park", "Cherey Parren");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {

            assertEquals("Delivery area with id -1 does not exist", e.getMessage());
        }
    }


    //Test #: 3
    //Test Objective: To test validation of delivery person name with invalid data (one less char than expected)
    //Inputs: name = "Jo M"
    //Expected Output: Exception Message: "Delivery Person name does not meet the minimum length requirements"
    public void testValidateDeliveryPersonName001() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryPersonName("Jo M");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery Person name does not meet the minimum length requirements", e.getMessage());
        }
    }

    //Test #: 4
    //Test Objective: To test validation of delivery person name with invalid data (one more char than expected)
    //Inputs: name = "JonathanJonathanJona JonathanJonathanJonan"
    //Expected Output: Exception Message: "Delivery Person name exceeds the maximum length requirements"
    public void testValidateDeliveryPersonName002() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            String name = "JonathanJonathanJonathan JonathanJonathanJonatanJonat";
            System.out.println(name.length());
            docket.validateDeliveryPersonName(name);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery Person name exceeds the maximum length requirements", e.getMessage());
        }
    }

    //Test #: 5
    //Test Objective: To test validation of delivery person name with invalid data (empty string)
    //Inputs: name = ""
    //Expected Output: Exception Message: "Delivery Person name cannot be empty"
    public void testValidateDeliveryPersonName003() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryPersonName("");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery Person name cannot be empty", e.getMessage());
        }
    }

    //Test #: 6
    //Test Objective: To test validation of delivery person name with invalid data (null string)
    //Inputs: name = null
    //Expected Output: Exception Message: "Delivery Person name cannot be null"
    public void testValidateDeliveryPersonName004() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryPersonName(null);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery Person name cannot be null", e.getMessage());
        }
    }

    //Test #: 7
    //Test Objective: To test validation of a correct delivery person name (min valid)
    //Inputs: name = "Jo Mo"
    //Expected Output: No Exception Message
    public void testValidateDeliveryPersonName005() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryPersonName("Jo Mo");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 8
    //Test Objective: To test validation of a correct delivery person name (max valid)
    //Inputs: name =  "JonathanJonathanJona JonathanJonathanJona"
    //Expected Output: No Exception Message
    public void testValidateDeliveryPersonName006() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryPersonName("JonathanJonathanJona JonathanJonathanJona");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 9
    //Test Objective: To test validation of delivery area name with invalid data (one less than min)
    //Inputs: deliveryAreaName = "B"
    //Expected Output: Exception Message: "Delivery area name does not meet the minimum length requirements"
    public void testValidateDeliveryAreaName001() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryAreaName("B");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area name does not meet the minimum length requirements", e.getMessage());
        }
    }

    //Test #: 10
    //Test Objective: To test validation of delivery area name with invalid data (one more than max)
    //Inputs: deliveryAreaName = "A nice area name yes"
    //Expected Output: Exception Message: "Delivery area name exceeds the maximum length requirements"
    public void testValidateDeliveryAreaName002() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryAreaName("A nice area name yes");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area name exceeds the maximum length requirements", e.getMessage());
        }
    }

    //Test #: 10
    //Test Objective: To test validation of delivery area name with invalid data (empty string)
    //Inputs: deliveryAreaName = ""
    //Expected Output: Exception Message: "Delivery area name cannot be empty"
    public void testValidateDeliveryAreaName003() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryAreaName("");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area name cannot be empty", e.getMessage());
        }
    }

    //Test #: 11
    //Test Objective: To test validation of delivery area name with invalid data (null string)
    //Inputs: deliveryAreaName = null
    //Expected Output: Exception Message: "Delivery area name cannot be null"
    public void testValidateDeliveryAreaName004() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryAreaName(null);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area name cannot be null", e.getMessage());
        }
    }

    //Test #: 12
    //Test Objective: To test validation of a correct  delivery area name (min)
    //Inputs: deliveryAreaName = "Ar"
    //Expected Output: No Exception Message
    public void testValidateDeliveryAreaName005() {
        try {

            //Call method under test
            DeliveryDocket docket = new DeliveryDocket();
            docket.validateDeliveryAreaName("Ar");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 13
    //Test Objective: To test validation of a correct  delivery area name (max)
    //Inputs: deliveryAreaName = "ArealArealArealArea"
    //Expected Output: No Exception Message
    public void testValidateDeliveryAreaName006() {
        try {

            //Call method under test
            DeliveryDocket d = new DeliveryDocket();
            d.validateDeliveryAreaName("ArealArealArealArea");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 14
    //Test Objective: To test validation of an incorrect delivery area id
    //Inputs: deliveryAreaId = 0
    //Expected Output: Exception Message: "Delivery area with id 0 does not exist"
    public void testValidateDeliveryAreaID001() {
        try {

            //Call method under test
            DeliveryDocket d = new DeliveryDocket();
            d.validateDeliveryAreaID(0);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area with id 0 does not exist", e.getMessage());
        }
    }

    //Test #: 15
    //Test Objective: To test validation of an incorrect delivery area id
    //Inputs: deliveryAreaId = 2000
    //Expected Output: Exception Message: "Delivery area with id 2000 does not exist"
    public void testValidateDeliveryAreaID002() {
        try {

            //Call method under test
            DeliveryDocket d = new DeliveryDocket();
            d.validateDeliveryAreaID(2000);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery area with id 2000 does not exist", e.getMessage());
        }
    }

    //Test #: 16
    //Test Objective: To test validation of a correct delivery area id
    //Inputs: deliveryAreaId = 1
    //Expected Output: No Exception Message
    public void testValidateDeliveryAreaID003() {
        try {

            //Call method under test
            DeliveryDocket d = new DeliveryDocket();
            d.validateDeliveryAreaID(1);

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 17
    //Test Objective: To check validation of an incorrect date
    //Inputs: date = "10-11-2020"
    //Expected Output: Date format is incorrect
    public void testValidateDate001() {
        try {
            DeliveryDocket d = new DeliveryDocket();
            d.validateDate("10 11 2020");
            fail("Exception excpected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Date format is incorrect", e.getMessage());
        }
    }

    //Test #: 18
    //Test Objective: To check validation of an incorrect date
    //Inputs: date = null
    //Expected Output: Delivery docket date cannot be null
    public void testValidateDate002() {
        try {
            DeliveryDocket d = new DeliveryDocket();
            d.validateDate(null);
            fail("Exception excpected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Delivery docket date cannot be null", e.getMessage());
        }
    }

    //Test #: 19
    //Test Objective: To check validation of a correct date
    //Inputs: date = "2021-03-07"
    //Expected Output: Delivery docket date cannot be null
    public void testValidateDate003() {
        try {
            DeliveryDocket d = new DeliveryDocket();
            d.validateDate("2021-03-07");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not excpected");
        }
    }
}