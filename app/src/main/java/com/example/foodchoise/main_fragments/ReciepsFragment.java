package com.example.foodchoise.main_fragments;

import com.example.foodchoise.entity_classes.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        Activity activity = getActivity();
        adapter = new BriefRecipeCardAdapter();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addRecipeCard(new BriefRecipeCard(1,"1"));
            }
        });

        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Activity activity = getActivity();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.GONE);
    }
}
