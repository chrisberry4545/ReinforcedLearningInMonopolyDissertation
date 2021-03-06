package Model.Players.NeuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A TD(λ) node.
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
public class TDNode extends AbstractNode
{
    // The lamda value.
    private double lambda;
    // The delta value at each output.
    private Map<TDOutput, Double> deltas;
    // The current delta cycle.
    private long deltaCycle;
    // The output layer.
    private List<TDOutput> outputLayer;
    // Keep track of the feed number of the last calculation.
    private long lastFeedNumber = Long.MAX_VALUE;
    // Remember the value calculated at the last feed.
    private double currentValue;
    // The whole network.
    private Network net;
    
    // Whether we are debugging.
    private boolean debug = false;
    
    // Optimization fields.
    private boolean optimize = true;
    // Cache the sources and sinks known to be TDNodes.
    private TDNode[] jsinks;
    private TDNode[] tdsources;

    /**
     * Constructor for objects of class TDNode
     * @param net The whole network.
     * @param lambda The lambda value.
     */
    public TDNode(Network net, double lambda)
    {
        this.lambda = lambda;
        this.net = net;
        this.deltas = new HashMap<TDOutput, Double>();
        this.outputLayer = null;
        reset();
    }
    
    /**
     * Return the value of this node.
     * @return The sum of the input values multiplied by the weights.
     */
    @Override
    public double getValue()
    {
        long feed = net.getFeedNumber();
        if(feed != lastFeedNumber) {
            currentValue = super.getValue();
            lastFeedNumber = feed;
        }
        return currentValue;
    }
    
    /**
     * Reset the node completely.
     */
    @Override
    public void reset()
    {
        super.reset();
        if(outputLayer == null && net != null) {
            List<Output> olayer = net.getOutputLayer();
            if (olayer.size() > 0) {
                outputLayer = new ArrayList<TDOutput>(olayer.size());
                for (Output o : olayer) {
                    outputLayer.add((TDOutput) o);
                }
            }
        }
        if(deltas != null) {
            deltas.clear();
        }
        deltaCycle = -1;
    }

    /**
     * Return the delta ki, where this node is i.
     * @param cycle The cycle for this delta.
     * @param k Output k.
     * @return The delta ki.
     */
    public double getDelta(long cycle, TDOutput k)
    {
        if(!isFrozen()) {
            calculateDelta(cycle, k);
        }
        else if(cycle != deltaCycle) {
            throw new IllegalStateException();
        }
        return deltas.get(k);
    }
    
    /**
     * Calculate delta(ki) where i is this.
     * @param cycle The cycle for this delta.
     * @param k Output k.
     */
    protected void calculateDelta(long cycle, TDOutput k)
    {
        if(isFrozen()) {
            throw new IllegalStateException();
        }
        deltaCycle = cycle;
        
        Source i = this;
        //If k = 1 delta is yi(t) * (1 - yi(t) )
        if(k == i) {
            double value = getValue();
            deltas.put(k, value * (1 - value));
        }
        //If k != i and i is an Fan out from k, delta is 0.
        else if(i instanceof TDOutput) {
            deltas.put(k, 0.0); 
        }
        else {
            if(optimize) {
                // Avoid run-time cast checks by storing in
                // subclass-specific data structures.
                if(jsinks == null) {
                    // Set up optimizable arrays.
                    List<Sink> sinkList = getSinks();
                    jsinks = new TDNode[sinkList.size()];
                    for(int j = 0; j < jsinks.length; j++) {
                        jsinks[j] = (TDNode) sinkList.get(j);
                    }
                    
                    if(tdsources == null) {
                        optimizeSetupTDSources();
                    }
                    
                }
                double deltaSum = 0;
                // Fetch the deltas and the weights.
                for(int j = 0; j < jsinks.length; j++) {
                    deltaSum += jsinks[j].getDelta(cycle, k) * jsinks[j].getWeight(i);
                }
                double yi = i.getValue();

                deltas.put(k, deltaSum * yi * (1 - yi));                
                // Back propagate the calculation of deltas.
                for (TDNode src : tdsources) {
                    src.calculateDelta(cycle, k);
                }
            }
            else {
                List<Sink> sinks = getSinks();
                double deltaSum = 0;
                for (Sink s : sinks) {
                    TDNode j = (TDNode) s;
                    deltaSum += j.getDelta(cycle, k) * j.getWeight(i);
                }
                double yi = i.getValue();

                deltas.put(k, deltaSum * yi * (1 - yi));

                // Back propagate the calculation of deltas.
                for (Source src : getSources()) {
                    if (src instanceof TDNode) {
                        ((TDNode) src).calculateDelta(cycle, k);
                    }
                }
            }
        }
    }
    
