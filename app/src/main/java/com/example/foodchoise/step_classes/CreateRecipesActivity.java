package com.example.foodchoise.step_classes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.BriefRecipeCard;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.main_fragments.ReciepsFragment;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CreateRecipesActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_creation_window);
        StepFragmentsAdapter adapter = new StepFragmentsAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    /**
     * Приказывает Activity начать собирать BriefRecipeCard , отслеживая все ошибки пользователя.
     */
    void buildBriefRecipeCard(){
        //TODO: Посмотреть про различия тегов , и если что , поменять эти теги и все ост.
        Timber.i("Начало создание RecipeCard");
        ViewPager viewPager = findViewById(R.id.view_pager);

        StepFragmentsAdapter adapter = (StepFragmentsAdapter)viewPager.getAdapter();
        StepNameFragment stepNameFragment = (StepNameFragment)adapter.getItem(0);

        //region Проверка заполнненых данных под RecipeCard.

        //region StepNameFragment
        Uri image_uri = stepNameFragment.getImageUri();
        if (image_uri == null) {
            Timber.i("Нет ссылки на изображения для image_uri");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("image_uri = %s" , image_uri.toString());

        String dishes_name = stepNameFragment.getTextNameDishes().trim();
        //TODO: Сделать ограничение по символам.
        if(dishes_name.isEmpty()){
            Timber.i("Пустое имя для dishes_name");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("dishes_name = %s .",dishes_name);

        String dishes_descr = stepNameFragment.getTextDescrDishes().trim();
        //TODO: Сделать ограничение по символам.
        if (dishes_descr.isEmpty()) {
            Timber.i("Пустое имя для dishes_descr");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("dishes_descr = %s .",dishes_descr);
        Timber.i("Проверка StepNameFragment прошла успешна");
        //endregion StepNameFragment

        //region StepIngridientFragment
        StepIngridientFragment stepIngridientFragment = (StepIngridientFragment)adapter.getItem(1);

        ArrayList<String> dishes_ingridients = stepIngridientFragment.getIngridients();
        if (dishes_ingridients.size() == 0) {
            Timber.i("Не указано ни одного ингридиента.");
            viewPager.setCurrentItem(1);
            return;
        }
        Timber.i("Проверка StepIngridientFragment прошла успешна");
        //endregion StepIngridientFragment

        //region StepInstrFragment
        StepInstrFragment instrFragment = (StepInstrFragment)adapter.getItem(2);

        ArrayList<String> dishes_instructions = instrFragment.getInstructions();
        if (dishes_instructions.size() == 0) {
            Timber.i("Не указано ни одной инструкции.");
            viewPager.setCurrentItem(2);
            return;
        }
        Timber.i("Проверка StepInstrFragment прошла успешна");
        //endregion StepInstrFragment

        /* if (...) {
        ...
        return;
        } - проверки на неверные данные. */

        //endregion

        RecipeCard recipeCard = new RecipeCard(image_uri,dishes_name,dishes_descr,dishes_ingridients,dishes_instructions);
        Timber.i("BriefRecipeCard успешно создана");
        Intent data = new Intent();
        //TODO: Отрефакторить все соотв. на RecipeCard.
        data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
        setResult(RESULT_OK, data);
        finish();
    }
}
