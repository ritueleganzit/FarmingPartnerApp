package com.eleganzit.e_farmingcustomer;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.e_farmingcustomer.databinding.ChooseExerciseItemBinding;
import com.eleganzit.e_farmingcustomer.databinding.SelectedExerciseItemBinding;

/**
 * Created by Sumeet on 16-07-2017.
 */
public class ExcerciseListAdapter extends RecyclerView.Adapter<ExcerciseListAdapter.MyViewHolder> {
    private ObservableList<ExcercisePojo> exerciseObservableList;
    private Context context;
    private RecyclerView recyclerExercise;
    private int layoutId;
    private int TYPE_MAIN = 0;
    private int TYPE_LAST = 1;

    public ExcerciseListAdapter(RecyclerView recyclerExercise, ObservableArrayList<ExcercisePojo> exerciseObservableList, int layoutId) {
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
            final ExcercisePojo excercisePojo = exerciseObservableList.get(position);
            if (layoutId == R.layout.layout_choose_exercise_item) {
                if (exerciseObservableList.size() > 0) {
                    ChooseExerciseItemBinding chooseExerciseItemBinding = (ChooseExerciseItemBinding) ((ProjectHolder) holder).chooseExerciseItemBinding;
                    chooseExerciseItemBinding.setExercise(excercisePojo);
                    chooseExerciseItemBinding.setChooseExerciseListAdapter(this);
                    Glide
                            .with(context)
                            .asBitmap()
                            .apply(new RequestOptions().transform(new RoundedCorners(8)).placeholder(R.drawable.pr).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(excercisePojo.img)
                            .thumbnail(.1f)
                            .into(chooseExerciseItemBinding.img);

                }
            } else if (layoutId == R.layout.layout_selected_exercise_item) {
                if (exerciseObservableList.size() > 0) {
                    SelectedExerciseItemBinding selectedExerciseItemBinding = (SelectedExerciseItemBinding) ((ProjectHolder) holder).chooseExerciseItemBinding;
                    selectedExerciseItemBinding.setExercise(excercisePojo);
                    selectedExerciseItemBinding.setChooseExerciseListAdapter(this);
                    Glide
                            .with(context)
                            .asBitmap()
                            .apply(new RequestOptions().transform(new RoundedCorners(8)).placeholder(R.drawable.pr).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(excercisePojo.img)
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
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.veg_details_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
    }
}
