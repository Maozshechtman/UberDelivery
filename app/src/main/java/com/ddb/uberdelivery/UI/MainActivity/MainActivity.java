package com.ddb.uberdelivery.UI.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ddb.uberdelivery.Entities.Parcel;
import com.ddb.uberdelivery.UI.MainActivity.HistoryParcelsFragment.HistoryParcelViewModel;
import com.ddb.uberdelivery.R;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {
    private String[] weightspinerValues = {"0.5", "1", "5", "20"};
    private Button addButton;
    private Spinner wightSpiner;
    private Spinner typeSpiner;
    private TextView textView;
    private TextView locationTextView;
    private TextView PhoneNumbertv;
    private  Button historyButton;
    private Calendar calendar;
    private Button reciveDateButton;
    private TextView dliveryDateTextView;
    private int mYear, mMonth, mDay;

    //GPS
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        /*
        //region Firebase Setup
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("message");

        myRef.setValue("Hello, World!");
        //endregion
        */

        //region Location Setup
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());

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

        //region ButtonListeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                try {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("msg");
                    Task<Void> task = myRef.setValue(PhoneNumbertv.getText().toString());
                    System.out.println(task.isSuccessful());

                    ///todo:  ADD  THE Logical PART (PARCEL VALIDATION)

                    builder.setTitle("success");
                    builder.setMessage("The parcel add to the FireBase");
                }
                catch (Exception ex) {
                    builder.setMessage(ex.getMessage());
                }
                finally {
                    builder.create();
                    builder.show();
                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, HistoryParcelViewModel.class);
                    startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(MainActivity.this, EX.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });
        //endregion

    }

    private void initializeViews() {
        typeSpiner = findViewById(R.id.packageTypeSpinner2);
        typeSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Types));
        wightSpiner = findViewById(R.id.packageWeightSpinner);
        wightSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Weights));
        addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);
        historyButton=findViewById(R.id.historyButton);
        locationTextView = findViewById(R.id.packageLocationTextView);
        PhoneNumbertv = findViewById(R.id.phonePlainText);
        PhoneNumbertv.setText(null);
        dliveryDateTextView = findViewById(R.id.deliveryDateTextView);
    }
}




