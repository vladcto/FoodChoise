package com.example.foodchoise.entity_classes;

public class BriefRecipeCard {
    private int id_dishes_image;
    private String dishes_name;
    //TODO: dishes_tasty_rating , dishes_complexity_rating

    public BriefRecipeCard(int id_dishes_image, String dishes_name) {
        this.id_dishes_image = id_dishes_image;
        this.dishes_name = dishes_name;
    }
}
