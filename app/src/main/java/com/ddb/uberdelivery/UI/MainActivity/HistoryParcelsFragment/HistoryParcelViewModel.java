package com.ddb.uberdelivery.UI.MainActivity.HistoryParcelsFragment;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ddb.uberdelivery.R;

public class HistoryParcelViewModel extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String [] names={"Maoz","Natanzon","Shelomhi","Tal","Yossi"};
        setContentView(R.layout.activity_history_parcel);
        listView=findViewById(R.id.ParcelsListview);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(adapter);
    }

}
