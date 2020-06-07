package com.example.foodchoise.main_fragments;

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
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainFragment extends Fragment {
    BriefRecipeCardAdapter topTastyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_main,container,false);

        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                .limit(5);
        topTastyAdapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(),query);
        RecyclerView recyclerView = view.findViewById(R.id.topTasty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(topTastyAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        topTastyAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        topTastyAdapter.stopListening();
    }
}
