/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Players.NeuralNetwork;

/**
 * Interface for an evaluator.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public interface Evaluator
{
    /**
     * Return the network.
     * @return The network.
     */
    public Network getNet();
    
    /**
     * Feed properties to the network and receive the resulting outputs.
     * @param properties The input properties.
     * @return The values at the output nodes.
     */
    public double[] evaluate(double[] properties);
    
}
