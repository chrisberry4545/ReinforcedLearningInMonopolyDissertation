package Model.Players.NeuralNetwork;


/**
 * Write a description of interface Node here.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public interface Node
{

    public double getValue();
    
    public void initWeights();
    
    /**
     * Adjust the weights according to the error for this node.
     */
    public void adjustWeights(long cycle, double alpha);
    
    public void setWeight(int w, double weight);

    /**
     * Calculate the delta for this node.
     */
    public void calculateErrorTerm(long cycle);
    
    public void freeze(long cycle);
    public void unfreeze();
    public void reset();
}
