package com.example.foodchoise.main_fragments;

import com.example.foodchoise.CreateRecipesActivity;
import com.example.foodchoise.entity_classes.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foodchoise.R;

public class ReciepsFragment extends Fragment {

    BriefRecipeCardAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recieps_page,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final Activity activity = getActivity();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.VISIBLE);
        adapter = new BriefRecipeCardAdapter(activity)
        ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateRecipesActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Activity activity = getActivity();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.GONE);
    }
}
