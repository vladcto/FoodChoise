package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.themeUtil.ThemeController;
import com.google.android.material.tabs.TabLayout;

public class DisplayRecipeActivity extends AppCompatActivity {

    RecipeCard recipeCard ;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeController.setNowTheme(this);
        super.onCreate(savedInstanceState);
        recipeCard = getIntent().getParcelableExtra(BriefRecipeCardAdapter.RECIPECARD_DATA);
        setContentView(R.layout.activity_display_recipe);
        DisplayFragmentAdapter adapter = new DisplayFragmentAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(recipeCard.getDishesName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public RecipeCard getRecipeCard() {
        return recipeCard;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

