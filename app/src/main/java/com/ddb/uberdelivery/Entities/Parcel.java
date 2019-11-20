package com.ddb.uberdelivery.Entities;

public class Parcel {
    //region Enums
    public enum Type{
        Envelope, SmallPackage, LargePackage
    }
    public enum Status{
        Registered, CollectionOffered, OnTheWay, Delivered
    }

    //endregion
    private Status status = Status.Registered;
    private Type type;
    private Boolean isFragile;
    private double weight;
    private String distributionCenterAddress;
    private String recipientAddress;
    private String recipientName;
    private String recipientPhoneNumber;
    private String ParcelID;
    public Parcel(Type type, Boolean isFragile, double weight, String distributionCenterAddress,
                  String recipientAddress, String recipientName, String recipientPhoneNumber,
                  String ParcelID) {
        this.type = type;
        this.isFragile = isFragile;
        this.weight = weight;
        this.distributionCenterAddress = distributionCenterAddress;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.ParcelID = ParcelID;
    }
    public Parcel() {this(null, false, 0, "", "", "", "", "");}
}
