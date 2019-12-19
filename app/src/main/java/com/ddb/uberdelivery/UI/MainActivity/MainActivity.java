package com.ddb.uberdelivery.UI.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ddb.uberdelivery.Entities.Member;
import com.ddb.uberdelivery.Entities.Parcel;
import com.ddb.uberdelivery.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private CheckBox isFragileCheckBox;
    private String[] weightspinerValues = {"0.5", "1", "5", "20"};
    private Button addButton;
    private Spinner weightSpinner;
    private Spinner typeSpiner;
    private TextView textView;
    private TextView locationTextView;
    private TextView phoneNumberTextView;
    private TextView targetNameTextView;
    private Button historyButton;
    private Calendar calendar;
    private Button reciveDateButton;
    private TextView deliveryDateTextView;
    private int mYear, mMonth, mDay;

    //GPS
    LocationManager locationManager;
    LocationListener locationListener;

    //FireBase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Data
    private int parcelsCount;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Parcel> parcels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        //region Location Setup
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (addressList != null && addressList.size() > 0){

                        String address = "";

                        if (addressList.get(0).getThoroughfare() != null){
                            address += addressList.get(0).getThoroughfare();
                        }

                        if (addressList.get(0).getSubThoroughfare() != null){
                            if (address != ""){
                                address += " " + addressList.get(0).getSubThoroughfare();
                            }
                        }

                        if (addressList.get(0).getLocality() != null){
                            if (address != ""){
                                address += ", ";
                            }

                            address += addressList.get(0).getLocality();
                        }

                        if (addressList.get(0).getCountryName() != null){
                            if (address != ""){
                                address += ", ";
                            }
                            address += addressList.get(0).getCountryName();
                        }

                        locationTextView.setText(address);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //if there permission wasnt granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{//if permission granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        //endregion

        //region Firebase Setup
        database = FirebaseDatabase.getInstance();

        //Get Members
        myRef = database.getReference("Member");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                members.add(dataSnapShotToMember(dataSnapshot));
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

        //Get Parcels Count
        DatabaseReference dbRef = database.getReference("Config");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parcelsCount = dataSnapshot.child("ParcelsCount").getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //endregion

        //addMember("308478098", "elyasaf", "elbaz", "0546401267", "beit el, sufa 4","elyasaf755@gmail.com");
        //addMember("312589963", "Maoz", "Shachtman", "0525525223", "habrecha 3, haifa", "maozShechtman@gmail.com");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    private void initializeViews() {
        isFragileCheckBox = findViewById(R.id.isFragileCheckBox);
        typeSpiner = findViewById(R.id.packageTypeSpinner2);
        typeSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Types));
        weightSpinner = findViewById(R.id.packageWeightSpinner);
        weightSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Weights));
        addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);
        historyButton=findViewById(R.id.historyButton);
        locationTextView = findViewById(R.id.packageLocationTextView);
        phoneNumberTextView = findViewById(R.id.phonePlainText);
        phoneNumberTextView.setText(null);
        targetNameTextView = findViewById(R.id.targetNameTextView);
        deliveryDateTextView = findViewById(R.id.deliveryDateTextView);
        deliveryDateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getParcelId(){

        return String.valueOf(parcelsCount);
    }

    private Member getMemberByPhoneNumber(String phoneNumber){
        for (Member member : members){
            if (member.getPhoneNumber().equals(phoneNumberTextView.getText().toString())){
                return member;
            }
        }

        return null;
    }

    //TODO:
    public void goToHistoryParcelsActivity(View view){
        try{
            Intent intent= new Intent(MainActivity.this,HistoryParcels.class);
            startActivity(intent);
        }
        catch (Exception ex){
            for(int i=0;i<3;i++)
                Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    //Converters

    private Member dataSnapShotToMember(DataSnapshot dataSnapshot){
        if (dataSnapshot.child("firstName").getValue(String.class) == null){
            throw new IllegalArgumentException();
        }

        return new Member(
                dataSnapshot.child("id").getValue(String.class),
                dataSnapshot.child("firstName").getValue(String.class),
                dataSnapshot.child("lastName").getValue(String.class),
                dataSnapshot.child("phoneNumber").getValue(String.class),
                dataSnapshot.child("address").getValue(String.class),
                dataSnapshot.child("emailAddress").getValue(String.class)
        );
    }

    private Parcel dataSnapShotToParcel(DataSnapshot dataSnapshot){
        if (dataSnapshot.child("isFragile").getChildrenCount() > 0 && dataSnapshot.child("isFragile").getValue(String.class) == null){
            throw new IllegalArgumentException();
        }

        return new Parcel(
                Parcel.Status.valueOf(dataSnapshot.child("status").getValue(String.class)),
                dataSnapshot.child("type").getValue(String.class),
                dataSnapshot.child("fragile").getValue(Boolean.class),
                dataSnapshot.child("weight").getValue(String.class),
                dataSnapshot.child("distributionCenterAddress").getValue(String.class),
                dataSnapshot.child("recipientAddress").getValue(String.class),
                dataSnapshot.child("recipientFirstName").getValue(String.class),
                dataSnapshot.child("recipientLastName").getValue(String.class),
                dataSnapshot.child("recipientPhoneNumber").getValue(String.class),
                dataSnapshot.child("recipientEmailAddress").getValue(String.class),
                dataSnapshot.child("receivedDate").getValue(String.class),
                dataSnapshot.child("deliveryDate").getValue(String.class),
                dataSnapshot.child("shippedDate").getValue(String.class),
                dataSnapshot.child("deliveryGuyName").getValue(String.class),
                dataSnapshot.child("id").getValue(String.class)
        );
    }


    //Actions

    public void addParcel(View view){

        if (checkFields() == false){
            addButton.setEnabled(false);

            return;
        }

        Member member = getMemberByPhoneNumber(phoneNumberTextView.getText().toString());

        Parcel parcel = new Parcel(
                Parcel.Status.Registered,
                typeSpiner.getSelectedItem().toString(),
                isFragileCheckBox.isChecked(),
                weightSpinner.getSelectedItem().toString(),
                locationTextView.getText().toString(),
                member.getAddress(),
                member.getFirstName(),
                member.getLastName(),
                member.getPhoneNumber(),
                member.getEmailAddress(),
                deliveryDateTextView.getText().toString(),
                "NOT YET DELIVERED",
                "NOT YET SHIPPED",
                "NOT YET TAKEN BY DELIVERY GUY",
                getParcelId()
                );

        //Get Parcels
        myRef = database.getReference("Parcel");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                parcels.add(dataSnapShotToParcel(dataSnapshot));
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

        myRef.child(String.valueOf(parcelsCount)).setValue(parcel);

        //Increase parcel counter
        DatabaseReference dbref = database.getReference("Config").child("ParcelsCount");
        parcelsCount += 1;

        dbref.setValue(parcelsCount);
    }

    private void addMember(String id, String firstName, String lastName, String phoneNumber, String address, String emailAdress){
        Member member = new Member(id, firstName, lastName, phoneNumber, address, emailAdress);

        myRef.child(id).setValue(member);
    }

    public void findMember(View view){

        Member member = getMemberByPhoneNumber(phoneNumberTextView.getText().toString());

        if (member != null){
            targetNameTextView.setText(member.getFirstName() + " " + member.getLastName());
        }
        else{
            targetNameTextView.setText("");
            Toast.makeText(MainActivity.this, "Member not found!!!", Toast.LENGTH_LONG).show();
        }
    }


    //Validations

    public boolean checkFields(){
            if (checkLocation() && checkWeight() && checkPersonalInfo() && checkType()){
            addButton.setEnabled(true);
            return true;
            }
            else{
            addButton.setEnabled(false);
            return false;
            }
            }

    public void checkInfo(View view){
            checkFields();
            }

    public boolean checkWeight(){
            return weightSpinner.getSelectedItem().toString() != "Select Weight";
            }

    public boolean checkType(){
            return typeSpiner.getSelectedItem().toString() != "Select Type";
            }
    //phone + name
    public boolean checkPersonalInfo(){
            Member member = getMemberByPhoneNumber(phoneNumberTextView.getText().toString());
            if (member == null){
            return false;
            }

            return (member.getFirstName() + " " + member.getLastName()).equals(targetNameTextView.getText().toString());
            }

    public boolean checkLocation(){
            return locationTextView.getText().toString() != "" && checkInternetConnection();
            }

    public boolean checkInternetConnection(){
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
            }
            else{
            return false;
            }
            }

}




