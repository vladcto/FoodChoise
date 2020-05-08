package com.example.foodchoise.helperFirebase.database;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreHelper {
    //region Singleton
    private static FirestoreHelper firestoreHelper;

    private FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirestoreHelper getInstance() {
        if (firestoreHelper == null) {
            firestoreHelper = new FirestoreHelper();
        }
        return firestoreHelper;
    }
    //endregion

    FirebaseFirestore db;

    //region public_const
    public static final String COLLECTION_RECIPES = "recipes";
    public static final String TEST = "test";
    //endregion

    //Мда... это бы в Котлине передалать, код в разы понятней будет.
    public Task<DocumentReference> addRecipeCard(RecipeCard recipeCard){
        CollectionReference recipesCollection = db.collection(COLLECTION_RECIPES);

        Map<String, Object> recipeData = new HashMap<>();
        recipeData.put("complexity_rating",recipeCard.getDishesComplexityRating());
        recipeData.put("dishes_descr",recipeCard.getDishesDescription());
        recipeData.put("ingridients",recipeCard.getDishesIngridient());
        recipeData.put("instr",recipeCard.getDishesInstruction());
        recipeData.put("name",recipeCard.getDishesName());
        recipeData.put("tasty_rating",recipeCard.getDishesTastyRating());

        return recipesCollection.add(recipeData);
    }
}
