package com.example.foodchoise.step_classes.create_recipe;

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
import com.example.foodchoise.step_classes.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;

public class StepInstrIngridientAdapter extends RecyclerView.Adapter<StepInstrIngridientAdapter.ItemInstrIngridientViewHolder> implements ItemTouchHelperAdapter {
    ArrayList<String> step_instr_ingridients = new ArrayList<String>();

    public StepInstrIngridientAdapter() { }

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
        Timber.i("Добавлена инструкция/ингридиент.");
        notifyDataSetChanged();
    }

    /**
     * Получить созданные пользователем шаги (пустые и заполненные).
     *
     * @return ArrayList созданных шагов.
     */
    public ArrayList<String> getInstrIngrid(){
        return step_instr_ingridients;
    }

    @Override
    public void onItemDismiss(int position) {
        step_instr_ingridients.remove(position);
        Timber.i("С позиции %s удален ингр / инстр",position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,step_instr_ingridients.size()-position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap( step_instr_ingridients, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap( step_instr_ingridients, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
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
            //TODO: Проверить очно ли тут нужен тег d?
            Timber.d("Bind инструкцию/ингридиент на позиции: %s",position);
            //Для предовтращения добавления >1 слушателя на один TextView
            if(textWatcher!= null) {
                stepInstrIngridient.removeTextChangedListener(textWatcher);
            }
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Если изменение вызвано не программой.
                    if(stepInstrIngridient.isFocused()){
                        Timber.d("Изменен текст на позиции %s",position+1);
                        step_instr_ingridients.set(position,s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) { }
            };
            ordinal.setText(String.valueOf(position+1));
            //что-бы при bind не удалялось введенное значение.
            stepInstrIngridient.setText(step_instr_ingridients.get(position));
            stepInstrIngridient.addTextChangedListener(textWatcher);
        }
    }
}
