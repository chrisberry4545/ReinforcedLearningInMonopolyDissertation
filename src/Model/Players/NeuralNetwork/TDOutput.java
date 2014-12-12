package Model.Players.NeuralNetwork;

import java.util.HashMap;
import java.util.Map;

/**
 * A TD(λ) output node.
 * 
 * Implementation based on Richard S. Sutton's description in
 * "Implementation Details of the TD(λ) Procedure for the Case of Vector
 * Predictions and Backpropagation",
 * GTE Laboratories Technical Note TN87-509.1 May 1987 Corrected Aug 1989
 * @url http://www.incompleteideas.net/sutton/papers/sutton-89.pdf
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class TDOutput extends TDNode implements Output
{
    // Map of eijk
    private Map<Source, Map<Sink, Double>> eligibilities;
    // The difference between the last P(t+1) and P(t).
    private double difference;
    // The cycle on which the difference was calculated.
    private long lastDifferenceCycle;
    
    private boolean debug = false;
    
    /**
     * Constructor for objects of class TDOutput
     * @param net The whole network.
     * @param lambda The lambda value.
     */
    public TDOutput(Network net, double lambda)
    {
        super(net, lambda);
        eligibilities = new HashMap<Source, Map<Sink, Double>>();
        difference = 0;
        lastDifferenceCycle = -1;
    }
    
    /**
     * Make adjustments in the light of the value of P(t+1).
     * @param cycle The cycle to which the adjustment applies.
     * @param Ptplus1 P(t+1)
     */
    @Override
    public void adjust(long cycle, double Ptplus1)
    {
        double Pt = getValue();
        difference = Ptplus1 - Pt;
        lastDifferenceCycle = cycle;
        
        calculateDelta(cycle, this);
    }
    
    /**
     * Back propagate.
     * @param cycle The cycle of the back propagation.
     */
    @Override
    public void backPropagate(long cycle)
    {
        calculateEligibilities(cycle);
    }
    
    /**
     * Reset the node completely.
     */
    @Override
    public void reset()
    {
        super.reset();
        if(eligibilities != null) {
            for(Source src : eligibilities.keySet()) {
                Map<Sink, Double> eList = eligibilities.get(src);
                for(Sink sink : eList.keySet()) {
                    eList.put(sink, 0.0);
                }
            }
        }
    }
    
    /**
     * Get the difference for the given cycle.
     * @param cycle The cycle of the most recent difference calculation.
     * @return The difference.
     */
    public double getDifference(long cycle)
    {
        if(cycle != lastDifferenceCycle) {
            throw new RuntimeException("Mismatch in cycles: " + cycle +
                                       " vs " + lastDifferenceCycle);
        }
        return difference;
    }

    /**
     * Return eligibility ijk.
     * @param cycle The cycle of the eligibility.
     * @param i The source node.
     * @param j Node j.
     * @return eijk
     */
    public double getEligibility(long cycle, Source i, TDNode j)
    {
        if(!isFrozen()) {
            calculateEligibility(cycle, i, j);
        }
        Map<Sink, Double> elList = eligibilities.get(i);
        double eligibility = elList.get(j);
        if(debug) {
            System.out.println("get eligibility: " + cycle + "/" +
                     i + "/" + j + "/" + this + " = " +
                     eligibility);
        }
        return eligibility;
    }
    
    /**
     * Set eijk where k is this output node.
     * @param cycle The cycle of the eligibility.
     * @param i The source node.
     * @param j Node j.
     */
    protected void calculateEligibility(long cycle, Source i, TDNode j)
    {
        if(isFrozen()) {
            throw new IllegalStateException();
        }

        Double previousValue;        
        Map<Sink, Double> sinkList = eligibilities.get(i);
        if(sinkList == null) {
            sinkList = new HashMap<Sink, Double>();
            eligibilities.put(i, sinkList);
            previousValue = 0.0;
        }
        else {
            previousValue = sinkList.get(j);
            if(previousValue == null) {
                previousValue = 0.0;
            }
        }
            
        double yi = i.getValue();
        TDOutput k = this;
        double eligibility = getLambda() * previousValue +
                             j.getDelta(cycle, k) * yi;
        if(debug) {
            System.out.println("calculate eligibility: " + cycle + "/" +
                     i + "/" + j + "/" + this +
                     " = " + eligibility);
        }
        sinkList.put(j, eligibility);                
    }   
}
