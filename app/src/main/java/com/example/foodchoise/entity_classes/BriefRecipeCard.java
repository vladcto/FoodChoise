package com.example.foodchoise.entity_classes;

public class BriefRecipeCard {
    private int id_dishes_image;
    private String dishes_name;
    private int dishes_tasty_rating;
    private int dishes_complexity_rating;

    public BriefRecipeCard(int id_dishes_image, String dishes_name) {
        this.id_dishes_image = id_dishes_image;
        this.dishes_name = dishes_name;
        dishes_tasty_rating = 0;
        dishes_complexity_rating = 0;
    }

    public BriefRecipeCard(int id_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating) {
        this.id_dishes_image = id_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_tasty_rating = dishes_tasty_rating;
        this.dishes_complexity_rating = dishes_complexity_rating;
    }

    public int getIdDishesImage() {
        return id_dishes_image;
    }

    public String getDishesName() {
        return dishes_name;
    }

    public int getDishesTastyRating() {
        return dishes_tasty_rating;
    }

    public int getDishesComplexityRating() {
        return dishes_complexity_rating;
    }
}
