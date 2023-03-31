
package com.openclassrooms.entrevoisins.neighbour_list;

import static org.junit.Assert.assertTrue;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import java.util.List;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    /**
     * Fixed items count on the list
     */
    private static int ITEMS_COUNT = 12;

    /**
     * First position item
     */
    private static int FIRST_POSITION_ITEM = 0;

    /**
     * The list of neighbours activity
     */
    private ListNeighbourActivity mActivity;

    /**
     * The API service
     */
    private NeighbourApiService mService;

    /**
     * A scenario that launch the list of neighbours activity
     */
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    /**
     * Setup the test (before each test)
     */
    @Before
    public void setUp() {
        // Get the activity
        mActivity = mActivityRule.getActivity();
        // assert that the activity is not null
        assertThat(mActivity, notNullValue());
        // Get the API service
        mService = DI.getNewInstanceApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours))
            .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.list_neighbours))
            .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.list_neighbours))
            .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.list_neighbours))
            .check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we select an item, the details/profile activity is shown
     */

    public void myNeighboursList_onClickItem_shouldOpenProfileActivity() {
        // Given : we start the Details/Profile Activity
        // When perform a click on item position
        onView(withId(R.id.list_neighbours))
            .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_POSITION_ITEM, click()));
        // Then : we check if textView name_text in Profile Activity is displayed
        onView(withId(R.id.name_text))
            .check(matches(isDisplayed()));
    }

    /**
     * When we select an neighbour, the details/profile activity - of this neighbour - is shown
     */

    @Test
    public void nameText_onProfileActivity_isCorrect() {
        // Pick a neighbour from the service
        List<Neighbour> neighboursList = mService.getNeighbours();
        Neighbour aNeighbour = neighboursList.get(FIRST_POSITION_ITEM);

        // Given : correct name in textView from Profile Activity
        // When : open the details/profile Activity
        onView(withId(R.id.list_neighbours))
            .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_POSITION_ITEM, click()));
        // Then : we check if text Name displayed in DetailActivity match with neigbour name.
        onView(withId(R.id.name_text))
            .check(matches(withText(aNeighbour.getName())));
    }

    /**
     * Verify if favorite tab item list shows only favorites neighbours.
     */

    @Test
    public void myFavoritesList_onFavoriteTabItem_showOnlyFavoriteNeighbours() {

        // Given : favorite neighbours and only favorite neighbours in favorite tab item

        // When : add two favorites onClick on the "fav_button" (details/profile activity) from the full list of neighbours
        onView(withId(R.id.list_neighbours))
            .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_POSITION_ITEM, click()));
        onView(withId(R.id.fav_button))
            .perform(click());

        pressBack();

        onView(withId(R.id.list_neighbours))
            .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_POSITION_ITEM+1, click()));
        onView(withId(R.id.fav_button))
            .perform(click());

        pressBack();

        onView(withId(R.id.container)) // container declared in Activity List Neighbour
            .perform(swipeLeft());

        pressBack();

        // Then : Check if the number of items in Favorite list is same as the number neigbours we added.
        onView(withId(R.id.list_neighbours_favorite))
            .check(withItemCount(2));

    }

}

