package com.openclassrooms.entrevoisins.ui.neighbour_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity which displays the profile details of a neighbour
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Tag for logging, passing data to the activity, ...
     */
    public static final String NEIGHBOUR_ARG = "NEIGHBOUR";

    /**
     * UI Components
     */

    // previous button
    @BindView(R.id.previous_button)
    public ImageButton mButtonPrevious;

    // avatar image
    @BindView(R.id.avatar_image)
    public ImageView mImage;

    // favorite button
    @BindView(R.id.fav_button)
    public FloatingActionButton mButtonFav;

    // name text (at the top)
    @BindView(R.id.name_text)
    public TextView mTextName;

    // about text
    @BindView(R.id.about_text)
    public TextView mTextAbout;

    // name text (at the middle)
    @BindView(R.id.name2_text)
    public TextView mTextName2;

    // address text
    @BindView(R.id.address_text)
    public TextView mTextAddress;

    // phone text
    @BindView(R.id.phone_text)
    public TextView mTextPhone;

    // web text
    @BindView(R.id.website_text)
    public TextView mTextWebsite;

    /**
     * Neighbour API service
     */
    private NeighbourApiService mApiService;

    /**
     * Method called when the activity is created
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // always call the super method first
        super.onCreate(savedInstanceState);
        // set the content view
        setContentView(R.layout.activity_profile);
        // bind the UI components
        ButterKnife.bind(this);
        // get the neighbour API service
        mApiService = DI.getNeighbourApiService();
        // get the neighbour from the intent
        Intent mIntent = getIntent();
        Neighbour neighbour = (Neighbour) mIntent.getSerializableExtra(ProfileActivity.NEIGHBOUR_ARG);

        // NOTE : in case of parcelable, use this instead :
        // neighbour = getIntent().getParcelableExtra(ProfileActivity.NEIGHBOUR_ARG);


        // generate the social network URI
        String mLinkFb = "https://facebook.com/" + neighbour.getName();
        // load the avatar image
        Glide.with(mImage.getContext())
                .load(neighbour.getAvatarUrl())
                .centerCrop()
                .into(mImage);
        // set the ui name text (at the top)
        mTextName.setText(neighbour.getName());
        // set the ui about text
        mTextAbout.setText(neighbour.getAboutMe());
        // set the ui name text (at the middle)
        mTextName2.setText(neighbour.getName());
        // set the ui address text
        mTextAddress.setText(neighbour.getAddress());
        // set the ui phone text
        mTextPhone.setText(neighbour.getPhoneNumber());
        // set the ui web text
        mTextWebsite.setText(mLinkFb);

        // set the favorite icon is the neighbour is favorite
        setNeighbourFavoriteIconIfNeeded(neighbour);

        // set the previous button listener to go back to the previous activity
        mButtonPrevious.setOnClickListener(v -> finish());

        // set the favorite button listener to add/remove the neighbour from the favorite list
        mButtonFav.setOnClickListener(v -> {
            // if the neighbour is favorite, remove it from the favorite list
            // else add it to the favorite list
            mApiService.setIsFavoriteNeighbour(neighbour, !neighbour.isFavorite());
            // set the favorite icon is the neighbour is favorite
            setNeighbourFavoriteIconIfNeeded(neighbour);
        });
    }

    /**
     * Set the favorite icon is the neighbour is favorite
     * else, set the standard icon
     * @param neighbour the neighbour
     */
    public void setNeighbourFavoriteIconIfNeeded(Neighbour neighbour) {
        mButtonFav.setImageResource(
                !neighbour.isFavorite() ?
                R.drawable.ic_star_border_white_24dp :
                R.drawable.ic_star_white_24dp
        );
    }

    /**
     * Used to navigate to this activity
     * @param activity the activity that calls this method
     */
    public static void navigate(FragmentActivity activity, Neighbour neighbour) {
        // create the intent
        Intent anIntent = new Intent(activity, ProfileActivity.class);
        // push the neighbour to the intent
        anIntent.putExtra(ProfileActivity.NEIGHBOUR_ARG, (Serializable) neighbour);
        // start the activity
        ActivityCompat.startActivity(activity, anIntent, null);
    }
}