    /**
     * Calculate all the eijk for this node.
     * @param cycle The cycle for this delta.
     */
    protected void calculateEligibilities(long cycle)
    {
        if(debug) {
            System.out.println("calculate eligibilities " + cycle +
                               " " + this);
        }
        if(isFrozen()) {
            throw new IllegalStateException();
        }
        
        TDNode j = this;
        Source[] sources = getSources();
        for(Source i : sources) {
            for(TDOutput k : outputLayer) {
                k.calculateEligibility(cycle, i, j);
            }
        }
        if(optimize) {
            if (tdsources == null) {
                optimizeSetupTDSources();
            }
            for (TDNode i : tdsources) {
                i.calculateEligibilities(cycle);
            }
        }
        else {
            for (Source i : sources) {
                if (i instanceof TDNode) {
                    ((TDNode) i).calculateEligibilities(cycle);
                }
            }
        }
    }
    
    /**
     * Adjust the weights according to the eror for this node.
     * @param cycle The cycle for this delta.
     * @param alpha The learning rate.
     */
    @Override
    public void adjustWeights(long cycle, double alpha)
    {
        if(!isFrozen()) {
            throw new IllegalStateException();
        }
        else if(cycle != deltaCycle) {
            throw new IllegalStateException(deltaCycle + " vs " + cycle + " in " + this);
        }
        TDNode j = this;
        Source[] sources = getSources();
        for(int w = 0; w < sources.length; w++) {
            Source i = sources[w];
            double eligibilitySum = 0;
            //
            for(TDOutput k : outputLayer) {
                //This calculates the sum of all errors in outputs multiplyed
                //by the eligibility for each output.
                eligibilitySum += k.getDifference(cycle) *
                                  k.getEligibility(cycle, i, j);
            }
            //Sets weight for between this and each of its sources.
            //Represents Wij(t+1) = Wij(t) + alpa * eligibilitySum 
            setWeight(w, getWeight(i) + alpha * eligibilitySum);
        }
    }
    
    
    /**
     * Calculate the delta for this node.
     * @param cycle The cycle for this delta.
    */
    @Override
    public void calculateErrorTerm(long cycle)
    {
        throw new UnsupportedOperationException();
    }
    

    /**
     * Update the error on this node.
     * @param cycle The cycle for this update.
     * @throws UnsupportedOperationException 
     */
    @Override
    public void updateError(long cycle)
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Return a String version of this node.
     * @return The node's ID.
     */
    @Override
    public String toString()
    {
        return "" + getID();
    }
    
    /**
     * Return the value of lambda.
     * @return lambda.
     */
    protected double getLambda()
    {
        return lambda;
    }    

    /**
     * Optimization routine.
     * To avoid runtime instanceof checks and casts, set up an array of
     * the TDNodes.
     */
    private void optimizeSetupTDSources()
    {
        if(optimize) {
            Source[] sources = getSources();
            int count = 0;
            for (Source s : sources) {
                if (s instanceof TDNode) {
                    count++;
                }
            }
            tdsources = new TDNode[count];
            int index = 0;
            for (Source s : sources) {
                if (s instanceof TDNode) {
                    tdsources[index] = (TDNode) s;
                    index++;
                }
            }          
        }
    }
}
