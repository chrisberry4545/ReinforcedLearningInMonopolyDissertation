package Model.Players.NeuralNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Common elements of nodes.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public abstract class AbstractNodeV2 extends Source implements Sink, Serializable, Node
{
    // The cycle number of the last error.
    private long lastErrorCycle;
    // The number of sources.
    private int numSources;
    // The maximum ID of the sources of this node.
    private int maxSourceID;
    // A compact list for external consumption.
    // sources.length == numSources.
    private Source[] sources;
    // The index of each source in the sourceList.
    // sourceIndices.length == numSources.
    private int[] sourceID;
    // The index of each weight given a source.
    // weightIndices.length = sourceList.length.
    private int[] weightIndex;
    private List<Sink> sinks;
    
    // The input weights.
    private double[] weights;
    // Frozen version of the weights when updating errors.
    private double[] frozenWeights;
    // The error term.
    private double errorTerm;
    // The propagated error.
    private double propagatedError;
    // Whether the weights are frozen or not.
    private boolean frozen;
    // The node's value.
    private double value;
    
    /**
     * Constructor for objects of class Node
     */
    public AbstractNodeV2()
    {
        // The list of sources, for efficient access.
        sources = new Source[0];
        // Mapping from Source ID to weight index.
        // sources[weightIndex[src.getID()]] == src
        weightIndex = null;
        numSources = 0;
        maxSourceID = -1;
        sinks = new ArrayList<Sink>();
        reset();        
        // The first input is always a dummy.
        addSource(InputZero.getInstance());
    }
    
    /**
     * Reset the state for a fresh cycle.
     */
    @Override
    public void reset()
    {
        lastErrorCycle = -1;
        frozen = false;
        errorTerm = Double.NaN;
        propagatedError = Double.NaN;
        value = Double.NaN;
    }
    
    /**
     * Return the value.
     * @return The sum of the input values multiplied by the weights.
     */
    @Override
    public double getValue()
    {
        if(!frozen) {
            double net = 0.0;
            for(int w = 0; w < weights.length; w++) {
                net += weights[w] * sources[w].getValue();
            }
            value = 1.0 / (1.0 + Math.exp(-net));
        }
        return value;
    }

    /**
     * Take a copy of the weights and don't permit
     * further calculation of the error term.
     * @param cycle The cycle to which the frozen state belongs.
     */
    @Override
    public void freeze(long cycle)
    {
        // Take a copy of the weights.
        System.arraycopy(weights, 0, frozenWeights, 0, weights.length);
        frozen = true;
    }
    
    /**
     * Release the node for weight changes.
     */
    @Override
    public void unfreeze()
    {
        frozen = false;
    }
    /**
     *  Initialize the weights randomly.
     */
    @Override
    public void initWeights()
    {
        weights = new double[numSources];
        for(int w = 0; w < weights.length; w++) {
            weights[w] = Math.random() / 10 - 0.05;
        }
        
        // Make provision for freezing the weights.
        frozenWeights = new double[weights.length];
        
        // Reverse lookup from source ID to weight.
        weightIndex = new int[maxSourceID + 1];
        // Fill with illegal values as a safety net.
        Arrays.fill(weightIndex, -1);
        for(int i = 0; i < sources.length; i++) {
            weightIndex[sources[i].getID()] = i;
        }
    }
    
    public void initFixedWeights(double weightValue) {
        weights = new double[numSources];
        for (int w = 0; w < weights.length; w++) {
            weights[w] = weightValue;
        }
        frozenWeights = new double[weights.length];
        
        weightIndex = new int [maxSourceID + 1];
        
        Arrays.fill(weightIndex, -1);
        for (int i = 0; i < sources.length; i++) {
            weightIndex[sources[i].getID()] = i;
        }
    }
    
    /**
     * Return the list of sinks.
     * @return The sinks.
     */
    protected List<Sink> getSinks()
    {
        return sinks;
    }
    
    /**
     * Return the sources.
     * @return The sources.
     */
    protected Source[] getSources()
    {
        return sources;
    }
    
    /**
     * Add the given source as a input.
     * @param src The source.
     */
    @Override
    public void addSource(Source src)
    {
        int sourceIdent = src.getID();
        
        // Update the sparse list.
        if(maxSourceID < sourceIdent) {
            maxSourceID = sourceIdent;
        }
        
        // Increase the size of the source list by 1.
        Source[] largerSources = new Source[numSources + 1];
        System.arraycopy(sources, 0, largerSources, 0, sources.length);
        sources = largerSources;
        sources[numSources] = src;
        
        numSources++;
    }
    
    /**
     * Add the given sink as an output.
     * @param sink The sink.
     */
    public void addSink(Sink sink)
    {
        sinks.add(sink);
    }
    
    /**
     * Return the weight associated with the given source.
     * @param source The source whose weight is required.
     * @return The weight associated with source.
     */
    public double getWeight(Source source)
    {
        int w = weightIndex[source.getID()];
        if(w < 0) {
            System.out.println("No weight for " + source.getID());
            throw new RuntimeException("Source not found.");
        }
        if(sources[w].getID() != source.getID()) {
            System.out.println("Mismatch.");
            throw new RuntimeException();
        }
        if(isFrozen()) {
            return frozenWeights[w];
        }
        else {
            return weights[w];
        }
    }

    /**
     * Return the error term for the given cycle.
     * @param cycle The cycle associated with the error term.
     */
    @Override
    public double getError(long cycle)
    {
        if(frozen) {
            return errorTerm;
        }
        else {
            calculateErrorTerm(cycle);
            return errorTerm;
        }
    }
    
    /**
     * Return a String representation of this node.
     * @return A String representation of this node.
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(double w : weights) {
            builder.append(w);
            builder.append(", ");
        }
        builder.append("] ");
        builder.append(" (").append(getError(lastErrorCycle)).append(")");
        return builder.toString();
    }

    /**
     * Return the error term for the given source.
     * @param cycle The cycle associated with the error term.
     * @param src The src whose error term is required.
     * @return The error term associated with src.
     */
    @Override
    public double getPropagatedError(long cycle, Source src)
    {
        if(frozen) {
            // Should only be called when frozen.
            throw new IllegalStateException("getPropagatedError called when not frozen");
        }
        else {
            double e = getError(cycle);
            double w = getWeight(src);
            propagatedError = e * w;
        }
        return propagatedError;
    }

    /**
     * Set the error term for the given cycle.
     * @param errorTerm The error term.
     * @param cycle The associated cycle.
     */
    protected void setErrorTerm(double errorTerm, long cycle)
    {
        this.errorTerm = errorTerm;
        lastErrorCycle = cycle;
    }
    
    /**
     * Set the weight for one of the sources.
     * @param w Which source.
     * @param weight The weight to be set.
     */
    @Override
    public void setWeight(int w, double weight)
    {
        weights[w] = weight;
    }
    
    /**
     * Is the node frozen?
     * @return true if frozen, false otherwise.
     */
    protected boolean isFrozen()
    {
        return frozen;
    }
    
    /**
     * Return the cycle number of the most recent error term.
     * @return The cycle number of the most recent error term.
     */
    protected long getLastErrorCycle()
    {
        return lastErrorCycle;
    }
    
    /**
     * Return the error term.
     * @return The error term.
     */
    protected double getErrorTerm()
    {
        return errorTerm;
    }    
}
