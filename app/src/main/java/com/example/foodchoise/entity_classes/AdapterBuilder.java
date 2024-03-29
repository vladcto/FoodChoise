package com.example.foodchoise.entity_classes;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class AdapterBuilder {
    public static BriefRecipeCardAdapter getBriefRecipeAdapter(Activity activity, Query baseQuery) {

        //Config
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();

        //Options
        FirestorePagingOptions<RecipeCard> options = new FirestorePagingOptions.Builder<RecipeCard>()
                .setQuery(baseQuery, config, new SnapshotParser<RecipeCard>() {
                    @NonNull
                    @Override
                    public RecipeCard parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return FirestoreHelper.createRecipeCardFromSnapshot(snapshot);
                    }
                })
                .build();
        return new BriefRecipeCardAdapter(options, activity);
    }

    public static CardStackViewAdapter getCardStackAdapter(Activity activity, Query baseQuery){

        //Config
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(10)
                .setPageSize(10)
                .build();

        //Options
        FirestorePagingOptions<RecipeCard> options = new FirestorePagingOptions.Builder<RecipeCard>()
                .setQuery(baseQuery, config, new SnapshotParser<RecipeCard>() {
                    @NonNull
                    @Override
                    public RecipeCard parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return FirestoreHelper.createRecipeCardFromSnapshot(snapshot);
                    }
                })
                .build();
        return new CardStackViewAdapter(options);
    }

    public static CommentsAdapter getCommentsAdapter(Query baseQuery) {
        //Config
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(2)
                .setPageSize(10)
                .build();

        //Options
        FirestorePagingOptions<UserReview> options = new FirestorePagingOptions.Builder<UserReview>()
                .setQuery(baseQuery, config, new SnapshotParser<UserReview>() {
                    @NonNull
                    @Override
                    public UserReview parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return FirestoreHelper.getInstance().userReviewFromSnapshot(snapshot);
                    }
                })
                .build();
        return new CommentsAdapter(options);
    }
}
