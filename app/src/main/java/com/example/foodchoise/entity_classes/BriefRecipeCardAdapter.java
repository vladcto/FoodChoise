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
import java.util.ArrayList;

import timber.log.Timber;

public class BriefRecipeCardAdapter extends FirestorePagingAdapter<RecipeCard, BriefRecipeCardAdapter.BriefRecipeCardViewHolder> {
    static public String RECIPECARD_DATA = "RECIPECARD_DATA";
    private ArrayList<RecipeCard> recipeCards = new ArrayList<RecipeCard>();
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
        return new BriefRecipeCardViewHolder(view);
    }


    public class BriefRecipeCardViewHolder extends RecyclerView.ViewHolder {
        ImageView dishesImage;
        TextView dishesName;

        public BriefRecipeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            dishesImage = itemView.findViewById(R.id.dishes_image);
            dishesName = itemView.findViewById(R.id.dishes_name);
        }

        public void bind(final RecipeCard recipeCard) {
            dishesName.setText(recipeCard.getDishesName());
            //TODO: убрать этот BriefRecipeCard, если так будет не рабоатть.
            dishesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timber.d("Сработал OnClickListener");
                    Intent intent = new Intent(activity.get(), DisplayRecipeActivity.class);
                    intent.putExtra(RECIPECARD_DATA, recipeCard);
                    activity.get().startActivity(intent);
                }
            });


            //TODO: Подумать над логикой этой части.

            StorageFirebaseHelper storageFirebaseHelper = StorageFirebaseHelper.getInstance();
            dishesImage.setImageDrawable(null);
            storageFirebaseHelper.downloadPhotoInImageView(StorageFirebaseHelper.RECIPES_MAIN_PHOTO + "/" + recipeCard.getID() + "/main_photo",
                    dishesImage);
        }

    }
}
