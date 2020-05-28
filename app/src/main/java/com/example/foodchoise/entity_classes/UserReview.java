package com.example.foodchoise.entity_classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserReview {
    private double tastyRating, priceRating;
    private int hardRating;
    private String comment;

    public UserReview(@NonNull double tastyRating, @NonNull double priceRating, @NonNull int hardRating, String comment) {
        this.tastyRating = tastyRating;
        this.priceRating = priceRating;
        this.hardRating = hardRating;
        this.comment = comment;
    }

    public double getTastyRating() {
        return tastyRating;
    }

    public double getPriceRating() {
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
        double tastyRating,priceRating;
        int hardRating;
        String comment = null;

        public Builder() {}

        @Override
        public IUserReviewBuilder setTastyRating(@NonNull double tastyRating) {
            this.tastyRating = tastyRating;
            return this;
        }

        @Override
        public IUserReviewBuilder setPriceRating(@NonNull double priceRating) {
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
