package com.ddb.uberdelivery.UI.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ddb.uberdelivery.Entities.Member;
import com.ddb.uberdelivery.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryParcels extends AppCompatActivity {
    private ListView parcelsListView;
    ArrayList<String>parcelsList=new ArrayList<>();
   ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_parcels);
        parcelsListView=findViewById(R.id.parcelsListView);
        adapter= new ArrayAdapter(HistoryParcels.this,android.R.layout.simple_expandable_list_item_1,parcelsList);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference parcelsRef=database.getReference("Parcel");
        parcelsListView.setAdapter(adapter);
        setListvalues(parcelsRef);

    }

    private void setListvalues(DatabaseReference parcelsRef) {
        parcelsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getStringFromDatasnapshot(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getStringFromDatasnapshot(DataSnapshot dataSnapshot) {

            Member member = new Member(
                    dataSnapshot.child("id").getValue(String.class),
                    dataSnapshot.child("firstName").getValue(String.class),
                    dataSnapshot.child("lastName").getValue(String.class),
                    dataSnapshot.child("phoneNumber").getValue(String.class),
                    dataSnapshot.child("address").getValue(String.class),
                    dataSnapshot.child("emailAddress").getValue(String.class)
            );

            parcelsList.add(member.getFirstName() + "\t" + member.getLastName() + "\t" + member.getEmailAddress());
            Log.d("members=", parcelsList.toString());
            adapter.notifyDataSetChanged();



    }


}
