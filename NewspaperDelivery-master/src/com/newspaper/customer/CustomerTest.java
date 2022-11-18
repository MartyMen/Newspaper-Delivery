package com.newspaper.customer;

import com.newspaper.db.DBconnection;
import junit.framework.TestCase;
/**
 * @author  Yuliia Dovbak
 */
public class CustomerTest extends TestCase {

    private Customer customer;

    public CustomerTest(){
        customer = new Customer();
        DBconnection.init_db();
    }


    //Test #: 1
    //Test Objective: To create a    Customer Record
    //Inputs: firstName = "Jack", lastName = "Martin",  address1 = 23, address2 = "Dublin Road", town = "Athlone",
    //      eircode="M35UJ99", phoneNumber = "084 831 6481", holidayStartDate = "2021-02-19", holidayEndDate = "2021-02-29", status = "true"
    //Expected Output:    Customer Object created with id = 0, firstName = "Jack", lastName = "Martin",  address1 = 23,
    //      address2 = "Dublin Road", town = "Athlone",
    //      eircode="M35UJ99", phoneNumber = "084 831 6481", holidayStartDate = "", holidayEndDate = "", status = "true"

    public void testCustomer001() {

        // create the    Customer object

        try {

            // call constructor under test
            Customer customer = new Customer("Jack", "Martin", 23, "Dublin Road", "Athlone","M35UJ99","084 8316481", "2021-02-19", "2021-02-29",  true, 6);

            // checking object creation
            assertEquals(0, customer.getCustomerId());
            assertEquals("Jack", customer.getFirstName());
            assertEquals("Martin", customer.getLastName());
            assertEquals(23, customer.getAddress1());
            assertEquals("Dublin Road", customer.getAddress2());
            assertEquals("Athlone", customer.getTown());
            assertEquals("M35UJ99", customer.getEircode());
            assertEquals("084 8316481", customer.getPhoneNumber());
            assertEquals("2021-02-19", customer.getHolidayStartDate());
            assertEquals("2021-02-29", customer.getHolidayEndDate());
            assertEquals(true, customer.getStatus());
            assertEquals(6, customer.getDeliveryAreaId());
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }

    }

    //Test #: 2
    //Test Objective: To catch an invalid customer name
    //Inputs: line = "B", nameOfField = "First name"
    //Expected Output: Exception Message: "   Customer Name does not meet minimum length requirements"

    public void testValidateName001() {
        try {

            //Call method under test
            customer.validateName("B", "First name");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("First name does not meet minimum length requirements", e.getMessage());
        }
    }

    //Test #: 3
    //Test Objective: To catch an invalid customer name (more than 25 symbols in a name)
    //Inputs: line = "aaaaaaaaaaaaaaaaaaaaaaaaaa", nameOfField = "First name"
    //Expected Output: Exception Message: "First name exceeds maximum length requirements"
    public void testValidateName002() {
        try {

            //Call method under test
            customer.validateName("aaaaaaaaaaaaaaaaaaaaaaaaaa", "First name");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("First name exceeds maximum length requirements", e.getMessage());
        }
    }

    //Test #: 4
    //Test Objective: To catch an invalid customer name (empty string)
    //Inputs: line = "", nameOfField = "First name"
    //Expected Output: Exception Message: "First name  NOT specified"
    public void testValidateName003() {
        try {

            //Call method under test
            customer.validateName("", "First name");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("First name NOT specified", e.getMessage());
        }
    }

    //Test #: 5
    //Test Objective: To catch an invalid customer name (null string)
    //Inputs: line = null, nameOfField = "First name"
    //Expected Output: Exception Message: "NULL value in the arguments"
    public void testValidateName004() {
        try {

            //Call method under test
            customer.validateName(null, "First name");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("NULL value in the arguments", e.getMessage());
        }
    }

    //Test #: 6
    //Test Objective: To catch an invalid customer name (containing digits)
    //Inputs: line = "bbb23", nameOfField = "Last name"
    //Expected Output: Exception Message: "Last name cannot include numbers"
    public void testValidateName005() {
        try {

            //Call method under test
            customer.validateName("bbb23", "Last name");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Last name cannot include numbers", e.getMessage());
        }
    }

