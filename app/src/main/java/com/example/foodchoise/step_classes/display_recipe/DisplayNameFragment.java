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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.CommentsAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DisplayNameFragment extends Fragment {
    private DisplayRecipeActivity activity;
    private CommentsAdapter adapter;

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //Сделать также запрос по времени создания.
        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                .document(activity.getRecipeCard().getID())
                .collection(FirestoreHelper.COLLECTION_USER_REVIEWS)
                .whereGreaterThan("comment", "");
        adapter = AdapterBuilder.getCommentsAdapter(query);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
    }
}
