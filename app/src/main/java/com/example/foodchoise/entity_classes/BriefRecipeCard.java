package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class BriefRecipeCard implements Parcelable {
    //TODO: отрекфакторить имя переменной
    private Uri id_dishes_image;
    private String dishes_name;
    private int dishes_tasty_rating;
    private int dishes_complexity_rating;

    //region getter's
    public Uri getIdDishesImage() {
        return id_dishes_image;
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

    public BriefRecipeCard(Uri id_dishes_image, String dishes_name) {
        this.id_dishes_image = id_dishes_image;
        this.dishes_name = dishes_name;
        dishes_tasty_rating = 0;
        dishes_complexity_rating = 0;
    }

    public BriefRecipeCard(Uri id_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating) {
        this.id_dishes_image = id_dishes_image;
        this.dishes_name = dishes_name;
        this.dishes_tasty_rating = dishes_tasty_rating;
        this.dishes_complexity_rating = dishes_complexity_rating;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        id_dishes_image = Uri.parse(in.readString());
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
        dest.writeString(id_dishes_image.toString());
        dest.writeString(dishes_name);
        dest.writeInt(dishes_tasty_rating);
        dest.writeInt(dishes_complexity_rating);
    }
    //endregion

}
