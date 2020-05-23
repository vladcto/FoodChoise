package com.example.foodchoise.helperFirebase.database;

import androidx.annotation.NonNull;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final String USERS_COLLECTION = "users";
    //endregion

    //region private_const
    private static final String FAVORITE_RECIPES = "favorite_recipes";
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

    public void addToFavorite(String recipeUid){
        //Не очень уверен , что стоит этому классу заниматься аунтентификацией.
        //Потом посмторю , а пока так.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user!= null ;
        final String uidUser = user.getUid();

        final HashMap<String,Object> map = new HashMap<String, Object>();
        map.put(FAVORITE_RECIPES,recipeUid);

        final CollectionReference users_collection = db.collection(USERS_COLLECTION);
        users_collection.document(uidUser).update(FAVORITE_RECIPES, FieldValue.arrayUnion(recipeUid)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                users_collection.document(uidUser).set(map).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

}
