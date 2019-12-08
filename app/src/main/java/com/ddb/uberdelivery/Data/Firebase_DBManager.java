package com.ddb.uberdelivery.Data;

import com.ddb.uberdelivery.Entities.Member;
import com.ddb.uberdelivery.Entities.Parcel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class  Firebase_DBManager {
    public interface Action<T> {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }

        public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);
        void onFailure(Exception exception);
    }

    static DatabaseReference parcelsRef;
    static List<Parcel> parcelList;
    static DatabaseReference membersRef;
    static List<Parcel> memberslList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parcelsRef = database.getReference("parcels");
        parcelList = new ArrayList<>();
        membersRef=database.getReference("members");
        memberslList=new ArrayList<>();
    }

    public  static  void addParcel(final Parcel parcel,final  Action<Long> action){
        parcelsRef.setValue(parcel);


    }

    public static  void addMember(Member member){
        membersRef.push().setValue(member);
    }

}
