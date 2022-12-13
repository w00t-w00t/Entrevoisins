package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.entrevoisins.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * List Neighbours screen
 */
public class ListNeighbourActivity extends AppCompatActivity {

    /**
     * Ui components
     */

    // tabs for the view pager
    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    // the toolbar
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // the view pager
    @BindView(R.id.container)
    ViewPager mViewPager;

    /**
     * The onCreate method called when the activity is created
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // always call the super method first
        super.onCreate(savedInstanceState);
        // set the content view, to the proper activity layout
        setContentView(R.layout.activity_list_neighbour);
        // bind the ui components
        ButterKnife.bind(this);
        // set the toolbar
        setSupportActionBar(mToolbar);
        // set the view pager adapter to the list neighbour pager adapter
        mViewPager.setAdapter(new ListNeighbourPagerAdapter(getSupportFragmentManager()));
        // set the tab layout with the view pager
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        // set the view pager with the tab layout
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    /**
     * The onClick method called when the user clicks on the add button
     */
    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }
}
