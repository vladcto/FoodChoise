package com.example.foodchoise.entity_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс для отображения в RecipesFragment
 */
class BriefRecipeCard implements Parcelable {
    private String dishesName;
    private double dishesTastyRating;
    private double dishesComplexityRating;
    private String ID;

    public String getDishesName() {
        return dishesName;
    }

    public double getDishesTastyRating() {
        return dishesTastyRating;
    }

    public double getDishesComplexityRating() {
        return dishesComplexityRating;
    }

    public String getID(){
        return ID;
    }
    //endregion

    protected BriefRecipeCard(String dishesName, String id , double dishesTastyRating, double dishesComplexityRating) {
        this.ID = id;
        this.dishesName = dishesName;
        this.dishesTastyRating = dishesTastyRating;
        this.dishesComplexityRating = dishesComplexityRating;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        dishesName = in.readString();
        dishesTastyRating = in.readDouble();
        dishesComplexityRating = in.readDouble();
        ID = in.readString();
    }

    public static final Creator<BriefRecipeCard> CREATOR = new Creator<BriefRecipeCard>() {
        @Override
        public BriefRecipeCard createFromParcel(Parcel in) {
            return new BriefRecipeCard(in);
        }

        @Override
        public BriefRecipeCard[] newArray(int size) {
            return new BriefRecipeCard[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dishesName);
        dest.writeDouble(dishesTastyRating);
        dest.writeDouble(dishesComplexityRating);
        dest.writeString(ID);
    }
    //endregion

}
