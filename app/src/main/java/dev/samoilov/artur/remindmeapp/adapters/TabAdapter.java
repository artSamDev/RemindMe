package dev.samoilov.artur.remindmeapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dev.samoilov.artur.remindmeapp.fragments.HistoryFragment;
import dev.samoilov.artur.remindmeapp.fragments.TaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numbersOfTabs;

    public TabAdapter(FragmentManager fm, int numbersOfTabs) {
        super(fm);
        this.numbersOfTabs = numbersOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TaskFragment();
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numbersOfTabs;
    }
}
