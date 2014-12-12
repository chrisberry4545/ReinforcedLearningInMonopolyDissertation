package Model.Players.NeuralNetwork;

import java.io.Serializable;


/**
 * A source of input to a node.
 * Each source has a unique ID and provides a value.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */

public abstract class Source implements Serializable
{
    // Unique id for each source.
    private static int nextID = 0;
    
    // Used for source-list indexing.
    private int id;

    public Source()
    {
        id = nextID;
        nextID++;
    }

    /**
     * Return this source's unique ID.
     * @return the ID
     */
    public int getID()
    {
        return id;
    }
    
    
    /**
     * Return the value.
     */
    public abstract double getValue();
    /**
     * Update the error on the given cycle.
     */
    public abstract void updateError(long cycle);
}
