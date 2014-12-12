package Model.Players.NeuralNetwork.CriticPackage;

import Model.Game;
import Model.Players.NeuralNetwork.Generalizer;

/** 
 * A class for interpreting whether the game has finished or is still in 
 * progress and sending the required variables to analysed to the generaliser.
 * This class is specific to the game of Monopoly and should be remade for
 * different neural networks.
 * @author Chris Berry
 */
public class CriticDifferentPayoffs extends Critic{
    
    private double winPayoff= 1;
    private double losePayoff = -1;
    
    /**
     * Creates a new critic with the given parameters.
     * @param generalizer which critic will feed information to.
     * @param numberOfOutputNodes used by the TD AIs utilising the neural
     * network.
     * @param offsetForMoneyOutput value where the monetary amount of actions
     * starts appearing.
     */
    public CriticDifferentPayoffs(Generalizer generalizer, int numberOfOutputNodes) {
        super(generalizer, numberOfOutputNodes);
    }
    
    /**
     * Returns the win payoff for this Critic.
     * @return win payoff.
     */
    @Override
    public double getWinPayoff() {
        return winPayoff;
    }

    /**
     * Returns the loss payoff for this Critic.
     * @return loss payoff
     */
    @Override
    public double getLossPayoff() {
        return losePayoff;
    }
    
    
}
