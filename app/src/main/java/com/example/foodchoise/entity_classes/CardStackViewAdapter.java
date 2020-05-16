package com.example.foodchoise.entity_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.LinkedList;
import java.util.List;

public class CardStackViewAdapter extends RecyclerView.Adapter<CardStackViewAdapter.MyViewHolder> {
    List<RecipeCard> recipeCards = new LinkedList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return recipeCards.size();
    }

    public void addRecipesCard(List<RecipeCard> recipeCards){
        this.recipeCards.addAll(recipeCards);
    }

    public void TEST_addRecipeCard(RecipeCard recipeCard){
        recipeCards.add(recipeCard);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(){

        }
    }
}
