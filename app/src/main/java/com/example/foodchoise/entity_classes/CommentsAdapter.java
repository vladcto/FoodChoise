package com.example.foodchoise.entity_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class CommentsAdapter extends FirestorePagingAdapter<UserReview, CommentsAdapter.MyViewHolder> {

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    CommentsAdapter(@NonNull FirestorePagingOptions<UserReview> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UserReview model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
        }

        public void bind(UserReview userReview) {
            comment.setText(userReview.getComment());
        }
    }
}
