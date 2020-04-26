package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

public class DisplayIngrFragment extends Fragment {
    final DisplayIngrAdapter adapter;
    DisplayRecipeActivity activity;

    public DisplayIngrFragment(DisplayRecipeActivity activity) {
        this.activity = activity;
        adapter = new DisplayIngrAdapter(activity.getRecipeCard().getDishesIngridient());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_step_ingridient,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.ingrid_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
