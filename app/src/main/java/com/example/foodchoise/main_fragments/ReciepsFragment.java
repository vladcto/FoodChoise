package com.example.foodchoise.main_fragments;

import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.example.foodchoise.step_classes.create_recipe.CreateRecipesActivity;
import com.example.foodchoise.entity_classes.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ReciepsFragment extends Fragment {
    private static  final int REQUEST_ACCESS_TYPE=1;
    public static final String BRIEFCARD_DATA = "BRIEFCARD_DATA";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.page_recieps,container,false);
        final BriefRecipeCardAdapter adapter = new BriefRecipeCardAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final Activity activity = getActivity();
        ImageButton button = view.findViewById(R.id.add_recipe_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateRecipesActivity.class);
                startActivityForResult(intent,REQUEST_ACCESS_TYPE);
            }
        });

        new MyTask(adapter).execute();
        return view;
    }

    private static class MyTask extends AsyncTask<Void, Void, List<RecipeCard>> {

        private WeakReference<BriefRecipeCardAdapter> mAdapter;

        // only retain a weak reference to the activity
        MyTask(BriefRecipeCardAdapter adapter) {
            mAdapter = new WeakReference<>(adapter);
        }

        @Override
        protected List<RecipeCard> doInBackground(Void... params) {
            FirestoreHelper firestoreHelper = FirestoreHelper.getInstance();
            return firestoreHelper.getRecipesCard();
        }

        @Override
        protected void onPostExecute(List<RecipeCard> result) {
            mAdapter.get().addRecipesCard(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode==RESULT_OK){
                RecipeCard recipeCard = data.getParcelableExtra(BRIEFCARD_DATA);
                RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
                BriefRecipeCardAdapter adapter =(BriefRecipeCardAdapter) recyclerView.getAdapter();
                adapter.addRecipeCard(recipeCard);
            }
        }
    }


}
