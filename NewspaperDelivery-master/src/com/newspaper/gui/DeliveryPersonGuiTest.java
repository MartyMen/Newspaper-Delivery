//package com.newspaper.gui;
//
//import junit.framework.TestCase;
//
//public class DeliveryPersonGuiTest extends TestCase {
//    DeliveryPerson dp = new DeliveryPerson();
//
//    //Test #: 1
//    //Test Objective: To create a com.newspaper.customer.Customer Record
//    //Inputs: id= 5 firstName = "Pam", lastName = "Beezley",  address1 = 87, address2 = "Willow Park", town = "Athlone",
//    // deliveryPhoneNumber ="087 8888888", dateOfBirth = "1997-11-12", accessLevel = "2", deliveryStatus = "true",
//    // userName = "PamAndJim", password = "1111"
//    //Expected Output: com.newspaper.customer.Customer Object created with id = 5, firstName = "Pam", lastName = "Beezley",
//    // address1 = 87, address2 = "Willow Park", town = "Athlone", deliveryPhoneNumber ="087 8888888", dateOfBirth = "1997-11-12",
//    // accessLevel = "2", deliveryStatus = "true", userName = "PamAndJim", password = "1111"
//
//    public void testDeliveryPerson001() {
//        try {
//            // create the com.newspaper.customer.Customer object
//            // call constructor under test
//            DeliveryPerson deliveryPerson = new DeliveryPerson(5, "Pam", "Beezley", "87", "Willow Park", "Athlone", "087 8888888", "1997-11-12", "2", "true", "PamAndJim", "1111");
//
//            // checking object creation
//            assertEquals(5, deliveryPerson.getDeliveryPersonId());
//            assertEquals("Pam", deliveryPerson.getFirstName());
//            assertEquals("Beezley", deliveryPerson.getLastName());
//            assertEquals("87", deliveryPerson.getAddress1());
//            assertEquals("Willow Park", deliveryPerson.getAddress2());
//            assertEquals("Athlone", deliveryPerson.getTown());
//            assertEquals("087 8888888", deliveryPerson.getDeliveryPhoneNumber());
//            assertEquals("1997-11-12", deliveryPerson.getDateOfBirth());
//            assertEquals("2", deliveryPerson.getAccessLevel());
//            assertEquals("true", deliveryPerson.getDeliveryStatus());
//            assertEquals("PamAndJim", deliveryPerson.getUserName());
//            assertEquals("1111", deliveryPerson.getPassword());
//        } catch (Exception e) {
//            fail("Exception not expected");
//        }
//
//    }
//    //Test #: 2
//    //Test Objective: To catch an invalid customer name
//    //Inputs: line = "B"
//    //Expected Output: false"
//
//    public void testValidateName001() {
//
//        assertEquals(false, dp.validateString("B"));
//    }
//
//    //Test #: 3
//    //Test Objective: To catch an invalid customer name (more than 20 symbols in a name)
//    //Inputs: line = "aaaaaaaaaaaaaaaaaaaaaaaaaa"
//    //Expected Output: false
//    public void testValidateName002() {
//
//        //Call method under test
//        assertEquals(false, dp.validateString("aaaaaaaaaaaaaaaaaaaaaaaaaa"));
//    }
//
//    //Test #: 4
//    //Test Objective: To catch an invalid customer name (empty string)
//    //Inputs: line = ""
//    //Expected Output: false
//    public void testValidateName003() {
//
//        //Call method under test
//        assertEquals(false, dp.validateString(" "));
//    }
//
//
//    //Test #: 5
//    //Test Objective: To catch an invalid customer name (containing digits)
//    //Inputs: line = "bbb23"
//    //Expected Output: false
//    public void testValidateName004() {
//
//
//        //Call method under test
//        assertEquals(false, dp.validateString("bbb23"));
//
//    }
//
//    //Test #: 6
//    //Test Objective: validate a valid string )
//    //Inputs: line = "Ray"
//    //Expected Output: true
//    public void testValidateName005() {
//
//        //Call method under test
//        assertEquals(true, dp.validateString("Ray"));
//
//    }
//
//
//    //Test #: 7
//    //Test Objective: To validate a string that can take both characters and numbers
//    //Inputs: line = "Mary123"
//    //Expected Output: true
//    public void testValidateName007() {
//        assertEquals(true, dp.validateStringWithNumbers("Mary123"));
//    }
//
//
//    //Test #: 8
//    //Test Objective: To validate a string that can take both characters and numbers using just characters
//    //Inputs: line = "Mary"
//    //Expected Output: true
//    public void testValidateName008() {
//        assertEquals(true, dp.validateStringWithNumbers("Mary"));
//    }
//
//
//    //Test #: 9
//    //Test Objective: To validate a string that can take both characters and numbers using just numbers
//    //Inputs: line = "Mary123"
//    //Expected Output: true
//    public void testValidateName009() {
//        assertEquals(true, dp.validateStringWithNumbers("123"));
//    }
//
//
//    // Test #: 10
//    //Test Objective: To catch an invalid number being entered - only 2 digits allowed max
//    //Inputs: 111111
//    //Expected Output:false
//    public void testValidateEntry001() {
//        assertEquals(false, dp.validateEntry("111111"));
//    }
//
//    //Test #: 11
//    //Test Objective: To catch an invalid number being entered - no characters allowed
//    //Inputs: "Ray"
//    //Expected Output: no exceptions
//    public void testValidateEntry002() {
//        assertEquals(false, dp.validateEntry("Ray"));
//    }
//
//    //Test #: 12
//    //Test Objective: To catch an invalid number being entered - negative numbers not allowed
//    //Inputs: line = -11
//    //Expected Output: false
//    public void testValidateEntry003() {
//        assertEquals(false, dp.validateEntry("-11"));
//    }
//
//
//    //Test #: 13
//    //Test Objective: To catch an invalid number being entered - " " not allowed
//    //Inputs: address = " "
//    //Expected Output: false
//    public void testValidateEntry0041()  {
//        assertEquals(false, dp.validateEntry(" "));
//    }
//
//    //Test #: 14
//    //Test Objective: validate a valid entry of 2 numbers
//    //Inputs: "22"
//    //Expected Output: true
//    public void testValidateEntry005() {
//        assertEquals(true, dp.validateEntry("22"));
//    }
//
//    //Test #: 15
//    //Test Objective: validate a valid entry of 1 number
//    //Inputs: "22"
//    //Expected Output: true
//    public void testValidateEntry006() {
//        assertEquals(true, dp.validateEntry("2"));
//    }
//
//    //Test #: 16
//    //Test Objective: To catch an invalid phone number using text
//    //Inputs: "ffff"
//    //Expected Output: false
//    public void testValidatePhone001() {
//        assertEquals(false, dp.validatePhoneNumber("ffff"));
//    }
//
//    //Test #: 17
//    //Test Objective: To catch an invalid phone number using incorrect format
//    //Inputs: "0877777777"
//    //Expected Output: false
//    public void testValidatePhone002() {
//        assertEquals(false, dp.validatePhoneNumber("0877777777"));
//    }
//
//
//    //Test #: 18
//    //Test Objective: To catch an invalid phone number using incorrect format
//    //Inputs: "0877 777777"
//    //Expected Output: false
//    public void testValidatePhone003() {
//        assertEquals(false, dp.validatePhoneNumber("0877 777777"));
//    }
//
//    //Test #: 19
//    //Test Objective: To validate a valid phone number using correct format
//    //Inputs: "087 1234567"
//    //Expected Output: true
//    public void testValidatePhone004() {
//        assertEquals(true, dp.validatePhoneNumber("087 1234567"));
//    }
//
//    //Test #: 20
//    //Test Objective: To catch an invalid House number using text
//    //Inputs: "gggg"
//    //Expected Output: false
//    public void testValidateHouseNumber001() {
//        assertEquals(false, dp.validateHouseNumber("gggg"));
//    }
//
//    //Test #: 21
//    //Test Objective: To catch an invalid House number using over 4 characters
//    //Inputs: "087 1234567"
//    //Expected Output: false
//    public void testValidateHouseNumber002() {
//        assertEquals(false, dp.validateHouseNumber("12345"));
//    }
//
//    //Test #: 22
//    //Test Objective: To catch an invalid House number using over 0 characters
//    //Inputs: " "
//    //Expected Output: false
//    public void testValidateHouseNumber003() {
//        assertEquals(false, dp.validateHouseNumber(" "));
//    }
//
//    //Test #: 23
//    //Test Objective: To catch an invalid House number using negative number
//    //Inputs: "-1111"
//    //Expected Output: false
//    public void testValidateHouseNumber004() {
//        assertEquals(false, dp.validateHouseNumber("-1111"));
//    }
//
//    //Test #: 24
//    //Test Objective: To validate a valid house number using correct format of between 1 to 4 digits
//    //Inputs: "12345"
//    //Expected Output: true
//    public void testValidateHouseNumber005() {
//        assertEquals(true, dp.validateHouseNumber("1234"));
//    }
//
//
//    //Test #: 25
//    //Test Objective: To validate a valid house number using correct format of between 1 to 4 digits
//    //Inputs: "1"
//    //Expected Output: true
//    public void testValidateHouseNumber006() {
//        assertEquals(true, dp.validateHouseNumber("1"));
//    }
//
//
//    //Test #: 26
//    //Test Objective: To catch an invalid year input for Date of Birth - Dob must be between 1901 - present year
//    //Inputs: "1900"
//    //Expected Output: true
//    public void testValidateYear001() {
//        assertEquals(false, dp.validateYear("1900"));
//    }
//
//    //Test #: 27
//    //Test Objective: To catch an invalid year input for Date of Birth - Dob must be between 1901 - present year
//    //Inputs: "2022"
//    //Expected Output: true
//    public void testValidateYear002() {
//        assertEquals(false, dp.validateYear("2022"));
//    }
//
//    //Test #: 28
//    //Test Objective: To catch an invalid year input for Date of Birth - cannot contain characters
//    //Inputs: "ffff"
//    //Expected Output: true
//    public void testValidateYear003() {
//        assertEquals(false, dp.validateYear("ffff"));
//    }
//
//    //Test #: 29
//    //Test Objective: Validate a valid year input for Date of Birth 1901 - present year
//    //Inputs: "1901"
//    //Expected Output: true
//    public void testValidateYear004() {
//        assertEquals(true, dp.validateYear("1901"));
//    }
//
//    //Test #: 30
//    //Test Objective: Validate a valid year input for Date of Birth 1901 - present year
//    //Inputs: "2021"
//    //Expected Output: true
//    public void testValidateYear005() {
//        assertEquals(true, dp.validateYear("2021"));
//    }
//
//    //Test #: 31
//    //Test Objective: catch an empty value passed as a year input for Date of Birth 1901 - present year
//    //Inputs: " "
//    //Expected Output: true
//    public void testValidateYear006() {
//        assertEquals(false, dp.validateYear(" "));
//    }
//
//    //Test #: 32
//    //Test Objective: catch an empty value passed as a month input for Date of Birth
//    //Inputs: " "
//    //Expected Output: true
//    public void testValidateMonth001() {
//        assertEquals(false, dp.validateMonth(" "));
//    }
//
//    //Test #: 33
//    //Test Objective: catch a character value passed as a month input for Date of Birth
//    //Inputs: "ff"
//    //Expected Output: true
//    public void testValidateMonth002() {
//        assertEquals(false, dp.validateMonth("ff"));
//    }
//
//    //Test #: 34
//    //Test Objective: catch a mixed value passed as a month input for Date of Birth
//    //Inputs: "1f"
//    //Expected Output: true
//    public void testValidateMonth003() {
//        assertEquals(false, dp.validateMonth("1f"));
//    }
//
//    //Test #: 35
//    //Test Objective: catch a value of 13 passed as a month input for Date of Birth
//    //Inputs: "13"
//    //Expected Output: true
//    public void testValidateMonth004() {
//        assertEquals(false, dp.validateMonth("13"));
//    }
//
//    //Test #: 36
//    //Test Objective: catch a value of 00 passed as a month input for Date of Birth
//    //Inputs: "00"
//    //Expected Output: true
//    public void testValidateMonth005() {
//        assertEquals(false, dp.validateMonth("00"));
//    }
//
//    //Test #: 37
//    //Test Objective: catch a valid value of 01 passed as a month input for Date of Birth
//    //Inputs: "01"
//    //Expected Output: true
//    public void testValidateMonth006() {
//        assertEquals(true, dp.validateMonth("01"));
//    }
//
//    //Test #: 38
//    //Test Objective: catch a valid value of 12 passed as a month input for Date of Birth
//    //Inputs: "12"
//    //Expected Output: true
//    public void testValidateMonth007() {
//        assertEquals(true, dp.validateMonth("12"));
//    }
//
//    //Test #: 39
//    //Test Objective: catch an empty value passed as a day input for Date of Birth
//    //Inputs: " "
//    //Expected Output: true
//    public void testValidateDay001() {
//        assertEquals(false, dp.validateDate(" "));
//    }
//
//    //Test #: 40
//    //Test Objective: catch a character value passed as a day input for Date of Birth
//    //Inputs: "ff"
//    //Expected Output: true
//    public void testValidateDay002() {
//        assertEquals(false, dp.validateDate("ff"));
//    }
//
//    //Test #: 41
//    //Test Objective: catch a mixed value passed as a day input for Date of Birth
//    //Inputs: "1d"
//    //Expected Output: true
//    public void testValidateDay003() {
//        assertEquals(false, dp.validateDate("1d"));
//    }
//
//    //Test #: 42
//    //Test Objective: catch a mixed value passed as a day input for Date of Birth
//    //Inputs: "d1"
//    //Expected Output: true
//    public void testValidateDay004() {
//        assertEquals(false, dp.validateDate("d1"));
//    }
//
//    //Test #: 43
//    //Test Objective: catch an invlaid value passed as a day input for Date of Birth
//    //Inputs: "32"
//    //Expected Output: true
//    public void testValidateDay005() {
//        assertEquals(false, dp.validateDate("32"));
//    }
//
//    //Test #: 44
//    //Test Objective: catch an invalid value passed as a day input for Date of Birth
//    //Inputs: "00"
//    //Expected Output: true
//    public void testValidateDay006() {
//        assertEquals(false, dp.validateDate("00"));
//    }
//
//    //Test #: 45
//    //Test Objective: validate a valid value passed as a day input for Date of Birth
//    //Inputs: "01"
//    //Expected Output: true
//    public void testValidateDay007() {
//        assertEquals(true, dp.validateDate("01"));
//    }
//
//    //Test #: 46
//    //Test Objective: validate a valid value passed as a day input for Date of Birth
//    //Inputs: "31"
//    //Expected Output: true
//    public void testValidateDay008() {
//        assertEquals(true, dp.validateDate("31"));
//    }
//}
