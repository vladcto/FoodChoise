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
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import java.lang.ref.WeakReference;

import static com.example.foodchoise.entity_classes.BriefRecipeCardAdapter.RECIPECARD_DATA;

public class CardStackViewAdapter extends FirestorePagingAdapter<RecipeCard, CardStackViewAdapter.MyViewHolder> {
    WeakReference<Activity> activityWeakReference;
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
     CardStackViewAdapter(@NonNull FirestorePagingOptions<RecipeCard> options, Activity activity) {
        super(options);
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull RecipeCard model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_choose,parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView dishesImage;
        TextView dishesName, dishesDescr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dishesImage = itemView.findViewById(R.id.dishesImage);
            dishesName = itemView.findViewById(R.id.dishesName);
            dishesDescr = itemView.findViewById(R.id.dishesDescr);
        }

        void bind(final RecipeCard recipeCard){
            dishesName.setText(recipeCard.getDishesName());
            dishesDescr.setText(recipeCard.getDishesDescription());

            dishesImage.setImageBitmap(null);
            StorageFirebaseHelper storageFirebaseHelper = StorageFirebaseHelper.getInstance();
            storageFirebaseHelper.downloadPhotoInImageView(StorageFirebaseHelper.RECIPES_MAIN_PHOTO + "/" + recipeCard.getID() + "/main_photo",
                    dishesImage);
            dishesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activityWeakReference.get(), DisplayRecipeActivity.class);
                    intent.putExtra(RECIPECARD_DATA, recipeCard);
                    activityWeakReference.get().startActivity(intent);
                }
            });
        }
    }
}
