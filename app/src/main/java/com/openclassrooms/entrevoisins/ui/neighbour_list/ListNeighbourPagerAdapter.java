package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A fragment pager adapter to handle the tabs
 */
public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    /**
     * Constructor
     * @param fm the fragment manager that will keep each fragment's state in the adapter across swipes.
     */
    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position position of the page
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        // determine if the fragment is the first (position == 0) or the second (position == 1)
        boolean isFavoriteFragmentRequested = position == 1;
        // return the fragment
        return NeighbourFragment.newInstance(isFavoriteFragmentRequested);
    }

    /**
     * get the number of pages
     * @return number of pages
     */
    @Override
    public int getCount() {
        // 2, because we have the neighbour list and the favorite list
        return 2;
    }
}

