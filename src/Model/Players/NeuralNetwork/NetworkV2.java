package Model.Players.NeuralNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Neural network structure consisting of one input layer, one hidden
 * layer and one output layer.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
@SuppressWarnings("serial")
public class NetworkV2 implements Serializable
{
    // Mean square error threshold for training termination.
    private double ERROR_THRESHOLD = 0.00001;

    // All the nodes in the network.
    private List<Node> nodes;
    // The input layer.
    private List<InputValue> inputs;
    // The hidden layer;
    private List<AbstractNode> hiddenLayer;
    // The output layer.
    private List<Output> outputLayer;
    // Lambda value for TD(λ) networks.
    // A value of 0 is taken to mean a non-TD(λ) network.
    private double lambda;
    
    // Maintain a count to help the network determine whether it
    // needs to recalculate its values or not.
    private long feedNumber = -1;
    
    private boolean debug;
    
    /**
     * Constructor for objects of class Network
     * @param lambda Value for TD(λ), or 0 for non-TD(λ) network.
     */
    public NetworkV2(double lambda)
    {
        this.lambda = lambda;
        this.nodes = new ArrayList<Node>();
        this.inputs = new ArrayList<InputValue>();
        this.hiddenLayer = new ArrayList<AbstractNode>();
        this.outputLayer = new ArrayList<Output>();
        
        this.debug = false;
    }

    /**
     * Set up the required number of nodes in each layer.
     * @param numInputs Number of inputs.
     * @param numHiddenNodes Number of hidden nodes.
     * @param numOutputNodes Number of output nodes.
     */
//    public void setup(int numInputs, int numHiddenNodes, int numOutputNodes)
//    {
//        nodes.clear();
//        inputs.clear();
//        hiddenLayer.clear();
//        outputLayer.clear();
//        
//        List<AbstractNode> lastLayer = null;
//
//        for(int i = 0; i < numInputs; i++) {
//            inputs.add(new InputValue());
//        }
//                        
//        if(numHiddenNodes > 0) {
//            // Use the value of lambda to decide whether to create
//            // TDNodes or BasicNodes.
//            for(int i = 0; i < numHiddenNodes; i++) {
//                AbstractNode h;
//                
//                if(lambda == 0.0) {
//                    h = new BasicNode();
//                }
//                else {
//                    h = new TDNode(this, lambda);
//                }
//                hiddenLayer.add(h);
//                // Every node receives all inputs.
//                for(InputValue in : inputs) {
//                    h.addSource(in);
//                }
//                h.initWeights();
//                nodes.add(h);
//            }
//            lastLayer = hiddenLayer;
//        }
//        
//
//        // Use the value of lambda to decide whether to create
//        // TDOutputs or BasicOutputs.
//        for(int i = 0; i < numOutputNodes; i++) {
//            Output o;
//            if(lambda == 0) {
//                o = new BasicOutput();
//            }
//            else {
//                o = new TDOutput(this, lambda);
//            }
//            outputLayer.add(o);
//            if(lastLayer != null) {
//                for(AbstractNode n : lastLayer) {
//                    o.addSource((Source) n);
//                    n.addSink(o);
//                }
//            }
//            else {
//                // Every node receives all inputs.
//                for(InputValue in : inputs) {
//                    o.addSource(in);
//                }
//            }
//            o.initWeights();
//            nodes.add(o);
//        }
//        
//    }
    
    /**
     * Attempt to learn given the training values and results.
     * Return the mean squared error.
     * @param trainingValues The training values.
     * @param results The results.
     * @param alpha The learning rate.
     * @param passes The number of passes to make.
     */
    public double learn(double[][] trainingValues, double[][] results,
                        double alpha,
                        int passes)
    {        
        int MAX_CYCLES = passes * trainingValues.length;
        int cycle = 1;
        double[] mean_error_sq = new double[outputLayer.size()];
        double error = 2 * ERROR_THRESHOLD;
        
        while(error > ERROR_THRESHOLD && cycle < MAX_CYCLES) {
            for(int e = 0; e < mean_error_sq.length; e++) {
                mean_error_sq[e] = 0;
            }
            
            for(int t = 0; t < trainingValues.length; t++) {
                double[] values = trainingValues[t];
                double[] expected = results[t];
                
                update(values, expected, cycle, mean_error_sq, alpha);
                cycle++;
            }
            error = 0;
            for(int e = 0; e < mean_error_sq.length; e++) {
                mean_error_sq[e] /= trainingValues.length;
                error += mean_error_sq[e];
            }
            error /= mean_error_sq.length;
        }
        System.out.println("# Cycle " + (cycle / trainingValues.length) + " E: " + error + ": ");
        for(int e = 0; e < mean_error_sq.length; e++) {
            System.out.print(mean_error_sq[e] + " ");
        }
        System.out.println();
        return error;
    }
    
