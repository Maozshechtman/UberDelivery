package com.ddb.uberdelivery.Data.Convertors;

import com.ddb.uberdelivery.Entities.Parcel;
import  com.ddb.uberdelivery.Entities.Parcel.Status;

public class EnumConvertor {
    public static Enum<Parcel.Status> fromStringtoStatus(String statusStr) {
        return statusStr.equals("Registered") ? Status.Registered : statusStr.equals("CollectionOffered") ? Status.CollectionOffered
                : statusStr.equals("OnTheWay") ? Status.OnTheWay : statusStr.equals("Delivered") ? Status.Delivered : Status.Registered;
    }
        public static String fromStatusToString(Enum<Status> status){
        return  status.toString();
        }
    public static Enum<Parcel.Type> fromStringtoType(String statusStr) {
        return statusStr.equals("Envelope") ? Parcel.Type.Envelope : statusStr.equals("SmallPackage") ?Parcel.Type.SmallPackage
             : Parcel.Type.LargePackage;
    }
    public static String fromParcelTypeoString(Enum<Parcel.Type> type){
        return  type.toString();
    }


}
