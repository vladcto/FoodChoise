package com.example.foodchoise.entity_classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeCard extends BriefRecipeCard  implements Parcelable {
    private String dishesDescr;
    private ArrayList<String> dishesIngridient = new ArrayList<String>();
    private ArrayList<String> dishesInstr = new ArrayList<String>();

    //region getter's
    public String getDishesDescription(){
        return dishesDescr;
    }

    public ArrayList<String> getDishesIngridient() {
        return dishesIngridient = dishesIngridient;
    }

    public ArrayList<String> getDishesInstruction(){
        return dishesInstr;
    }
    //endregion

    //region Конструкторы

    public RecipeCard(Uri uri_dishes_image, String dishes_name, int dishes_tasty_rating, int dishes_complexity_rating, String dishesDescr, ArrayList<String> dishesIngridient, ArrayList<String> dishesInstr) {
        super(uri_dishes_image, dishes_name, dishes_tasty_rating, dishes_complexity_rating);
        this.dishesDescr = dishesDescr;
        this.dishesIngridient = dishesIngridient;
        this.dishesInstr = dishesInstr;
    }

    public RecipeCard(Uri uri_dishes_image, String dishes_name, String dishesDescr, ArrayList<String> dishesIngridient, ArrayList<String> dishesInstr) {
        super(uri_dishes_image, dishes_name);
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

}
