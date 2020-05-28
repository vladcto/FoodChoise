package com.example.foodchoise.main_fragments;

import android.os.AsyncTask;
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
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

public class FavoritesFragment extends Fragment {
    RecyclerView recyclerView;
    BriefRecipeCardAdapter adapter;
    AsyncTask task;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_favorites,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        task = FirestoreHelper.getInstance().getFavoritesRecipesRefernce();
        List<String> ids = new ArrayList<>();
        //TODO: Реализовать патерн слушатель , что бы экран не зависал.
        task.execute();
        try {
           ids =(List<String>) task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                .whereIn(FieldPath.documentId(),ids);
        Timber.e("id: %s", ids.toString());
        adapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(),query);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        task.cancel(true);
        super.onDestroy();
    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }
}
