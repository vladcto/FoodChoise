package com.example.foodchoise.step_classes.display_recipe;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodchoise.step_classes.GetFragmentItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragmentAdapter extends FragmentPagerAdapter implements GetFragmentItemAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();
    //TODO: Сделать через ресурсы.
    private String[] titles = new String[]{"Инфо","Состав","Шаги","Оценка"};

    public DisplayFragmentAdapter(FragmentManager fm, DisplayRecipeActivity activity) {
        super(fm);
        fragments.add(new DisplayNameFragment(activity));
        fragments.add(new DisplayIngrFragment(activity));
        fragments.add(new DisplayInstrFragment(activity));
        fragments.add(new DisplayReviewFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
