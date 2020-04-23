package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс для отображения в RecipesFragment
 */
public class BriefRecipeCard implements Parcelable {
    protected Uri uri_dishes_image;
    protected String dishes_name;
    protected int dishes_tasty_rating;
    protected int dishes_complexity_rating;

    //region getter's
    public Uri getUriDishesImage() {
        return uri_dishes_image;
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
    //endregion

    public BriefRecipeCard(Uri uri_dishes_image, String dishes_name) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        dishes_tasty_rating = 0;
        dishes_complexity_rating = 0;
    }

    public BriefRecipeCard(Uri uri_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_tasty_rating = dishes_tasty_rating;
        this.dishes_complexity_rating = dishes_complexity_rating;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        uri_dishes_image = Uri.parse(in.readString());
        dishes_name = in.readString();
        dishes_tasty_rating = in.readInt();
        dishes_complexity_rating = in.readInt();
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
        dest.writeString(uri_dishes_image.toString());
        dest.writeString(dishes_name);
        dest.writeInt(dishes_tasty_rating);
        dest.writeInt(dishes_complexity_rating);
    }
    //endregion

}
