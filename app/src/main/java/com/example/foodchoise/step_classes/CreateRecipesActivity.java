package com.example.foodchoise.step_classes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.BriefRecipeCard;
import com.example.foodchoise.main_fragments.ReciepsFragment;

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
        Timber.i("Начало создание RecipeCard");
        ViewPager viewPager = findViewById(R.id.view_pager);

        StepFragmentsAdapter adapter = (StepFragmentsAdapter)viewPager.getAdapter();
        StepNameFragment stepNameFragment = (StepNameFragment)adapter.getItem(0);

        //region Проверка заполнненых данных под RecipeCard.
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

        /* if (...) {
        ...
        return;
        } - проверки на неверные данные. */
        //endregion

        //TODO: Создавать RecipeCard, а не BriefRecipeCard.
        BriefRecipeCard recipeCard = new BriefRecipeCard(image_uri,dishes_name);
        Timber.i("BriefRecipeCard успешно создана");
        Intent data = new Intent();
        data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
        setResult(RESULT_OK, data);
        finish();
    }
}
