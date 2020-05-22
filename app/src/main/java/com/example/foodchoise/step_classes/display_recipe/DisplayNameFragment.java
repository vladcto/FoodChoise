package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;

public class DisplayNameFragment extends Fragment {
    DisplayRecipeActivity activity;

    public DisplayNameFragment(DisplayRecipeActivity activity) {
        this.activity = activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_name_recipe, container, false);
        RecipeCard recipeCard = activity.getRecipeCard();

        ImageView imageView = view.findViewById(R.id.recipe_image);
        StorageFirebaseHelper storageFirebaseHelper  = StorageFirebaseHelper.getInstance();
        storageFirebaseHelper.downloadPhotoInImageView(StorageFirebaseHelper.RECIPES_MAIN_PHOTO+"/"+recipeCard.getID()+"/main_photo",
                imageView);

        TextView textView = view.findViewById(R.id.textDescr);
        textView.setText(recipeCard.getDishesDescription());

        return view;
    }
}
