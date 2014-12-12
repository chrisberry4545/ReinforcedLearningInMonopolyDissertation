package Model.Players.NeuralNetwork;

/**
 * A basic (non-TD) output node.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class BasicOutput extends BasicNode implements Output
{
    /**
     * Constructor for objects of class BasicOutput
     */
    public BasicOutput()
    {
        super();
    }
    
    /**
     * Make adjustments in the light of the expected value.
     * @param cycle The cycle of the adjustment.
     * @param expectedValue The expected value.
     */
    @Override
    public void adjust(long cycle, double expectedValue)
    {
        double value = getValue();
        setErrorTerm((expectedValue - value) * value * (1 - value),
                     cycle);
    }
    
    /**
     * Backpropagate the error.
     * @param cycle The cycle of the back propagation.
     */
    @Override
    public void backPropagate(long cycle)
    {
        for(Source src : getSources()) {
            src.updateError(cycle);
        }
    }

    /**
     * Calculate the error term for the given cycle.
     * @param cycle The cycle of the error term.
     */
    @Override
    public void calculateErrorTerm(long cycle)
    {
        //throw new UnsupportedOperationException();
    }
    
    /**
     * Return the error. This should only be requested
     * after calculateErrorTerm has been called for the
     * given cycle.
     * @param cycle The cycle of the error.
     */
    @Override
    public double getError(long cycle)
    {
        if(cycle != getLastErrorCycle()) {
            throw new RuntimeException("Cycle mismatch: " + cycle +
                                       " vs " + getLastErrorCycle());
        }
        return getErrorTerm();
    }
}
