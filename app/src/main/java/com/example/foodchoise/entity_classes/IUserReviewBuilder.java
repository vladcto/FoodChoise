package com.example.foodchoise.entity_classes;

import androidx.annotation.NonNull;

public interface IUserReviewBuilder {
    public IUserReviewBuilder setTastyRating(@NonNull double tastyRating);

    public IUserReviewBuilder setPriceRating(@NonNull double priceRating);

    public IUserReviewBuilder setHardRating(@NonNull int hardRating);

    public IUserReviewBuilder addComment(@NonNull String comment);

    public @NonNull UserReview build();
}
