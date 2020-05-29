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
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;
import com.example.foodchoise.step_classes.display_recipe.DisplayRecipeActivity;
import com.willy.ratingbar.ScaleRatingBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class OldBriefRecipeCardAdapter extends RecyclerView.Adapter<OldBriefRecipeCardAdapter.BriefRecipeCardViewHolder> {
    static public String RECIPECARD_DATA = "RECIPECARD_DATA";
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
        return new BriefRecipeCardViewHolder(view);
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

    public class BriefRecipeCardViewHolder extends RecyclerView.ViewHolder {
        ImageView dishesImage;
        TextView dishesName;
        ScaleRatingBar tastyRatingBar, priceRatingBar;

        public BriefRecipeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            dishesImage = itemView.findViewById(R.id.dishes_image);
            dishesName = itemView.findViewById(R.id.dishes_name);
            tastyRatingBar = itemView.findViewById(R.id.tastyRatingBar);
            priceRatingBar = itemView.findViewById(R.id.priceRatingBar);
        }

        public void bind(final RecipeCard recipeCard) {
            dishesName.setText(recipeCard.getDishesName());
            float priceRating = (float) (recipeCard.getPriceRating() / recipeCard.getUsersComplete() + 0.01);
            float tastyRating = (float) (recipeCard.getDishesTastyRating() / recipeCard.getUsersComplete() + 0.01);
            if (Float.isNaN(priceRating)) {
                priceRating = 0;
            }
            if (Float.isNaN(tastyRating)) {
                tastyRating = 0;
            }
            priceRatingBar.setRating(priceRating);
            tastyRatingBar.setRating(tastyRating);
            dishesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.get(), DisplayRecipeActivity.class);
                    intent.putExtra(RECIPECARD_DATA, recipeCard);
                    activity.get().startActivity(intent);
                }
            });

            StorageFirebaseHelper storageFirebaseHelper = StorageFirebaseHelper.getInstance();
            dishesImage.setImageDrawable(null);
            storageFirebaseHelper.downloadPhotoInImageView(StorageFirebaseHelper.RECIPES_MAIN_PHOTO + "/" + recipeCard.getID() + "/main_photo",
                    dishesImage);

        }

    }
}
