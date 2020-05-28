package com.example.foodchoise.helperFirebase.database;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.entity_classes.UserReview;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
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
    public static final String COLLECTION_USER_REVIEWS = "user_reviews";
    //endregion

    //region private_const
    private static final String FAVORITE_RECIPES = "favorite_recipes";
    //endregion

    //Мда... это бы в Котлине передалать, код в разы понятней будет.
    public Task<DocumentReference> addRecipeCard(RecipeCard recipeCard) {
        CollectionReference recipesCollection = db.collection(COLLECTION_RECIPES);

        Map<String, Object> recipeData = firestoreHelperIntegration.mapFromRecipeCard(recipeCard);

        return recipesCollection.add(recipeData);
    }

    public List<RecipeCard> getRecipesCardIn(String recipesCollection) {
        List<Map<String, Object>> maps = super.getMapDocumentsInCollection(recipesCollection);
        return firestoreHelperIntegration.recipeCardsFromMaps(maps);
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

        return firestoreHelperIntegration.recipeCardsFromMaps(recipesCardData);
    }

    public AsyncTask getFavoritesRecipesRefernce() {
        return new MyTask();
    }
    private static class MyTask extends AsyncTask<Object,Object,List<String>> {


        @Override
        protected List<String> doInBackground(Object... params) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            final String uidUser = user.getUid();

            Task<DocumentSnapshot> snapshotTask = FirebaseFirestore.getInstance()
                    .collection(USERS_COLLECTION)
                    .document(uidUser)
                    .get();
            try {
                Tasks.await(snapshotTask);
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }

            DocumentSnapshot snapshot = snapshotTask.getResult();
            Object documentsReference = snapshot.get(FAVORITE_RECIPES);
            ArrayList<String> result = new ArrayList<>();
            if(documentsReference == null){
                //Костыль , так как для запроса Query понадобиться хот что-то.
                result.add("null");
                return result;
            }
            try {
                List<DocumentReference> references = (ArrayList<DocumentReference>) documentsReference;
                for (DocumentReference reference: references){
                    result.add(reference.getId());
                }
            } catch (ClassCastException e) {
                DocumentReference reference = (DocumentReference) documentsReference;
                result.add(reference.getId());
            }

            return result;
        }
    }

    public void addToFavorite(String recipeUid) {
        //Не очень уверен , что стоит этому классу заниматься аунтентификацией.
        //Потом посмторю , а пока так.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String uidUser = user.getUid();

        final CollectionReference users_collection = db.collection(USERS_COLLECTION);
        final DocumentReference recipeReference = db.collection(COLLECTION_RECIPES).document(recipeUid);

        final HashMap<String, DocumentReference> map = new HashMap<String, DocumentReference>();
        map.put(FAVORITE_RECIPES, recipeReference);

        users_collection.document(uidUser).update(FAVORITE_RECIPES, FieldValue.arrayUnion(recipeReference)).addOnFailureListener(new OnFailureListener() {
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
        Map<String, Object> map = snapshot.getData();
        map.put("id", snapshot.getId());
        return FirestoreHelperIntegration.recipeCardFromMap(map);
    }

    public void sendReview(UserReview userReview, String recipe_ID){
        DocumentReference recipeRefernce = db.collection(COLLECTION_RECIPES).document(recipe_ID);
        CollectionReference usersReviewCollection = recipeRefernce.collection(COLLECTION_USER_REVIEWS);

        Map<String,Object> map = FirestoreHelperIntegration.mapFromUserReview(userReview);
        final String uidUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersReviewCollection.document(uidUser).set(map);
        recipeRefernce.update("all_tasty_rating",FieldValue.increment(userReview.getTastyRating()),
                "all_complexity_rating",FieldValue.increment(userReview.getPriceRating()),
                "all_price_rating",FieldValue.increment(userReview.getPriceRating()),
                "users_complete",FieldValue.increment(1));
    }
}
