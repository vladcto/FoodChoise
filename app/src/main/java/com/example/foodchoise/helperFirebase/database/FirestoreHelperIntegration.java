package com.example.foodchoise.helperFirebase.database;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.RecipeCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для инкапсулирования методов не связанных непосредственно с работой Firestore.
 */
//Этот класс нужен ,чтобы FirestoreHelper не занимался созданием рецптов и т.п., а только БД.
final class FirestoreHelperIntegration {

    static Map<String, Object> createMapFromRecipeCard(RecipeCard recipeCard) {
        Map<String, Object> recipeData = new HashMap<>();

        recipeData.put("complexity_rating", recipeCard.getDishesComplexityRating());
        recipeData.put("dishes_descr", recipeCard.getDishesDescription());
        recipeData.put("ingridients", recipeCard.getDishesIngridient());
        recipeData.put("instr", recipeCard.getDishesInstruction());
        recipeData.put("name", recipeCard.getDishesName());
        recipeData.put("tasty_rating", recipeCard.getDishesTastyRating());

        return recipeData;
    }

    static List<RecipeCard> createRecipeCardsFromMaps(List<Map<String, Object>> maps){
        List<RecipeCard> recipeCards = new ArrayList<RecipeCard>();
        for (Map<String,Object> map : maps) {
            recipeCards.add(createRecipeCardFromMap(map));
        }
        return recipeCards;
    }

    private static RecipeCard createRecipeCardFromMap(Map<String, Object> map){
        //TODO: Проверка на то, что такого ключа нет.
        String dishes_descr = (String)map.get("dishes_descr");
        long complexity_rating = (long)map.get("complexity_rating");
        ArrayList<String> ingridients = (ArrayList<String>) map.get("ingridients");
        ArrayList<String> instr = (ArrayList<String>) map.get("instr");
        String name = (String)map.get("name");
        long tasty_rating = (long)map.get("tasty_rating");
        //TODO: СДЕЛАТЬ КОСНТРУКТОР.
        Uri uri = Uri.parse("android.resource://com.example.foodchoise/drawable/back.xml");
        RecipeCard recipeCard = new RecipeCard(uri,name,tasty_rating,complexity_rating,dishes_descr,ingridients,instr);
        recipeCard.addId((String)map.get("id"));
        return recipeCard;
    }
}