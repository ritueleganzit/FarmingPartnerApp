package com.eleganzit.farmingpartner.adapter;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.activity.NotificationsActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.FarmingpartnerNotification;
import com.eleganzit.farmingpartner.model.ForgotPasswordResponse;
import com.eleganzit.farmingpartner.model.NotificationData;
import com.eleganzit.farmingpartner.model.UpdateNotificationStatus;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    String status,status_name,progress_name;
    ArrayList<NotificationData> arr;
    Context context;
    ProgressDialog progressDialog;

    public NotificationAdapter(ArrayList<NotificationData> arr, Context context) {
        this.arr = arr;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_layout,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final NotificationData notificationsData=arr.get(i);

        Log.d("dsss",""+notificationsData.getVeg_cal_status_id());
        Log.d("sdsd",""+notificationsData.getFarm_name());
        Log.d("sdsd",""+notificationsData.getCustomer_id());
            myViewHolder.sapling_date.setText("" + notificationsData.getDate());
            myViewHolder.txt_status.setText("" + notificationsData.getStatusData());
            myViewHolder.txt_title.setText(notificationsData.getName()+" of " + notificationsData.getVeg_name() + " in " + notificationsData.getPlot_name());

        Glide
                .with(context)
                .load(notificationsData.getFarm_photo())
                .apply(new RequestOptions().transforms(new CircleCrop())).into(myViewHolder.img_farm);

            myViewHolder.cardviewdashboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.change_status_dialog);

                    RadioGroup rg_status=dialog.findViewById(R.id.rg_status);
                    RadioButton rb_complete=dialog.findViewById(R.id.rb_complete);
                    RadioButton rb_pending=dialog.findViewById(R.id.rb_pending);
                    TextView btn_cancel=dialog.findViewById(R.id.btn_cancel);
                    TextView btn_ok=dialog.findViewById(R.id.btn_ok);


                    rg_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId==R.id.rb_complete)
                            {
                                status="Complete";
                            }
                            else
                            {
                                status="Pending";
                            }
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
if (notificationsData.getName().equalsIgnoreCase("Sapling"))
{
    status_name="sapling_date_status";
    progress_name="sapling_date";
   // Toast.makeText(context, ""+notificationsData.getCustomer_id(), Toast.LENGTH_SHORT).show();
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Deweeding1"))
{
    status_name="deweeding1_status";
    progress_name="deweeding1";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Deweeding2"))
{
    status_name="deweeding2_status";
    progress_name="deweeding2";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Deweeding3"))
{
    status_name="deweeding3_status";
    progress_name="deweeding3";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Fertilizing1"))
{
    status_name="fertilizing1_status";
    progress_name="fertilizing1";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Fertilizing1"))
{
    status_name="fertilizing1_status";
    progress_name="fertilizing1";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Fertilizing2"))
{
    status_name="fertilizing2_status";
    progress_name="fertilizing2";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Fertilizing3"))
{
    status_name="fertilizing3_status";
    progress_name="fertilizing3";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}if (notificationsData.getName().equalsIgnoreCase("Harvesting"))
{
    status_name="harvesting_status";
    progress_name="harvesting";
    updateNotificationStatus(dialog,notificationsData.getVeg_cal_status_id(),notificationsData.getCustomer_id(),notificationsData.getFarm_name(),notificationsData.getVeg_name(),notificationsData.getPlot_name(),status_name,progress_name,status);

}

                        }
                    });

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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

                    dialog.show();
                  //  Toast.makeText(context, ""+notificationsData.getFarm_photo(), Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(context, ""+notificationsData.getVeg_name(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard;
        ImageView img_farm;
        TextView txt_title,txt_status,sapling_date;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            img_farm=itemView.findViewById(R.id.img_farm);
            txt_title=itemView.findViewById(R.id.txt_title);
            sapling_date=itemView.findViewById(R.id.sapling_date);
            txt_status=itemView.findViewById(R.id.txt_status);

        }
    }


    public void updateNotificationStatus(final Dialog dialog, String veg_cal_status_id, String customer_id, String farm_name, String veg_name, String plot_name, String status_name, String progress_name, String status)
    {
        progressDialog.show();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateNotificationStatus> call=myInterface.updateNotificationStatus(veg_cal_status_id,customer_id,farm_name,veg_name,plot_name,status_name,progress_name,status);
        call.enqueue(new Callback<UpdateNotificationStatus>() {
            @Override
            public void onResponse(Call<UpdateNotificationStatus> call, Response<UpdateNotificationStatus> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, HomeActivity.class));

                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateNotificationStatus> call, Throwable t) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });



    }
}
