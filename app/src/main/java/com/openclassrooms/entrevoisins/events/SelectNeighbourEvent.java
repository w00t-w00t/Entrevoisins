package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user selects a neighbour
 * This event is not perfectly relevant for the current project, but have an educational purpose
 */
public class SelectNeighbourEvent {

    /**
     * Neighbour to select
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour neighbour to select
     */
    public SelectNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
