package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс для отображения в RecipesFragment
 */
public class BriefRecipeCard implements Parcelable {
    protected Uri uriDishesImage;
    protected String dishesName;
    protected int dishesTastyRating;
    protected int dishesComplexityRating;

    //region getter's
    public Uri getUriDishesImage() {
        return uriDishesImage;
    }

    public String getDishesName() {
        return dishesName;
    }

    public int getDishesTastyRating() {
        return dishesTastyRating;
    }

    public int getDishesComplexityRating() {
        return dishesComplexityRating;
    }
    //endregion

    public BriefRecipeCard(Uri uriDishesImage, String dishesName) {
        this.uriDishesImage = uriDishesImage;
        this.dishesName = dishesName;
        dishesTastyRating = 0;
        dishesComplexityRating = 0;
    }

    public BriefRecipeCard(Uri uriDishesImage, String dishesName, int dishesTastyRating, int dishesComplexityRating) {
        this.uriDishesImage = uriDishesImage;
        this.dishesName = dishesName;
        this.dishesTastyRating = dishesTastyRating;
        this.dishesComplexityRating = dishesComplexityRating;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        uriDishesImage = Uri.parse(in.readString());
        dishesName = in.readString();
        dishesTastyRating = in.readInt();
        dishesComplexityRating = in.readInt();
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
        dest.writeString(uriDishesImage.toString());
        dest.writeString(dishesName);
        dest.writeInt(dishesTastyRating);
        dest.writeInt(dishesComplexityRating);
    }
    //endregion

}
