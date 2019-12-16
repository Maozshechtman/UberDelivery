package com.ddb.uberdelivery.Entities;

import android.location.Location;
import android.provider.ContactsContract;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class Parcel {
    //region Enums
    public enum Type{
        
        Envelope,
        SmallPackage,
        LargePackage
    }
    public enum Status{
        Registered, CollectionOffered, OnTheWay, Delivered
    }

   //Fields
    private Status status = Status.Registered;
    private Type type;
    private Boolean isFragile;
    private double weight;
    private Location distributionCenterAddress;
    private String recipientAddress;
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientEmailAddress;
    private Date deliveryDate ;
    private Date ReceivedDate;
    private String deliveryGuyName;
    @Exclude
     private String ID;
    //Constructors
    public Parcel(Status status, Type type, Boolean isFragile, double weight, Location distributionCenterAddress,
                  String recipientAddress, String recipientName, String recipientPhoneNumber,
                  String recipientEmailAddress,
                  Date deliverDate, Date receivedDate, String deliveryPersonName,String id) {

        this.status = status;
        this.type = type;
        this.isFragile = isFragile;
        this.weight = weight;
        this.distributionCenterAddress = distributionCenterAddress;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.recipientEmailAddress = recipientEmailAddress;
        deliveryDate = deliverDate;
        ReceivedDate = receivedDate;
        this.deliveryGuyName = deliveryPersonName;
        this.ID=id;

    }

    public Parcel() {
        this(null,null,false,0,null,
                "","","","",
                null,null,"NO","");
    }

    //Getters & Setters
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getFragile() {
        return isFragile;
    }

    public void setFragile(Boolean fragile) {
        isFragile = fragile;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Location getDistributionCenterAddress() {
        return distributionCenterAddress;
    }

    public void setDistributionCenterAddress(Location distributionCenterAddress) {
        this.distributionCenterAddress = distributionCenterAddress;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getRecipientEmailAddress() {
        return recipientEmailAddress;
    }

    public void setRecipientEmailAddress(String recipientEmailAddress) {
        this.recipientEmailAddress = recipientEmailAddress;
    }

    public Date getDeliverDate() {
        return deliveryDate;
    }

    public void setDeliverDate(Date deliverDate) {
        deliverDate = deliverDate;
    }

    public Date getReceivedDate() {
        return ReceivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        ReceivedDate = receivedDate;
    }

    public String getDeliveryPersonName() {
        return deliveryGuyName;
    }

    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryGuyName= deliveryPersonName;
    }
     @Exclude
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
