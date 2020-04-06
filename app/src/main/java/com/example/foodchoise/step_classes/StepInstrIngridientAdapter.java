package com.example.foodchoise.step_classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

public class StepInstrIngridientAdapter extends RecyclerView.Adapter<StepInstrIngridientAdapter.ItemInstrIngridientViewHolder> {
    List<String> step_instr_ingridients = new ArrayList<String>();

    @NonNull
    @Override
    public ItemInstrIngridientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_instr_ingridient, parent, false);
        return new ItemInstrIngridientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInstrIngridientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return step_instr_ingridients.size();
    }

    public void addItem(){
        step_instr_ingridients.add("");
        notifyDataSetChanged();
    }

    class ItemInstrIngridientViewHolder extends RecyclerView.ViewHolder{
        TextView ordinal;
        EditText stepInstrIngridient;
        TextWatcher textWatcher;

        ItemInstrIngridientViewHolder(@NonNull View itemView) {
            super(itemView);
            ordinal= itemView.findViewById(R.id.ordinal);
            stepInstrIngridient = itemView.findViewById(R.id.input_instr_ingridient);
        }

        void bind(final int position){
            if(textWatcher!= null) {
                stepInstrIngridient.removeTextChangedListener(textWatcher);
            }
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(stepInstrIngridient.isFocused()){
                        Timber.d("Изменен текст на позиции %s",position);
                        step_instr_ingridients.set(position,s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            ordinal.setText(String.valueOf(position+1));
            stepInstrIngridient.setText(step_instr_ingridients.get(position));
            stepInstrIngridient.addTextChangedListener(textWatcher);
        }
    }
}