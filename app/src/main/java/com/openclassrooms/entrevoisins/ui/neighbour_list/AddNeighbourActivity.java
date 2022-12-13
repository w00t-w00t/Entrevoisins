package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity which add a new neighbour
 */
public class AddNeighbourActivity extends AppCompatActivity {

    /**
     * Avatar image of the neighbour
     */
    @BindView(R.id.avatar)
    ImageView avatar;

    /**
     * Name of the neighbour
     */
    @BindView(R.id.nameTextInput)
    TextInputLayout nameInput;

    /**
     * Phone number of the neighbour
     */
    @BindView(R.id.phoneNumberTextInput)
    TextInputLayout phoneInput;

    /**
     * Address of the neighbour
     */
    @BindView(R.id.addressTextInput)
    TextInputLayout addressInput;

    /**
     * About (Facebook link) of the neighbour
     */
    @BindView(R.id.aboutMeTextInput)
    TextInputLayout aboutMeInput;

    /**
     * Create button
     */
    @BindView(R.id.create)
    MaterialButton addButton;

    /**
     * Neighbour API service
     */
    private NeighbourApiService mApiService;

    /**
     * Neighbour image URL
     */
    private String mNeighbourImage;

    /**
     * On UI activity creation
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // always call the superclass method first
        super.onCreate(savedInstanceState);
        // inflate the UI
        setContentView(R.layout.activity_add_neighbour);
        // bind UI components
        ButterKnife.bind(this);
        // make the icon clickable and add the < at the left of the icon
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // get the neighbour API service from the dependency injection class
        mApiService = DI.getNeighbourApiService();
        // set the default neighbour image
        init();
    }

    /**
     * When the user click on the icon at the left of the toolbar, go back to the previous activity
     *
     * Note that : setHomeButtonEnabled(true) does still call onOptionsItemSelected
     *
     * @param item the item clicked
     * @return true if the item is the home button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // finish the activity
            finish();
            // return true to consume the click event
            return true;
        }
        // in any case use default behaviour
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the activity
     */
    private void init() {
        // pick a random image
        mNeighbourImage = randomImage();

        // load the image
        Glide.with(this).load(mNeighbourImage).placeholder(R.drawable.ic_account)
                .apply(RequestOptions.circleCropTransform()).into(avatar);

        // add a text watcher to the name input
        nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                // enable the button if the name is not empty
                addButton.setEnabled(s.length() > 0);
            }
        });

    }

    /**
     * When the user click on the create button
     */
    @OnClick(R.id.create)
    void addNeighbour() {

        // build the neighbour object model
        Neighbour neighbour = new Neighbour(
                System.currentTimeMillis(),
                nameInput.getEditText().getText().toString(),
                mNeighbourImage,
                addressInput.getEditText().getText().toString(),
                phoneInput.getEditText().getText().toString(),
                aboutMeInput.getEditText().getText().toString()
        );
        // push that neighbour to the API service
        mApiService.createNeighbour(neighbour);

        // finish the activity (return the previous one)
        finish();

    }

    /**
     * Generate a random image. Useful to mock image picker
     * @return String
     */
    String randomImage() {
        return "https://i.pravatar.cc/150?u="+ System.currentTimeMillis();
    }

    /**
     * Used to navigate to this activity (pattern navigate)
     * @param activity the origin activity
     */
    public static void navigate(FragmentActivity activity) {
        // create the intent
        Intent intent = new Intent(activity, AddNeighbourActivity.class);
        // start the new activity (this activity)
        ActivityCompat.startActivity(activity, intent, null);
    }
}
