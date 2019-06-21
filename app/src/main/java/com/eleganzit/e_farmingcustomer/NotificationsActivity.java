package com.eleganzit.e_farmingcustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.e_farmingcustomer.adapters.ManageMyFarmAdapter;
import com.eleganzit.e_farmingcustomer.adapters.NotificationsAdapter;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;
import com.eleganzit.e_farmingcustomer.model.NotificationsData;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView rc_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_notifications=findViewById(R.id.rc_notifications);

        NotificationsData notificationsData=new NotificationsData("","","");

        ArrayList<NotificationsData> arrayList=new ArrayList<>();

        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);
        arrayList.add(notificationsData);

        rc_notifications.setAdapter(new NotificationsAdapter(arrayList,this));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
