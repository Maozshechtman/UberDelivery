package com.ddb.uberdelivery.Entities;

import com.google.firebase.database.Exclude;

public class Member {
    //Fields


    @Exclude
    String ID;
    private String phoneNumber;
    private String name;
    private String address;
    private String emailAdress;

    //Constructors
    public Member(String phoneNumber, String name, String address, String emailAdress) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.emailAdress = emailAdress;
    }

    public  Member(){this("0000000000","John Doe"," Wizard 2 EmeraldCity","johndoe@gmail.com");}

    //Getters and Setters


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
    @Exclude

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
