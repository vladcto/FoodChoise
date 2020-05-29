package com.example.foodchoise.helperFirebase.database;

import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.entity_classes.UserReview;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        Random random = new Random();

        //Случайные числа для будующей случайной выборки.
        recipeData.put("random_1",random.nextLong());
        recipeData.put("random_2",random.nextLong());
        recipeData.put("random_3",random.nextLong());
        //Дефолтные поля.
        recipeData.put("users_complete",(long)0);
        recipeData.put("all_complexity_rating",(double)0.0);
        recipeData.put("all_tasty_rating",(double)0.0);

        String author_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recipeData.put("author",author_id);

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
        // Сделать присваивание дефолтное при создании рецепта.
        //region Считываем данные
        double complexity_rating,tasty_rating;
        long users_complete;
        try {
            users_complete = (long) map.get("users_complete");
        }catch (NullPointerException e){
            users_complete = 0;
        }
        if(users_complete == 0) {
            complexity_rating = 0;
            tasty_rating = 0;
        }else {
            complexity_rating = (double) map.get("all_complexity_rating") / users_complete;
            tasty_rating = (double) map.get("all_tasty_rating") / users_complete;
        }
        String dishes_descr = (String)map.get("dishes_descr");
        ArrayList<String> ingridients = (ArrayList<String>) map.get("ingridients");
        ArrayList<String> instr = (ArrayList<String>) map.get("instr");
        String name = (String)map.get("name");
        String id = (String) map.get("id");
        //endregion

        RecipeCard recipeCard = new RecipeCard.Builder()
                .setName(name)
                .setTastyRating(tasty_rating)
                .setComplexityRating(complexity_rating)
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
        map.put("complexity_rating",userReview.getHardRating());
        map.put("price_rating",userReview.getPriceRating());
        map.put("comment",userReview.getComment());
        return map;
    }

    static UserReview userReviewFromMap(Map<String, Object> map) {
        //FIXME Потенциальная ошибка при целочисленном отзыве.
        Long complexity_rating = (Long) map.get("complexity_rating");
        return new UserReview.Builder()
                .setHardRating(complexity_rating.intValue())
                .setPriceRating((double) map.get("price_rating"))
                .setTastyRating((double) map.get("tasty_rating"))
                //автор это айди документа.
                .setAuthor((String) map.get("author"))
                .addComment((String) map.get("comment"))
                .build();
    }
}