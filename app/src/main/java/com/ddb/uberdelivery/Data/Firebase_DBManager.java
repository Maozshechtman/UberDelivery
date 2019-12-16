package com.ddb.uberdelivery.Data;

import android.support.annotation.NonNull;

import com.ddb.uberdelivery.Data.Convertors.DateConvertor;
import com.ddb.uberdelivery.Entities.Member;
import com.ddb.uberdelivery.Entities.Parcel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parcelsRef = database.getReference("RegisteredPackages");
        parcelList = new ArrayList<>();

    }
    public static void addParcel(final Parcel parcel, final Action<String> action) {
        String phone = parcel.getRecipientPhoneNumber();
        String key = parcel.getID();
        parcelsRef.child(phone + "/" + key).setValue(parcel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(parcel.getID());
                action.onProgress("upload parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload parcel data", 100);

            }
        });
    }


    public static void removeParcel(String parcelid, final Action<String> action) {
        final String key = parcelid;

        parcelsRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Parcel value = dataSnapshot.getValue(Parcel.class);
                if (value == null)
                    action.onFailure(new Exception("parcel not find ..."));
                else {
                    parcelsRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            action.onSuccess(value.getID());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            action.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }

    public static void updateParcel(final Parcel toUpdate, final Action<String> action) {
        final String key = toUpdate.getID();
        final String phone = toUpdate.getRecipientPhoneNumber();
        parcelsRef.child(phone + '/' + key).setValue(toUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(key);
                action.onProgress("updated parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
            }
        });
    }

    private static ChildEventListener parcelRefChildEventListener;
    public static void notifyToParcelList(final NotifyDataChange<List<Parcel>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (parcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify pacel list"));
                return;
            }
            parcelList.clear();

            parcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                        Parcel parcel = uniqueKeySnapshot.getValue(Parcel.class);
                        boolean flag = true;
                        for (int i = 0; i < parcelList.size(); i++) {
                            if (parcelList.get(i).getID().equals(parcel.getID())) {
                                parcelList.set(i, parcel);
                                flag = false;
                                break;
                            }
                        }
                        if (flag)
                            parcelList.add(parcel);
                    }
                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                        Parcel parcel = uniqueKeySnapshot.getValue(Parcel.class);
                        boolean flag = true;
                        for (int i = 0; i < parcelList.size(); i++) {
                            if (parcelList.get(i).getID().equals(parcel.getID())) {
                                parcelList.set(i, parcel);
                                flag = false;
                                break;
                            }
                        }
                        if (flag)
                            parcelList.add(parcel);
                    }

                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Parcel parcel = dataSnapshot.getValue(Parcel.class);
                    String parcelid = dataSnapshot.getKey();

                    for (int i = 0; i < parcelList.size(); i++) {
                        if (parcelList.get(i).getID() == parcelid) {
                            parcelList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            parcelsRef.addChildEventListener(parcelRefChildEventListener);
        }
    }

    public static void stopNotifyToParcelList() {
        if (parcelRefChildEventListener != null) {
            parcelsRef.removeEventListener(parcelRefChildEventListener);
            parcelRefChildEventListener = null;
        }
    }


}

