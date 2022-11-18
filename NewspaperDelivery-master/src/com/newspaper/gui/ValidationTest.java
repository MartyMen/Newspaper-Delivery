package com.newspaper.gui;

import com.newspaper.deliveryperson.DeliveryPerson;
import junit.framework.TestCase;

public class ValidationTest extends TestCase {


    Validation validation = new Validation();

        //Test #: 2
        //Test Objective: To catch an invalid customer name
        //Inputs: line = "B"
        //Expected Output: false"

        public void testValidateName001() {

            assertEquals(true, validation.validateString("B"));
        }

        //Test #: 3
        //Test Objective: To catch an invalid customer name (more than 20 symbols in a name)
        //Inputs: line = "aaaaaaaaaaaaaaaaaaaaaaaaaa"
        //Expected Output: false
        public void testValidateName002() {

            //Call method under test
            assertEquals(false, validation.validateString("aaaaaaaaaaaaaaaaaaaaaaaaaa"));
        }

        //Test #: 4
        //Test Objective: To catch an invalid customer name (empty string)
        //Inputs: line = ""
        //Expected Output: false
        public void testValidateName003() {

            //Call method under test
            assertEquals(true, validation.validateString(" "));
        }


        //Test #: 5
        //Test Objective: To catch an invalid customer name (containing digits)
        //Inputs: line = "bbb23"
        //Expected Output: false
        public void testValidateName004() {


            //Call method under test
            assertEquals(false, validation.validateString("bbb23"));

        }

        //Test #: 6
        //Test Objective: validate a valid string )
        //Inputs: line = "Ray"
        //Expected Output: true
        public void testValidateName005() {

            //Call method under test
            assertEquals(true, validation.validateString("Ray"));

        }


        //Test #: 7
        //Test Objective: To validate a string that can take both characters and numbers
        //Inputs: line = "Mary123"
        //Expected Output: true
        public void testValidateName007() {
            assertEquals(true, validation.validateStringWithNumbers("Mary123"));
        }


        //Test #: 8
        //Test Objective: To validate a string that can take both characters and numbers using just characters
        //Inputs: line = "Mary"
        //Expected Output: true
        public void testValidateName008() {
            assertEquals(true, validation.validateStringWithNumbers("Mary"));
        }


        //Test #: 9
        //Test Objective: To validate a string that can take both characters and numbers using just numbers
        //Inputs: line = "Mary123"
        //Expected Output: true
        public void testValidateName009() {
            assertEquals(true, validation.validateStringWithNumbers("123"));
        }


        // Test #: 10
        //Test Objective: To catch an invalid number being entered - only 2 digits allowed max
        //Inputs: 111111
        //Expected Output:false
        public void testValidateEntry001() {
            assertEquals(false, validation.validateEntry("111111"));
        }

        //Test #: 11
        //Test Objective: To catch an invalid number being entered - no characters allowed
        //Inputs: "Ray"
        //Expected Output: no exceptions
        public void testValidateEntry002() {
            assertEquals(false, validation.validateEntry("Ray"));
        }

        //Test #: 12
        //Test Objective: To catch an invalid number being entered - negative numbers not allowed
        //Inputs: line = -11
        //Expected Output: false
        public void testValidateEntry003() {
            assertEquals(false, validation.validateEntry("-11"));
        }


        //Test #: 13
        //Test Objective: To catch an invalid number being entered - " " not allowed
        //Inputs: address = " "
        //Expected Output: false
        public void testValidateEntry0041()  {
            assertEquals(false, validation.validateEntry(" "));
        }

        //Test #: 14
        //Test Objective: validate a valid entry of 2 numbers
        //Inputs: "22"
        //Expected Output: true
        public void testValidateEntry005() {
            assertEquals(true, validation.validateEntry("22"));
        }

        //Test #: 15
        //Test Objective: validate a valid entry of 1 number
        //Inputs: "22"
        //Expected Output: true
        public void testValidateEntry006() {
            assertEquals(true, validation.validateEntry("2"));
        }

        //Test #: 16
        //Test Objective: To catch an invalid phone number using text
        //Inputs: "ffff"
        //Expected Output: false
        public void testValidatePhone001() {
            assertEquals(false, validation.validatePhoneNumber("ffff"));
        }

