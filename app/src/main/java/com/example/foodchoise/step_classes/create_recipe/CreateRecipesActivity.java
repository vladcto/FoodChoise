package com.example.foodchoise.step_classes.create_recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.RecipeBuilder;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.example.foodchoise.helperFirebase.storage.StorageFirebaseHelper;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.example.foodchoise.themeUtil.ThemeController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

import timber.log.Timber;

public class CreateRecipesActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeController.setNowTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_creation);
        StepFragmentsAdapter adapter = new StepFragmentsAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
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

    /**
     * Приказывает Activity начать собирать BriefRecipeCard , отслеживая все ошибки пользователя.
     */
    void buildBriefRecipeCard() {
        Timber.i("Начало создание RecipeCard");
        ViewPager viewPager = findViewById(R.id.view_pager);

        StepFragmentsAdapter adapter = (StepFragmentsAdapter) viewPager.getAdapter();
        StepNameFragment stepNameFragment = (StepNameFragment) adapter.getItem(0);
        RecipeBuilder recipeBuilder = new RecipeBuilder();
        //region Проверка заполнненых данных под RecipeCard.

        //region StepNameFragment
        final Uri image_uri = stepNameFragment.getImageUri();
        if (image_uri == null) {
            Timber.i("Нет ссылки на изображения для image_uri");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("image_uri = %s", image_uri.toString());

        String dishes_name = stepNameFragment.getTextNameDishes().trim();
        //TODO: Сделать ограничение по символам.
        if (dishes_name.isEmpty()) {
            Timber.i("Пустое имя для dishes_name");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("dishes_name = %s .", dishes_name);
        recipeBuilder.setDescription(dishes_name);

        String dishes_descr = stepNameFragment.getTextDescrDishes().trim();
        //TODO: Сделать ограничение по символам.
        if (dishes_descr.isEmpty()) {
            Timber.i("Пустое имя для dishes_descr");
            viewPager.setCurrentItem(0);
            return;
        }
        Timber.i("dishes_descr = %s .", dishes_descr);
        recipeBuilder.setDescription(dishes_descr);

        Timber.i("Проверка StepNameFragment прошла успешна");
        //endregion StepNameFragment

        //region StepIngridientFragment
        StepIngridientFragment stepIngridientFragment = (StepIngridientFragment) adapter.getItem(1);

        ArrayList<String> dishes_ingridients = stepIngridientFragment.getIngridients();
        //TODO: Проверить на пустые ингридиенты.
        if (dishes_ingridients.size() == 0) {
            Timber.i("Не указано ни одного ингридиента.");
            viewPager.setCurrentItem(1);
            return;
        }
        recipeBuilder.setIngredient(dishes_ingridients);
        Timber.i("Проверка StepIngridientFragment прошла успешна");
        //endregion StepIngridientFragment

        //region StepInstrFragment
        StepInstrFragment instrFragment = (StepInstrFragment) adapter.getItem(2);

        ArrayList<String> dishes_instructions = instrFragment.getInstructions();
        //TODO: Проверить на пустые инструкции.
        if (dishes_instructions.size() == 0) {
            Timber.i("Не указано ни одной инструкции.");
            viewPager.setCurrentItem(2);
            return;
        }
        recipeBuilder.setInstructions(dishes_instructions);
        Timber.i("Проверка StepInstrFragment прошла успешна");
        //endregion StepInstrFragment

        /* if (...) {
        ...
        return;
        } - проверки на неверные данные. */

        //endregion

        final RecipeCard recipeCard = recipeBuilder.setNewRecipe().getResult();

        final StorageFirebaseHelper storageFirebaseHelper = StorageFirebaseHelper.getInstance();
        FirestoreHelper firestoreHelper = FirestoreHelper.getInstance();
        //TODO: Сложно читать, отрефакторить код.
        firestoreHelper.addRecipeCard(recipeCard).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Timber.d("Рецепт успешно загружен.");
                Timber.d(documentReference.getId());
                String id = documentReference.getId();
                //TODO: Сделать main_photo константой
                storageFirebaseHelper.uploadFile(StorageFirebaseHelper.RECIPES_MAIN_PHOTO+"/"+id+"/main_photo",
                        image_uri).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.d(e.getStackTrace().toString());
                    }
                });
            }
        });
        Timber.i("RecipeCard успешно создана");
        Intent data = new Intent();
        //TODO: Отрефакторить все соотв. на RecipeCard.
        data.putExtra(ReciepsFragment.BRIEFCARD_DATA, recipeCard);
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
}
