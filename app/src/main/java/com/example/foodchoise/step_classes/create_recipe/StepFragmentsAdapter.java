package com.example.foodchoise.step_classes.create_recipe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodchoise.step_classes.GetFragmentItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class StepFragmentsAdapter extends FragmentPagerAdapter implements GetFragmentItemAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();

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

}
