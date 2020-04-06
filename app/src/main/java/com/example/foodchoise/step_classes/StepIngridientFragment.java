package com.example.foodchoise.step_classes;

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

import timber.log.Timber;

public class StepIngridientFragment extends Fragment {
    final StepInstrIngridientAdapter adapter = new StepInstrIngridientAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_ingridient_recipes_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.ingrid_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Button button = view.findViewById(R.id.add_ingr_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("Add ingridient.");
                adapter.addItem();
            }
        });
        return view;
    }

}
