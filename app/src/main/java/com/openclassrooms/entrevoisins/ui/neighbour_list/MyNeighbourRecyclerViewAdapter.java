package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.SelectNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for the list of neighbours
 */
public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    /**
     * List of neighbours
     */
    private final List<Neighbour> mNeighbours;

    /**
     * Constructor
     * @param items list of neighbours
     */
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * (in our case, the ViewHolder represent a single neighbour at a time)
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // We create the view thanks to the layout inflater
        // Keep in mind the corresponding layout file "fragment_neighbour.xml", represent
        // a single neighbour
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);

        // build the view holder and return it back
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to know how to display the data at the specified position.
     * (in our case, this method will be called for each user in the list of neighbour to display)
     *
     * This method should update the contents of the {@link ViewHolder#itemView} to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // get the neighbour at the specified position in our list
        Neighbour neighbour = mNeighbours.get(position);

        // set the holder neighbour name with the neighbour name
        holder.mNeighbourName.setText(neighbour.getName());

        // set the holder neighbour avatar with the neighbour avatar
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                // apply a circle crop transformation to give it a round shape
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        // set the click action, when the user click on the neighbour avatar (or the whole row)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                  METHOD 1 : use the intent here

                    Intent intent = new Intent(v.getContext(), DetailNeighbourActivity.class);
                    intent.putExtra(DetailNeighbourActivity.NEIGHBOUR, (Serializable) neighbour);

                    // or with parcelable :
                    intent.putExtra(DetailNeighbourActivity.NEIGHBOUR, neighbour);

                    v.getContext().startActivity(intent);
                 */

                /*
                 * METHOD 2 : use the EventBus
                 */
                EventBus.getDefault().post(new SelectNeighbourEvent(neighbour));
            }
        });

        // set the click action, when the user click on the delete button
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use the EventBus to send the neighbour to delete
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    /**
     * The type View holder.
     *
     * Keep in mind that the View holder represent a single item at once.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * UI components
         */

        // the avatar of the neighbour
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;

        // the name of the neighbour
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;

        // the delete button
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        // the constructor
        public ViewHolder(View view) {
            super(view);
            // bind ui components
            ButterKnife.bind(this, view);
        }
    }
}

