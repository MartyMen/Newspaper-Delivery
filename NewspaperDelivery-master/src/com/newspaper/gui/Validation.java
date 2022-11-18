package com.newspaper.gui;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.db.DBconnection;
import com.newspaper.order.Order;
import com.newspaper.order.OrderExceptionHandler;
import com.newspaper.order.OrderView;
//import com.newspaper.order;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean validateString(String name) {
        if (name.length() > 0 && name.length() < 20) {
            if (name.matches("[a-zA-z\\s]*")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateCustomer(String name) {
        if (name.length() > 0 && name.length() < 20) {
            if (name.matches("[a-zA-z\\s]*")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateEntry(String DPID) {
        if (DPID.length() > 2 || DPID.length() < 1) {
            return false;
        } else if (DPID.matches("[0-9\\d]*")) {
            return true;
        }
        return false;
    }


//    TODO Valid house number not working for mix of letters and numbers

    public boolean validateHouseNumber(String address) {
        try {
            if (address.length() >= 1 && address.length() <= 3) {
                int address1 = Integer.parseInt(address);
                int minAddress = 1;
                int maxAddress = 999;

                if (address1 < minAddress) {
                    return false;
                } else if (address1 > maxAddress) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public boolean validateStringWithNumbers(String name) {
        if (name.length() > 0 && name.length() < 20) {
            return true;
        } else
            return false;
    }

    public boolean validatePublication(String name) {
        if (name.length() > 0 && name.length() < 25) {
            return true;
        } else
            return false;
    }

    public boolean validateCustomer35(String name) {
        if (name.length() > 0 && name.length() < 20) {
            return true;
        } else
            return false;
    }

    public boolean validatePhoneNumber(String deliveryPhoneNumber) {
        if (deliveryPhoneNumber.matches("\\d{3}[ ]\\d{7}")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateDoB(String dateOfBirth) {
        if (dateOfBirth.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateDate(String date) {
        if (date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateEndDate(String date) {
        if (date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateHolidayStartBeforeEnd(String startDate, String endDate) {
        // null in both fields is acceptable hor holiday field

        if (endDate == null) {
            return true;
        } else if (startDate != null && endDate != null) {
            // setting the format for date 2021-02-29
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // variables for saving dates in Date type
            Date start;
            Date end;

            // converting dates
            try {
                start = format.parse(startDate);
                end = format.parse(endDate);

                // if start date is not before the end date, throwing an exception
                if (start.before(end)) {
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        } else return true;

    }

    public boolean validateHoliday(String startDate, String endDate) {
        // null in both fields is acceptable hor holiday field
        if (startDate != null && endDate != null) {
            // setting the format for date 2021-02-29
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // variables for saving dates in Date type
            Date start;
            Date end;

            // converting dates
            try {
                start = format.parse(startDate);
                end = format.parse(endDate);

                // if start date is not before the end date, throwing an exception
                if (start.before(end)) {
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        } else return false;
    }

    public boolean validateAccess(String access) {
        if (access.equals("1") || access.equals("2")) {
            return true;
        } else return false;
    }

    public boolean validateStatus(String status) {
        if (status.equals("true") || status.equals("false")) {
            return true;
        } else return false;
    }

    public boolean validatePassword(String password) {
        if (password.length() == 4) {
            return true;
        } else return false;
    }

    public boolean validateEircode(String eircode) {
        // check for null value

        if (eircode.length() != 7) {
            return false;
        } else if (eircode.matches("[A-Z0-9]{7}")) {
            return true;
        }
        return false;
    }


    public boolean validateID(String DPID) {
        if (DPID.length() < 1 || DPID.length() > 3) {
            return false;
        } else if (DPID.matches("[0-9\\d]*")) {
            return true;
        }
        return false;
    }


//    public boolean validateDeliveryID(String DPID) {
//        if (DPID.length() < 1 || DPID.length() > 3) {
//            return false;
//        } else if (DPID.matches("[0-9\\d]*")) {
//            String query = "select count(*) as total from delivery_area where delivery_area_id = " + DPID;
//            ResultSet rs;
//            int count = 0;
//            try {
//                rs = DBconnection.stmt.executeQuery(query);
//                while (rs.next()) {
//                    count = rs.getInt("total");
//                }
//                if (count == 0) {
//                    System.out.println("fghfhgfghfghfghjfjghfghjfgjh");
//                    return false;
//                } else return true;
//            } catch (SQLException sqle) {
//                System.out.println(sqle.getMessage());
//                System.out.println(query);
//                return false;
//            }
//        }
//        return false;
//    }

//    public boolean validateDPIDIsNum(String dpid) {
//        if (dpid.length() < 1 || dpid.length() > 3) {
//            return false;
//        } else if (dpid.matches("[0-9\\d]*")) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean validateDPID(String dpid) throws SQLException {
//
//        int count1;
//        String strg;
//
//        if (dpid.length() == 0) {
//            return false;
//        } else if (validateEntry(dpid)) {
//            System.out.println("ghfdfgfgdfgdfgdfgdfgdfg");
//            strg = "select count(*) as total from delivery_Person where delivery_person_id = " + dpid;
//            ResultSet rs = DBconnection.stmt.executeQuery(strg);
//            count1 = 0;
//            while (rs.next()) {
//                count1 = rs.getInt("total");
//            }
//            if (count1 > 0) {
//                System.out.println("************************************");
//                return true;
//
//            } else {
//                System.out.println("$%£$%£$%£$%£$%^£$%£^$%£^%$£%$");
//                return false;
//            }
//        }
//        return false;

//    }


    public boolean validateStock(String DPID) {
        if (DPID.length() < 1 || DPID.length() > 3) {
            return false;
        } else if (DPID.matches("[0-9\\d]*")) {
            return true;
        }
        return false;
    }

    public boolean validateCost(String cost) {
        if (cost.length() >= 1 && cost.length() <= 5) {
            if (cost.matches("[+]?([0-9]*[.])?[0-9]+")) {
                return true;
            } else return false;
        } else return false;
    }


    public boolean validateFrequency(String frequency) {
        if (frequency.equals("daily") || frequency.equals("weekly")) {

            return true;
        } else return false;
    }


//----------------------------------------------------------------------------------------------------------------------
//Order Validation
//----------------------------------------------------------------------------------------------------------------------

    public boolean validateOrderFrequency(String frequency) {
        try {
            if (frequency.length() == 1) {
                int orderFrequency = Integer.parseInt(frequency);
                int minFrequency = 1;
                int maxFrequency = 7;

                if (orderFrequency < minFrequency) {
                    return false;
                } else if (orderFrequency > maxFrequency) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}