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

    /**
     * @param userReviewSecond Отзыв относительно которого высчитывается разница.
     * @return На как много 1 отзыв меньше 2.
     */
    //Присваиваеться комменатрий второго.
    public UserReview differenceWith(UserReview userReviewSecond) {
        return new Builder()
                .setHardRating(userReviewSecond.getHardRating() - hardRating)
                .setTastyRating(userReviewSecond.getTastyRating() - tastyRating)
                .setPriceRating(userReviewSecond.getPriceRating() - priceRating)
                .addComment(userReviewSecond.getComment())
                .build();
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
