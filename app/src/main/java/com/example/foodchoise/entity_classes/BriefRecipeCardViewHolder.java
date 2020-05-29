package com.example.foodchoise.entity_classes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;
import com.example.foodchoise.step_classes.display_recipe.DisplayRecipeActivity;
import com.willy.ratingbar.ScaleRatingBar;

import java.lang.ref.WeakReference;

class BriefRecipeCardViewHolder extends RecyclerView.ViewHolder {
    static public String RECIPECARD_DATA = "RECIPECARD_DATA";
    ImageView dishesImage;
    TextView dishesName;
    ScaleRatingBar tastyRatingBar, priceRatingBar;
    WeakReference<Activity> activityWeakReference;

    public BriefRecipeCardViewHolder(@NonNull View itemView, WeakReference<Activity> activity) {
        super(itemView);
        dishesImage = itemView.findViewById(R.id.dishes_image);
        dishesName = itemView.findViewById(R.id.dishes_name);
        tastyRatingBar = itemView.findViewById(R.id.tastyRatingBar);
        priceRatingBar = itemView.findViewById(R.id.priceRatingBar);
        activityWeakReference = activity;
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
                Intent intent = new Intent(activityWeakReference.get(), DisplayRecipeActivity.class);
                intent.putExtra(RECIPECARD_DATA, recipeCard);
                activityWeakReference.get().startActivity(intent);
            }
        });

        StorageFirebaseHelper storageFirebaseHelper = StorageFirebaseHelper.getInstance();
        dishesImage.setImageDrawable(null);
        storageFirebaseHelper.downloadPhotoInImageView(StorageFirebaseHelper.RECIPES_MAIN_PHOTO + "/" + recipeCard.getID() + "/main_photo",
                dishesImage);

    }

}
