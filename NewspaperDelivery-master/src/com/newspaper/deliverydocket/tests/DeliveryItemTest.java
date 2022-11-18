package com.newspaper.deliverydocket.tests;

import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import com.newspaper.deliverydocket.DeliveryItem;
import com.newspaper.deliverydocket.InvoiceDeliveryItem;
import com.newspaper.deliverydocket.PublicationDeliveryItem;
import junit.framework.TestCase;
/**
 * @author  Yuliia Dovbak
 */
public class DeliveryItemTest extends TestCase {

    private DeliveryItem deliveryItem;
    private PublicationDeliveryItem publicationDeliveryItem;
    private InvoiceDeliveryItem invoiceDeliveryItem;

    public DeliveryItemTest() {
        deliveryItem = new DeliveryItem();
        publicationDeliveryItem = new PublicationDeliveryItem();
        invoiceDeliveryItem = new InvoiceDeliveryItem();
        DBconnection.init_db();
    }


    //Test #: 1
    //Test Objective: To create a com.newspaper.deliverydocket.DeliveryItem Record
    //Inputs:  customerID = 1, customerName = "John Martin", customerAddress = "5 Custume Pier, Athlone", type = "invoice", isDelivered = false
    //Expected Output: com.newspaper.deliverydocket.DeliveryItem object created with no exceptions

