package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    /**
     * Dummy list of neighbours
     */
    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        // create a new list to store favorite neighbours
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        // for each neighbour in the list of neighbours
        for (Neighbour currentNeighbour : neighbours) {
            // if the neighbour is favorite
            if (currentNeighbour.isFavorite().equals(Boolean.TRUE)) {
                // add the neighbour to the list of favorite neighbours
                favoriteNeighbours.add(currentNeighbour);
            }
        }
        // return the list of favorite neighbours
        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsFavoriteNeighbour(Neighbour neighbourCopy, boolean isFavorite) {
        // We use the Neighbour as a serizalizable object to pass it to the activity
        // thanks to the intent. So we need to find the original neighbour in the list to update it.
        // We basically search the original neighbour from its copy.
        for (Neighbour currentOriginalNeighbour : neighbours) {
            // if the current origin neighbour is the same as the copy
            if(currentOriginalNeighbour.equals(neighbourCopy)){
                // we update the original neighbour, and set is as favorite
                currentOriginalNeighbour.setIsFavorite(isFavorite);
                // for retro-compatibility, we update the copy too
                neighbourCopy.setIsFavorite(isFavorite);
            }
        }
    }

}

