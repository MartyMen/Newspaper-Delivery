package com.newspaper.deliveryarea;

import junit.framework.TestCase;

public class DeliveryAreaTest extends TestCase {
    DeliveryArea da = new DeliveryArea();

    //Test #: 1
    //Test Objective: To test validation of a correct delivery area name
    //Inputs: name="Wu"
    //Expected Output: no exception
    public void testValidateDeliveryAreaName001() {
        //Call method under test
        assertEquals(true, da.validateDesc("Wu"));
    }

    //Test #: 2
    //Test Objective: To test validation of an incorrect delivery area name
    //Inputs: name="Aaaaaaaaaaaaaaaaaaaa" (20 chars)
    //Expected Output: Exception thrown
    public void testValidateDeliveryAreaName002()
    {
        //Call method under test
        assertEquals(false, da.validateDesc("Aaaaaaaaaaaaaaaaaaaa"));
    }

    //Test #: 3
    //Test Objective: To test validation of an incorrect delivery area name (No entry)
    //Inputs: line = ""
    //Expected Output: false
    public void testValidateName003() {

        //Call method under test
        assertEquals(false, da.validateDeliveryAreaName(""));
    }

    //Test #: 4
    //Test Objective: To catch an invalid delivery area name (containing digits)
    //Inputs: line = "bbb23"
    //Expected Output: false
    public void testValidateName004() {

        //Call method under test
        assertEquals(false, da.validateDeliveryAreaName("bbb23"));
    }

    //Test #: 5
    //Test Objective: Test for valid delivery area name
    //Inputs: line = "marty"
    //Expected Output: true
    public void testValidateName005() {

    //Call method under test
    assertEquals(true, da.validateDeliveryAreaName("marty"));

    }

    //Test #: 6
    //Test Objective: To catch an invalid Delivery Area name (-2)
    //Inputs: line = "-2"
    //Expected Output: false
    public void testValidateName006() {

        //Call method under test
        assertEquals(false, da.validateDeliveryAreaName("-2"));
    }

    //Test #: 6
    //Test Objective: To catch an invalid Delivery Area name (22)
    //Inputs: line = "22"
    //Expected Output: false
    public void testValidateName007() {

        //Call method under test
        assertEquals(false, da.validateDeliveryAreaName("22"));
    }

    //Test #: 7
    //Test Objective: To catch an invalid Delivery Area name (22)
    //Inputs: line = "-J"
    //Expected Output: false
    public void testValidateName008() {

        //Call method under test
        assertEquals(false, da.validateDeliveryAreaName("-J"));
    }

    //Test #: 7
    //Test Objective: To catch an invalid Delivery Area Description (Greater than 14)
    //Inputs: line = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    //Expected Output: false
    public void testValidateDesc001() {

        //Call method under test
        assertEquals(false, da.validateDesc("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    //Test #: 8
    //Test Objective: To catch an invalid Delivery Area Description (more than 0 and less than 14)
    //Inputs: line = "Marty Test"
    //Expected Output: false
    public void testValidateDesc002() {

        //Call method under test
        assertEquals(true, da.validateDesc("Marty Test"));
    }

    //Test #: 9
    //Test Objective: To catch a valid Delivery Area Description (more than 0 and less than 14 with numbers.)
    //Inputs: line = "Marty Test"
    //Expected Output: false
    public void testValidateDesc003() {

        //Call method under test
        assertEquals(true, da.validateDesc("Test 123"));
    }

    //Test #: 10
    //Test Objective: To catch an invalid Delivery Area Description (!!!!)
    //Inputs: line = "Marty Test"
    //Expected Output: false
    public void testValidateDesc004() {

        //Call method under test
        assertEquals(false, da.validateDesc("!!!!"));
    }

    //Test #: 11
    //Test Objective: To catch an invalid number being entered - no characters allowed
    //Inputs: "Ray"
    //Expected Output: no exceptions
    public void testValidateEntry002() {
        assertEquals(false, da.validateEntry("Ray"));
    }

    //Test #: 12
    //Test Objective: To catch an invalid number being entered - negative numbers not allowed
    //Inputs: line = -11
    //Expected Output: false
    public void testValidateEntry003() {
        assertEquals(false, da.validateEntry("-11"));
    }


    //Test #: 13
    //Test Objective: To catch an invalid number being entered - " " not allowed
    //Inputs: address = " "
    //Expected Output: false
    public void testValidateEntry004()  {
        assertEquals(false, da.validateEntry(" "));
    }

    //Test #: 14
    //Test Objective: validate a valid entry of 2 numbers
    //Inputs: "22"
    //Expected Output: true
    public void testValidateEntry005() {
        assertEquals(true, da.validateEntry("22"));
    }

    //Test #: 15
    //Test Objective: validate a valid entry of 1 number
    //Inputs: "99"
    //Expected Output: true
    public void testValidateEntry006() {
        assertEquals(true, da.validateEntry("99"));
    }

    // Test #: 16
    //Test Objective: To catch an invalid number being entered - only 2 digits allowed max
    //Inputs: 100
    //Expected Output:false
    public void testValidateEntry001() {
        assertEquals(false, da.validateEntry("100"));
    }
}