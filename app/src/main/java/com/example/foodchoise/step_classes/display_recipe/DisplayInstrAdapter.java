package com.example.foodchoise.step_classes.display_recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;

import java.util.ArrayList;

public class DisplayInstrAdapter extends RecyclerView.Adapter<DisplayInstrAdapter.InstructionViewHolder> {
    ArrayList<String> display_instr = new ArrayList<String>();

    public DisplayInstrAdapter(ArrayList<String> display_instr) {
        this.display_instr = display_instr;
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
        return display_instr.size();
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
            stepInstr.setText(display_instr.get(position));
        }
    }
}
