package com.ddb.uberdelivery.UI.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
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
       private Button button;
       private  TextView textView;
    List<com.ddb.uberdelivery.Entities.Parcel> parcelList=new ArrayList<>();
    List<Member> members= new ArrayList<>();
    //region deleteAfter

    private void  fakeDetailes(){
        String [] warehouseAddresses={"Htichon 11 Hifa","Moshe Solomon 3 Jerusalem","Hgamal 24 Beersheva"};
        members.add(new Member("051111113","Yair","git hub 3 Jerusalem"));
        members.add(new Member("0525525223","Tal","kuzar 30 Jerusalem"));
        members.add(new Member("0525525623","Tomer","None 13 Ariel"));

    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.phonePlainText);
        tv.setText("");
        fakeDetailes();
        button=(Button)(findViewById(R.id.addButton));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                try
                {
                    ///todo:  ADD  THE Logical PART (PARCEL VALIDATION)
                    Parcel parcel=new Parcel();
                    parcelList.add(parcel);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");



    }
}