    public void testDeliveryItem001() {
        try {

            DeliveryItem item = new DeliveryItem(1, 1, "John Martin", "5 Custume Pier, Athlone", "invoice", false);

            // checking object creation
            assertEquals(1, item.getCustomerID());
            assertEquals("John Martin", item.getCustomerName());
            assertEquals("5 Custume Pier, Athlone", item.getCustomerAddress());
            assertEquals("invoice", item.getType());
            assertEquals(false, item.isDelivered());

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 2
    //Test Objective: To create a com.newspaper.deliverydocket.DeliveryItem Record with invalid parameters
    //Inputs:  customerID = 1, customerName = "Jo", customerAddress = "5 Custume Pier, Athlone", type = "invoice", isDelivered = false
    //Expected Output: Exception message: "com.newspaper.customer.Customer name does not meet the minimum length requirements"

    public void testDeliveryItem002() {
        try {
            DeliveryItem item = new DeliveryItem(1, 1, "Jo M", "5 Custume Pier, Athlone", "invoice", false);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name does not meet the minimum length requirements", e.getMessage());
        }
    }

    //Test #: 3
    //Test Objective: To create a com.newspaper.deliverydocket.PublicationDeliveryItem Record
    //Inputs:  customerID = 1, customerName = "John Martin", customerAddress = "5 Custume Pier, Athlone", type = "invoice", isDelivered = false
    //Expected Output: com.newspaper.deliverydocket.DeliveryItem object created with no exceptions

    public void testPublicationDeliveryItem001() {
        try {
            PublicationDeliveryItem item = new PublicationDeliveryItem(45, 2, 1, "John Martin", "5 Custume Pier, Athlone", false);

            // checking object creation
            assertEquals(1, item.getCustomerID());
            assertEquals("John Martin", item.getCustomerName());
            assertEquals("5 Custume Pier, Athlone", item.getCustomerAddress());
            assertEquals("publication", item.getType());
            assertEquals(false, item.isDelivered());
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 4
    //Test Objective: To create a com.newspaper.deliverydocket.DeliveryItem Record with invalid parameters
    //Inputs:  customerID = -8, customerName = "Mary Collins", customerAddress = "56 Dublin Road, Athlone", type = "invoice", isDelivered = false
    //Expected Output: Exception message: "com.newspaper.customer.Customer name does not meet the minimum length requirements"

    public void testPublicationDeliveryItem002() {
        try {
            PublicationDeliveryItem item = new PublicationDeliveryItem(7, -8, 3, "Mary Collins", "56 Dublin Road, Athlone", false);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Publication with id -8 does not exist", e.getMessage());
        }
    }


    //Test #: 5
    //Test Objective: To create a com.newspaper.deliverydocket.InvoiceDeliveryItem Record
    //Inputs:  customerID = 1, customerName = "John Martin", customerAddress = "5 Custume Pier, Athlone", type = "invoice", isDelivered = false
    //Expected Output: com.newspaper.deliverydocket.DeliveryItem object created with no exceptions

    public void testInvoiceDeliveryItem001() {
        try {
            InvoiceDeliveryItem item = new InvoiceDeliveryItem(2, 1, "John Martin", "5 Custume Pier, Athlone", false);

            // checking object creation
            assertEquals(1, item.getCustomerID());
            assertEquals("John Martin", item.getCustomerName());
            assertEquals("5 Custume Pier, Athlone", item.getCustomerAddress());
            assertEquals("invoice", item.getType());
            assertEquals(true, item.isDelivered());
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 6
    //Test Objective: To create a com.newspaper.deliverydocket.InvoiceDeliveryItem Record with invalid parameters
    //Inputs:  customerID = -8, customerName = "Mary Collins", customerAddress = "56 Dublin Road, Athlone", type = "invoice", isDelivered = false
    //Expected Output: Exception message: "com.newspaper.customer.Customer name does not meet the minimum length requirements"

    public void testInvoiceDeliveryItem002() {
        try {
            InvoiceDeliveryItem item = new InvoiceDeliveryItem(-8, 3, "Mary Collins", "56 Dublin Road, Athlone", false);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Invoice with id -8 does not exist", e.getMessage());
        }
    }


    //Test #: 7
    //Test Objective: To catch a customer does not exist in the DB
    //Inputs: customerID = 0
    //Expected Output: Exception Message: "com.newspaper.customer.Customer with id 0 does not exist"
    public void testValidateCustomerId001() {
        try {

            //Call method under test
            deliveryItem.validateCustomerId(0);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer with id 0 does not exist", e.getMessage());
        }
    }


    //Test #: 8
    //Test Objective: To catch a customer does not exist in the DB
    //Inputs: customerID = 22
    //Expected Output: Exception Message: "Customer with id 22 does not exist"
    public void testValidateCustomerId002() {
        try {

            //Call method under test
            deliveryItem.validateCustomerId(3000);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer with id 3000 does not exist", e.getMessage());
        }
    }

    //Test #: 9
    //Test Objective: To catch validation of a correct data
    //Inputs: customerID = 21
    //Expected Output: no exception
    public void testValidateCustomerId003() {
        try {

            //Call method under test
            deliveryItem.validateCustomerId(21);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 10
    //Test Objective: To catch validation of a correct data
    //Inputs: customerID = 1
    //Expected Output: no exception
    public void testValidateCustomerId004() {
        try {

            //Call method under test
            deliveryItem.validateCustomerId(1);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 11
    //Test Objective: To catch an invalid customer name exception
    //Inputs: customerName = "P B"
    //Expected Output: Exception Message: "Customer name does not meet the minimum length requirements"
    public void testValidateCustomerName001() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("P B");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name does not meet the minimum length requirements", e.getMessage());
        }
    }

    //Test #: 12
    //Test Objective: To catch an invalid customer name exception
    //Inputs: customerName = "Aaaaaaaaaaaaaaaaaaaaaaaaaaa Aaaaaaaaaaaaaaaaaaaaaaaaaa"
    //Expected Output: Exception Message: "Customer name exceeds the maximum length requirements"
    public void testValidateCustomerName002() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("Aaaaaaaaaaaaaaaaaaaaaaaaaaa Aaaaaaaaaaaaaaaaaaaaaaaaaa");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name exceeds the maximum length requirements", e.getMessage());
        }
    }

    //Test #: 13
    //Test Objective: To catch an invalid customer name exception
    //Inputs: customerName = null
    //Expected Output: Exception Message: "Customer name cannot be null"
    public void testValidateCustomerName003() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName(null);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name cannot be null", e.getMessage());
        }
    }

    //Test #: 14
    //Test Objective: To catch an invalid customer name exception
    //Inputs: customerName = ""John6 Martin""
    //Expected Output: Exception Message: "Customer name cannot consist of numbers"
    public void testValidateCustomerName004() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("John6 Martin");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name cannot consist of numbers", e.getMessage());
        }
    }

    //Test #: 15
    //Test Objective: To test validation of a correct name
    //Inputs: customerName = "Jo Ma"
    //Expected Output: No Exception
    public void testValidateCustomerName005() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("Jo Ma");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 16
    //Test Objective: To test validation of a correct name (max valid)
    //Inputs: customerName = "Aaaaaaaaaaaaaaaaaaaaaaaaaa Aaaaaaaaaaaaaaaaaaaaaaaaaa"
    //Expected Output: No Exception
    public void testValidateCustomerName006() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("Aaaaaaaaaaaaaaaaaaaaaaaaaa Aaaaaaaaaaaaaaaaaaaaaaaaa");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }
    //Test #: 17
    //Test Objective: To catch an invalid customer name exception
    //Inputs: customerName = ""
    //Expected Output: Exception Message: "Customer name cannot consist of numbers"
    public void testValidateCustomerName007() {
        try {

            //Call method under test
            deliveryItem.validateCustomerName("");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Customer name cannot be empty", e.getMessage());
        }
    }


    //Test #: 18
    //Test Objective: To catch an invalid address exception (One char less than min)
    //Inputs: customerAddress = "1, S, To, Y77EG48"
    //Expected Output: Exception Message: "Address does not meet the minimum length requirements"
    public void testValidateCustomerAddress001() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress("1, S, To, Y77EG48");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Address does not meet the minimum length requirements", e.getMessage());
        }
    }

    //Test #: 19
    //Test Objective: To catch an invalid address exception (One char more than max)
    //Inputs: customerAddress = "1234, StreetStreetStreetStreetStreetStree, TownTownTownTownTownTownTownTownTown, Y77EG48"
    //Expected Output: Exception Message: "Address exceeds the maximum length requirements"
    public void testValidateCustomerAddress002() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress("1234, StreetStreetStreetStreetStreetStree, TownTownTownTownTownTownTownTownTown, Y77EG48");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Address exceeds the maximum length requirements", e.getMessage());
        }
    }

    //Test #: 20
    //Test Objective: To catch an invalid address exception
    //Inputs: customerAddress = null
    //Expected Output: Exception Message: "Address cannot be null"
    public void testValidateCustomerAddress003() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress(null);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Address cannot be null", e.getMessage());
        }
    }

    //Test #: 21
    //Test Objective: To test validation of a minimum possible address
    //Inputs: customerAddress = "1, So, To, Y77EG48"
    //Expected Output: No Exception
    public void testValidateCustomerAddress004() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress("1, So, To, Y77EG48");

        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception expected");
        }
    }

    //Test #: 22
    //Test Objective: To test validation of a maximum possible address
    //Inputs: customerAddress = "1234, StreetStreetStreetStreetStreetStree, TownTownTownTownTownTownTownTownTown, Y77EG48"
    //Expected Output: No Exception
    public void testValidateCustomerAddress005() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress("1234, StreetStreetStreetStreetStreetStree, TownTownTownTownTownTownTownTownTow, Y77EG48");
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 23
    //Test Objective: To catch an invalid address exception (empty string)
    //Inputs: customerAddress = ""
    //Expected Output: Exception Message: "Address cannot be empty"
    public void testValidateCustomerAddress006() {
        try {

            //Call method under test
            deliveryItem.validateCustomerAddress("");
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Address cannot be empty", e.getMessage());
        }
    }

    //Test #: 24
    //Test Objective: To catch an invalid invoice ID
    //Inputs: invoiceId = 0
    //Expected Output: Exception Message: "com.newspaper.invoice.Invoice with id 0 does not exist"
    public void testValidateInvoiceId001() {
        try {

            //Call method under test
            invoiceDeliveryItem.validateInvoiceId(0);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Invoice with id 0 does not exist", e.getMessage());
        }
    }

    //Test #: 25
    //Test Objective: To catch an invalid invoice ID
    //Inputs: invoiceId = 2000
    //Expected Output: Exception Message: "com.newspaper.invoice.Invoice with id 2000 does not exist"
    public void testvalidateInvoiceId002() {
        try {

            //Call method under test
            invoiceDeliveryItem.validateInvoiceId(2000);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Invoice with id 2000 does not exist", e.getMessage());
        }
    }

    //Test #: 26
    //Test Objective: To test a validation of valid invoice id
    //Inputs: invoiceId = 1
    //Expected Output: No Exception
    public void testvalidateInvoiceId003() {
        try {

            //Call method under test
            invoiceDeliveryItem.validateInvoiceId(1);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 27
    //Test Objective: To test a validation of valid invoice id
    //Inputs: invoiceId = 51
    //Expected Output: No Exception
    public void testvalidateInvoiceId004() {
        try {

            //Call method under test
            invoiceDeliveryItem.validateInvoiceId(1);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 28
    //Test Objective: To catch an invalid publication ID
    //Inputs: invoiceId = 0
    //Expected Output: Exception Message: "com.newspaper.publication.Publication with id 0 does not exist"
    public void testvalidatePublicationId001() {
        try {

            //Call method under test
            publicationDeliveryItem.validatePublicationId(0);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Publication with id 0 does not exist", e.getMessage());
        }
    }

    //Test #: 29
    //Test Objective: To catch an invalid publication ID
    //Inputs: invoiceId = 20000
    //Expected Output: Exception Message: "com.newspaper.publication.Publication with id 20000 does not exist"
    public void testvalidatePublicationId002() {
        try {

            //Call method under test
            publicationDeliveryItem.validatePublicationId(20000);
            fail("Exception expected");
        }
        catch (DeliveryDocketExceptionHandler e) {
            assertEquals("Publication with id 20000 does not exist", e.getMessage());
        }
    }

    //Test #: 30
    //Test Objective: To test validation of a correct publication id
    //Inputs: invoiceId = 1
    //Expected Output: No Exception
    public void testvalidatePublicationId003() {
        try {

            //Call method under test
            publicationDeliveryItem.validatePublicationId(1);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 31
    //Test Objective: To test validation of a correct publication id
    //Inputs: invoiceId = 14
    //Expected Output: No Exception
    public void testvalidatePublicationId004() {
        try {

            //Call method under test
            publicationDeliveryItem.validatePublicationId(14);
        }
        catch (DeliveryDocketExceptionHandler e) {
            fail("Exception not expected");
        }
    }
}