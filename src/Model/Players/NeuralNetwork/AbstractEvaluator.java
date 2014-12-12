/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Players.NeuralNetwork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public class AbstractEvaluator implements Evaluator 
{
    private Network net;
        
    /**
     * Constructor for objects of class Evaluator
     * @param lambda The lambda value for the associated network.
     */
    public AbstractEvaluator(double lambda, int numInputs, int numHiddenNodes, int numOutputNodes)
    {
        net = new Network(lambda);
        net.setup(numInputs, numHiddenNodes, numOutputNodes);
    }
    
//    public AbstractEvaluator(double lambda, int numInputs, int numHiddenNodes,
//            int numOutputNodes, double weight) {
//        net = new Network(lambda);
//        net.setupWithEqualWeights(numInputs, numHiddenNodes, numOutputNodes, weight);
//    }
    
    /**
     * Create an evaluator using a network in the given file.
     * @param networkFile The file containing the network.
     */
    public AbstractEvaluator(String networkFile)
    {
        net = loadNet(networkFile);
        if(net == null) {
            throw new RuntimeException("Unable to load a network from: " +
                                       networkFile);
        }   
    }
        
    /**
     * Evaluate the board according to its properties and
     * the current weights.
     */
    @Override
    public double[] evaluate(double[] properties)
    {
        double[] results = net.feedForward(properties);
        return results;
    }
    
    /**
     * Restart the network.
     */
    public void restartNetwork()
    {
        net.restart();
    }
    
    @Override
    /**
     * Get the network.
     * @return The network.
     */
    public Network getNet()
    {
        return net;
    }

    /**
     * Save the network to the given file.
     * @param filename The file for the network.
     */
    public void saveNet(String filename)
    {
        try {
            ObjectOutputStream os = new ObjectOutputStream(
                new FileOutputStream(filename));
            os.writeObject(net);
            os.close();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Load the network from the given file
     * @param filename The file containing the network.
     * @return The network, or null if it could not be loaded.
     */
    public Network loadNet(String filename)
    {
        try {
            ObjectInputStream is =
                new ObjectInputStream(
                new FileInputStream(filename));
            Network n = (Network) is.readObject();
            is.close();
            return n;
        }
        catch(FileNotFoundException e) {
            System.out.println("No saved network loaded from: " + filename);
            return null;
        }
        catch(IOException e) {
            System.out.println(e);
            return null;
        }
        catch(ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }
}
