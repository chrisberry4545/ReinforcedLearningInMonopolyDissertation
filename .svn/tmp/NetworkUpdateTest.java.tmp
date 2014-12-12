package NeuralNetTests;

import Model.Players.NeuralNetwork.*;
<<<<<<< .mine
=======
import Model.Bank;
import Model.Game;
import Model.Players.TDInputGenerators.FirstTDPlayer;
import Model.Players.Player;
import Model.Players.TDInputGenerators.SecondTDPlayer;
import Model.Players.TestAI;
import java.util.ArrayList;
>>>>>>> .r134
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import PlayerTests.TDPlayerTests.TDTestEnvirorment;
import static org.junit.Assert.*;

/**
 * Tests a part of the critic class to ensure it is doing the correct thing.
 * @author Chris Berry
 */
public class NetworkUpdateTest extends TDTestEnvirorment{
    
    public NetworkUpdateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Ensures that the values held by the hidden layer are all different 
     * from the previous values once a network has been updated.
     */
    @Test
    public void networkUpdateTest() {
        super.setUpTDTestEnvirorment();
        Network net = critic.getGeneralizer().getEvaluator().getNet();
        //Copy hidden layer.
        double[] startingValues = new double[net.getHiddenLayer().size()];
        for (int i = 0; i < net.getHiddenLayer().size(); i++) {
            startingValues[i] = net.getHiddenLayer().get(i).getValue();
        }
        int inputSize = net.getInputs().size();
        int outputSize = net.getNumOutputs();
        double[] inputs = new double[inputSize];
        //Set all inputs to 1.
        for (int i = 0; i < inputSize; i++) {
            inputs[i] = 1;
        }
        double[] outputs = new double[outputSize];
        //set all outputs to 1.
        for (int i = 0; i < outputSize; i++){
            outputs[i] = 1;
        }
        double[] error_sq = new double[net.getOutputLayer().size()];
        double defaultAlpha = 0.1;
        int cycle = 0;
        net.update(inputs, outputs, cycle, error_sq, defaultAlpha);
        
        List<AbstractNode> updatedHiddenLayer = net.getHiddenLayer();
        
        //Make sure all the values are different.
        for (int i = 0; i < startingValues.length; i++) {
            if (startingValues[i] == 
                    updatedHiddenLayer.get(i).getValue()) {
                fail("The weight of the node, " + i + " hasn't changed. It is "
                        + "still " + updatedHiddenLayer.get(i).getValue());
            }
        }
        
        System.out.println("Test successful. All hidden nodes have been "
                + "successfully updated");
    }
        
}
