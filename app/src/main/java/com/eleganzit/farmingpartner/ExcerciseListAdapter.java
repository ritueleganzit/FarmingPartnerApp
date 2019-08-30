package com.eleganzit.farmingpartner;

import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.databinding.ChooseExerciseItemBinding;
import com.eleganzit.farmingpartner.databinding.SelectedExerciseItemBinding;
import com.eleganzit.farmingpartner.model.GetAllVegetables;

/**
 * Created by Sumeet on 16-07-2017.
 */
public class ExcerciseListAdapter extends RecyclerView.Adapter<ExcerciseListAdapter.MyViewHolder> {
    private ObservableList<GetAllVegetables> exerciseObservableList;
    private Context context;
    private RecyclerView recyclerExercise;
    private int layoutId;
    private int TYPE_MAIN = 0;
    private int TYPE_LAST = 1;
    public int pos=0;
    public boolean isClickable = false;

    public ExcerciseListAdapter(RecyclerView recyclerExercise, ObservableArrayList<GetAllVegetables> exerciseObservableList, int layoutId) {
        this.exerciseObservableList = exerciseObservableList;
        this.recyclerExercise = recyclerExercise;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        if (recyclerExercise.getId() == R.id.rcv_selected_exercise) {
            if (layoutId==R.layout.layout_selected_exercise_item && position == exerciseObservableList.size()) {
                return TYPE_LAST;
            } else {
                return TYPE_MAIN;
            }
        }/*if (recyclerExercise.getId()==R.id.rcv_choose_exercise) {
            Log.d("sdddddd", "chooseselected"+recyclerExercise.getId());
        }*/
        return TYPE_MAIN;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (recyclerExercise.getId() == R.id.rcv_selected_exercise) {
            if (viewType == TYPE_MAIN) {
                View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                context = parent.getContext();
                return new ProjectHolder(v);
            }
            if (viewType == TYPE_LAST) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_row_layout, parent, false);
                context = parent.getContext();
                return new LastViewHolder(v);
            }
        }
        if (recyclerExercise.getId() == R.id.rcv_choose_exercise) {
        }
        if (recyclerExercise.getId() == R.id.rcv_choose_exercise) {
        }
        if (viewType == TYPE_MAIN) {
            View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            context = parent.getContext();
            return new ProjectHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder instanceof ProjectHolder) {
            pos=position;
            final GetAllVegetables GetAllVegetables = exerciseObservableList.get(position);
            if (layoutId == R.layout.layout_choose_exercise_item) {
                if (exerciseObservableList.size() > 0) {
                    ChooseExerciseItemBinding chooseExerciseItemBinding = (ChooseExerciseItemBinding) ((ProjectHolder) holder).chooseExerciseItemBinding;
                    chooseExerciseItemBinding.setExercise(GetAllVegetables);
                    chooseExerciseItemBinding.setChooseExerciseListAdapter(this);

                    Glide
                            .with(context)
                            .asBitmap()
                            .apply(new RequestOptions().transform(new RoundedCorners(8)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(GetAllVegetables.getVegImage())
                            .thumbnail(.1f)
                            .into(chooseExerciseItemBinding.img);

                    /*chooseExerciseItemBinding.cardd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Dialog dialog = new Dialog(context);
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

                            if(GetAllVegetables.getSaplingDate()!=null)
                            {
                                if(GetAllVegetables.getSaplingDate().isEmpty() || GetAllVegetables.getSaplingDate()==null)
                                {
                                    txt_sapling_date.setText("Not Provided");
                                }
                                else
                                {
                                    txt_sapling_date.setText(GetAllVegetables.getSaplingDate()+"");
                                }
                            }
                            else
                            {
                                txt_sapling_date.setText("Not Provided");
                            }

                            if(GetAllVegetables.getDeweeding1()!=null) {
                                if (GetAllVegetables.getDeweeding1().isEmpty() || GetAllVegetables.getDeweeding1() == null) {
                                    txt_deweeding1.setText("Not Provided");
                                } else {
                                    txt_deweeding1.setText(GetAllVegetables.getDeweeding1() + "");
                                }
                            }
                            else
                            {
                                txt_deweeding1.setText("Not Provided");
                            }
                            if(GetAllVegetables.getDeweeding2()!=null) {
                                if (GetAllVegetables.getDeweeding2().isEmpty() || GetAllVegetables.getDeweeding2() == null) {
                                    txt_deweeding2.setText("Not Provided");
                                } else {
                                    txt_deweeding2.setText(GetAllVegetables.getDeweeding2() + "");
                                }
                            }
                            else
                            {
                                txt_deweeding2.setText("Not Provided");
                            }
                            if(GetAllVegetables.getDeweeding3()!=null) {
                                if (GetAllVegetables.getDeweeding3().isEmpty() || GetAllVegetables.getDeweeding3() == null) {
                                    txt_deweeding3.setText("Not Provided");
                                } else {
                                    txt_deweeding3.setText(GetAllVegetables.getDeweeding3() + "");
                                }
                            }
                            else
                            {
                                txt_deweeding3.setText("Not Provided");
                            }
                            if(GetAllVegetables.getFertilizing1()!=null) {
                                if (GetAllVegetables.getFertilizing1().isEmpty() || GetAllVegetables.getFertilizing1() == null) {
                                    txt_fertilising1.setText("Not Provided");
                                } else {
                                    txt_fertilising1.setText(GetAllVegetables.getFertilizing1() + "");
                                }
                            }
                            else
                            {
                                txt_fertilising1.setText("Not Provided");
                            }
                            if(GetAllVegetables.getFertilizing2()!=null) {
                                if (GetAllVegetables.getFertilizing2().isEmpty() || GetAllVegetables.getFertilizing2() == null) {
                                    txt_fertilising2.setText("Not Provided");
                                } else {
                                    txt_fertilising2.setText(GetAllVegetables.getFertilizing2() + "");
                                }
                            }
                            else
                            {
                                txt_fertilising2.setText("Not Provided");
                            }
                            if(GetAllVegetables.getFertilizing3()!=null) {
                                if (GetAllVegetables.getFertilizing3().isEmpty() || GetAllVegetables.getFertilizing3() == null) {
                                    txt_fertilising3.setText("Not Provided");
                                } else {
                                    txt_fertilising3.setText(GetAllVegetables.getFertilizing3() + "");
                                }
                            }
                            else
                            {
                                txt_fertilising3.setText("Not Provided");
                            }
                            if(GetAllVegetables.getHarvesting()!=null) {
                                if (GetAllVegetables.getHarvesting().isEmpty() || GetAllVegetables.getHarvesting() == null) {
                                    txt_harvesting.setText("Not Provided");
                                } else {
                                    txt_harvesting.setText(GetAllVegetables.getHarvesting() + "");
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
                    });*/
                }
            } else if (layoutId == R.layout.layout_selected_exercise_item) {
                if (exerciseObservableList.size() > 0) {
                    SelectedExerciseItemBinding selectedExerciseItemBinding = (SelectedExerciseItemBinding) ((ProjectHolder) holder).chooseExerciseItemBinding;
                    selectedExerciseItemBinding.setExercise(GetAllVegetables);
                    selectedExerciseItemBinding.setChooseExerciseListAdapter(this);

                    Glide
                            .with(context)
                            .asBitmap()
                            .apply(new RequestOptions().transform(new RoundedCorners(8)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(GetAllVegetables.getVegImage())
                            .thumbnail(.1f)
                            .into(selectedExerciseItemBinding.img);
                }
            }
            ((ProjectHolder) holder).chooseExerciseItemBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        if (exerciseObservableList == null) {
            return 1;
        }
        if (recyclerExercise.getId() == R.id.rcv_selected_exercise) {
            return exerciseObservableList.size() + 1;
        }
        return exerciseObservableList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ProjectHolder extends MyViewHolder {
        public ViewDataBinding chooseExerciseItemBinding;

        public ProjectHolder(View itemView) {
            super(itemView);
            chooseExerciseItemBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class LastViewHolder extends MyViewHolder {
        public LastViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public boolean onLongClick(View view) {

        /*if(pos>6)
        {
            Toast.makeText(context, "can drag", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "cannot drag", Toast.LENGTH_SHORT).show();
        }*/
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        if (layoutId == R.layout.layout_choose_exercise_item) {
            MainActivity.isFromExercise = true;
        } else {
            MainActivity.isFromExercise = false;
        }
        return true;
    }

    public void onListItemClick(View view) {
/*
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.veg_details_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();*/
    }

    public GetAllVegetables getItem(int position) {
        return exerciseObservableList.get(position);
    }


}
