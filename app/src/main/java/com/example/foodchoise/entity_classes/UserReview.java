package com.example.foodchoise.entity_classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserReview {
    private float tastyRating, priceRating;
    private int hardRating;
    private String comment;

    public UserReview(@NonNull float tastyRating, @NonNull float priceRating,@NonNull int hardRating, String comment) {
        this.tastyRating = tastyRating;
        this.priceRating = priceRating;
        this.hardRating = hardRating;
        this.comment = comment;
    }

    public float getTastyRating() {
        return tastyRating;
    }

    public float getPriceRating() {
        return priceRating;
    }

    public int getHardRating() {
        return hardRating;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public static class Builder implements IUserReviewBuilder{
        float tastyRating,priceRating;
        int hardRating;
        String comment = null;

        public Builder() {}

        @Override
        public IUserReviewBuilder setTastyRating(@NonNull float tastyRating) {
            this.tastyRating = tastyRating;
            return this;
        }

        @Override
        public IUserReviewBuilder setPriceRating(@NonNull float priceRating) {
            this.priceRating = priceRating;
            return this;
        }

        @Override
        public IUserReviewBuilder setHardRating(@NonNull int hardRating) {
            this.hardRating = hardRating;
            return this;
        }

        @Override
        public IUserReviewBuilder addComment(@NonNull String comment) {
            return this;
        }

        @NonNull
        @Override
        public UserReview build() {
            return new UserReview(tastyRating,priceRating,hardRating,comment);
        }
    }
}