        //Test #: 17
        //Test Objective: To catch an invalid phone number using incorrect format
        //Inputs: "0877777777"
        //Expected Output: false
        public void testValidatePhone002() {
            assertEquals(false, validation.validatePhoneNumber("0877777777"));
        }


        //Test #: 18
        //Test Objective: To catch an invalid phone number using incorrect format
        //Inputs: "0877 777777"
        //Expected Output: false
        public void testValidatePhone003() {
            assertEquals(false, validation.validatePhoneNumber("0877 777777"));
        }

        //Test #: 19
        //Test Objective: To validate a valid phone number using correct format
        //Inputs: "087 1234567"
        //Expected Output: true
        public void testValidatePhone004() {
            assertEquals(true, validation.validatePhoneNumber("087 1234567"));
        }

        //Test #: 20
        //Test Objective: To catch an invalid House number using text
        //Inputs: "gggg"
        //Expected Output: false
        public void testValidateHouseNumber001() {
            assertEquals(false, validation.validateHouseNumber("gggg"));
        }

        //Test #: 21
        //Test Objective: To catch an invalid House number using over 4 characters
        //Inputs: "087 1234567"
        //Expected Output: false
        public void testValidateHouseNumber002() {
            assertEquals(false, validation.validateHouseNumber("12345"));
        }

        //Test #: 22
        //Test Objective: To catch an invalid House number using over 0 characters
        //Inputs: " "
        //Expected Output: false
        public void testValidateHouseNumber003() {
            assertEquals(false, validation.validateHouseNumber(" "));
        }

        //Test #: 23
        //Test Objective: To catch an invalid House number using negative number
        //Inputs: "-1111"
        //Expected Output: false
        public void testValidateHouseNumber004() {
            assertEquals(false, validation.validateHouseNumber("-1111"));
        }

        //Test #: 24
        //Test Objective: To validate a valid house number using correct format of between 1 to 4 digits
        //Inputs: "12345"
        //Expected Output: true
        public void testValidateHouseNumber005() {
            assertEquals(false, validation.validateHouseNumber("1234"));
        }


        //Test #: 25
        //Test Objective: To validate a valid house number using correct format of between 1 to 4 digits
        //Inputs: "1"
        //Expected Output: true
        public void testValidateHouseNumber006() {
            assertEquals(true, validation.validateHouseNumber("1"));
        }


    //Test #: 26
    //Test Objective: To validate a string that can take characters not numbers
    //Inputs: line = "Mary123"
    //Expected Output: true
    public void testValidateCustomer001() {
        assertEquals(false, validation.validateCustomer("Mary123"));
    }


    //Test #: 27
    //Test Objective: To validate a string that can take that can take characters not numbers
    //Inputs: line = "Mary"
    //Expected Output: true
    public void testValidateCustomer02() {
        assertEquals(true, validation.validateCustomer("Mary"));
    }


    //Test #: 28
    //Test Objective: To validate a string that can take that can take characters not numbers
    //Inputs: line = "Mary123"
    //Expected Output: true
    public void testValidateCustomer003() {
        assertEquals(false, validation.validateCustomer("123"));
    }


    //Test #: 29
    //Test Objective: To validate a Publication name that can take chars and numbers 1-20 max
    //Inputs: line = "123"
    //Expected Output: true
    public void testValidatePublication001() {
        assertEquals(true, validation.validatePublication("123"));
    }

    //Test #: 30
    //Test Objective: To validate a Publication name that can take chars and numbers 1-20 max
    //Inputs: line = "The Times"
    //Expected Output: true
    public void testValidatePublication002() {
        assertEquals(true, validation.validatePublication("The Times"));
    }

    //Test #: 31
    //Test Objective: To validate a Publication name that can take chars and numbers 1-20 max
    //Inputs: line = "The Times"
    //Expected Output: true
    public void testValidatePublication003() {
        assertEquals(false, validation.validatePublication("The Times The Times The Times The Times"));
    }

    //Test #: 31
    //Test Objective: To validate a Publication name that can take chars and numbers 1-20 max
    //Inputs: line = "1"
    //Expected Output: true
    public void testValidatePublication004() {
        assertEquals(true, validation.validatePublication("1"));
    }

