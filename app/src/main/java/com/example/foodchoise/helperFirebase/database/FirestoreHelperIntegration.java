package com.example.foodchoise.helperFirebase.database;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.entity_classes.UserReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для инкапсулирования методов не связанных непосредственно с работой Firestore.
 */
//Этот класс нужен ,чтобы FirestoreHelper не занимался созданием рецптов и т.п., а только БД.
//TODO: Сделать статичным.
final class FirestoreHelperIntegration {

    Map<String, Object> mapFromRecipeCard(RecipeCard recipeCard) {
        Map<String, Object> recipeData = new HashMap<>();

        recipeData.put("name", recipeCard.getDishesName());
        recipeData.put("dishes_descr", recipeCard.getDishesDescription());
        recipeData.put("ingridients", recipeCard.getDishesIngridient());
        recipeData.put("instr", recipeCard.getDishesInstruction());

        return recipeData;
    }

    List<RecipeCard> recipeCardsFromMaps(List<Map<String, Object>> maps){
        List<RecipeCard> recipeCards = new ArrayList<RecipeCard>();
        for (Map<String,Object> map : maps) {
            recipeCards.add(recipeCardFromMap(map));
        }
        return recipeCards;
    }

    static RecipeCard recipeCardFromMap(Map<String, Object> map){
        //TODO: Проверка на то, что такого ключа нет.
        //region Считываем данные
        String dishes_descr = (String)map.get("dishes_descr");
        Long complexity_rating = (Long) map.get("complexity_rating");
        ArrayList<String> ingridients = (ArrayList<String>) map.get("ingridients");
        ArrayList<String> instr = (ArrayList<String>) map.get("instr");
        String name = (String)map.get("name");
        String id = (String) map.get("id");
        Long tasty_rating = (Long) map.get("tasty_rating");
        //endregion

        RecipeCard recipeCard = new RecipeCard.Builder()
                .setName(name)
                .setTastyRating(tasty_rating.intValue())
                .setComplexityRating(complexity_rating.intValue())
                .setDescription(dishes_descr)
                .setID(id)
                .setIngredient(ingridients)
                .setInstructions(instr)
                .build();

        return recipeCard;
    }

    static Map<String,Object> mapFromUserReview(UserReview userReview){
        Map<String,Object> map = new HashMap<>();
        map.put("tasty_rating",userReview.getTastyRating());
        map.put("hard_rating",userReview.getHardRating());
        map.put("price_rating",userReview.getPriceRating());
        map.put("comment",userReview.getComment());
        return map;
    }
}