package com.ddb.uberdelivery.Entities;

public class Member {
    //** For testing we created inner member class

    @Override
    public String toString() {
        return "Member{" +
                 name + '\'' +
                " " + address + '\'' +
                '}';
    }

    private String phoneNumber;
    private String name;
    private String address;

        public Member(String phoneNumber, String name, String address) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            this.address = address;
        }
       public  Member(){this("000-0000000","avi","");}



    ///TODO:Delete this class member after the presentation!!
}
