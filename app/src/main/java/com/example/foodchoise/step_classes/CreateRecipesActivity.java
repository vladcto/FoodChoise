package com.example.foodchoise.step_classes;

import android.content.Intent;
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

        String dishes_name = stepNameFragment.getTextNameDishes().trim();
        if(dishes_name.isEmpty()){
            Timber.i("Недопустмое имя для dishes_name = %s .",dishes_name);
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("RecipeCard dishes_name = %s .",dishes_name);

        /* if (...) {
        ...
        return;
        } - проверки на неверные данные. */

        BriefRecipeCard recipeCard = new BriefRecipeCard(1,dishes_name);
        Timber.i("RecipeCard успешно создана");
        Intent data = new Intent();
        data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
        setResult(RESULT_OK, data);
        finish();
    }
}
