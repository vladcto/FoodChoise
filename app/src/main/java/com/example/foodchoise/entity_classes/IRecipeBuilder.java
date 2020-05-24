package com.example.foodchoise.entity_classes;

import org.jetbrains.annotations.TestOnly;

import java.util.List;

public interface IRecipeBuilder {
    public void reset();

    public IRecipeBuilder setNewRecipe();

    public IRecipeBuilder setName(String name);

    public IRecipeBuilder setTastyRating(int tastyRating);

    public IRecipeBuilder setComplexityRating(int complexityRating);

    public IRecipeBuilder setID(String id);

    public IRecipeBuilder setDescription(String description);

    public IRecipeBuilder setIngredient(List<String> ingredients);

    public IRecipeBuilder setInstructions(List<String> instructions);

    public RecipeCard getResult();

    @TestOnly
    public RecipeCard getTestCard();
}
