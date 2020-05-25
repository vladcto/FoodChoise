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
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;

import java.lang.ref.WeakReference;
import java.util.List;

import leakcanary.AppWatcher;

public class FavoritesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_favorites,container,false);

        BriefRecipeCardAdapter adapter = new BriefRecipeCardAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyTask myTask = new MyTask(adapter);
        AppWatcher.INSTANCE.getObjectWatcher().watch(adapter,"MyTask was detached");
        myTask.execute();

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
            return firestoreHelper.getFavoritesRecipesCard();
        }

        @Override
        protected void onPostExecute(List<RecipeCard> result) {
            //Костыль.
            try{
                mAdapter.get().addRecipesCard(result);
                mAdapter = null;
            }catch (NullPointerException ignored){}
        }
    }
}