package com.eleganzit.farmingpartner;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eleganzit.farmingpartner.model.GetAllVegetables;

/**
 * Created by Sumeet on 16-07-2017.
 */

public class BindingUtils {

    @BindingAdapter({"bind:exerciseChooseItems", "bind:layoutId"})
    public static void bindExerciseList(RecyclerView view, ObservableArrayList<GetAllVegetables> list, int layoutId) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(layoutManager);
        view.setAdapter(new ExcerciseListAdapter(view, list, layoutId));
    }


    @BindingAdapter({"bind:exerciseHorizontalItems", "bind:layoutId"})
    public static void bindHorizontalList(RecyclerView view, ObservableArrayList<GetAllVegetables> list, int layoutId) {

        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 3);
        view.setLayoutManager(layoutManager);
        view.setAdapter(new ExcerciseListAdapter(view, list, layoutId));
    }

}
