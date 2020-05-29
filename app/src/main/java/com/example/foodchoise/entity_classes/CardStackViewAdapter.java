package com.example.foodchoise.entity_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class CardStackViewAdapter extends FirestorePagingAdapter<RecipeCard, CardStackViewAdapter.MyViewHolder> {
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    CardStackViewAdapter(@NonNull FirestorePagingOptions<RecipeCard> options) {
        super(options);
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
        }
    }
}
