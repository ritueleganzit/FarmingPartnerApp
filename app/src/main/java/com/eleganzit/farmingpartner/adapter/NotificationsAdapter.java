package com.eleganzit.farmingpartner.adapter;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.model.FarmingpartnerNotification;
import com.eleganzit.farmingpartner.model.NotificationData;
import com.eleganzit.farmingpartner.model.NotificationsData;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    ArrayList<FarmingpartnerNotification> arrayList;
    ArrayList<NotificationData> arr;
    Context context;

    public NotificationsAdapter(ArrayList<FarmingpartnerNotification> arrayList, Context context) {
        this.arrayList = arrayList;

        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notificationlayout, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        arr = new ArrayList<>();
        FarmingpartnerNotification notificationsData = arrayList.get(i);
       // Log.d("Dataafdgaa", i + " " + notificationsData.getStatusData().get(0).getSaplingDate());
        Log.d("hhhhhhh", i + " "+notificationsData.getVegCalStatusId());


        if (!notificationsData.getStatusData().get(0).getSaplingDate().equalsIgnoreCase("0000-00-00"))
        { NotificationData notificationData = new NotificationData("" + notificationsData.getStatusData().get(0).getSaplingDate(), notificationsData.getStatusData().get(0).getSaplingDateStatus(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData.setName("Sapling");
            notificationData.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData.setFarm_name(notificationsData.getFarmName());
            notificationData.setFarm_photo(notificationsData.getFarm_photo());

            arr.add(notificationData);

        }
        if (!notificationsData.getStatusData().get(0).getDeweeding1().equalsIgnoreCase("0000-00-00"))
        {
            NotificationData notificationData2 = new NotificationData("" + notificationsData.getStatusData().get(0).getDeweeding1(), notificationsData.getStatusData().get(0).getDeweeding1Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData2.setName("Deweeding1");
            notificationData2.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData2.setFarm_name(notificationsData.getFarmName());
            notificationData2.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData2);

        }
        if (!notificationsData.getStatusData().get(0).getDeweeding2().equalsIgnoreCase("0000-00-00"))
        {
            NotificationData notificationData3 = new NotificationData("" + notificationsData.getStatusData().get(0).getDeweeding2(), notificationsData.getStatusData().get(0).getDeweeding2Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData3.setName("Deweeding2");
            notificationData3.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData3.setFarm_name(notificationsData.getFarmName());
            notificationData3.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData3);
        }
        if (!notificationsData.getStatusData().get(0).getDeweeding3().equalsIgnoreCase("0000-00-00"))
        {
            NotificationData notificationData4 = new NotificationData("" + notificationsData.getStatusData().get(0).getDeweeding3(), notificationsData.getStatusData().get(0).getDeweeding3Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData4.setName("Deweeding3");
            notificationData4.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData4.setFarm_name(notificationsData.getFarmName());
            notificationData4.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData4);
        }
        if (!notificationsData.getStatusData().get(0).getFertilizing1().equalsIgnoreCase("0000-00-00"))
        {

            NotificationData notificationData5 = new NotificationData("" + notificationsData.getStatusData().get(0).getFertilizing1(), notificationsData.getStatusData().get(0).getFertilizing1Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData5.setName("Fertilizing1");
            notificationData5.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData5.setFarm_name(notificationsData.getFarmName());
            notificationData5.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData5);



        }
        if (!notificationsData.getStatusData().get(0).getFertilizing2().equalsIgnoreCase("0000-00-00"))
        {   NotificationData notificationData8 = new NotificationData("" + notificationsData.getStatusData().get(0).getFertilizing2(), notificationsData.getStatusData().get(0).getFertilizing2Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData8.setName("Fertilizing2");
            notificationData8.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData8.setFarm_name(notificationsData.getFarmName());
            notificationData8.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData8);

        } if (!notificationsData.getStatusData().get(0).getFertilizing3().equalsIgnoreCase("0000-00-00"))
        {
            NotificationData notificationData6 = new NotificationData("" + notificationsData.getStatusData().get(0).getFertilizing3(), notificationsData.getStatusData().get(0).getFertilizing3Status(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData6.setName("Fertilizing3");
            notificationData6.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData6.setFarm_name(notificationsData.getFarmName());
            notificationData6.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData6);
        }
        if (!notificationsData.getStatusData().get(0).getHarvesting().equalsIgnoreCase("0000-00-00"))
        {

            NotificationData notificationData7 = new NotificationData("" + notificationsData.getStatusData().get(0).getHarvesting(), notificationsData.getStatusData().get(0).getHarvestingStatus(), notificationsData.getVegetableId(), "" + notificationsData.getPlotName(), "" + notificationsData.getVegName(), "" + notificationsData.getCustomer_id());
            notificationData7.setName("Harvesting");
            notificationData7.setVeg_cal_status_id(notificationsData.getVegCalStatusId());
            notificationData7.setFarm_name(notificationsData.getFarmName());
            notificationData7.setFarm_photo(notificationsData.getFarm_photo());
            arr.add(notificationData7);
        }



















        
          /*  if (notificationsData.getStatusData().get(0).getDeweeding1().equalsIgnoreCase("0000-00-00")) {


            } else {



            }
            if (notificationsData.getStatusData().get(0).getDeweeding2().equalsIgnoreCase("0000-00-00")) {


            } else {

               


            }*/

           /* if (notificationsData.getStatusData().get(0).getDeweeding3().equalsIgnoreCase("0000-00-00")) {


            } else {

                

            }if (notificationsData.getStatusData().get(0).getFertilizing1().equalsIgnoreCase("0000-00-00")) {


            } else {




            }
            if (notificationsData.getStatusData().get(0).getFertilizing2().equalsIgnoreCase("0000-00-00")) {


            } else {

              


            }if (notificationsData.getStatusData().get(0).getFertilizing3().equalsIgnoreCase("0000-00-00")) {


            } else {



            }if (notificationsData.getStatusData().get(0).getHarvesting().equalsIgnoreCase("0000-00-00")) {


            } else {

             

            }*/


        //  Log.d("Dataafdgaa",i+" "+notificationsData.getStatusData().get(0).getSaplingDate());

        myViewHolder.rc_notifications.setAdapter(new NotificationAdapter(arr, context));

       /* if (notificationsData.getStatusData()!=null) {
            for (int j=0;j<notificationsData.getStatusData().size();i++)
            {
                if (notificationsData.getStatusData().get(j).getSaplingDate().equalsIgnoreCase("0000-00-00")) {

                } else {
                    myViewHolder.sapling_date.setText("" + notificationsData.getStatusData().get(j).getSaplingDate());
                    myViewHolder.txt_status.setText("" + notificationsData.getStatusData().get(j).getSaplingDateStatus());
                    myViewHolder.txt_title.setText("Sapling of " + notificationsData.getVegName() + " in " + notificationsData.getVegName());

                }
            }

        }
*/

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        RecyclerView rc_notifications;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);


            rc_notifications = itemView.findViewById(R.id.rc_notifications);


        }
    }

}
