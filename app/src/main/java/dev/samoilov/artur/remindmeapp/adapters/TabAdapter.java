package dev.samoilov.artur.remindmeapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dev.samoilov.artur.remindmeapp.fragments.DoneTaskFragment;
import dev.samoilov.artur.remindmeapp.fragments.CurrentTaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numbersOfTabs;

    public static final int TASK_FRAGMENT_POSITION = 0;
    public static final int HISTORY_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    public TabAdapter(FragmentManager fm, int numbersOfTabs) {
        super(fm);
        this.numbersOfTabs = numbersOfTabs;

       currentTaskFragment = new CurrentTaskFragment();
       doneTaskFragment = new DoneTaskFragment();

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return currentTaskFragment;
            case 1:
                return doneTaskFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numbersOfTabs;
    }
}
