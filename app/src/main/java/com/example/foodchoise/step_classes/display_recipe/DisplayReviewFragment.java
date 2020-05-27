package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.UserReview;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.willy.ratingbar.ScaleRatingBar;

import timber.log.Timber;

public class DisplayReviewFragment extends Fragment {
    private ScaleRatingBar tastyRatingBar,priceRatingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_review,container,false) ;
        tastyRatingBar = view.findViewById(R.id.tastyRatingBar);
        priceRatingBar = view.findViewById(R.id.priceTastyBar);
        ImageButton button = view.findViewById(R.id.imageSendReview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayRecipeActivity activity = (DisplayRecipeActivity) getActivity();
                float tastyRating = tastyRatingBar.getRating();
                float priceRating = priceRatingBar.getRating();
                Timber.i(tastyRating +" "+ priceRating);

                UserReview.Builder builder = new UserReview.Builder();
                builder.setTastyRating(tastyRating)
                        .setPriceRating(priceRating)
                        //FIXME Нет под коробкой добавления
                        .setHardRating(2);
                FirestoreHelper.getInstance().sendReview(builder.build(),activity.getRecipeCard().getID());
            }
        });
        return view;
    }
}
