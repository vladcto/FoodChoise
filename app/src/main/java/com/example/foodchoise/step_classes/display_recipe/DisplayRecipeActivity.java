package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;

public class DisplayRecipeActivity extends AppCompatActivity {

    RecipeCard recipeCard ;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeCard = getIntent().getParcelableExtra(BriefRecipeCardAdapter.RECIPECARD_DATA);
        setContentView(R.layout.activity_display_recipe);
        DisplayFragmentAdapter adapter = new DisplayFragmentAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    public RecipeCard getRecipeCard() {
        return recipeCard;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

