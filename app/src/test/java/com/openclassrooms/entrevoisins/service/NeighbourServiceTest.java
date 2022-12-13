package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    /**
     * The neighbour service
     */
    private NeighbourApiService service;

    /**
     * Setup the service
     */
    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    /**
     * Test if we get all our neighbours
     */
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /**
     * Test if we deletion is working
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * Test if we can add a neighbour to favorites
     */
    @Test
    public void getFavoriteNeighboursListNotEmpty(){
        Neighbour neighbour = service.getNeighbours().get(0);
        service.setIsFavoriteNeighbour(neighbour,true);
        assertFalse(service.getFavoriteNeighbours().isEmpty());
    }

    /**
     * Test if we can add a neighbour to favorites
     */
    @Test
    public void enableFavoriteNeighbourWithSuccess() {
        Neighbour aNeighbour = service.getNeighbours().get(0);
        // as we use serializable (with the intent), we reproduce the same behaviour as the app,
        // by copying the neighbour to a new one
        Neighbour clonedNeighbour = new Neighbour(
                aNeighbour.getId(), // only the ID is relevant for equals() method test
                aNeighbour.getName(), aNeighbour.getAvatarUrl(), aNeighbour.getAddress(),
                aNeighbour.getPhoneNumber(), aNeighbour.getAboutMe()
        );
        // we add the neighbour to favorites
        service.setIsFavoriteNeighbour(clonedNeighbour, true);
        // we check if the neighbour is in the favorites list
        assertTrue(aNeighbour.isFavorite());
        assertEquals(aNeighbour, service.getFavoriteNeighbours().get(0));
    }

    /**
     * Test if we can remove a neighbour from favorites
     */
    @Test
    public void disableFavoriteNeighbourWithSuccess(){
        // get the neighbour
        Neighbour aNeighbour = service.getNeighbours().get(2);

        // add the neighbour to favorites
        service.setIsFavoriteNeighbour(aNeighbour, true);
        // check if the neighbour is in the favorites list (list not empty)
        assertFalse(service.getFavoriteNeighbours().isEmpty());

        // remove the neighbour from favorites
        service.setIsFavoriteNeighbour(aNeighbour, false);
        // check if the neighbour is not in the favorites list (list empty)
        assertTrue(service.getFavoriteNeighbours().isEmpty());

        // check if the neighbour is not in the favorites list (list expected to be empty)
        assertFalse(service.getFavoriteNeighbours().contains(aNeighbour));
    }
}


