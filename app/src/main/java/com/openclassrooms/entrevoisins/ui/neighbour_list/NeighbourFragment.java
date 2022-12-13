package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.SelectNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_profile.ProfileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

/**
 * Fragment representing the list of Neighbours
 */
public class NeighbourFragment extends Fragment {

    /**
     * Log tag
     */
    private static final String TAG = "NeighbourFragment";

    /**
     * Neighbour API service
     */
    private NeighbourApiService mApiService;

    /**
     * List of neighbours
     */
    private List<Neighbour> mNeighbours;

    /**
     * RecyclerView (UI list system)
     */
    private RecyclerView mRecyclerView;

    /**
     * Indicate if the fragment is the favorite list (or the neighbour list)
     */
    private Boolean favorite_only;

    /**
     * Create and return a new instance of the NeighbourFragment
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(Boolean favorite_only) {

        // Create a new fragment
        NeighbourFragment fragment = new NeighbourFragment();

        // Use the bundle to pass the favorite_only parameter
        Bundle args = new Bundle();
        args.putBoolean("favorite_only", favorite_only);
        fragment.setArguments(args);

        // Return the fragment
        return fragment;
    }

    /**
     * Method called when the fragment is being created
     * @param savedInstanceState The saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the api service from the dependency injection
        mApiService = DI.getNeighbourApiService();
        // get the favorite_only parameter from the bundle
        favorite_only = getArguments().getBoolean("favorite_only");
        // do some logging with the appropriate TAG
        Log.d(TAG, "favorite_only = " + favorite_only);
    }

    /**
     * We override onCreateView, to further define the behavior of the UI at startup
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // We use different layouts to ambiguous view exceptions (same id twice) between the two fragments
        View view = inflater.inflate(
                favorite_only ? R.layout.fragment_neighbourfavorite_list : R.layout.fragment_neighbour_list
                , container, false);

        // Get the recycler view
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;

        // Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Set the divider between items on the list (vertical one)
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

        // return the view
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        // get the list of neighbours from the api service depending on the favorite_only parameter
        mNeighbours = favorite_only ? mApiService.getFavoriteNeighbours() : mApiService.getNeighbours();
        // set the adapter to the recycler view
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
    }

    /**
     * Method called when the fragment is visible : we init the list of neighbours being displayed
     */
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    /**
     * On activity start, we register this object to the event bus (to consume the events it will receive)
     */
    @Override
    public void onStart() {
        super.onStart();
        // register this object to the event bus
        if(!favorite_only)
            EventBus.getDefault().register(this);
    }

    /**
     * On activity stop, we unregister this object to the event bus (to stop consuming the events anymore)
     */
    @Override
    public void onStop() {
        super.onStop();
        // unregister from the event bus
        if(!favorite_only)
            EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event The delete event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        // we delete a neighbour from the list
        mApiService.deleteNeighbour(event.neighbour);
        // we refresh the list
        initList();
    }

    /**
     * Fired if a neighbour is selected
     */
    @Subscribe
    public void onSelectNeighbour(SelectNeighbourEvent event){
        // we start the profile details activity
        ProfileActivity.navigate(this.getActivity(), event.neighbour);
    }

}
