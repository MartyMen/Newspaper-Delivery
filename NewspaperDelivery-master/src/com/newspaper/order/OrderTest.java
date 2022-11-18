package com.newspaper.order;

import com.newspaper.db.DBconnection;
import com.newspaper.gui.OrderMainGUI;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.time.DateTimeException;

public class OrderTest extends TestCase {

    private final Order order;

    public OrderTest(){
        order = new Order();
        DBconnection.init_db();
    }

    OrderView ov = new OrderView();
    OrderMainGUI omg = new OrderMainGUI();

    //Test #: 1
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = 0
    //Expected Output: Exception Message: "Customer id does not exist"

    public void testValidateCustomerId001() {
        try {
            //Call method under test
            order.validateCustomerId(0);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Customer id does not exist", e.getMessage());
        }
    }

    //Test #: 2
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: customer_id = 44, nameOfField = "Customer ID"
    //Expected Output: Exception Message: "Customer id does not exist"

    public void testValidateCustomerId002() {
        try {
            //Call method under test
            order.validateCustomerId(44);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Customer id does not exist", e.getMessage());
        }
    }

    //Test #: 3
    //Test Objective: To check validation on a correct ID
    //Inputs: customer_id = 1, nameOfField = "Customer ID"
    //Expected Output: No Exception

