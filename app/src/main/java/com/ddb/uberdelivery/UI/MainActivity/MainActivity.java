package com.ddb.uberdelivery.UI.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            initializeViews();

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
                    DatabaseReference myRef=database.getReference("msg");
                    Task<Void> task=myRef.setValue(PhoneNumbertv.getText().toString());
                    System.out.println(task.isSuccessful());

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
          historyButton.setOnClickListener(new View.OnClickListener() {
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
          try{
          reciveDateButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  calendar = Calendar.getInstance();
                  mDay = calendar.get(Calendar.DAY_OF_MONTH);
                  mMonth = calendar.get(Calendar.MONTH);
                  mYear = calendar.get(Calendar.YEAR);

                  DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                          new DatePickerDialog.OnDateSetListener() {

                              @Override
                              public void onDateSet(DatePicker view, int year,
                                                    int monthOfYear, int dayOfMonth) {

                                  dliveryDateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                              }
                          }, mYear, mMonth, mDay);
                  datePickerDialog.show();
              }

          });
        }
        catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }





    private void initializeViews() {
        typeSpiner = findViewById(R.id.packageTypeSpinner2);
        typeSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Parcel.Type.values()));
        wightSpiner = findViewById(R.id.packageWeightSpinner);
        wightSpiner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weightspinerValues));
        addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);
        historyButton=findViewById(R.id.historyButton);
        locationTextView = findViewById(R.id.packageLocationTextView);
        PhoneNumbertv = findViewById(R.id.phonePlainText);
        PhoneNumbertv.setText(null);
        reciveDateButton = findViewById(R.id.datePickerbutton);
        dliveryDateTextView = findViewById(R.id.deliveryDateTextView);

    }
}




