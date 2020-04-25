package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeCard extends BriefRecipeCard  implements Parcelable {
    private String dishes_descr;
    private ArrayList<String> dishes_ingridient = new ArrayList<String>();
    private ArrayList<String> dishes_ingstr = new ArrayList<String>();

    //region getter's
    public String getDishesDescription(){
        return dishes_descr;
    }
    //endregion

    //region Конструкторы

    public RecipeCard(Uri uri_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating, String dishes_descr, ArrayList<String> dishes_ingridient, ArrayList<String> dishes_ingstr) {
        super(uri_dishes_image, dishes_name, dishes_tasty_rating, dishes_complexity_rating);
        this.dishes_descr = dishes_descr;
        this.dishes_ingridient = dishes_ingridient;
        this.dishes_ingstr = dishes_ingstr;
    }

    public RecipeCard(Uri uri_dishes_image, String dishes_name, String dishes_descr, ArrayList<String> dishes_ingridient, ArrayList<String> dishes_ingstr) {
        super(uri_dishes_image, dishes_name);
        this.dishes_descr = dishes_descr;
        this.dishes_ingridient = dishes_ingridient;
        this.dishes_ingstr = dishes_ingstr;
    }

//endregion Конструкторы

    //region Реализация Parcelable

    /**
     * Конструктор для Parcelable
     */
    private RecipeCard(Parcel in) {
        super(in);
        in.readStringList(dishes_ingridient);
        in.readStringList(dishes_ingstr);
        dishes_descr = in.readString();
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
        dest.writeStringList(dishes_ingridient);
        dest.writeStringList(dishes_ingstr);
        dest.writeString(dishes_descr);
    }
    //endregion

}
