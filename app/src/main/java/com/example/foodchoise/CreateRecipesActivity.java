package com.example.foodchoise;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.entity_classes.BriefRecipeCard;
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

    public BriefRecipeCard buildBriefRecipeCard(){
        ViewPager viewPager = findViewById(R.id.view_pager);
        StepFragmentsAdapter adapter = (StepFragmentsAdapter)viewPager.getAdapter();
        StepNameFragment stepNameFragment = (StepNameFragment)adapter.getItem(0);
        return new BriefRecipeCard(1,stepNameFragment.getTextNameDishes());
    };
}
