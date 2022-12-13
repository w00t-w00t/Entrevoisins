package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all the Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour the neighbour to delete
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour the neighbour to create
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get all the favorite Neighbours
     * @return the list of favorite neighbours
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Add a neighbour to the favorite list
     * @param neighbour the neighbour to add
     * @param isFavorite indicate if the neighbour is favorite or not
     */
    void setIsFavoriteNeighbour(Neighbour neighbour, boolean isFavorite);

}

