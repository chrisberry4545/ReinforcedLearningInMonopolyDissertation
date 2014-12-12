package Model.Players.NeuralNetwork;

/**
 * A standard (non-TD) node.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class BasicNode extends AbstractNode
{
    /**
     * Constructor for objects of class Node
     */
    public BasicNode()
    {
        super();
    }

    /**
     * Adjust the weights according to the error for this node.
     * @param cycle The cycle of the adjustment.
     * @param alpha The learning rate.
     */
    @Override
    public void adjustWeights(long cycle, double alpha)
    {
        if(!isFrozen()) {
            throw new IllegalStateException();
        }
        double delta = getError(cycle);
        Source[] sources = getSources();
        for(int w = 0; w < sources.length; w++) {
            Source i = sources[w];
            setWeight(w, getWeight(i) + alpha * delta * i.getValue());
        }
    }
    
    /**
     * Calculate the delta for this node.
     * @param cycle The cycle of the error term.
     */
    @Override
    public void calculateErrorTerm(long cycle)
    {
        if(isFrozen()) {
            throw new IllegalStateException();
        }
        double term = 0;
        for(Sink s : getSinks()) {
            term += s.getPropagatedError(cycle, this);
        }
        double value = getValue();
        term *= value * (1 - value);
        setErrorTerm(term, cycle);
    }
    
    /**
     * Update the error term for this cycle, and have
     * the sources do likewise.
     * @param cycle The cycle of the update.
     */
    @Override
    public void updateError(long cycle)
    {
        calculateErrorTerm(cycle);
        for(Source src : getSources()) {
            src.updateError(cycle);
        }
    }

}
