package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//TODO: Наследовать от BriefRecipeCard.
public class RecipeCard implements Parcelable {
    private Uri uri_dishes_image;
    private String dishes_name;
    private String dishes_descr;
    private int dishes_tasty_rating ;
    private int dishes_complexity_rating ;
    private ArrayList<String> dishes_ingridient = new ArrayList<String>();
    private ArrayList<String> dishes_ingstr = new ArrayList<String>();

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

    public RecipeCard(Uri uri_dishes_image, String dishes_name, String dishes_descr, int dishes_tasty_rating, int dishes_complexity_rating, ArrayList<String> dishes_ingridient, ArrayList<String> dishes_ingstr) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_descr = dishes_descr;
        this.dishes_tasty_rating = dishes_tasty_rating;
        this.dishes_complexity_rating = dishes_complexity_rating;
        this.dishes_ingridient = dishes_ingridient;
        this.dishes_ingstr = dishes_ingstr;
    }

    public RecipeCard(Uri uri_dishes_image, String dishes_name, String dishes_descr, ArrayList<String> dishes_ingridient, ArrayList<String> dishes_ingstr) {
        this.uri_dishes_image = uri_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_descr = dishes_descr;
        this.dishes_ingridient = dishes_ingridient;
        this.dishes_ingstr = dishes_ingstr;
    }

    //endregion Конструкторы

    public BriefRecipeCard buildBriefRecipeCard(){
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
        in.readStringList(dishes_ingstr);
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
        dest.writeStringList(dishes_ingstr);
    }
    //endregion

}
