package com.example.foodchoise.entity_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.ArrayList;

public class BriefRecipeCardAdapter extends RecyclerView.Adapter<BriefRecipeCardAdapter.BriefRecipeCardViewHolder> {
    private ArrayList<BriefRecipeCard> recipeCards = new ArrayList<BriefRecipeCard>();

    @NonNull
    @Override
    public BriefRecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_main, parent, false);
        return new  BriefRecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BriefRecipeCardViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return recipeCards.size();
    }

    public void addRecipeCard(BriefRecipeCard card){
        recipeCards.add(card);
        notifyDataSetChanged();
    }

    class BriefRecipeCardViewHolder extends RecyclerView.ViewHolder{

        public BriefRecipeCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(){

        }
    }
}
