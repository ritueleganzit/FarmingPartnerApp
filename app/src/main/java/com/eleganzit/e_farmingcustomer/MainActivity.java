package com.eleganzit.e_farmingcustomer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.adapters.AvailablePlotsAdapter;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.databinding.MainActivityBinding;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsResponse;
import com.eleganzit.e_farmingcustomer.model.ForgotPasswordResponse;
import com.eleganzit.e_farmingcustomer.model.SubmitPlotResponse;
import com.eleganzit.e_farmingcustomer.model.UpdateResponse;
import com.eleganzit.e_farmingcustomer.model.VegetablesResponse;
import com.eleganzit.e_farmingcustomer.utils.ClickListener;
import com.eleganzit.e_farmingcustomer.utils.RecyclerTouchListener;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnDragListener {

    private MainActivityBinding mainActivityBinding;
    public ObservableArrayList<ExcercisePojo> exerciseList;
    public ObservableArrayList<ExcercisePojo> exerciseSelectedList = new ObservableArrayList<>();
    public ExcercisePojo exerciseToMove;
    private int newContactPosition = -1;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    private int currentPosition = -1;
    private boolean isExerciseAdded = false;
    public static boolean isFromExercise = false;
    private String farm_id, farm_name, farm_desc,farm_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //loadExerciseData();

        userSessionManager=new UserSessionManager(this);

        farm_id=getIntent().getStringExtra("farm_id");
        farm_name=getIntent().getStringExtra("farm_name");
        farm_desc=getIntent().getStringExtra("farm_desc");
        farm_address=getIntent().getStringExtra("farm_address");

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        getVegetables();

        mainActivityBinding.setMainActivity(this);
        mainActivityBinding.rcvSelectedExercise.setOnDragListener(this);

        mainActivityBinding.rcvChooseExercise.setOnDragListener(new MyDragInsideRcvListener());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.scale_3dp);
        mainActivityBinding.rcvSelectedExercise.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mainActivityBinding.rcvChooseExercise.addOnItemTouchListener(new RecyclerTouchListener(this, mainActivityBinding.rcvChooseExercise, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ExcercisePojo excercisePojo=((ExcerciseListAdapter)mainActivityBinding.rcvChooseExercise.getAdapter()).getItem(position);
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.veg_details_dialog);

                TextView txt_sapling_date,txt_deweeding1,txt_deweeding2,txt_deweeding3,txt_fertilising1,txt_fertilising2,txt_fertilising3,txt_harvesting;

                txt_sapling_date=dialog.findViewById(R.id.txt_sapling_date);
                txt_deweeding1=dialog.findViewById(R.id.txt_deweeding1);
                txt_deweeding2=dialog.findViewById(R.id.txt_deweeding2);
                txt_deweeding3=dialog.findViewById(R.id.txt_deweeding3);
                txt_fertilising1=dialog.findViewById(R.id.txt_fertilising1);
                txt_fertilising2=dialog.findViewById(R.id.txt_fertilising2);
                txt_fertilising3=dialog.findViewById(R.id.txt_fertilising3);
                txt_harvesting=dialog.findViewById(R.id.txt_harvesting);

                if(excercisePojo.getSaplingDate()!=null)
                {
                    if(excercisePojo.getSaplingDate().isEmpty() || excercisePojo.getSaplingDate()==null)
                    {
                        txt_sapling_date.setText("Not Provided");
                    }
                    else
                    {
                        txt_sapling_date.setText(excercisePojo.getSaplingDate()+"");
                    }
                }
                else
                {
                    txt_sapling_date.setText("Not Provided");
                }

                if(excercisePojo.getDeweeding1()!=null) {
                    if (excercisePojo.getDeweeding1().isEmpty() || excercisePojo.getDeweeding1() == null) {
                        txt_deweeding1.setText("Not Provided");
                    } else {
                        txt_deweeding1.setText(excercisePojo.getDeweeding1() + "");
                    }
                }
                else
                {
                    txt_deweeding1.setText("Not Provided");
                }
                if(excercisePojo.getDeweeding2()!=null) {
                    if (excercisePojo.getDeweeding2().isEmpty() || excercisePojo.getDeweeding2() == null) {
                        txt_deweeding2.setText("Not Provided");
                    } else {
                        txt_deweeding2.setText(excercisePojo.getDeweeding2() + "");
                    }
                }
                else
                {
                    txt_deweeding2.setText("Not Provided");
                }
                if(excercisePojo.getDeweeding3()!=null) {
                    if (excercisePojo.getDeweeding3().isEmpty() || excercisePojo.getDeweeding3() == null) {
                        txt_deweeding3.setText("Not Provided");
                    } else {
                        txt_deweeding3.setText(excercisePojo.getDeweeding3() + "");
                    }
                }
                else
                {
                    txt_deweeding3.setText("Not Provided");
                }
                if(excercisePojo.getFertilizing1()!=null) {
                    if (excercisePojo.getFertilizing1().isEmpty() || excercisePojo.getFertilizing1() == null) {
                        txt_fertilising1.setText("Not Provided");
                    } else {
                        txt_fertilising1.setText(excercisePojo.getFertilizing1() + "");
                    }
                }
                else
                {
                    txt_fertilising1.setText("Not Provided");
                }
                if(excercisePojo.getFertilizing2()!=null) {
                    if (excercisePojo.getFertilizing2().isEmpty() || excercisePojo.getFertilizing2() == null) {
                        txt_fertilising2.setText("Not Provided");
                    } else {
                        txt_fertilising2.setText(excercisePojo.getFertilizing2() + "");
                    }
                }
                else
                {
                    txt_fertilising2.setText("Not Provided");
                }
                if(excercisePojo.getFertilizing3()!=null) {
                    if (excercisePojo.getFertilizing3().isEmpty() || excercisePojo.getFertilizing3() == null) {
                        txt_fertilising3.setText("Not Provided");
                    } else {
                        txt_fertilising3.setText(excercisePojo.getFertilizing3() + "");
                    }
                }
                else
                {
                    txt_fertilising3.setText("Not Provided");
                }
                if(excercisePojo.getHarvesting()!=null) {
                    if (excercisePojo.getHarvesting().isEmpty() || excercisePojo.getHarvesting() == null) {
                        txt_harvesting.setText("Not Provided");
                    } else {
                        txt_harvesting.setText(excercisePojo.getHarvesting() + "");
                    }
                }
                else
                {
                    txt_harvesting.setText("Not Provided");
                }
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);

                dialog.show();

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, ""+((ExcerciseListAdapter)mainActivityBinding.rcvChooseExercise.getAdapter()).getItem(position).name, Toast.LENGTH_SHORT).show();
            }
        }));

        mainActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mainActivityBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVegetables();
            }
        });

    }


    private void sendVegetables() {

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<exerciseSelectedList.size();i++)
        {
            Log.d("productsssssssss",exerciseSelectedList.get(i)+"");
            if (i==exerciseSelectedList.size()-1)
            {
                sb.append(exerciseSelectedList.get(i).getVegetableId()).append("");
            }
            else {
                sb.append(exerciseSelectedList.get(i).getVegetableId()).append(",");

            }
        }

        Log.d("uuuuuuuuuuu",sb.toString()+"  "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION)+"   "+farm_id+"    "+farm_name+"    "+farm_desc);

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SubmitPlotResponse> call = myInterface.sendVegetables(
                userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID),
                farm_id,
                farm_name,
                farm_desc,
                "",
                "",
                farm_address,
                "",
                "",
                "HDFC Bank loan",
                "500",
                sb.toString());

        call.enqueue(new Callback<SubmitPlotResponse>() {
            @Override
            public void onResponse(Call<SubmitPlotResponse> call, Response<SubmitPlotResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        Toast.makeText(MainActivity.this, "Submitted Successfully ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, NavHomeActivity.class).putExtra("from","select_veg");
                        // Closing all the Activities

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // Staring Login Activity
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();

                    } else {

                        Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(MainActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SubmitPlotResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getVegetables() {
        progressDialog.show();

        exerciseList = new ObservableArrayList<>();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Log.d("iddddsdas", farm_id+"");
        Call<VegetablesResponse> call=myInterface.vegetablesList(farm_id);
        call.enqueue(new Callback<VegetablesResponse>() {
            @Override
            public void onResponse(Call<VegetablesResponse> call, Response<VegetablesResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    ArrayList<AvailablePlotsData> arrayList=new ArrayList<>();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        if (response.body().getData()!=null)
                        {

                            for (int i=0;i<response.body().getAdminVegList().size();i++)
                            {
                                exerciseSelectedList.add(new ExcercisePojo(response.body().getAdminVegList().get(i).getVegetableId(), response.body().getAdminVegList().get(i).getVegName(), response.body().getAdminVegList().get(i).getVegImage(),response.body().getAdminVegList().get(i).getVegCatId(),response.body().getAdminVegList().get(i).getLocalLanguage()));
                                Log.d("certecccc","1  "+response.body().getAdminVegList().get(i).exerciseId+"   ");

                            }

                            for (int i=0;i<response.body().getData().size();i++)
                            {

                                exerciseList.add(new ExcercisePojo(response.body().getData().get(i).getVegetableId(), response.body().getData().get(i).getVegName(), response.body().getData().get(i).getVegImage(),response.body().getData().get(i).getVegCatId(),response.body().getData().get(i).getLocalLanguage()));
                                Log.d("certecccc","2  "+"   "+response.body().getData().get(i).exerciseId);

                            }

                            exerciseList.removeAll(exerciseSelectedList);

                            if(exerciseList.size()==0)
                            {
                                mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                mainActivityBinding.noVeg.setVisibility(View.GONE);
                            }

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                    }
                }
                else
                {

                    Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<VegetablesResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void loadExerciseData() {

        /*exerciseList.add(new ExcercisePojo(1, "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo(2, "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo(3, "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));
        exerciseList.add(new ExcercisePojo(4, "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo(5, "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));*/
       /* exerciseList.add(new ExcercisePojo(1, "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo(2, "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo(6, "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo(7, "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseList.add(new ExcercisePojo(8, "Vegetable " + 8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo(9, "Vegetable " + 9, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo(10, "Vegetable " + 10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));

        exerciseSelectedList.add(new ExcercisePojo(1, "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(2, "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(3, "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));
        exerciseSelectedList.add(new ExcercisePojo(4, "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseSelectedList.add(new ExcercisePojo(5, "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseSelectedList.add(new ExcercisePojo(6, "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(7, "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
*/

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View selectedView = (View) dragEvent.getLocalState();
        RecyclerView rcvSelected = (RecyclerView) view;
        int currentPosition=0;
        try {
            currentPosition = mainActivityBinding.rcvChooseExercise.getChildAdapterPosition(selectedView);

            // Ensure the position is valid.
            if (currentPosition != -1) {
                exerciseToMove = exerciseList.get(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:

                View onTopOf = rcvSelected.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
                newContactPosition = rcvSelected.getChildAdapterPosition(onTopOf);

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
/*
                Toast.makeText(this, "selected currentPosition : " + currentPosition, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "selected newContactPosition : " + newContactPosition, Toast.LENGTH_SHORT).show();
*/

                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:

                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DROP:
                //when Item is dropped off to recyclerview.

                /*if(currentPosition>6) {*/
                    if (isFromExercise) {
                        exerciseSelectedList.add(exerciseToMove);
                        exerciseList.remove(exerciseToMove);
                        if (exerciseList.size() == 0) {
                            mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                        } else {
                            mainActivityBinding.noVeg.setVisibility(View.GONE);
                        }
                        mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemRemoved(currentPosition);
                        mainActivityBinding.executePendingBindings();
                    }
                /*}
                else
                {
                    Toast.makeText(MainActivity.this, "These are required vegetables", Toast.LENGTH_SHORT).show();
                }*/
                //This is to hide/add the container!
                /*ViewGroup owner = (ViewGroup) (view.getParent());
                if (owner != null) {
                    //owner.removeView(selectedView);
                    //owner.addView(selectedView);

                    try {
                        LinearLayout rlContainer = (LinearLayout) view;
                        rlContainer.addView(selectedView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //selectedView.setVisibility(View.VISIBLE);
                }*/

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                // Reset the visibility for the Contact item's view.
                // This is done to reset the state in instances where the drag action didn't do anything.
                selectedView.setVisibility(View.VISIBLE);

                // Boundary condition, scroll to top is moving list item to position 0.
                if (newContactPosition != -1) {
                    rcvSelected.scrollToPosition(newContactPosition);
                    newContactPosition = -1;
                } else {
                    rcvSelected.scrollToPosition(0);
                }
                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
            default:
                break;
        }
        return true;
    }

    /**
     * This listener class is for Vertical Recyclerview.
     */
    class MyDragInsideRcvListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            RecyclerView rcv = (RecyclerView) v;
            int currentPosition=0;
            View selectedView = (View) event.getLocalState();
            try {
                currentPosition = rcv.getChildAdapterPosition(selectedView);
                // Ensure the position is valid.
                if (currentPosition != -1) {
                    exerciseToMove = exerciseSelectedList.get(currentPosition);
                    //exerciseSelectedList.get(currentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    View onTopOf = rcv.findChildViewUnder(event.getX(), event.getY());
                    newContactPosition = rcv.getChildAdapterPosition(onTopOf);

                    //Flag for our own understanding!
                    //isFromExercise = true;

                    //This is for internal dragging (inside recyclerview only).  VVIP!
                    // Ensure the new position is valid.

                    //If you wanted to swap the items in recyclerview only.
                    //It requires bit changes.
                   /* if (newContactPosition != -1) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rcv.getLayoutManager();
                        int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                        // Scroll up or down one if we are over the first or last visible list item.
                        if (newContactPosition >= lastVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition + 1, 0);
                        else if (newContactPosition <= firstVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition - 1, 0);

                        // Update the location of the Contact
                        exerciseList.remove(exerciseToMove);
                        exerciseList.add(newContactPosition, exerciseToMove);
                        rcv.getAdapter().notifyDataSetChanged();
                    }*/
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset the visibility for the Contact item's view.
                    // This is done to reset the state in instances where the drag action didn't do anything.

/*

                    Toast.makeText(MainActivity.this, "choose currentPosition : "+currentPosition, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "choose newContactPosition : "+newContactPosition, Toast.LENGTH_SHORT).show();
*/

                    selectedView.setVisibility(View.VISIBLE);
                    // Boundary condition, scroll to top is moving list item to position 0.
                    if (newContactPosition != -1) {
                        rcv.scrollToPosition(newContactPosition);
                        newContactPosition = -1;
                    } else {
                        rcv.scrollToPosition(0);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if(currentPosition>6)
                    {
                        //Toast.makeText(MainActivity.this, "can drag", Toast.LENGTH_SHORT).show();
                        if (!isFromExercise) {
                            //THIS IS FOR WHEN WE TAKE ITEM OF OTHER LIST AND DROP IN THIS LIST.
                            exerciseList.add(exerciseToMove);
                            exerciseSelectedList.remove(exerciseToMove);
                            mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemInserted(currentPosition);
                            mainActivityBinding.executePendingBindings();
                        }
                    }
                    else
                    {
                        if (!isFromExercise) {
                            Toast.makeText(MainActivity.this, "These are required vegetables", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;

                default:
                    break;
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

