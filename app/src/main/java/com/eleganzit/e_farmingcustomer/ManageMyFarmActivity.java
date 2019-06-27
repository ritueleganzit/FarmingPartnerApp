package com.eleganzit.e_farmingcustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.e_farmingcustomer.adapters.AvailablePlotsAdapter;
import com.eleganzit.e_farmingcustomer.adapters.ManageFarmAdapter;
import com.eleganzit.e_farmingcustomer.adapters.ManageMyFarmAdapter;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;

import java.util.ArrayList;

public class ManageMyFarmActivity extends AppCompatActivity {


    RecyclerView rc_farms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_farm);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_farms=findViewById(R.id.rc_farms);
        ManageFarmData manageFarmData=new ManageFarmData();

        ArrayList<ManageFarmData> arrayList=new ArrayList<>();

        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);

        rc_farms.setAdapter(new ManageMyFarmAdapter(arrayList,this));

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
