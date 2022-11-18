package com.newspaper.publication;

import junit.framework.TestCase;

public class PublicationTest extends TestCase {

    Publication publication = new Publication();

    public PublicationTest() {
        Publication publication = new Publication();
    }


    //Test #: 1
    //Test Objective: To create a publication Record
    //Inputs: publication_Name = "Rays Thoughts",  publication_Frequency = Daily, publication_Cost = "2.50", publication_Stock_Level= "30",
    //Expected Output: com.newspaper.publication.Publication Object created with id = 0, publication_Name = "Rays Thoughts", publication_Cost = 2.50,  publication_Frequency= "Daily", publication_Stock_Level =35

    public void testPublication001()  {

        // create the com.newspaper.publication.Publication object

        // call constructor under test
        Publication publication = new Publication(0, "Rays Thoughts", 2.50, "Daily", 35);

        // checking object creation
        assertEquals(0, publication.getpublication_id());
        assertEquals("Rays Thoughts", publication.getpublication_name());
        assertEquals(2.50, publication.getCost());
        assertEquals("Daily", publication.getFrequency());
        assertEquals(35, publication.getStockLevel());


    }
//
    //Test #: 2
    //Test Objective: To catch an invalid publication name with less than 3 characters
    //Inputs: com.newspaper.publication.Publication name = "JJ",
    //Expected Output: Exception Message: "The com.newspaper.publication.Publication Name you entered is not the valid length"

    public void testValidateName001()
    {

        //Call method

        publication.validatePublicationName("JJ");
        assertEquals("The com.newspaper.publication.Publication Name you entered is not the valid length", "The com.newspaper.publication.Publication Name you entered is not the valid length");
    }

    //Test #: 3
    //Test Objective: To catch an invalid publication name (31 symbols in a name)
    //Inputs: publication_Name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    //Expected Output: Exception Message: "The com.newspaper.publication.Publication Name you entered is not the valid length"
    public void testValidateName002()  {

        //Call method under test
        publication.validatePublicationName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("The com.newspaper.publication.Publication Name you entered is not the valid length", "The com.newspaper.publication.Publication Name you entered is not the valid length");

    }
//
    //Test #: 4
    //Test Objective: To catch an invalid publication name (empty string)
    //Inputs: publication_Name = ""
    //Expected Output: Exception Message: "The publication name you have entered contains invalid character(s). Please try again."
    public void testValidateName003() {


            //Call method under test
        publication.validatePublicationName("");
        assertEquals("The com.newspaper.publication.Publication Name you entered is not the valid length", "The com.newspaper.publication.Publication Name you entered is not the valid length");


    }

    //Test #: 5
    //Test Objective: To catch an invalid publication name (number in string)
    //Inputs: publication_Name = "The Clara 1Times"
    //Expected Output: Exception Message: "The publication name you have entered contains invalid character(s). Please try again."
    public void testValidateName004() {


            //Call method under test
        publication.validatePublicationName("The Clara 1Times");
        assertEquals("The publication name you have entered contains invalid character(s). Please try again.", "The publication name you have entered contains invalid character(s). Please try again.");

    }

    //Test #: 5.5
    //Test Objective: To pass a valid publication name
    //Inputs: publication_Name = "The Clara Times"
    //Expected Output: PASS
    public void testValidateName005() {


        //Call method under test
        assertEquals(true, publication.validatePublicationName("The Clara Times"));
    }

    //
    //Test #: 6
    //Test Objective: To test that frequency only accepts "Daily" and "Weekly" (invalid string)
    //Inputs: publication_Frequency = "1"
    //Expected Output:"Please only enter the words, 'Daily' or 'Weekly'"
    public void testValidateFrequency001() {


        publication.validatePublicationFrequency("1");
        assertEquals("Please only enter the words, 'Daily' or 'Weekly'", "Please only enter the words, 'Daily' or 'Weekly'");

    }
    //
    //Test #: 7
    //Test Objective: To test that frequency only accepts "Daily and "Weekly" (no capitilization)
    //Inputs: publication_Frequency ="daily"
    //Expected Output: Exception Message: "com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)"
    public void testValidateFrequency002() {


            //Call method under test
            publication.validatePublicationFrequency("daily");
            assertEquals("com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)", "com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)");


    }

    //Test #: 8
    //Test Objective: To test that frequency only accepts "Daily and "Weekly" (no capitilization)
    //Inputs: publication_Frequency = "weekly"
    //Expected Output: Exception Message: "com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)"
    public void testValidateFrequency003() {

        //Call method under test
        publication.validatePublicationFrequency("weekly");
        assertEquals("com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)", "com.newspaper.publication.Publication NOT updated. Please use capitilization, thank you :)");

    }

    //Test #: 9
    //Test Objective: To test that frequency only accepts "Daily and "Weekly" (PASS)
    //Inputs: publication_Frequency ="Daily"
    //Expected Output: PASS
    public void testValidateFrequency004() {


        //Call method under test
        assertEquals(true, publication.validatePublicationFrequency("Daily"));
    }

    //Test #: 10
    //Test Objective: To test that frequency only accepts "Daily and Weekly" (PASS)
    //Inputs: publication_Frequency = "Weekly"
    //Expected Output: PASS
    public void testValidateFrequency005() {


            //Call method under test
                   assertEquals(true, publication.validatePublicationFrequency("Weekly"));

    }


    //Test #: 11
    //Test Objective: To catch an invalid input when a number is prompted, used for publicationCost
    //Inputs: ten
    //Expected Output: "A character you entered is not a valid number, please try again using only valid numbers."
    public void testValidateNumber001() {


        //Call method under test
        publication.validateANumber("ten");
        assertEquals("A character you entered is not a valid number, please try again using only valid numbers.", "A character you entered is not a valid number, please try again using only valid numbers.");

    }

    //Test #: 11
    //Test Objective: To catch an invalid input when a number is prompted, used for publicationCost
    //Inputs: 1.5
    //Expected Output: PASS
    public void testValidateNumber002() {

        assertEquals(true, publication.validateANumber("1.5"));
    }

    //Test #: 12
    //Test Objective:To catch an invalid input when a number is prompted, used for publicationCost
    //Inputs: 9.5
    //Expected Output: PASS
    public void testValidateNumber003() {


        //Call method under test
        assertEquals(true, publication.validateANumber("9.5"));
    }

    //Test #: 13
    //Test Objective: To test an ID entered is a valid number ( invalid character)
    //Inputs: sixteen
    //Expected Output:  "Your entry is invalid as it does not contain a whole number"
    public void testValidateId001() {


            //Call method under test
            publication.validateAWholeNumber("sixteen");
        assertEquals("Your entry is invalid as it does not contain a whole number", "Your entry is invalid as it does not contain a whole number");

    }

    //Test #: 14
    //Test Objective: To test an ID entered is a valid number
    //Inputs: 1
    //Expected Output: PASS
    public void testValidateId002() {


            //Call method under test
        assertEquals(true, publication.validateAWholeNumber("1"));

    }

    //Test #: 15
    //Test Objective:  To test an ID entered is a valid number
    //Inputs: 9
    //Expected Output: PASS
    public void testValidateID003() {


            //Call method under test
        assertEquals(true, publication.validateAWholeNumber("9"));

    }



    //Test #: 16
    //Test Objective:  To test an ID entered is a valid number
    //Inputs: 15.5
    //Expected Output: PASS
    public void testValidateID004() {


        //Call method under test
        assertEquals(false, publication.validateAWholeNumber("15.5"));

    }

}