    //Test #: 32
    //Test Objective: To validate a Customer name that can take chars and numbers 1-20 max
    //Inputs: line = "123"
    //Expected Output: false
    public void testValidateCustomer35001() {
        assertEquals(false, validation.validateCustomer("123"));
    }

    //Test #: 33
    //Test Objective: To validate a Customer name that can take chars and numbers 1-20 max
    //Inputs: line = "The Times"
    //Expected Output: true
    public void testValidateCustomer35002() {
        assertEquals(true, validation.validateCustomer("John"));
    }

    //Test #: 34
    //Test Objective: To validate a Customer name that can take chars and numbers 1-20 max
    //Inputs: line = "JohnJohnJohnJohnJohnJohnJohn"
    //Expected Output: true
    public void testValidateCustomer35003() {
        assertEquals(false, validation.validateCustomer("JohnJohnJohnJohnJohnJohnJohn"));
    }

    //Test #: 35
    //Test Objective: To validate a Customer name that can take chars and numbers 1-20 max
    //Inputs: line = "The Times"
    //Expected Output: true
    public void testValidateCustomer35004() {
        assertEquals(false, validation.validateCustomer("1"));
    }


    //Test #: 39
        //Test Objective: catch an empty value passed as a day input for Date of Birth
        //Inputs: " "
        //Expected Output: true
        public void testValidateDay001() {
            assertEquals(false, validation.validateDate(" "));
        }

        //Test #: 40
        //Test Objective: catch a character value passed as a day input for Date of Birth
        //Inputs: "ff"
        //Expected Output: true
        public void testValidateDay002() {
            assertEquals(false, validation.validateDate("ff"));
        }

        //Test #: 41
        //Test Objective: catch a mixed value passed as a day input for Date of Birth
        //Inputs: "1d"
        //Expected Output: true
        public void testValidateDay003() {
            assertEquals(false, validation.validateDate("1d"));
        }

        //Test #: 42
        //Test Objective: catch a mixed value passed as a day input for Date of Birth
        //Inputs: "d1"
        //Expected Output: true
        public void testValidateDay004() {
            assertEquals(false, validation.validateDate("d1"));
        }

        //Test #: 43
        //Test Objective: catch an invlaid value passed as a day input for Date of Birth
        //Inputs: "32"
        //Expected Output: true
        public void testValidateDay005() {
            assertEquals(false, validation.validateDate("32"));
        }

        //Test #: 44
        //Test Objective: catch an invalid value passed as a day input for Date of Birth
        //Inputs: "00"
        //Expected Output: true
        public void testValidateDay006() {
            assertEquals(false, validation.validateDate("00"));
        }

        //Test #: 45
        //Test Objective: validate a valid value passed as a day input for Date of Birth
        //Inputs: "01"
        //Expected Output: true
        public void testValidateDay007() {
            assertEquals(false, validation.validateDate("01"));
        }

        //Test #: 46
        //Test Objective: validate a valid value passed as a day input for Date of Birth
        //Inputs: "31"
        //Expected Output: true
        public void testValidateDay008() {
            assertEquals(false, validation.validateDate("31"));
        }

    //Test #: 47
    //Test Objective: validate a valid value passed as a day input for Date of Birth
    //Inputs: "2001-01-02"
    //Expected Output: true
    public void testValidateDay009() {
        assertEquals(true, validation.validateDate("2001-01-02"));
    }


    //Test #: 47
    //Test Objective: validate a Holiday start date is before a holiday end date
    //Inputs: "2001-01-02", "2001-02-01"
    //Expected Output: true
    public void testValidateHolidayStartDate001() {
        assertEquals(true, validation.validateHolidayStartBeforeEnd("2001-01-02", "2001-02-01"));
    }

    //Test #: 48
    //Test Objective: validate a Holiday start date is before a holiday end date
    //Inputs: "2001-05-02", "2001-02-01"
    //Expected Output: true
    public void testValidateHolidayStartDate002() {
        assertEquals(false, validation.validateHolidayStartBeforeEnd("2001-05-02", "2001-02-01"));
    }

    //Test #: 49
    //Test Objective: validate access only accepts 1 or 2
    //Inputs: "1"
    //Expected Output: true
    public void testValidateAccess001() {
        assertEquals(true, validation.validateAccess("1"));
    }

