package Model.Players.NeuralNetwork;

import java.io.Serializable;

/**
 * An input source to the network.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class InputValue extends Source implements  Serializable
{
    // The value of this source.
    private double value;

    /**
     * Constructor for objects of class Input
     */
    public InputValue()
    {
    }

    /**
     * Return the value of this source.
     * @return The source value.
     */
    @Override
    public double getValue()
    {
        return value;
    }
    
    /**
     * Set the value of this source.
     * @param value The new value
     */
    public void setValue(double value)
    {
        this.value = value;
    }
    
    /**
     * Return a string representation of this source.
     * @return "I: " and the value.
     */
    @Override
    public String toString()
    {
        return "I: " + getValue();
    }
    
    /**
     * Update the error.
     * No update is required.
     * @param cycle 
     */
    @Override
    public void updateError(long cycle)
    {
        // No update required.
    }
}