    public void testValidateCustomerId003() {
        try {
            //Call method under test
            order.validateCustomerId(1);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 4
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: publication_id = 100, nameOfField = "Publication ID"
    //Expected Output: Exception Message: "Publication id does not exist"

    public void testValidatePublicationId001() {
        try {
            //Call method under test
            order.validatePublicationId(15);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Publication id does not exist", e.getMessage());
        }
    }

    //Test #: 5
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: publication_id = -1, nameOfField = "Publication ID"
    //Expected Output: Exception Message: "Publication id does not exist"

    public void testValidatePublicationId002() {
        try {
            //Call method under test
            order.validatePublicationId(-1);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Publication id does not exist", e.getMessage());
        }
    }

    //Test #: 6
    //Test Objective: To check validation on a correct ID
    //Inputs: publication_id = 10, nameOfField = "Publication ID"
    //Expected Output: No Exception

    public void testValidatePublicationId003() {
        try {
            //Call method under test
            order.validatePublicationId(10);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }


    //Test #: 7
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int frequency = "0", nameOfField = "Frequency"
    //Expected Output: Exception Message: "Frequency does not exist, please enter a number between 1 and 7"

    public void testValidateFrequency001() {
        try{
            order.validateFrequency(0);
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Frequency does not exist, please enter a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 8
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int frequency = "8", nameOfField = "Frequency"
    //Expected Output: Exception Message: "Frequency does not exist, please enter a number between 1 and 7"
    public void testValidateFrequency002() {
        try{
            order.validateFrequency(8);
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Frequency does not exist, please enter a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 9
    //Test Objective: To check validation on a correct ID
    //Inputs: int frequency = "5", nameOfField = "Frequency"
    //Expected Output: No Exception
    public void testValidateFrequency003() {
        try{
            order.validateFrequency(1);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 10
    //Test Objective: To check validation on a correct ID
    //Inputs: int customerID = "1", nameOfField = "Customer ID"
    //Expected Output: Valentino Firman
    public void testGetCustomerName001() {
        try {
            //Call method under test
            ov.getCustomerName(1);
        }
        catch (Exception e) {
            fail("No exception expected");
            assertEquals("Valentino Firman", e.getMessage());
        }
    }

    //Test #: 11
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int customerID = "-1", nameOfField = "Customer ID"
    //Expected Output: "Customer does not exist"
    public void testGetCustomerName002() {
        try {
            //Call method under test
            ov.getCustomerName(-1);
        }
        catch (OrderExceptionHandler sqle) {
            assertEquals("Customer does not exist", sqle.getMessage());
        }
    }

    //Test #: 12
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int customerID = "58", nameOfField = "Customer ID"
    //Expected Output: "Customer does not exist"
    public void testGetCustomerName003() {
        try {
            //Call method under test
            ov.getCustomerName(58);
        }
        catch (OrderExceptionHandler sqle) {
            assertEquals("Customer does not exist", sqle.getMessage());
        }
    }

    //Test #: 13
    //Test Objective: To check validation on a correct ID
    //Inputs: int publicationID = "5", nameOfField = "Publication ID"
    //Expected Output: "The Sunday Mirror"
    public void testGetPublicationByID001() {
        try {
            //Call method under test
            ov.getPublicationByID(5);
        }
        catch (OrderExceptionHandler sqle) {
            fail("No exception expected");
            assertEquals("The Sunday Mirror", sqle.getMessage());
        }
    }

    //Test #: 14
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int publicationID = "-1", nameOfField = "Publication ID"
    //Expected Output: "Publication does not exist"
    public void testGetPublicationByID002() {
        try {
            //Call method under test
            ov.getPublicationByID(-1);
        }
        catch (OrderExceptionHandler sqle) {
            assertEquals("Publication does not exist", sqle.getMessage());
        }
    }

    //Test #: 15
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int publicationID = "15", nameOfField = "Publication ID"
    //Expected Output: "Publication does not exist"
    public void testGetPublicationByID003() {
        try {
            //Call method under test
            ov.getPublicationByID(15);
        }
        catch (OrderExceptionHandler sqle) {
            assertEquals("Publication does not exist", sqle.getMessage());
        }
    }

    //Test #: 16
    //Test Objective: To check validation on a correct ID
    //Inputs: int frequency = "5", nameOfField = "Frequency"
    //Expected Output: "Friday"
    public void testConvertFrequency001() {
        try {
            //Call method under test
            ov.convertFrequency(5);
        }
        catch (OrderExceptionHandler sqle) {
            fail("No exception expected");
            assertEquals("Friday", sqle.getMessage());
        }
    }

    //Test #: 17
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int frequency = "-1", nameOfField = "Frequency"
    //Expected Output: Exception Message: "Frequency does not exist, please enter a number between 1 and 7" || "Invalid value for DayOfWeek: -1"
    public void testConvertFrequency002() {
        try {
            //Call method under test
            ov.convertFrequency(-1);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {

            assertEquals("Frequency does not exist, please enter a number between 1 and 7", e.getMessage());
        }
        catch (DateTimeException e) {

            assertEquals("Invalid value for DayOfWeek: -1", e.getMessage());
        }
    }

    //Test #: 18
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: int frequency = "8", nameOfField = "Frequency"
    //Expected Output: Exception Message: "Frequency does not exist, please enter a number between 1 and 7" || "Invalid value for DayOfWeek: 8"
    public void testConvertFrequency003() {
        try {
            //Call method under test
            ov.convertFrequency(8);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {

            assertEquals("Frequency does not exist, please enter a number between 1 and 7", e.getMessage());
        }
        catch (DateTimeException e) {

            assertEquals("Invalid value for DayOfWeek: 8", e.getMessage());
        }
    }


    //Test #: 19
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: customer_id = 100, nameOfField = "Customer ID"
    //Expected Output: Exception Message: "Customer does not have an order"

    public void testValidateOrderCustomerId001() {
        try {
            //Call method under test
            ov.validateOrderCustomerId(100);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Customer does not have an order", e.getMessage());
        }
    }

    //Test #: 20
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: customer_id = -1, nameOfField = "Customer ID"
    //Expected Output: Exception Message: "Customer does not have an order"

    public void testValidateOrderCustomerId002() {
        try {
            //Call method under test
            ov.validateOrderCustomerId(-1);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Customer does not have an order", e.getMessage());
        }
    }

    //Test #: 21
    //Test Objective: To check validation on a correct ID
    //Inputs: customer_id = 10, nameOfField = "Customer ID"
    //Expected Output: No Exception

    public void testValidateOrderCustomerId003() {
        try {
            //Call method under test
            ov.validateOrderCustomerId(10);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 22
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: order_id = 105, nameOfField = "Order ID"
    //Expected Output: Exception Message: "Order id does not exist"

    public void testValidateOrderId001() {
        try {
            //Call method under test
            order.validateOrderId(105);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Order id does not exist", e.getMessage());
        }
    }

    //Test #: 23
    //Test Objective: To see if a number outside the range triggers the exception handler
    //Inputs: order_id = 0, nameOfField = "Order ID"
    //Expected Output: Exception Message: "Order id does not exist"

    public void testValidateOrderId002() {
        try {
            //Call method under test
            order.validateOrderId(0);
            fail("Exception expected");
        }
        catch (OrderExceptionHandler e) {
            assertEquals("Order id does not exist", e.getMessage());
        }
    }

    //Test #: 24
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = 1, nameOfField = "Order ID"
    //Expected Output: No Exception

    public void testValidateOrderId003() {
        try {
            //Call method under test
            order.validateOrderId(1);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 25
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "10", publication_id = "5"
    //Expected Output: No Exception

    public void testUpdateOrderPublicationGUI001() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("10", "5");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 26
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "10", publication_id = "-1"
    //Expected Output: Publication ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI002() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("10", "-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 27
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "10", publication_id = "15"
    //Expected Output: Publication ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI003() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("10", "15");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 28
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "10", publication_id = "f"
    //Expected Output: Publication ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI004() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("10", "f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 29
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "10", publication_id = "%"
    //Expected Output: Publication ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI005() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("10", "%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 30
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "-1", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI006() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("-1", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 31
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "105", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI007() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("105", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 32
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "f", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI008() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("f", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 33
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "%", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI009() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("%", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 34
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "0001", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI010() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("0001", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 35
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "", publication_id = "10"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI011() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("", "10");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 36
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", publication_id = ""
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderPublicationGUI012() {
        try {
            //Call method under test
            ov.updateOrderPublicationGUI("1", "");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 37
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "22", frequency_id = "7"
    //Expected Output: No exception

    public void testUpdateOrderFrequencyGUI001() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("22", "7");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 38
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", frequency_id = "-1"
    //Expected Output: Frequency ID not valid - must be a number between 1 and 7

    public void testUpdateOrderFrequencyGUI002() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("1", "-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency ID not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 39
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", frequency_id = "8"
    //Expected Output: Frequency ID not valid - must be a number between 1 and 7

    public void testUpdateOrderFrequencyGUI003() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("1", "8");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency ID not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 40
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", frequency_id = "f"
    //Expected Output: Frequency ID not valid - must be a number between 1 and 7

    public void testUpdateOrderFrequencyGUI004() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("1", "f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency ID not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 41
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", frequency_id = "%"
    //Expected Output: Frequency ID not valid - must be a number between 1 and 7

    public void testUpdateOrderFrequencyGUI005() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("1", "%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency ID not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 42
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "-1", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI006() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("-1", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 43
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "105", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI007() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("105", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 44
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "f", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI008() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("f", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 45
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "%", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI009() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("%", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 46
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "0001", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI010() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("0001", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 47
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "", frequency_id = "1"
    //Expected Output: Order ID not valid - ID must contain 1 - 3 numbers only

    public void testUpdateOrderFrequencyGUI011() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("", "1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - ID must contain 1 - 3 numbers only", e.getMessage());
        }
    }

    //Test #: 48
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "1", frequency_id = ""
    //Expected Output: Frequency ID not valid - must be a number between 1 and 7

    public void testUpdateOrderFrequencyGUI012() {
        try {
            //Call method under test
            ov.updateOrderFrequencyGUI("1", "");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency ID not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 49
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "1"
    //Expected Output: No exception

    public void testDeleteOrderGUI001() {
        try {
            //Call method under test
            ov.deleteOrderGUI("1");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 50
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "0"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDeleteOrderGUI002() {
        try {
            //Call method under test
            ov.deleteOrderGUI("0");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 51
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "105"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDeleteOrderGUI003() {
        try {
            //Call method under test
            ov.deleteOrderGUI("105");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 52
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "f"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDeleteOrderGUI004() {
        try {
            //Call method under test
            ov.deleteOrderGUI("f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 53
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "%"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDeleteOrderGUI005() {
        try {
            //Call method under test
            ov.deleteOrderGUI("%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 54
    //Test Objective: To check validation on a correct ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = "1"
    //Expected Output: No exception

    public void testAddNewOrderGUI001() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 55
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "-1", publication_id = "1", frequency = "1"
    //Expected Output: Error, cannot add this order, customer ID not valid

    public void testAddNewOrderGUI002() {
        try {
            //Call method under test
            omg.addNewOrderGUI("-1", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, customer ID not valid", e.getMessage());
        }
    }

    //Test #: 56
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "44", publication_id = "1", frequency = "1"
    //Expected Output: Error, cannot add this order, customer ID not valid

    public void testAddNewOrderGUI003() {
        try {
            //Call method under test
            omg.addNewOrderGUI("44", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, customer ID not valid", e.getMessage());
        }
    }

    //Test #: 57
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "f", publication_id = "1", frequency = "1"
    //Expected Output: Error, cannot add this order, customer ID not valid

    public void testAddNewOrderGUI004() {
        try {
            //Call method under test
            omg.addNewOrderGUI("f", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, customer ID not valid", e.getMessage());
        }
    }

    //Test #: 58
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "%", publication_id = "1", frequency = "1"
    //Expected Output: Error, cannot add this order, customer ID not valid

    public void testAddNewOrderGUI005() {
        try {
            //Call method under test
            omg.addNewOrderGUI("%", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, customer ID not valid", e.getMessage());
        }
    }

    //Test #: 59
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "", publication_id = "1", frequency = "1"
    //Expected Output: Error, cannot add this order, customer ID not valid

    public void testAddNewOrderGUI006() {
        try {
            //Call method under test
            omg.addNewOrderGUI("", "1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, customer ID not valid", e.getMessage());
        }
    }

    //Test #: 60
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "-1", frequency = "1"
    //Expected Output: Error, cannot add this order, publication ID not valid

    public void testAddNewOrderGUI007() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "-1", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, publication ID not valid", e.getMessage());
        }
    }

    //Test #: 61
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "15", frequency = "1"
    //Expected Output: Error, cannot add this order, publication ID not valid

    public void testAddNewOrderGUI008() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "15", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, publication ID not valid", e.getMessage());
        }
    }

    //Test #: 62
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "f", frequency = "1"
    //Expected Output: Error, cannot add this order, publication ID not valid

    public void testAddNewOrderGUI009() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "f", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, publication ID not valid", e.getMessage());
        }
    }

    //Test #: 63
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "%", frequency = "1"
    //Expected Output: Error, cannot add this order, publication ID not valid

    public void testAddNewOrderGUI010() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "%", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, publication ID not valid", e.getMessage());
        }
    }

    //Test #: 64
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "", frequency = "1"
    //Expected Output: Error, cannot add this order, publication ID not valid

    public void testAddNewOrderGUI011() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "", "1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, publication ID not valid", e.getMessage());
        }
    }

    //Test #: 65
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = "-1"
    //Expected Output: Error, cannot add this order, frequency not valid

    public void testAddNewOrderGUI012() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "-1");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, frequency not valid", e.getMessage());
        }
    }

    //Test #: 66
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = "8"
    //Expected Output: Error, cannot add this order, frequency not valid

    public void testAddNewOrderGUI013() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "8");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, frequency not valid", e.getMessage());
        }
    }

    //Test #: 67
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = "f"
    //Expected Output: Error, cannot add this order, frequency not valid

    public void testAddNewOrderGUI014() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "f");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, frequency not valid", e.getMessage());
        }
    }

    //Test #: 68
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = "%"
    //Expected Output: Error, cannot add this order, frequency not valid

    public void testAddNewOrderGUI015() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "%");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, frequency not valid", e.getMessage());
        }
    }

    //Test #: 69
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id - "1", publication_id = "1", frequency = ""
    //Expected Output: Error, cannot add this order, frequency not valid

    public void testAddNewOrderGUI016() {
        try {
            //Call method under test
            omg.addNewOrderGUI("1", "1", "");
        }
        catch (OrderExceptionHandler e) {
            fail("Exception expected");
            assertEquals("Error, cannot add this order, frequency not valid", e.getMessage());
        }
    }

    //Test #: 70
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "1"
    //Expected Output: No exception

    public void testDisplayOrderByOrderIdGUI001() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("1");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 71
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "-1"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDisplayOrderByOrderIdGUI002() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 72
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "105"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDisplayOrderByOrderIdGUI003() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("105");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 73
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "f"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDisplayOrderByOrderIdGUI004() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 74
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = "%"
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDisplayOrderByOrderIdGUI005() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 75
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = ""
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDisplayOrderByOrderIdGUI006() {
        try {
            //Call method under test
            omg.displayOrderByOrderIdGUI("");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 76
    //Test Objective: To check validation on a correct ID
    //Inputs: customer_id = "1"
    //Expected Output: No exception

    public void testDisplayOrderByCustomerIdGUI001() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("1");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 77
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = "-1"
    //Expected Output: Customer ID not valid - please check the Customer section for valid ID

    public void testDisplayOrderByCustomerIdGUI002() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Customer ID not valid - please check the Customer section for valid ID", e.getMessage());
        }
    }

    //Test #: 78
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = "105"
    //Expected Output: Customer ID not valid - please check the Customer section for valid ID

    public void testDisplayOrderByCustomerIdGUI003() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("105");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Customer ID not valid - please check the Customer section for valid ID", e.getMessage());
        }
    }

    //Test #: 79
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = "f"
    //Expected Output: Customer ID not valid - please check the Customer section for valid ID

    public void testDisplayOrderByCustomerIdGUI004() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Customer ID not valid - please check the Customer section for valid ID", e.getMessage());
        }
    }

    //Test #: 80
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = "%"
    //Expected Output: Customer ID not valid - please check the Customer section for valid ID

    public void testDisplayOrderByCustomerIdGUI005() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Customer ID not valid - please check the Customer section for valid ID", e.getMessage());
        }
    }

    //Test #: 81
    //Test Objective: To check validation on an incorrect ID
    //Inputs: customer_id = ""
    //Expected Output: Customer ID not valid - please check the Customer section for valid ID

    public void testDisplayOrderByCustomerIdGUI006() {
        try {
            //Call method under test
            omg.displayOrderByCustomerIdGUI("");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Customer ID not valid - please check the Customer section for valid ID", e.getMessage());
        }
    }

    //Test #: 82
    //Test Objective: To check validation on a correct ID
    //Inputs: publication_id = 1
    //Expected Output: No exception

    public void testDisplayOrderByPublicationIdGUI001() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("1");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 83
    //Test Objective: To check validation on an incorrect ID
    //Inputs: publication_id = -1
    //Expected Output: Publication ID not valid - please check the Publication section for valid ID

    public void testDisplayOrderByPublicationIdGUI002() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - please check the Publication section for valid ID", e.getMessage());
        }
    }

    //Test #: 84
    //Test Objective: To check validation on an incorrect ID
    //Inputs: publication_id = 15
    //Expected Output: Publication ID not valid - please check the Publication section for valid ID

    public void testDisplayOrderByPublicationIdGUI003() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("15");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - please check the Publication section for valid ID", e.getMessage());
        }
    }

    //Test #: 85
    //Test Objective: To check validation on an incorrect ID
    //Inputs: publication_id = f
    //Expected Output: Publication ID not valid - please check the Publication section for valid ID

    public void testDisplayOrderByPublicationIdGUI004() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - please check the Publication section for valid ID", e.getMessage());
        }
    }

    //Test #: 86
    //Test Objective: To check validation on an incorrect ID
    //Inputs: publication_id = %
    //Expected Output: Publication ID not valid - please check the Publication section for valid ID

    public void testDisplayOrderByPublicationIdGUI005() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - please check the Publication section for valid ID", e.getMessage());
        }
    }

    //Test #: 87
    //Test Objective: To check validation on an incorrect ID
    //Inputs: publication_id = ""
    //Expected Output: Publication ID not valid - please check the Publication section for valid ID

    public void testDisplayOrderByPublicationIdGUI006() {
        try {
            //Call method under test
            omg.displayOrderByPublicationIdGUI("");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Publication ID not valid - please check the Publication section for valid ID", e.getMessage());
        }
    }

    //Test #: 88
    //Test Objective: To check validation on a correct ID
    //Inputs: frequency = "1"
    //Expected Output: No exception

    public void testDisplayOrderByFrequencyGUI001() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("1");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 89
    //Test Objective: To check validation on an incorrect ID
    //Inputs: frequency = "-1"
    //Expected Output: Frequency not valid - must be a number between 1 and 7

    public void testDisplayOrderByFrequencyGUI002() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("-1");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 90
    //Test Objective: To check validation on an incorrect ID
    //Inputs: frequency = "8"
    //Expected Output: Frequency not valid - must be a number between 1 and 7

    public void testDisplayOrderByFrequencyGUI003() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("8");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 91
    //Test Objective: To check validation on an incorrect ID
    //Inputs: frequency = "f"
    //Expected Output: Frequency not valid - must be a number between 1 and 7

    public void testDisplayOrderByFrequencyGUI004() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("f");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 92
    //Test Objective: To check validation on an incorrect ID
    //Inputs: frequency = "%"
    //Expected Output: Frequency not valid - must be a number between 1 and 7

    public void testDisplayOrderByFrequencyGUI005() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("%");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 93
    //Test Objective: To check validation on an incorrect ID
    //Inputs: frequency = ""
    //Expected Output: Frequency not valid - must be a number between 1 and 7

    public void testDisplayOrderByFrequencyGUI006() {
        try {
            //Call method under test
            omg.displayOrderByFrequencyGUI("");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Frequency not valid - must be a number between 1 and 7", e.getMessage());
        }
    }

    //Test #: 94
    //Test Objective: To check validation on an incorrect ID
    //Inputs: order_id = ""
    //Expected Output: Order ID not valid - please check Display All Orders section for valid ID

    public void testDeleteOrderGUI006() {
        try {
            //Call method under test
            ov.deleteOrderGUI("");
        }
        catch (SQLException e) {
            fail("Exception expected");
            assertEquals("Order ID not valid - please check Display All Orders section for valid ID", e.getMessage());
        }
    }

    //Test #: 95
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "1"
    //Expected Output: No exception expected

    public void testValidateOrderId004() {
        try {
            //Call method under test
            order.validateOrderId(104);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 96
    //Test Objective: To check validation on a correct ID
    //Inputs: int frequency = "7", nameOfField = "Frequency"
    //Expected Output: No Exception
    public void testValidateFrequency004() {
        try{
            order.validateFrequency(7);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }

    //Test #: 97
    //Test Objective: To check validation on a correct ID
    //Inputs: order_id = "104"
    //Expected Output: No exception

    public void testDeleteOrderGUI007() {
        try {
            //Call method under test
            ov.deleteOrderGUI("104");
        }
        catch (SQLException e) {
            fail("No exception expected");
        }
    }

    //Test #: 98
    //Test Objective: To check validation on a correct ID
    //Inputs: customer_id = 43, nameOfField = "Customer ID"
    //Expected Output: No Exception

    public void testValidateCustomerId004() {
        try {
            //Call method under test
            order.validateCustomerId(43);
        }
        catch (OrderExceptionHandler e) {
            fail("No exception expected");
        }
    }
}