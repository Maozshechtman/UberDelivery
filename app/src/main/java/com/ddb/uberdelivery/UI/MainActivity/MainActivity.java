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
    public enum parcelweight{

        UP_TO_FIVE_HUNDRED_GRAMS(0.5),
        UP_TO_ONE_KILOGRAM(1.0),
        UP_TO_FIVE_KILOGRAMS(5.0),
        UP_TO_TWENTY_KILOGRAMS(20.0);
        double value;

        @Override
        public String toString() {
            return value==0.5?"Up to 500 grams":value==1.0?"Up to 1 kilogram":value==5.0?"Up to 5 kilograms":"Up to 20 kilograms";
        }

        parcelweight(double value) {
            this.value=value;
        }
    }


    //region View Components
       private Button button;
       private Spinner wightSpiner;
       private Spinner typeSpiner;
       private  TextView textView;
       private  TextView locationTextView;
       //endregion
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
        //region initialize Comments
        typeSpiner=(Spinner)(findViewById(R.id.packageTypeSpinner2));
        typeSpiner.setAdapter(new ArrayAdapter<Parcel.Type>(this,android.R.layout.simple_list_item_1,Parcel.Type.values()));
        wightSpiner=(Spinner)(findViewById(R.id.packageWeightSpinner));
        wightSpiner.setAdapter(new ArrayAdapter<parcelweight>(this,android.R.layout.simple_list_item_1,parcelweight.values()));
        button=(Button)(findViewById(R.id.addButton));
        locationTextView=(TextView)(findViewById(R.id.packageLocationTextView));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

           //endregion
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




    }
}
