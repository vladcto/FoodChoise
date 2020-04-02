package com.example.foodchoise.step_classes;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.MainActivity;
import com.example.foodchoise.entity_classes.BriefRecipeCard;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.example.foodchoise.R;

import static android.app.Activity.RESULT_OK;

public class StepAcceptFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_accept_recipes_fragment, container, false);
        Button button = view.findViewById(R.id.accept_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                BriefRecipeCard recipeCard = new BriefRecipeCard(1,"1");
                data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
                getActivity().setResult(RESULT_OK, data);
                getActivity().finish();
            }
        });
        return view;
    }

}
