package com.example.foodchoise.step_classes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.BriefRecipeCard;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.example.foodchoise.step_classes.StepFragmentsAdapter;
import com.example.foodchoise.step_classes.StepNameFragment;

import java.util.List;

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
        ViewPager viewPager = findViewById(R.id.view_pager);
        Intent data = new Intent();

        StepFragmentsAdapter adapter = (StepFragmentsAdapter)viewPager.getAdapter();
        StepNameFragment stepNameFragment = (StepNameFragment)adapter.getItem(0);
        String dishes_name = stepNameFragment.getTextNameDishes();
        if(dishes_name == null){
            viewPager.setCurrentItem(0);
        }
        /* if (...) {...} - проверки на неверные данные. */
        BriefRecipeCard recipeCard = new BriefRecipeCard(1,dishes_name);
        data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
        setResult(RESULT_OK, data);
        finish();
    }
}
