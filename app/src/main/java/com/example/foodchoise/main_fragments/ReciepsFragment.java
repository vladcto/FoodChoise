package com.example.foodchoise.main_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.example.foodchoise.step_classes.create_recipe.CreateRecipesActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import leakcanary.AppWatcher;

public class ReciepsFragment extends Fragment {
    private static  final int REQUEST_ACCESS_TYPE=1;
    public static final String BRIEFCARD_DATA = "BRIEFCARD_DATA";
    MyTask myTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.page_recieps,container,false);
        BriefRecipeCardAdapter adapter = new BriefRecipeCardAdapter(getActivity());
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

        myTask = new MyTask(adapter);
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
            return firestoreHelper.getRecipesCardIn(FirestoreHelper.COLLECTION_RECIPES);
        }

        @Override
        protected void onPostExecute(List<RecipeCard> result) {
            //TODO: NPE , есди перейти на другой фрагмент
            //Костыль.
            if(mAdapter!= null) {
                mAdapter.get().addRecipesCard(result);
            }
            mAdapter = null;
        }
    }

    @Override
    public void onDestroyView() {
        myTask.cancel(true);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
