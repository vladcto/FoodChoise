package com.example.foodchoise.entity_classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.foodchoise.BuildConfig;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

public class RecipeCard extends BriefRecipeCard  implements Parcelable {
    private String dishesDescr;
    private List<String> dishesIngridient = new ArrayList<>();
    private List<String> dishesInstr = new ArrayList<>();
    //region getter's
    public String getDishesDescription(){
        return dishesDescr;
    }

    public List<String> getDishesIngridient() {
        return dishesIngridient;
    }

    public List<String> getDishesInstruction(){
        return dishesInstr;
    }

    //endregion

    //region Конструкторы
    RecipeCard(String dishes_name, long dishes_tasty_rating, long dishes_complexity_rating, String dishesDescr, List<String> dishesIngridient, List<String> dishesInstr, String id) {
        super(dishes_name, id,dishes_tasty_rating, dishes_complexity_rating);
        this.dishesDescr = dishesDescr;
        this.dishesIngridient = dishesIngridient;
        this.dishesInstr = dishesInstr;
    }
    //endregion Конструкторы

    //region Реализация Parcelable

    /**
     * Конструктор для Parcelable
     */
    private RecipeCard(Parcel in) {
        super(in);
        in.readStringList(dishesIngridient);
        in.readStringList(dishesInstr);
        dishesDescr = in.readString();
    }

    public static final Parcelable.Creator<RecipeCard> CREATOR = new Parcelable.Creator<RecipeCard>() {
        @Override
        public RecipeCard createFromParcel(Parcel in) {
            return new RecipeCard(in);
        }

        @Override
        public RecipeCard[] newArray(int size) {
            return new RecipeCard[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeStringList(dishesIngridient);
        dest.writeStringList(dishesInstr);
        dest.writeString(dishesDescr);
    }
    //endregion

    public static class Builder implements IRecipeBuilder {

        private String name,id,description;
        private Integer tastyRating,complexityRating;
        private List<String> instructions,ingredients;

        public Builder() { }

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
}
