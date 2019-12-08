package com.ddb.uberdelivery.UI.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ddb.uberdelivery.Entities.Parcel;
import com.ddb.uberdelivery.UI.MainActivity.HistoryParcelsFragment.HistoryParcelViewModel;
import com.ddb.uberdelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends Activity {
    private String[] weightspinerValues = {"0.5", "1", "5", "20"};
    private Button addButton;
    //private Button historyParcelButtons = findViewById(R.id.historyButton);
    private Spinner wightSpiner;
    private Spinner typeSpiner;
    private TextView textView;
    private TextView locationTextView;
    private TextView PhoneNumbertv;
    private  Button hidtoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeSpiner = findViewById(R.id.packageTypeSpinner2);
        typeSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Type.values()));
        wightSpiner = findViewById(R.id.packageWeightSpinner);
        wightSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weightspinerValues));
        addButton = findViewById(R.id.addButton);
        hidtoryButton=findViewById(R.id.historyButton);
        locationTextView = findViewById(R.id.packageLocationTextView);
        PhoneNumbertv = findViewById(R.id.phonePlainText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();


                try {

                    FirebaseAuth mAuth=FirebaseAuth.getInstance();


                    ///todo:  ADD  THE Logical PART (PARCEL VALIDATION)


                    builder.setTitle("success");
                    builder.setMessage("The parcel add to the FireBase");

                } catch (Exception ex) {
                    builder.setMessage(ex.getMessage());
                } finally {

                    builder.create();
                    builder.show();
                }

            }


        });
          hidtoryButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  try {
                      Intent intent = new Intent(MainActivity.this, HistoryParcelViewModel.class);
                      startActivity(intent);
                  }
                  catch (Exception EX)
                  {
                      Toast.makeText(MainActivity.this,EX.getMessage(),Toast.LENGTH_LONG).show();
                  }
              }
          });
    }
}




