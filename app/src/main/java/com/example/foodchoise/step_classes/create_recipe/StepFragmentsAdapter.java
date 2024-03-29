package com.example.foodchoise.step_classes.create_recipe;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodchoise.step_classes.GetFragmentItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class StepFragmentsAdapter extends FragmentPagerAdapter implements GetFragmentItemAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private String[] titles = new String[]{"Шаг 1","Шаг 2","Шаг 3","Готово!"};

    public StepFragmentsAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new StepNameFragment());
        fragments.add(new StepIngridientFragment());
        fragments.add(new StepInstrFragment());
        fragments.add(new StepAcceptFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
