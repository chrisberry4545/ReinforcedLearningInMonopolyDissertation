package Model.Players.NeuralNetwork;

import java.io.Serializable;

/**
 * A Source whose value is always 1.0.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class InputZero extends Source implements Serializable
{   
    // InputZero sources are immutable so an singleton is appropriate.
    private static InputZero singleton = new InputZero();
    
    /**
     * Suppress external instanciation.
     */
    private InputZero()
    {
        
    }
    
    /**
     * Return the singleton instance.
     * 
     * @return The singleton instance.
     */
    public static InputZero getInstance()
    {
        return singleton;
    }

    /**
     * Always return 1.0, regardless of the cycle.
     * @return 1.0
     */
    @Override
    final public double getValue()
    {
        return 1.0;
    }
    
    /**
     * Update the error.
     * No update is implemented.
     * @param cycle 
     */
    @Override
    public void updateError(long cycle)
    {
        // No update required.
    }
    
    /**
     * Freeze the value.
     * Since the same value is always returned, no freezing is required.
     * @param cycle 
     */
    public void freezeValue(long cycle)
    {
    }
    
    /**
     * Unfreeze the value.
     * Since the same value is always returned, no unfreezing is required.
     * @param cycle 
     */
    public void unfreeze()
    {
    }

    /**
     * Return a string representation of this source.
     * @return "IZ: 1.0"
     */
    @Override
    public String toString()
    {
        return "IZ: " + getValue();
    }
    
}
