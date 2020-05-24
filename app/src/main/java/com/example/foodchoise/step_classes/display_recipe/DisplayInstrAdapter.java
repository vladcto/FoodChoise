package com.example.foodchoise.step_classes.display_recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.List;

public class DisplayInstrAdapter extends RecyclerView.Adapter<DisplayInstrAdapter.InstructionViewHolder> {
    List<String> displayInstr;

    public DisplayInstrAdapter(List<String> displayInstr) {
        this.displayInstr = displayInstr;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_display_instruction, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return displayInstr.size();
    }

    protected class InstructionViewHolder extends RecyclerView.ViewHolder{
        TextView ordinal;
        TextView stepInstr;

        InstructionViewHolder(@NonNull View itemView) {
            super(itemView);
            ordinal = itemView.findViewById(R.id.ordinal);
            stepInstr = itemView.findViewById(R.id.display_instruction_text);
        }

        void bind(final int position){
            ordinal.setText(String.valueOf(position+1));
            stepInstr.setText(displayInstr.get(position));
        }
    }
}
