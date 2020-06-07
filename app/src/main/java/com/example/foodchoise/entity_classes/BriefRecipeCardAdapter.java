package com.example.foodchoise.entity_classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.foodchoise.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import java.lang.ref.WeakReference;

public class BriefRecipeCardAdapter extends FirestorePagingAdapter<RecipeCard, BriefRecipeCardViewHolder> {
    //TODO: Перенести это в отдельный класс.
    static public String RECIPECARD_DATA = "RECIPECARD_DATA";
    private WeakReference<Activity> activity;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public BriefRecipeCardAdapter(@NonNull FirestorePagingOptions<RecipeCard> options,Activity activity) {
        super(options);
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onBindViewHolder(@NonNull BriefRecipeCardViewHolder holder, int position, @NonNull RecipeCard model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public BriefRecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_main, parent, false);
        return new BriefRecipeCardViewHolder(view, activity);
    }
}

