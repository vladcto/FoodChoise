package com.example.foodchoise.step_classes.display_recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.ArrayList;

public class DisplayIngrAdapter extends RecyclerView.Adapter<DisplayIngrAdapter.IngridientViewHolder> {
    ArrayList<String> display_ingr = new ArrayList<String>();

    public DisplayIngrAdapter(ArrayList<String> display_ingr) {
        this.display_ingr = display_ingr;
    }

    @NonNull
    @Override
    public DisplayIngrAdapter.IngridientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_display_instr_ingridient, parent, false);
        return new DisplayIngrAdapter.IngridientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayIngrAdapter.IngridientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return display_ingr.size();
    }

    protected class IngridientViewHolder extends RecyclerView.ViewHolder{
        TextView ordinal;
        TextView stepInstrIngridient;

        IngridientViewHolder(@NonNull View itemView) {
            super(itemView);
            ordinal = itemView.findViewById(R.id.ordinal);
            stepInstrIngridient = itemView.findViewById(R.id.display_instr_ingridient);
        }

        void bind(final int position){
            ordinal.setText(String.valueOf(position+1));
            stepInstrIngridient.setText(display_ingr.get(position));
        }
    }
}
