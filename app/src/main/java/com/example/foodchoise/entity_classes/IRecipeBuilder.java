package com.example.foodchoise.entity_classes;

import java.util.List;

public interface IRecipeBuilder {
    public void reset();

    public IRecipeBuilder setNewRecipe();

    public IRecipeBuilder setName(String name);

    public IRecipeBuilder setTastyRating(double tastyRating);

    public IRecipeBuilder setComplexityRating(double complexityRating);

    public IRecipeBuilder setPriceRating(double priceRating);

    public IRecipeBuilder setUsersComplete(long usersComplete);

    public IRecipeBuilder setID(String id);

    public IRecipeBuilder setDescription(String description);

    public IRecipeBuilder setIngredient(List<String> ingredients);

    public IRecipeBuilder setInstructions(List<String> instructions);

    public RecipeCard build();

}
