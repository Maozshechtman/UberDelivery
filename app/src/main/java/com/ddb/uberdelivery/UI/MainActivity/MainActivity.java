package com.ddb.uberdelivery.UI.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.ddb.uberdelivery.Entities.Parcel;
import com.ddb.uberdelivery.Entities.Member;
import com.ddb.uberdelivery.R;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DatabaseReference; import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends Activity {
    private String []weightspinerValues={"0.5","1","5","20"};



       private Button addButton;
       private Spinner wightSpiner;
       private Spinner typeSpiner;
       private  TextView textView;
       private  TextView locationTextView;
       private TextView PhoneNumbertv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeSpiner= findViewById(R.id.packageTypeSpinner2);
        typeSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Type.values()));
        wightSpiner= findViewById(R.id.packageWeightSpinner);
        wightSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,weightspinerValues));
        addButton= findViewById(R.id.addButton);
        locationTextView= findViewById(R.id.packageLocationTextView);
        PhoneNumbertv = findViewById(R.id.phonePlainText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("Hello, World!");
                try
                {
                    ///todo:  ADD  THE Logical PART (PARCEL VALIDATION)


                  builder.setTitle("success");
                  builder.setMessage("The parcel add to the FireBase");
                }
                catch (Exception ex)
                {

                }
                finally {

                    builder.create();
                    builder.show();
                }

            }
        });




    }
}
