package com.example.foodchoise.main_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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


public class ReciepsFragment extends Fragment {
    public static final String BRIEFCARD_DATA = "BRIEFCARD_DATA";
    BriefRecipeCardAdapter adapter;
    RecyclerView recyclerView;
    CardView filtersGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_recieps, container, false);

        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES);
        adapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(), query);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final Activity activity = getActivity();
        ImageButton button = view.findViewById(R.id.add_recipe_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateRecipesActivity.class);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        filtersGroup = view.findViewById(R.id.filters);
        filtersGroup.setVisibility(View.GONE);
        final Button useButton = filtersGroup.findViewById(R.id.useFilter);
        useButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useFilter();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void useFilter() {
        RadioGroup filtersNameGroup = filtersGroup.findViewById(R.id.filters_name);
        int filtersName = filtersNameGroup.getCheckedRadioButtonId();
        RadioGroup filtersTypeGroup = filtersGroup.findViewById(R.id.filters_types);
        int filtersType = filtersTypeGroup.getCheckedRadioButtonId();

        String filterField;
        switch (filtersName) {
            case R.id.radioPopulary:
                filterField = "users_complete";
                break;
            case R.id.radioComplexity:
                filterField = "average_complexity_rating";
                break;
            case R.id.radioTasty:
                filterField = "average_tasty_rating";
                break;
            case R.id.radioPrice:
                filterField = "average_price_rating";
                break;
            default:
                filterField = "";
                break;
        }


        Query query;
        if (filtersType == R.id.radioDecrease) {
            query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                    .orderBy(filterField, Query.Direction.DESCENDING);
        } else {
            query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                    .orderBy(filterField, Query.Direction.ASCENDING);
        }
        BriefRecipeCardAdapter adapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(), query);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_filter, menu);
        MenuItem menuItem = menu.findItem(R.id.filter_button);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (filtersGroup.getVisibility() == View.VISIBLE) {
                    filtersGroup.setVisibility(View.GONE);
                    item.setIcon(R.drawable.filter);
                } else {
                    filtersGroup.setVisibility(View.VISIBLE);
                    item.setIcon(R.drawable.return_up);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
