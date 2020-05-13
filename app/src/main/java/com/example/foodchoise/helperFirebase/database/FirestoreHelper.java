package com.example.foodchoise.helperFirebase.database;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FirestoreHelper extends FirestoreHelperBasic {
    //region Singleton
    private static FirestoreHelper firestoreHelper;

    public static FirestoreHelper getInstance() {
        if (firestoreHelper == null) {
            firestoreHelper = new FirestoreHelper();
        }
        return firestoreHelper;
    }
    //endregion

    private FirestoreHelperIntegration firestoreHelperIntegration;

    private FirestoreHelper() {
        super();
        firestoreHelperIntegration = new FirestoreHelperIntegration();
    }


    //region public_const
    public static final String COLLECTION_RECIPES = "recipes";
    public static final String TEST = "test";
    //endregion


    //Мда... это бы в Котлине передалать, код в разы понятней будет.
    public Task<DocumentReference> addRecipeCard(RecipeCard recipeCard) {
        CollectionReference recipesCollection = db.collection(COLLECTION_RECIPES);

        Map<String, Object> recipeData = firestoreHelperIntegration.createMapFromRecipeCard(recipeCard);

        return recipesCollection.add(recipeData);
    }

    public List<RecipeCard> getRecipesCard() {
        List<Map<String, Object>> maps = super.getMapDocumentsInCollection(COLLECTION_RECIPES);
        return firestoreHelperIntegration.createRecipeCardsFromMaps(maps);
    }
}
