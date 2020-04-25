package com.example.foodchoise.step_classes.display_recipe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodchoise.step_classes.GetFragmentItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragmentAdapter extends FragmentPagerAdapter implements GetFragmentItemAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public DisplayFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 0;
    }
}