    //Test #: 50
    //Test Objective: validate access only accepts 1 or 2
    //Inputs: "2"
    //Expected Output: true
    public void testValidateAccess002() {
        assertEquals(true, validation.validateAccess("2"));
    }

    //Test #: 51
    //Test Objective: validate access only accepts 1 or 2
    //Inputs: "3"
    //Expected Output: false
    public void testValidateAccess003() {
        assertEquals(false, validation.validateAccess("3"));
    }

    //Test #: 52
    //Test Objective: validate access only accepts 1 or 2
    //Inputs: "0"
    //Expected Output: false
    public void testValidateAccess004() {
        assertEquals(false, validation.validateAccess("0"));
    }

    //Test #: 53
    //Test Objective: validate access only accepts 1 or 2
    //Inputs: "dddd"
    //Expected Output: false
    public void testValidateAccess005() {
        assertEquals(false, validation.validateAccess("dddd"));
    }

    //Test #: 54
    //Test Objective: validate status only accepts true or false
    //Inputs: "true"
    //Expected Output: true
    public void testValidateStatus001() {
        assertEquals(true, validation.validateStatus("true"));
    }

    //Test #: 55
    //Test Objective: validate status only accepts true or false
    //Inputs: "false"
    //Expected Output: true
    public void testValidateStatus002() {
        assertEquals(true, validation.validateStatus("false"));
    }

    //Test #: 56
    //Test Objective: validate status only accepts true or false
    //Inputs: "3"
    //Expected Output: false
    public void testValidateStatus003() {
        assertEquals(false, validation.validateStatus("3"));
    }

    //Test #: 57
    //Test Objective: validate status only accepts true or false
    //Inputs: "Hello"
    //Expected Output: false
    public void testValidateStatus004() {
        assertEquals(false, validation.validateStatus("Hello"));
    }

    //Test #: 58
    //Test Objective: validate status only accepts true or false
    //Inputs: "dddd"
    //Expected Output: false
    public void testValidateStatus005() {
        assertEquals(false, validation.validateStatus("dddd"));
    }

    //Test #: 59
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "dddd"
    //Expected Output: true
    public void testValidatePassword001() {
        assertEquals(true, validation.validatePassword("dddd"));
    }

    //Test #: 60
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "1234"
    //Expected Output: true
    public void testValidatePassword002() {
        assertEquals(true, validation.validatePassword("1234"));
    }

    //Test #: 61
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "ddddd"
    //Expected Output: false
    public void testValidatePassword003() {
        assertEquals(false, validation.validatePassword("ddddd"));
    }

    //Test #: 62
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "dd"
    //Expected Output: false
    public void testValidatePassword004() {
        assertEquals(false, validation.validatePassword("dd"));
    }

    //Test #: 63
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "12"
    //Expected Output: false
    public void testValidatePassword005() {
        assertEquals(false, validation.validatePassword("12"));
    }

    //Test #: 64
    //Test Objective: validate password is 4 characters exactly
    //Inputs: "12345"
    //Expected Output: false
    public void testValidatePassword006() {
        assertEquals(false, validation.validatePassword("12345"));
    }

    //Test #: 65
    //Test Objective: validate Eircode is only in the accepted format Of R35XY87 or a combination of these
    //Inputs: "R35XY87"
    //Expected Output: false
    public void testValidateEircode001() {
        assertEquals(true, validation.validateEircode("R35XY87"));
    }

    //Test #: 66
    //Test Objective: validate Eircode is only in the accepted format Of R35XY87 or a combination of these
    //Inputs: "R35XY877"
    //Expected Output: false
    public void testValidateEircode002() {
        assertEquals(false, validation.validateEircode("R35XY877"));
    }

    //Test #: 67
    //Test Objective: validate Eircode is only in the accepted format Of R35XY87 or a combination of these
    //Inputs: "R35XY"
    //Expected Output: false
    public void testValidateEircode003() {
        assertEquals(false, validation.validateEircode("R35XY"));
    }

    //Test #: 68
    //Test Objective: validate Eircode is only in the accepted format Of R35XY87 or a combination of these
    //Inputs: "r45ffty"
    //Expected Output: false
    public void testValidateEircode004() {
        assertEquals(false, validation.validateEircode("r45ffty"));
    }

