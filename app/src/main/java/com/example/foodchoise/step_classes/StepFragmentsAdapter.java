package com.example.foodchoise.step_classes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StepFragmentsAdapter extends FragmentPagerAdapter {

    public StepFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StepNameFragment();

            default:
                return new StepNameFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
