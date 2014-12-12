package Model.Players.NeuralNetwork;


/**
 * Interface to a sink node in a hidden or output layer that has
 * an associated error.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */

public interface Sink
{   
    /**
     * Add a source.
     * @param src The source.
     */
    public void addSource(Source src);
    /**
     * Return the error value.
     * @param cycle Which cycle's error is required.
     * @return The error for the given cycle.
     */
    public double getError(long cycle);
    /**
     * Return the propagated error value.
     * @param cycle Which cycle's error is required.
     * @return The propagated error for the given cycle.
     */
    public double getPropagatedError(long cycle, Source src);
}