    //Test #: 69
    //Test Objective: validate Eircode is only in the accepted format Of R35XY87 or a combination of these
    //Inputs: "1234567"
    //Expected Output: true
    public void testValidateEircode005() {
        assertEquals(true, validation.validateEircode("1234567"));
    }

    //Test #: 70
    //Test Objective: validate ID is only between 1-3 numbers
    //Inputs: "1"
    //Expected Output: true

    public void testValidateId001() {
        assertEquals(true, validation.validateID("1"));
    }

    //Test #: 71
    //Test Objective: validate ID is only between 1-3 numbers
    //Inputs: "123"
    //Expected Output: true
    public void testValidateId002() {
        assertEquals(true, validation.validateID("123"));
    }

    //Test #: 72
    //Test Objective: validate ID is only between 1-3 numbers
    //Inputs: "1234"
    //Expected Output: false
    public void testValidateId003() {
        assertEquals(false, validation.validateID("1234"));
    }

    //Test #: 73
    //Test Objective: validate ID is only between 1-3 numbers
    //Inputs: "ddd"
    //Expected Output: false
    public void testValidateId004() {
        assertEquals(false, validation.validateID("ddd"));
    }

    //Test #: 74
    //Test Objective: validate ID is only between 1-3 numbers
    //Inputs: ""
    //Expected Output: false
    public void testValidateId005() {
        assertEquals(false, validation.validateID(""));
    }

    public void testValidateStock001() {
        assertEquals(true, validation.validateStock("1"));
    }

    //Test #: 75
    //Test Objective: validate Stock is only between 1-3 numbers
    //Inputs: "123"
    //Expected Output: true
    public void testValidateStock002() {
        assertEquals(true, validation.validateStock("123"));
    }

    //Test #: 76
    //Test Objective: validate Stock is only between 1-3 numbers
    //Inputs: "1234"
    //Expected Output: false
    public void testValidateStock003() {
        assertEquals(false, validation.validateStock("1234"));
    }

    //Test #: 77
    //Test Objective: validate Stock is only between 1-3 numbers
    //Inputs: "ddd"
    //Expected Output: false
    public void testValidateStock004() {
        assertEquals(false, validation.validateStock("ddd"));
    }

    //Test #: 78
    //Test Objective: validate Stock is only between 1-3 numbers
    //Inputs: ""
    //Expected Output: false
    public void testValidateStock005() {
        assertEquals(false, validation.validateStock(""));
    }

    //Test #: 80
    //Test Objective: validate Cost is only between 1-5 characters
    //Inputs: "3.5"
    //Expected Output: true
    public void testValidateCost001() {
        assertEquals(true, validation.validateCost("3.5"));
    }

    //Test #: 81
    //Test Objective: validate Cost is only between 1-5 characters
    //Inputs: "333333"
    //Expected Output: false
    public void testValidateCost002() {
        assertEquals(false, validation.validateCost("333333"));
    }

    //Test #: 82
    //Test Objective: validate Cost is only between 1-5 characters
    //Inputs: "ghfhghg"
    //Expected Output: false
    public void testValidateCost003() {
        assertEquals(false, validation.validateCost("ghfhghg"));
    }

    //Test #: 83
    //Test Objective: validate Frequency is only daily or weekly
    //Inputs: "daily"
    //Expected Output: true
    public void testValidateFrequency001() {
        assertEquals(true, validation.validateFrequency("daily"));
    }

    //Test #: 84
    //Test Objective: validate Frequency is only daily or weekly
    //Inputs: "weekly"
    //Expected Output: true
    public void testValidateFrequency002() {
        assertEquals(true, validation.validateFrequency("weekly"));
    }

    //Test #: 85
    //Test Objective: validate Frequency is only daily or weekly
    //Inputs: "12345"
    //Expected Output: false
    public void testValidateFrequency003() {
        assertEquals(false, validation.validateFrequency("12345"));
    }

    //Test #: 86
    //Test Objective: validate Frequency is only daily or weekly
    //Inputs: "Hello"
    //Expected Output: false
    public void testValidateFrequency004() {
        assertEquals(false, validation.validateFrequency("Hello"));
    }


}



