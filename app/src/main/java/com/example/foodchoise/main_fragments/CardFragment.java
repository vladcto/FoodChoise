package com.example.foodchoise.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.CardStackViewAdapter;
import com.example.foodchoise.entity_classes.OldBriefRecipeCardAdapter;
import com.example.foodchoise.entity_classes.RecipeCard;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import timber.log.Timber;


public class CardFragment extends Fragment implements com.yuyakaido.android.cardstackview.CardStackListener {

    CardStackViewAdapter adapter;
    OldBriefRecipeCardAdapter recipeCardAdapter;
    List<RecipeCard> cards = new ArrayList<RecipeCard>();
    RecyclerView recyclerView;
    CardStackView stackView;
    int maxSizeCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.i("create");
        View view = inflater.inflate(R.layout.page_card, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recipeCardAdapter = new OldBriefRecipeCardAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recipeCardAdapter);

        //Настраиваем StackView
        stackView = view.findViewById(R.id.cardStackView);

        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(getContext(), this);
        cardStackLayoutManager.setStackFrom(StackFrom.None);

        stackView.setLayoutManager(cardStackLayoutManager);
        adapter = AdapterBuilder.getCardStackAdapter(getActivity(), getRandomQuery());
        stackView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //region Listener for StackView
    boolean first = true;
    @Override
    public void onCardDragging(Direction direction, float ratio) {
        if (first) {
            maxSizeCards = adapter.getCurrentList().size() - 1;
            Timber.i("size" + adapter.getCurrentList().size());
            first = false;
        }
    }

    int itemDisapered;

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            onCardAccepted();
        } else if (direction == Direction.Left) {
            onCardRejected();
        } else {
            Timber.w("Not realize direction = %s", direction);
        }
        checkRecycler(itemDisapered);
    }

    @Override
    public void onCardRewound() {
        Timber.i("2");
    }

    @Override
    public void onCardCanceled() {
    }

    @Override
    public void onCardAppeared(View view, int position) {
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        Timber.e("елемент " + position);
        itemDisapered = position;
    }

    HashSet<Integer> set = new HashSet<>();
    //Методы для обработки событий.
    private void onCardAccepted() {
        if (!set.contains(itemDisapered)) {
            set.add(itemDisapered);
            cards.add(FirestoreHelper.createRecipeCardFromSnapshot(adapter.getCurrentList().get(itemDisapered)));
        }
        if (cards.size() == 5) {
            workDone();
        }
    }

    private void onCardRejected() {
    }
    //endregion Listener for StackView

    private void workDone() {
        stackView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (cards.size() != 0) {
            recipeCardAdapter.addRecipesCard(cards);
            Toast.makeText(getContext(), R.string.card_complete, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), R.string.select_anymore, Toast.LENGTH_LONG).show();
        }
    }

    private void checkRecycler(int position) {
        Timber.i(maxSizeCards + " " + position);
        if (maxSizeCards == position) {
            workDone();
        }
    }

    private @NonNull
    Query getRandomQuery() {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES);
        int randomFirst = (int) (Math.random() * 3) + 1;
        int randomSecond = (int) Math.round(Math.random());
        Timber.i(randomFirst + " " + randomSecond);
        String field = "random_" + String.valueOf(randomFirst);
        Query query;
        if (randomSecond == 1) {
            query = collectionReference.orderBy(field, Query.Direction.ASCENDING)
                    .limit(10);
        } else {
            query = collectionReference.orderBy(field, Query.Direction.DESCENDING)
                    .limit(10);
        }

        return query;
    }
}
