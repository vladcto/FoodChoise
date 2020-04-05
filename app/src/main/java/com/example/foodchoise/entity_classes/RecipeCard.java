package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeCard implements Parcelable {
    private Uri uri_dishes_image;
    private String dishes_name;
    private int dishes_tasty_rating ;
    private int dishes_complexity_rating ;
    private ArrayList<String> dishes_ingridient = new ArrayList<String>();

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

    //region Конструкторы
    public RecipeCard(Uri uri_dishes_image, String dishes_name) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        dishes_tasty_rating = 0;
        dishes_complexity_rating = 0;
    }

    public RecipeCard(Uri uri_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_tasty_rating = dishes_tasty_rating;
        this.dishes_complexity_rating = dishes_complexity_rating;
    }

    public RecipeCard(Uri uri_dishes_image, String dishes_name,ArrayList<String> dishes_ingridient) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        dishes_tasty_rating = 0;
        dishes_complexity_rating = 0;
        this.dishes_ingridient = dishes_ingridient;
    }
    //endregion

    public BriefRecipeCard getBriefRecipeCard(){
        return new BriefRecipeCard(uri_dishes_image,dishes_name,dishes_tasty_rating,dishes_complexity_rating);
    }

    //region Реализация Parcelable

    /**
     * Конструктор для Parcelable
     */
    private RecipeCard(Parcel in) {
        uri_dishes_image = Uri.parse(in.readString());
        dishes_name = in.readString();
        dishes_tasty_rating = in.readInt();
        dishes_complexity_rating = in.readInt();
        in.readStringList(dishes_ingridient);
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
        dest.writeString(uri_dishes_image.toString());
        dest.writeString(dishes_name);
        dest.writeInt(dishes_tasty_rating);
        dest.writeInt(dishes_complexity_rating);
        dest.writeStringList(dishes_ingridient);
    }
    //endregion

}
