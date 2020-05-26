package com.example.foodchoise.helperFirebase.database;

import androidx.annotation.NonNull;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    public List<RecipeCard> getRecipesCardIn(String recipesCollection) {
        List<Map<String, Object>> maps = super.getMapDocumentsInCollection(recipesCollection);
        return firestoreHelperIntegration.createRecipeCardsFromMaps(maps);
    }

    public List<RecipeCard> getFavoritesRecipesCard() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String uidUser = user.getUid();

        List<RecipeCard> recipeCards = new ArrayList<>();
        Task<DocumentSnapshot> snapshotTask = db.collection(USERS_COLLECTION)
                .document(uidUser)
                .get();
        try {
            Tasks.await(snapshotTask);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return recipeCards;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return recipeCards;
        }

        List<Map<String, Object>> recipesCardData = new ArrayList<>();

        DocumentSnapshot snapshot = snapshotTask.getResult();
        Object documentsReference = snapshot.get(FAVORITE_RECIPES);
        ArrayList<DocumentReference> favoritesRecipes;
        try {
            favoritesRecipes = (ArrayList<DocumentReference>) documentsReference;
        } catch (ClassCastException e) {
            favoritesRecipes = new ArrayList<DocumentReference>();
            favoritesRecipes.add(((DocumentReference) documentsReference));
        }

        if (favoritesRecipes == null) {
            return null;
        }
        Map<String, Object> map;
        for (DocumentReference favoritesRecipe : favoritesRecipes) {
            Task<DocumentSnapshot> task = favoritesRecipe.get();
            try {
                Tasks.await(task);
            } catch (ExecutionException e) {
                e.printStackTrace();
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            map = task.getResult().getData();
            map.put("id", favoritesRecipe.getId());
            recipesCardData.add(map);
        }

        return firestoreHelperIntegration.createRecipeCardsFromMaps(recipesCardData);
    }

    public List<DocumentReference> getFavoritesRecipesRefernce() {
        ArrayList<DocumentReference> favoritesRecipes = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String uidUser = user.getUid();

        List<RecipeCard> recipeCards = new ArrayList<>();
        Task<DocumentSnapshot> snapshotTask = db.collection(USERS_COLLECTION)
                .document(uidUser)
                .get();
        try {
            Tasks.await(snapshotTask);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return favoritesRecipes;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return favoritesRecipes;
        }

        DocumentSnapshot snapshot = snapshotTask.getResult();
        Object documentsReference = snapshot.get(FAVORITE_RECIPES);
        try {
            favoritesRecipes = (ArrayList<DocumentReference>) documentsReference;
        } catch (ClassCastException e) {
            favoritesRecipes = new ArrayList<DocumentReference>();
            favoritesRecipes.add(((DocumentReference) documentsReference));
        }

        return favoritesRecipes;
    }

    public void addToFavorite(String recipeUid) {
        //Не очень уверен , что стоит этому классу заниматься аунтентификацией.
        //Потом посмторю , а пока так.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String uidUser = user.getUid();

        final CollectionReference users_collection = db.collection(COLLECTION_RECIPES);

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put(FAVORITE_RECIPES, uidUser);

        users_collection.document(recipeUid).update(FAVORITE_RECIPES, FieldValue.arrayUnion(uidUser)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseFirestoreException)
                    users_collection.document(uidUser).set(map).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static RecipeCard createRecipeCardFromSnapshot(DocumentSnapshot snapshot) {
        RecipeCard.Builder builder = new RecipeCard.Builder();
        Map<String, Object> map = snapshot.getData();
        map.put("id", snapshot.getId());
        return FirestoreHelperIntegration.createRecipeCardFromMap(map);
    }
}