    /**
     * Feed forward the given values via the input nodes.
     * @param values Values for the input nodes.
     * @return Return the values at the output nodes.
     */
    public double[] feedForward(double[] values)
    {
        // Distinguish this feed from previous feeds.
        incrementFeedNumber();
        double[] results = new double[outputLayer.size()];
        for(int i = 0; i <values.length; i++) {
            inputs.get(i).setValue(values[i]);
        }
        for(int r = 0; r < results.length; r++) {
            results[r] = outputLayer.get(r).getValue();
        }
        return results;
    }
    
    /**
     * Update the nodes given the input values and the expected output values.
     * @param values The input values.
     * @param expected The expected values.
     * @param cycle The current cycle.
     * @param error_sq For the calculated squared error at each output.
     * @param alpha The learning rate.
     */
    public void update(double[] values, double[] expected, long cycle,
                       double[] error_sq, double alpha)
    {
        double[] actual = feedForward(values);
        
        for(int r = 0 ; r < expected.length; r++) {
            double expectedResult = expected[r];
            double actualResult = actual[r];
            double diff = expectedResult - actualResult;

            error_sq[r] += diff * diff;
            
            Output o = outputLayer.get(r);
            o.adjust(cycle, expectedResult);
            if(debug) {
                System.out.println("Cycle: " + cycle + " " + actualResult +
                                   " (" + expectedResult + ")");
            }
        }
        
        for(Output o : outputLayer) {
            o.backPropagate(cycle);
        }
       
        for(Node n : nodes) {
            n.freeze(cycle);
        }
        for(Node n : nodes) {
            n.adjustWeights(cycle, alpha);
        }
        for(Node n : nodes) {
            n.unfreeze();
        }
    }
    
    /**
     * Completely reset the network.
     */
    public void restart()
    {        
        for(Node n : nodes) {
            n.reset();
        }
    }
    
    /**
     * Return the latest feed number.
     * @return The latest feed number.
     */
    public long getFeedNumber()
    {
        return feedNumber;
    }
    
    /**
     * Return the number of nodes in the output layer.
     * @return The number of output nodes.
     */
    public int getNumOutputs()
    {
        return outputLayer.size();
    }

    /**
     * Return the list of input nodes as an unmodifiable list.
     * @return The input nodes.
     */
    public List<InputValue> getInputs()
    {
        return Collections.unmodifiableList(inputs);
    }

    /**
     * Return the list of nodes in the hidden layer as an unmodifiable list.
     * @return The nodes in the hidden layer.
     */
    public List<AbstractNode> getHiddenLayer()
    {
        return Collections.unmodifiableList(hiddenLayer);
    }

    /**
     * Return the list of output nodes as an unmodifiable list.
     * @return The output nodes.
     */
    public List<Output> getOutputLayer()
    {
        return Collections.unmodifiableList(outputLayer);
    }


    /**
     * Return the value of lambda being used.
     * @return The value of lambda.
     */
    public double getLambda() 
    {
        return lambda;
    }
    /**
     * Run the network with the given validation data.
     * @param validationValues Validation data.
     */
    public void run(double[][] validationValues)
    {
        for(int t = 0; t < validationValues.length; t++) {
            System.out.print("# " + t + ": ");
            double[] values = validationValues[t];
            runTest(values, t);
        }
    }
    
    /**
     * Run one set of values through the network.
     * Currently only sets the input values.
     * @param values The input values.
     * @param t Test number.
     */
    public void runTest(double[] values, int t)
    {
        for(int i = 0; i < values.length; i++) {
            inputs.get(i).setValue(values[i]);
        }
    }      
    
    /**
     * Set the debugging status.
     * @param debug status: true to turn on, false to turn off.
     */
    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }
    
    /**
     * Increment the feednumber to help the nodes identify distinct feeds.
     */
    private void incrementFeedNumber()
    {
        feedNumber++;
    }
}
