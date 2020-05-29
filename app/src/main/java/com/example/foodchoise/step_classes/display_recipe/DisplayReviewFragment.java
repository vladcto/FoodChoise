package com.example.foodchoise.step_classes.display_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.UserReview;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.willy.ratingbar.ScaleRatingBar;

public class DisplayReviewFragment extends Fragment {
    private ScaleRatingBar tastyRatingBar,priceRatingBar;
    private EditText commentEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_review,container,false) ;
        tastyRatingBar = view.findViewById(R.id.tastyRatingBar);
        priceRatingBar = view.findViewById(R.id.priceTastyBar);
        commentEditText = view.findViewById(R.id.commentEditText);
        final ImageButton button = view.findViewById(R.id.imageSendReview);
        final RadioButton radioEasy = view.findViewById(R.id.easy);
        final RadioButton radioNorm = view.findViewById(R.id.norm);
        final RadioButton radioHell = view.findViewById(R.id.hell);

        radioEasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioNorm.setChecked(false);
                    radioHell.setChecked(false);
                }
            }
        });
        radioNorm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioEasy.setChecked(false);
                    radioHell.setChecked(false);
                }
            }
        });
        radioHell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioEasy.setChecked(false);
                    radioNorm.setChecked(false);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayRecipeActivity activity = (DisplayRecipeActivity) getActivity();
                float tastyRating = tastyRatingBar.getRating();
                float priceRating = priceRatingBar.getRating();
                double hardRating;
                if (radioEasy.isChecked()) {
                    hardRating = 1;
                } else if (radioNorm.isChecked()) {
                    hardRating = 2;
                } else {
                    hardRating = 3;
                }

                UserReview.Builder builder = new UserReview.Builder();
                builder.setTastyRating(tastyRating)
                        .setPriceRating(priceRating)
                        .setHardRating(hardRating)
                        .addComment(commentEditText.getText().toString());
                FirestoreHelper.getInstance().sendReview(builder.build(), activity.getRecipeCard().getID(), ((DisplayRecipeActivity) getActivity()).getRecipeCard());
            }
        });
        return view;
    }
}