    //Test #: 7
    //Test Objective: To catch an nameOfField exception.("First name" and "Last name" are the only valid strings)
    //Inputs: line = "John", nameOfField = "Random string"
    //Expected Output: Exception Message: "nameOfField is invalid"
    public void testValidateName006() {
        try {

            //Call method under test
            customer.validateName("John", "Random string");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("nameOfField is invalid", e.getMessage());
        }
    }

    //Test #: 8
    //Test Objective: To catch an nameOfField exception (null value)
    //Inputs: line = "Mary", nameOfField = null
    //Expected Output: Exception Message: "NULL value in the arguments"
    public void testValidateName007() {
        try {

            //Call method under test
            customer.validateName("Mary", null);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("NULL value in the arguments", e.getMessage());
        }
    }

    //Test #: 9
    //Test Objective: To test validation of a correct name
    //Inputs: line = "Martin", nameOfField = "First name"
    //Expected Output: no exceptions
    public void testValidateName008() {
        try {

            //Call method under test
            customer.validateName("Martin", "First name");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 10
    //Test Objective: To test validation of a correct name
    //Inputs: line = "O'Connor", nameOfField = "Last name"
    //Expected Output: no exceptions
    public void testValidateName009() {
        try {

            //Call method under test
            customer.validateName("O'Connor", "Last name");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }



    //Test #: 11
    //Test Objective: To catch an invalid address exception
    //Inputs: address = "b", nameOFField = "Town"
    //Expected Output: Town does not meet minimum length requirements
    public void testValidateAddress001() {
        try {

            //Call method under test
            customer.validateAddress("b", "Town");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Town does not meet minimum length requirements", e.getMessage());
        }
    }

    //Test #: 11
    //Test Objective: To catch an invalid name of field exception
    //Inputs: address = "Moate", nameOFField = "Townie"
    //Expected Output: Townie is incorrect name of address field
    public void testValidateAddress002() {
        try {

            //Call method under test
            customer.validateAddress("Moate", "Townie");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {

            assertEquals("Townie is incorrect name of address field", e.getMessage());
        }
    }

    //Test #: 12
    //Test Objective: To catch an invalid address exception (address line too long)
    //Inputs: address = "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", nameOFField = "Town"
    //Expected Output: Townie is incorrect name of address field
    public void testValidateAddress003() {
        try {

            //Call method under test
            customer.validateAddress("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Town");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Town exceeds maximum length requirements", e.getMessage());
        }
    }

    //Test #: 13
    //Test Objective: To catch a null exception
    //Inputs: address = "Dublin", nameOFField = null
    //Expected Output: NULL value in the arguments
    public void testValidateAddress005() {
        try {

            //Call method under test
            customer.validateAddress(null, null);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("NULL value in the arguments", e.getMessage());
        }
    }

    //Test #: 14
    //Test Objective: To check validation of a correct data
    //Inputs: address = "Dublin", nameOFField = "Town"
    //Expected Output: no exception
    public void testValidateAddress006() {
        try {

            //Call method under test
            customer.validateAddress("Dublin", "Town");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 15
    //Test Objective: To check validation of a correct data
    //Inputs: address = "Strand street", nameOFField = "Address line 2"
    //Expected Output: no exception
    public void testValidateAddress007() {
        try {

            //Call method under test
            customer.validateAddress("Strand street", "Address line 2");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 16
    //Test Objective: To check validation of a correct eircode
    //Inputs: eircode="A11AA11"
    //Expected Output: no exception
    public void testValidateEircode001() {
        try {
            customer.validateEircode("A11AA11");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 17
    //Test Objective: To check validation of an incorrect eircode
    //Inputs: eircode="a11aa11"
    //Expected Output: Eircode does not correspond to the format "A11AA11"
    public void testValidateEircode002() {
        try {
            customer.validateEircode("a11aa11");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Eircode does not correspond to the format \"A11AA11\"", e.getMessage());
        }
    }

    //Test #: 18
    //Test Objective: To check validation of an incorrect eircode
    //Inputs: eircode= null
    //Expected Output: NULL value in the argument
    public void testValidateEircode003() {
        try {
            customer.validateEircode(null);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("NULL value in the argument", e.getMessage());
        }
    }


    //Test #: 19
    //Test Objective: To check validation of a correct phone number
    //Inputs: phoneNumber = "085 856 7843"
    //Expected Output: No exception
    public void testValidatePhoneNumber001() {
        try {
            customer.validatePhoneNumber("085 8567843");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }


    //Test #: 20
    //Test Objective: To check validation of an incorrect phone number
    //Inputs: phoneNumber = "0858567843"
    //Expected Output: Phone number does not correspond to the format "000 000 0000"
    public void testValidatePhoneNumber002() {
        try {
            customer.validatePhoneNumber("0858567843");
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Phone number does not correspond to the format \"000 000 0000\"", e.getMessage());
        }
    }

    //Test #: 21
    //Test Objective: To check validation of an incorrect eircode
    //Inputs: eircode = null
    //Expected Output: NULL value in the argument
    public void testValidatePhoneNumber003() {
        try {
            customer.validateEircode(null);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("NULL value in the argument", e.getMessage());
        }
    }

    //Test #: 22
    //Test Objective: To check validation of a correct address1 (number of house or apartment)
    //Inputs: phoneNumber = 1
    //Expected Output: no exception
    public void testValidateAddress1001() {
        try {
            customer.validateAddress1(1);

        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 23
    //Test Objective: To check validation of a correct address1 (number of house or apartment)
    //Inputs: phoneNumber = 1000
    //Expected Output: no exception
    public void testValidateAddress1002() {
        try {
            customer.validateAddress1(1000);

        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 24
    //Test Objective: To check validation of an incorrect address1 (number of house or apartment)
    //Inputs: phoneNumber = -1
    //Expected Output: Address line 1 cannot be a negative number
    public void testValidateAddress1003() {
        try {
            customer.validateAddress1(-1);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Address line 1 cannot be a negative number", e.getMessage());
        }
    }

    //Test #: 25
    //Test Objective: To check validation of an incorrect address1 (number of house or apartment)
    //Inputs: phoneNumber = 1001
    //Expected Output: Address line 1 cannot be greater than 1000
    public void testValidateAddress1004() {
        try {
            customer.validateAddress1(1001);
            fail("Exception expected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Address line 1 cannot be greater than 1000", e.getMessage());
        }
    }


    //Test #: 26
    //Test Objective: To check validation of a correct date
    //Inputs: date = "2020-11-10"
    //Expected Output: no exception
    public void testValidateDate001() {
        try {
            customer.validateDate("2020-11-10");
        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    //Test #: 27
    //Test Objective: To check validation of an incorrect date
    //Inputs: date = "10-11-2020"
    //Expected Output: Date format is incorrect
    public void testValidateDate002() {
        try {
            customer.validateDate("10 11 2020");
            fail("Exception excpected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Date format is incorrect", e.getMessage());
        }
    }

    //Test #: 28
    //Test Objective: To check validation of an correct date value of null
    //Inputs: date = null
    //Expected Output: NULL value in the argument
    public void testValidateDate003() {
        try {
            customer.validateDate(null);

        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not excpected");
        }
    }

    //Test #: 27
    //Test Objective: To check validation of an incorrect delivery area id
    //Inputs: id = 58
    //Expected Output: CustomerExceptionHandler thrown
    public void testValidateDeliveryAreaId001() {
        try {
            customer.validateDeliveryArea(58);
            fail("Exception excpected");
        }
        catch (CustomerExceptionHandler e) {
            assertEquals("Delivery Area with id 58 does not exist", e.getMessage());
        }
    }

    //Test #: 27
    //Test Objective: To check validation of an correct delivery area id
    //Inputs: id = 4
    //Expected Output: No exception
    public void testValidateDeliveryAreaId002() {
        try {
            customer.validateDeliveryArea(4);

        }
        catch (CustomerExceptionHandler e) {
            fail("Exception not excpected");
        }
    }


}
