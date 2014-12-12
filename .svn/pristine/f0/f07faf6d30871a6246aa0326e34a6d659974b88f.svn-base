package Model.Players.NeuralNetwork;

import java.util.List;
import java.util.ArrayList;

/**
 * Presentation view for the output layer.
 * 
 * @author David J. Barnes (d.j.barnes@kent.ac.uk) 
 * @version 2013.03.11
 */
public class Presentation implements java.io.Serializable
{
    // The output layer.
    private List<Output> outputs;
    
    /**
     * Create a presentation view of the output layer.
     */
    public Presentation()
    {
        this.outputs = new ArrayList<Output>();
    }

    /**
     * Add an output to the presentation view.
     * @param out The output node.
     */
    public void addOutput(Output out)
    {
        outputs.add(out);
    }

    /**
     * Return the list of outputs in the presentation view.
     * @return The outputs in the presentation view.
     */
    protected List<Output> getOutputs()
    {
        return outputs;
    }
}
