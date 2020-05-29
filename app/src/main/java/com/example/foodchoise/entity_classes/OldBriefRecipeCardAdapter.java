package com.example.foodchoise.entity_classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class OldBriefRecipeCardAdapter extends RecyclerView.Adapter<BriefRecipeCardViewHolder> {
    private List<RecipeCard> recipeCards = new ArrayList<RecipeCard>();
    private WeakReference<Activity> activity;

    public OldBriefRecipeCardAdapter(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @NonNull
    @Override
    public BriefRecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_main, parent, false);
        return new BriefRecipeCardViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull BriefRecipeCardViewHolder holder, int position) {
        holder.bind(recipeCards.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeCards.size();
    }

    public void addRecipesCard(List<RecipeCard> recipeCards) {
        this.recipeCards.addAll(recipeCards);
        notifyDataSetChanged();
    }


}

