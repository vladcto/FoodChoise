package com.example.foodchoise.main_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.example.foodchoise.themeUtil.ThemeController;

import java.lang.ref.WeakReference;

public class ThemesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themes_choice,container,false);
        SwitchCompat switchCompat = view.findViewById(R.id.switchThemeButton);
        switchCompat.setChecked(ThemeController.getIdThemeNow() == 1);
        switchCompat.setOnCheckedChangeListener(this);
        return view;
    }

    WeakReference<Context> contextWeakReference;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contextWeakReference = new WeakReference<>(context);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked){
            ThemeController.setLightTheme(contextWeakReference.get());
        }else{
            ThemeController.setDarkTheme(contextWeakReference.get());
        }
        getActivity().recreate();
    }
}
