package com.eleganzit.e_farmingcustomer;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewFarmActivity extends AppCompatActivity {

    Button btn_sell,btn_walk,btn_set;
    TextView txt_slot_number,txt_vegetable,txt_sapling_date,txt_deweeding1,txt_deweeding2,txt_deweeding3,txt_fertilising1,txt_fertilising2,txt_fertilising3,txt_harvesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farm);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_sell=findViewById(R.id.btn_sell);
        btn_walk=findViewById(R.id.btn_walk);
        btn_set=findViewById(R.id.btn_set);
        txt_slot_number=findViewById(R.id.txt_slot_number);
        txt_vegetable=findViewById(R.id.txt_vegetable);
        txt_sapling_date=findViewById(R.id.txt_sapling_date);
        txt_deweeding1=findViewById(R.id.txt_deweeding1);
        txt_deweeding2=findViewById(R.id.txt_deweeding2);
        txt_deweeding3=findViewById(R.id.txt_deweeding3);
        txt_fertilising1=findViewById(R.id.txt_fertilising1);
        txt_fertilising2=findViewById(R.id.txt_fertilising2);
        txt_fertilising3=findViewById(R.id.txt_fertilising3);
        txt_harvesting=findViewById(R.id.txt_harvesting);


        final Dialog dialog=new Dialog(ViewFarmActivity.this);
        dialog.setContentView(R.layout.update_soon_dialog);

        Button btn_ok=dialog.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        btn_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
