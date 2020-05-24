package com.example.foodchoise.entity_classes;

import android.os.Parcel;
import android.os.Parcelable;

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
}
