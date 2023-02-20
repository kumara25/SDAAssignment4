package com.example.sdaassign4_2022;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*
 * viewPager adapter.
 * @author Chris Coughlan 2019
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private Context context;

    /**
     * intialize
     * @param fm
     * @param behavior
     * @param nContext
     */
    ViewPageAdapter(FragmentManager fm, int behavior, Context nContext) {
        super(fm, behavior);
        context = nContext;
    }

    /**
     * get fragment instance
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();

        //finds the tab position (note array starts at 0)
        position = position+1;

        //finds the fragment
        switch (position)
        {
            case 1:
                //code
                fragment = new Welcome();
                break;
            case 2:
                //code
                fragment = new BookList();
                break;
            case 3:
                //code
                fragment = new Settings();
                break;
        }

        return fragment;
    }

    /**
     * get tab count
     * @return
     */
    @Override
    public int getCount() {
        return 3;
    }

    /**
     * get page title
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        position = position+1;

        CharSequence tabTitle = "";

        switch (position)
        {
            case 1:

                tabTitle = context.getResources().getString(R.string.home);
                break;
            case 2:

                tabTitle = context.getResources().getString(R.string.books);
                break;
            case 3:

                tabTitle = context.getResources().getString(R.string.settings);
                break;
        }

        return tabTitle;
    }
}
