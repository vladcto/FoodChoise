package com.example.foodchoise.entity_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс для отображения в RecipesFragment
 */
class BriefRecipeCard implements Parcelable {
    protected String dishesName;
    protected long dishesTastyRating;
    protected long dishesComplexityRating;
    protected String ID;

    public String getDishesName() {
        return dishesName;
    }

    public long getDishesTastyRating() {
        return dishesTastyRating;
    }

    public long getDishesComplexityRating() {
        return dishesComplexityRating;
    }

    public String getID(){
        return ID;
    }
    //endregion

    protected BriefRecipeCard(String dishesName, String id , long dishesTastyRating, long dishesComplexityRating) {
        this.ID = id;
        this.dishesName = dishesName;
        this.dishesTastyRating = dishesTastyRating;
        this.dishesComplexityRating = dishesComplexityRating;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        dishesName = in.readString();
        dishesTastyRating = in.readLong();
        dishesComplexityRating = in.readLong();
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
        dest.writeLong(dishesTastyRating);
        dest.writeLong(dishesComplexityRating);
        dest.writeString(ID);
    }
    //endregion

}
