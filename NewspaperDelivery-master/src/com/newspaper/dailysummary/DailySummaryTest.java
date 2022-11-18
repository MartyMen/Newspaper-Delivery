package com.newspaper.dailysummary;

import junit.framework.TestCase;

public class DailySummaryTest extends TestCase {
    DailySummary ds = new DailySummary();


    //Test #: 1
    //Test Objective: To test that validate date returns false on a wrong input
    //Inputs: "1999-12-31"
    //Expected Output: false
    public void testValidateDate001() {
        assertEquals(false, ds.validateDate("1999-12-31"));
    }
    //Test #: 2
    //Test Objective: To test that validate date returns true on a correct input
    //Inputs: "2000-01-01"
    //Expected Output: true
    public void testValidateDate002() {
        assertEquals(true, ds.validateDate("2000-01-01"));
    }
    //Test #: 3
    //Test Objective: To test that validate date returns true on a correct input
    //Inputs: "2029-12-31"
    //Expected Output: true
    public void testValidateDate003() {
        assertEquals(true, ds.validateDate("2029-12-31"));
    }
    //Test #: 4
    //Test Objective: To test that validate date returns true on a correct input
    //Inputs: "2030-01-01"
    //Expected Output: false
    public void testValidateDate004() {
        assertEquals(false, ds.validateDate("2030-01-01"));
    }
    //Test #: 5
    //Test Objective: To test that validate date returns false on an incorrect input
    //Inputs: "f"
    //Expected Output: false
    public void testValidateDate005() {
        assertEquals(false, ds.validateDate("f"));
    }

    //Test #: 6
    //Test Objective: To test that validate year returns false on an incorrect input
    //Inputs: "2010"
    //Expected Output: false
    public void testValidateYear001() {
        assertEquals(false, ds.validateYear("2010"));
    }
    //Test #: 7
    //Test Objective: To test that validate year returns false on an incorrect input
    //Inputs: "2100"
    //Expected Output: false
    public void testValidateYear002() {
        assertEquals(false, ds.validateYear("2100"));
    }
    //Test #: 8
    //Test Objective: To test that validate year returns false on an incorrect input
    //Inputs: "f"
    //Expected Output: false
    public void testValidateYear003() {
        assertEquals(false, ds.validateYear("f"));
    }
    //Test #: 9
    //Test Objective: To test that validate year returns true on a correct input
    //Inputs: "2011"
    //Expected Output: true
    public void testValidateYear004() {
        assertEquals(true, ds.validateYear("2011"));
    }
    //Test #: 10
    //Test Objective: To test that validate year returns true on a correct input
    //Inputs: "2099"
    //Expected Output: true
    public void testValidateYear005() {
        assertEquals(true, ds.validateYear("2099"));
    }


    //Test #: 11
    //Test Objective: To test that validate month returns false on an incorrect input
    //Inputs: "2010-12"
    //Expected Output: false
    public void testValidateMonth001() {
        assertEquals(false, ds.validateMonth("2010-12"));
    }
    //Test #: 12
    //Test Objective: To test that validate month returns false on an incorrect input
    //Inputs: "2011-13"
    //Expected Output: false
    public void testValidateMonth002() {
        assertEquals(false, ds.validateMonth("2011-13"));
    }
    //Test #: 13
    //Test Objective: To test that validate month returns false on an incorrect input
    //Inputs: "2100-12"
    //Expected Output: false
    public void testValidateMonth003() {
        assertEquals(false, ds.validateMonth("2100-12"));
    }
    //Test #: 14
    //Test Objective: To test that validate month returns false on an incorrect input
    //Inputs: "2029-13"
    //Expected Output: false
    public void testValidateMonth004() {
        assertEquals(false, ds.validateMonth("2029-13"));
    }
    //Test #: 15
    //Test Objective: To test that validate month returns false on an incorrect input
    //Inputs: "f"
    //Expected Output: false
    public void testValidateMonth005() {
        assertEquals(false, ds.validateMonth("f"));
    } //Test #: 16
    //Test Objective: To test that validate month returns true on a correct input
    //Inputs: "2011-01"
    //Expected Output: true
    public void testValidateMonth006() {
        assertEquals(true, ds.validateMonth("2011-01"));
    }
    //Test #: 17
    //Test Objective: To test that validate month returns false on a correct input
    //Inputs: "2029-12"
    //Expected Output: true
    public void testValidateMonth007() {
        assertEquals(true, ds.validateMonth("2029-12"));
    }


}