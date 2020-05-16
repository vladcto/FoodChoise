package com.example.foodchoise.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.CardStackViewAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

import leakcanary.AppWatcher;
import timber.log.Timber;

public class CardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_card,container,false);

        //Настраиваем StackView
        CardStackView stackView = view.findViewById(R.id.cardStackView);
        StackChoiceListener listener = new StackChoiceListener();
        AppWatcher.INSTANCE.getObjectWatcher().watch(listener,"StackChoiceListener was detached");
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(getContext(),listener);
        stackView.setLayoutManager(cardStackLayoutManager);
        CardStackViewAdapter adapter = new CardStackViewAdapter();
        stackView.setAdapter(adapter);

        /*
        FirestoreHelper firestoreHelper = FirestoreHelper.getInstance();
        adapter.addRecipesCard(firestoreHelper.getRecipesCard());
         */

        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        adapter.TEST_addRecipeCard(new RecipeCard("name","dssd",new ArrayList<String>(),new ArrayList<String>()));
        return view;
    }

    static class StackChoiceListener implements com.yuyakaido.android.cardstackview.CardStackListener{

        @Override
        public void onCardDragging(Direction direction, float ratio) {
            Timber.d("Card Drag");
        }

        @Override
        public void onCardSwiped(Direction direction) {
            Timber.d("Card Swiped");
        }

        @Override
        public void onCardRewound() {
            Timber.d("Card Rewound");
        }

        @Override
        public void onCardCanceled() {
            Timber.d("Card Canceled");
        }

        @Override
        public void onCardAppeared(View view, int position) {
            Timber.d("Card Appeared");
        }

        @Override
        public void onCardDisappeared(View view, int position) {
            Timber.d("Card Disappeared");
        }
    }
}
