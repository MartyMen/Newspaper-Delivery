package com.newspaper.deliveryperson;

import java.util.Calendar;

public class DeliveryPerson {

    private int deliveryPersonId;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String town;
    private String deliveryPhoneNumber;
    private String dateOfBirth;
    private String accessLevel;
    private String deliveryStatus;
    private String userName;
    private String password;

   // Getters & Setters

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDeliveryPhoneNumber() {
        return deliveryPhoneNumber;
    }

    public void setDeliveryPhoneNumber(String deliveryPhoneNumber) {
        this.deliveryPhoneNumber = deliveryPhoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public DeliveryPerson() {

    }


// constructor
    public DeliveryPerson(int deliveryPersonId, String firstName, String lastName, String address1, String address2, String town, String deliveryPhoneNumber, String dateOfBirth, String accessLevel, String deliveryStatus, String userName, String password) {
        this.deliveryPersonId = deliveryPersonId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.town = town;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.accessLevel = accessLevel;
        this.deliveryStatus = deliveryStatus;
        this.userName = userName;
        this.password = password;
    }



//    *******************************************  VALIDATION *********************************************************

    public boolean validateDate(String dobDay) {

        boolean validDOB = false;
        if (dobDay.length() != 2) {
            System.out.println("The date must contain 2 numbers");
            validDOB = false;
        } else {
            dobDay = dobDay.toLowerCase();
            char[] dobArray = dobDay.toCharArray();
            for (int i = 0; i < dobArray.length; i++) {
                char ch = dobArray[i];
                if (dobDay.matches("(\\b(0[1-9]|[12][0-9]|3[01])\\b)")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return validDOB;
    }

    public boolean validateMonth(String dobMonth) {
        boolean validDOB = false;
        if (dobMonth.length() != 2) {
            System.out.println("The month must contain 2 numbers");
            validDOB = false;

        } else {
            dobMonth = dobMonth.toLowerCase();
            char[] dobArray = dobMonth.toCharArray();
            for (int i = 0; i < dobArray.length; i++) {
                char ch = dobArray[i];
                if (dobMonth.matches("(^[1-9]$)|(^0[1-9]|1[0-2]$)")) {
                    return true;
                } else {
                    return false;
                }
            }
            validDOB = true;
        }return false;

    }


    public boolean validateYear(String dobYear) {
        boolean validDOB = false;
        if (dobYear.length() != 4) {
            System.out.println("The year must contain 4 numbers");
            validDOB = false;

        } else {
//            dobYear = dobYear.toLowerCase();
//            char[] dobArray = dobYear.toCharArray();
//            for (int i = 0; i < dobArray.length; i++) {
//                char ch = dobArray[i];
            if (dobYear.matches("[0-9\\d]*")) {
                int dobYearTest = Integer.parseInt(dobYear);
                if (dobYearTest > 1900 && dobYearTest <= Calendar.getInstance().get(Calendar.YEAR)) {
                    validDOB = true;
                    return true;
                } else {
                    validDOB = false;
                    return false;
                }
            } else {
                validDOB = false;
                return false;
            }
        }validDOB = false;
        return false;

    }


    public boolean validateHouseNumber (String name){
        boolean validHouseNumber;
        if(name.length()>=1 && name.length()<5) {
            name = name.toLowerCase();
            char[] nameArray = name.toCharArray();
            for (int i = 0; i < nameArray.length; i++) {
                char ch = nameArray[i];
                if (ch >= '0' && ch <= '9') {
                    validHouseNumber = true;
                    return true;
                }
            }
            return false;
        }
        else
            validHouseNumber = false;
        return false;
    }

    public boolean validateStringWithNumbers (String name){
        if(name.length()>1 && name.length()<20) {
            return true;
        }
        else
            return false;
    }

    public boolean validateString (String name) {
        if (name.length() > 1 && name.length() < 20) {
            if (name.matches("[a-zA-z\\s]*")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean validateEntry (String id){
        if(id.length()>2) {

            System.out.println("invalid entry, you must enter no more than 2 numbers");
            return false;
        }
        else if (id.matches("[0-9\\d]*")){
                return true;
            }
            else {
            System.out.println("Invalid entry, please enter a valid ID");
                return false;
            }
        }


//    public boolean validateEntry (String id){
//        if (id.length() > 2) {
//            System.out.println("invalid entry, you must enter no more than 2 numbers");
//            return false;
//        } else {
//            try {
//
//            } catch (Exception e) {
//                System.out.println("invalid Text entered, please enter a number");
//                return false;
//            }
//        }
//        return true;
//
//    }

    public boolean validatePhoneNumber(String deliveryPhoneNumber){
        if(deliveryPhoneNumber.matches("\\d{3}[ ]\\d{7}")){
            return true;
        }
        else{
            return false;
        }
    }
}
