package com.example.foodchoise.entity_classes;

import com.example.foodchoise.BuildConfig;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder implements IRecipeBuilder {

    private String name,id,description;
    private Integer tastyRating,complexityRating;
    private List<String> instructions,ingredients;

    public RecipeBuilder() { }

    @Override
    public void reset() {
        name = id = description = null;
        tastyRating = complexityRating = null;
        instructions = ingredients = null;
    }

    public IRecipeBuilder setNewRecipe(){
        tastyRating = complexityRating = 0;
        return this;
    }

    @Override
    public IRecipeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IRecipeBuilder setTastyRating(int tastyRating) {
        this.tastyRating = tastyRating;
        return this;
    }


    @Override
    public IRecipeBuilder setComplexityRating(int complexityRating) {
        this.complexityRating = complexityRating;
        return this;
    }


    @Override
    public IRecipeBuilder setID(String id) {
        this.id = id;
        return this;
    }

    @Override
    public IRecipeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IRecipeBuilder setIngredient(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    @Override
    public IRecipeBuilder setInstructions(List<String> instructions) {
        this.instructions = instructions;
        return this;
    }

    @Override
    public RecipeCard getResult() {
        return new RecipeCard(name,tastyRating,complexityRating,description,ingredients,instructions,id);
    }

    //TODO: НИГДЕ НЕ ВЫЗЫВАТЬ В  РЕЛИЗЕ
    @TestOnly
    @Override
    public RecipeCard getTestCard() {
        if(BuildConfig.DEBUG) {
            name = description = "test";
            complexityRating = tastyRating = 5;
            ingredients = new ArrayList<>();
            ingredients.add("TEST");
            instructions = new ArrayList<>();
            instructions.add("TEST");
            id = "TEST";
            return new RecipeCard(name, tastyRating, complexityRating, description, ingredients, instructions, id);
        }else{
            throw new IllegalStateException();
        }
    }
}
