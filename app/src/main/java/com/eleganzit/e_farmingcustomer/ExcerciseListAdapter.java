package com.eleganzit.e_farmingcustomer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.databinding.ChooseExerciseItemBinding;
import com.eleganzit.e_farmingcustomer.databinding.SelectedExerciseItemBinding;

/**
 * Created by Sumeet on 16-07-2017.
 */

public class ExcerciseListAdapter extends RecyclerView.Adapter<ExcerciseListAdapter.ProjectHolder> {

    private ObservableList<ExcercisePojo> exerciseObservableList = new ObservableArrayList<>();
    private Context context;
    private RecyclerView recyclerExercise;
    private int layoutId;

    public ExcerciseListAdapter(RecyclerView recyclerExercise, ObservableArrayList<ExcercisePojo> exerciseObservableList, int layoutId) {
        this.exerciseObservableList = exerciseObservableList;
        this.recyclerExercise = recyclerExercise;
        this.layoutId = layoutId;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        context = parent.getContext();
        return new ProjectHolder(v);
    }

    @Override
    public void onBindViewHolder(ProjectHolder holder, int position) {
        final ExcercisePojo excercisePojo = exerciseObservableList.get(position);
        if (layoutId == R.layout.layout_choose_exercise_item) {
            ChooseExerciseItemBinding chooseExerciseItemBinding = (ChooseExerciseItemBinding) holder.chooseExerciseItemBinding;
            chooseExerciseItemBinding.setExercise(excercisePojo);
            chooseExerciseItemBinding.setChooseExerciseListAdapter(this);
        } else {
            SelectedExerciseItemBinding selectedExerciseItemBinding = (SelectedExerciseItemBinding) holder.chooseExerciseItemBinding;
            selectedExerciseItemBinding.setExercise(excercisePojo);
            selectedExerciseItemBinding.setChooseExerciseListAdapter(this);
        }
        holder.chooseExerciseItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (exerciseObservableList == null) {
            return 0;
        }
        return exerciseObservableList.size();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding chooseExerciseItemBinding;

        public ProjectHolder(View itemView) {
            super(itemView);
            chooseExerciseItemBinding = DataBindingUtil.bind(itemView);
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
        Toast.makeText(context, "cccccccccccc", Toast.LENGTH_SHORT).show();
    }

}
