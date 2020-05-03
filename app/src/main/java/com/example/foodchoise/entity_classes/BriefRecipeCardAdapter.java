package com.example.foodchoise.entity_classes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.step_classes.display_recipe.DisplayRecipeActivity;

import java.util.ArrayList;

import timber.log.Timber;

public class BriefRecipeCardAdapter extends RecyclerView.Adapter<BriefRecipeCardAdapter.BriefRecipeCardViewHolder> {
    static public String RECIPECARD_DATA = "RECIPECARD_DATA";
    private ArrayList<BriefRecipeCard> recipeCards = new ArrayList<BriefRecipeCard>();
    private Activity  activity;

    public BriefRecipeCardAdapter( Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public BriefRecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_main, parent, false);
        return new  BriefRecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BriefRecipeCardViewHolder holder, int position) {
        holder.bind(recipeCards.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeCards.size();
    }

    public void addRecipeCard(BriefRecipeCard card){
        recipeCards.add(card);
        Timber.i("BriefRecipeCard добавлена в Adapter");
        notifyDataSetChanged();
    }

    class BriefRecipeCardViewHolder extends RecyclerView.ViewHolder{
        ImageView dishesImage;
        TextView dishesName;
        TextView dishesTastyRating;
        TextView dishesComplexityRating;

        public BriefRecipeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            dishesImage = itemView.findViewById(R.id.dishes_image);
            dishesName = itemView.findViewById(R.id.dishes_name);
            dishesTastyRating = itemView.findViewById(R.id.dishes_tasty_rating);
            dishesComplexityRating = itemView.findViewById(R.id.dishes_complexity_rating);
        }

        public void bind(final BriefRecipeCard recipeCard){
            dishesImage.setImageURI(recipeCard.getUriDishesImage());
            dishesName.setText(recipeCard.getDishesName());
            dishesTastyRating.setText(String.valueOf(recipeCard.getDishesTastyRating()));
            dishesComplexityRating.setText(String.valueOf(recipeCard.getDishesComplexityRating()));
            //TODO: убрать этот BriefRecipeCard, если так будет не рабоатть.
            dishesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, DisplayRecipeActivity.class);
                    intent.putExtra(RECIPECARD_DATA,(RecipeCard)recipeCard);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
