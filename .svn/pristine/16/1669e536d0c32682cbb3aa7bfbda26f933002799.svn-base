package Model.Players.NeuralNetwork;


/**
 * Interface for a node in the output layer.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public interface Output extends Sink, Node
{
    /**
     * Make adjustments on cycle in the light of the expected value.
     * @param cycle Which cycle the adjustment applies to.
     * @param expectedValue The expected value.
     */
    public void adjust(long cycle, double expectedValue);
    /**
     * Back propagate the most recent error.
     * @param cycle Which cycle the propagation applies to.
     */
    public void backPropagate(long cycle);
}
