package com.example.foodchoise.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProfileFragment extends Fragment {

    BriefRecipeCardAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_profile,container,false);
        Button button = view.findViewById(R.id.signOutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                try {
                    getActivity().finish();
                }catch (NullPointerException ignored){}
            }
        });

        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                .whereEqualTo("author", FirebaseAuth.getInstance().getCurrentUser().getUid());
        adapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(), query);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
