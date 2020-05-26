package com.example.foodchoise.main_fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.example.foodchoise.step_classes.create_recipe.CreateRecipesActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import leakcanary.AppWatcher;

public class ReciepsFragment extends Fragment {
    public static final String BRIEFCARD_DATA = "BRIEFCARD_DATA";
    BriefRecipeCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.page_recieps,container,false);

        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES);
        adapter = AdapterBuilder.getAdapter(getActivity(),query);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final Activity activity = getActivity();
        AppWatcher.INSTANCE.getObjectWatcher().watch(adapter,"adapter was detached");
        ImageButton button = view.findViewById(R.id.add_recipe_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateRecipesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
