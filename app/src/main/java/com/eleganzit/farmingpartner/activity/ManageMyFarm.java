package com.eleganzit.farmingpartner.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.GetVegDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMyFarm extends AppCompatActivity {
    ImageView img_farm;
    ProgressDialog progressDialog;

    String vegetable_id,farm_id;
    TextView veg_name,sapling_date,deweeding1,deweeding2,deweeding3,fertilizing1,fertilizing2,fertilizing3,harvesting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_farm);
        img_farm=findViewById(R.id.img_farm);
        veg_name=findViewById(R.id.veg_name);
        sapling_date=findViewById(R.id.sapling_date);
        deweeding1=findViewById(R.id.deweeding1);
        deweeding2=findViewById(R.id.deweeding2);
        deweeding3=findViewById(R.id.deweeding3);
        fertilizing1=findViewById(R.id.fertilizing1);
        progressDialog = new ProgressDialog(ManageMyFarm.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        fertilizing2=findViewById(R.id.fertilizing2);
        fertilizing3=findViewById(R.id.fertilizing3);
        harvesting=findViewById(R.id.harvesting);
        vegetable_id=getIntent().getStringExtra("vegetable_id");
        farm_id=getIntent().getStringExtra("farm_id");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getVegetables();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getVegetables()
    {

        Log.d("ffff",""+vegetable_id);
        Log.d("ffff",""+farm_id);
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetVegDetailResponse> call=myInterface.getVegDetail(vegetable_id,farm_id);
        call.enqueue(new Callback<GetVegDetailResponse>() {
            @Override
            public void onResponse(Call<GetVegDetailResponse> call, Response<GetVegDetailResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                    if (response.body().getData()!=null)
                    {        String upperString = response.body().getData().get(0).getVegName().substring(0,1).toUpperCase() + response.body().getData().get(0).getVegName().substring(1);

                        veg_name.setText(""+upperString);


                        sapling_date.setText(""+response.body().getData().get(0).getSaplingDate());
                        deweeding1.setText(""+response.body().getData().get(0).getDeweeding1());
                        deweeding2.setText(""+response.body().getData().get(0).getDeweeding2());
                        deweeding3.setText(""+response.body().getData().get(0).getFertilizing3());
                        fertilizing1.setText(""+response.body().getData().get(0).getFertilizing1());
                        fertilizing2.setText(""+response.body().getData().get(0).getFertilizing2());
                        fertilizing3.setText(""+response.body().getData().get(0).getFertilizing3());
                        harvesting.setText(""+response.body().getData().get(0).getHarvesting());

                        Glide
                                .with(ManageMyFarm.this)
                                .load(""+response.body().getData().get(0).getVegImage())
                                .into(img_farm);


                    }
                                     }
                }

            }

            @Override
            public void onFailure(Call<GetVegDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ManageMyFarm.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
