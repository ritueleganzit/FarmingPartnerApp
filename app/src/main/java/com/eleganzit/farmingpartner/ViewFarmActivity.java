package com.eleganzit.farmingpartner;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.AvailablePlotsData;
import com.eleganzit.farmingpartner.model.SlotDetailsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFarmActivity extends AppCompatActivity {


    TextView txt_vegetable,txt_sapling_date,txt_deweeding1,txt_deweeding2,txt_deweeding3,txt_fertilising1,txt_fertilising2,txt_fertilising3,txt_harvesting;
    String vegetable_id,farm_id,slotimage,veg_name;
    ProgressDialog progressDialog;
    ImageView slotimageimg;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        vegetable_id=getIntent().getStringExtra("vegetable_id");
        farm_id=getIntent().getStringExtra("farm_id");
        slotimage=getIntent().getStringExtra("slotimage");
        veg_name=getIntent().getStringExtra("veg_name");


        slotimageimg=findViewById(R.id.slotimageimg);
        txt_vegetable=findViewById(R.id.txt_vegetable);
        txt_sapling_date=findViewById(R.id.txt_sapling_date);
        txt_deweeding1=findViewById(R.id.txt_deweeding1);
        txt_deweeding2=findViewById(R.id.txt_deweeding2);
        txt_deweeding3=findViewById(R.id.txt_deweeding3);
        txt_fertilising1=findViewById(R.id.txt_fertilising1);
        txt_fertilising2=findViewById(R.id.txt_fertilising2);
        txt_fertilising3=findViewById(R.id.txt_fertilising3);
        txt_harvesting=findViewById(R.id.txt_harvesting);



        if(slotimage!=null)
        {
            if(slotimage.isEmpty())
            {
            }
            else
            {
                Glide.with(ViewFarmActivity.this).load(slotimage).into(slotimageimg);

            }
        }
        else
        {
        }

        if(veg_name!=null)
        {
            if(veg_name.isEmpty())
            {
                txt_vegetable.setText("Not Provided");
            }
            else
            {
                String upperString = veg_name.substring(0,1).toUpperCase() + veg_name.substring(1);
                txt_vegetable.setText(upperString);
            }
        }
        else
        {
            txt_vegetable.setText("Not Provided");
        }

        vegetablesDetails();

    }

    private void vegetablesDetails() {
        progressDialog.show();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SlotDetailsResponse> call=myInterface.vegetablesDetails(farm_id,vegetable_id);
        call.enqueue(new Callback<SlotDetailsResponse>() {
            @Override
            public void onResponse(Call<SlotDetailsResponse> call, Response<SlotDetailsResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    ArrayList<AvailablePlotsData> arrayList=new ArrayList<>();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {

                        if(response.body().getData().get(0).getSaplingDate()!=null)
                        {
                            if(response.body().getData().get(0).getSaplingDate().isEmpty() || response.body().getData().get(0).getSaplingDate().equalsIgnoreCase("0000-00-00"))
                            {
                                txt_sapling_date.setText("Not Provided");
                            }
                            else
                            {
                                txt_sapling_date.setText(response.body().getData().get(0).getSaplingDate()+"");
                            }
                        }
                        else
                        {
                            txt_sapling_date.setText("Not Provided");
                        }

                        if(response.body().getData().get(0).getDeweeding1()!=null) {
                            if (response.body().getData().get(0).getDeweeding1().isEmpty() || response.body().getData().get(0).getDeweeding1().equalsIgnoreCase("0000-00-00")) {
                                txt_deweeding1.setText("Not Provided");
                            } else {
                                txt_deweeding1.setText(response.body().getData().get(0).getDeweeding1() + "");
                            }
                        }
                        else
                        {
                            txt_deweeding1.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getDeweeding2()!=null) {
                            if (response.body().getData().get(0).getDeweeding2().isEmpty() || response.body().getData().get(0).getDeweeding2().equalsIgnoreCase("0000-00-00")) {
                                txt_deweeding2.setText("Not Provided");
                            } else {
                                txt_deweeding2.setText(response.body().getData().get(0).getDeweeding2() + "");
                            }
                        }
                        else
                        {
                            txt_deweeding2.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getDeweeding3()!=null) {
                            if (response.body().getData().get(0).getDeweeding3().isEmpty() || response.body().getData().get(0).getDeweeding3().equalsIgnoreCase("0000-00-00")) {
                                txt_deweeding3.setText("Not Provided");
                            } else {
                                txt_deweeding3.setText(response.body().getData().get(0).getDeweeding3() + "");
                            }
                        }
                        else
                        {
                            txt_deweeding3.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getFertilizing1()!=null) {
                            if (response.body().getData().get(0).getFertilizing1().isEmpty() || response.body().getData().get(0).getFertilizing1().equalsIgnoreCase("0000-00-00")) {
                                txt_fertilising1.setText("Not Provided");
                            } else {
                                txt_fertilising1.setText(response.body().getData().get(0).getFertilizing1() + "");
                            }
                        }
                        else
                        {
                            txt_fertilising1.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getFertilizing2()!=null) {
                            if (response.body().getData().get(0).getFertilizing2().isEmpty() || response.body().getData().get(0).getFertilizing2().equalsIgnoreCase("0000-00-00")) {
                                txt_fertilising2.setText("Not Provided");
                            } else {
                                txt_fertilising2.setText(response.body().getData().get(0).getFertilizing2() + "");
                            }
                        }
                        else
                        {
                            txt_fertilising2.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getFertilizing3()!=null) {
                            if (response.body().getData().get(0).getFertilizing3().isEmpty() || response.body().getData().get(0).getFertilizing3().equalsIgnoreCase("0000-00-00")) {
                                txt_fertilising3.setText("Not Provided");
                            } else {
                                txt_fertilising3.setText(response.body().getData().get(0).getFertilizing3() + "");
                            }
                        }
                        else
                        {
                            txt_fertilising3.setText("Not Provided");
                        }
                        if(response.body().getData().get(0).getHarvesting()!=null) {
                            if (response.body().getData().get(0).getHarvesting().isEmpty() || response.body().getData().get(0).getHarvesting().equalsIgnoreCase("0000-00-00")) {
                                txt_harvesting.setText("Not Provided");
                            } else {
                                txt_harvesting.setText(response.body().getData().get(0).getHarvesting() + "");
                            }
                        }
                        else
                        {
                            txt_harvesting.setText("Not Provided");
                        }

                    }
                    else
                    {
                    }
                }
                else
                {

                    Toast.makeText(ViewFarmActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SlotDetailsResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(ViewFarmActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
