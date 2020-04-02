package com.example.foodchoise.main_fragments;

import com.example.foodchoise.CreateRecipesActivity;
import com.example.foodchoise.entity_classes.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foodchoise.R;

import static android.app.Activity.RESULT_OK;

public class ReciepsFragment extends Fragment {
    private static  final int REQUEST_ACCESS_TYPE=1;
    public static final String BRIEFCARD_DATA = "BRIEFCARD_DATA";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.recieps_page,container,false);
        BriefRecipeCardAdapter adapter = new BriefRecipeCardAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Activity activity = getActivity();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.VISIBLE);
        ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateRecipesActivity.class);
                startActivityForResult(intent,REQUEST_ACCESS_TYPE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Activity activity = getActivity();
        ImageButton button =(ImageButton)activity.findViewById(R.id.add_recipe_button);
        button.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i("result","Ввход в result");
        if(requestCode==REQUEST_ACCESS_TYPE){
            Log.i("result","Ввход в result2");
            if(resultCode==RESULT_OK){
                Log.i("result","Ввход в result3");
                BriefRecipeCard recipeCard = data.getParcelableExtra(BRIEFCARD_DATA);
                Log.i("result",recipeCard.getDishesName());
                RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
                Log.i("result",recyclerView.toString());
                BriefRecipeCardAdapter adapter =(BriefRecipeCardAdapter) recyclerView.getAdapter();
                Log.i("result",adapter.toString());
                adapter.addRecipeCard(recipeCard);
            }
        }
    }


}