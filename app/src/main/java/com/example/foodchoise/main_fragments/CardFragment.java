package com.example.foodchoise.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.CardStackViewAdapter;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import timber.log.Timber;


public class CardFragment extends Fragment implements com.yuyakaido.android.cardstackview.CardStackListener {

    CardStackViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.i("create");
        View view = inflater.inflate(R.layout.page_card, container, false);

        //Настраиваем StackView
        CardStackView stackView = view.findViewById(R.id.cardStackView);

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
    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Timber.v("Card Drag");
    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            onCardAccepted();
        } else if (direction == Direction.Left) {
            onCardRejected();
        } else {
            Timber.w("Not realize direction = %s",direction);
        }
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

    //Методы для обработки событий.
    private void onCardAccepted(){
        Timber.i("Card Accepted");
    }

    private void onCardRejected(){
        Timber.i("Card Rejected");
    }
    //endregion Listener for StackView

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
