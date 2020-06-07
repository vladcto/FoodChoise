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
    private double priceRating;
    private String ID;
    private long usersComplete;

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

    public double getPriceRating() {
        return priceRating;
    }

    public long getUsersComplete() {
        return usersComplete;
    }

    public void addUser() {
        usersComplete++;
    }

    public void addTastyRating(double tastyRating) {
        this.dishesTastyRating = dishesTastyRating + tastyRating;
    }

    public void addPriceRating(double priceRating) {
        this.priceRating = this.priceRating + priceRating;
    }

    public void addComplexityRating(double dishesComplexityRating) {
        this.dishesComplexityRating = this.dishesComplexityRating + dishesComplexityRating;
    }

    protected BriefRecipeCard(String dishesName, String id, double dishesTastyRating, double dishesComplexityRating, double priceRating, long usersComplete) {
        this.ID = id;
        this.dishesName = dishesName;
        this.dishesTastyRating = dishesTastyRating;
        this.dishesComplexityRating = dishesComplexityRating;
        this.priceRating = priceRating;
        this.usersComplete = usersComplete;
    }

    //region Реализация Parcelable

    //Конструктор для Parcelable
    protected BriefRecipeCard(Parcel in) {
        dishesName = in.readString();
        dishesTastyRating = in.readDouble();
        dishesComplexityRating = in.readDouble();
        priceRating = in.readDouble();
        ID = in.readString();
        usersComplete = in.readLong();
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
        dest.writeDouble(priceRating);
        dest.writeString(ID);
        dest.writeLong(usersComplete);
    }
    //endregion

}
