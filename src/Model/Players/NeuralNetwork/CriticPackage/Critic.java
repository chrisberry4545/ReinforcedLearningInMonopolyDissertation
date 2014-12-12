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
public class Critic {
    
    private Generalizer generalizer;
    private int numOutputNodes;
    private boolean blockLearning;
    
    private double winPayoff= 1;
    private double losePayoff = 0;
    
    /**
     * Creates a new critic with the given parameters.
     * @param generalizer which critic will feed information to.
     * @param numberOfOutputNodes used by the TD AIs utilising the neural
     * network.
     * @param offsetForMoneyOutput value where the monetary amount of actions
     * starts appearing.
     */
    public Critic(Generalizer generalizer, int numberOfOutputNodes) {
        this.generalizer = generalizer;
        this.numOutputNodes = numberOfOutputNodes;
        this.blockLearning = false;
    }

/**
 * Key method.
 */    
    /**
     * Feeds the previous states inputs and the actual results through to the 
     * generaliser. If this was the last move of the game, this is noted and
     * the appropriate values are used instead.
     * @param previousStatesInput
     * @param results 
     */
    public void generalize(double[] previousStatesInput, double[] results) {
        if (!blockLearning) {
            //If game isn't finished generalize as normal.
            if (!Game.getInstance().isFinished()) {
                generalizer.generalize(previousStatesInput, results);
            } else {
                System.err.println("Game has finished and the player wants"
                        + " to update their network");
            }
        }
    }
    
    /**
     * Feeds the previous states inputs and the results of the end game through
     * to the generalizer. This should only be called when the game has finished
     * to avoid producing an error.
     * @param previousStatesInput inputs from previous state.
     * @param wonGame true if the player who the inputs are for won the game.
     */
    public void generalize(double[] previousStatesInput, boolean wonGame) {
        if (!blockLearning) {
            if (Game.getInstance().isFinished()) {
                if (Game.getInstance().getPlayers().size() == 1) {
                    double[] finalResults = new double[numOutputNodes];
                    if (wonGame) {
                        finalResults[0] = getWinPayoff();
                    } else {
                        finalResults[0] = getLossPayoff();
                    }
                    generalizer.generalize(previousStatesInput, finalResults);
                } else {
                    System.err.println("The game is over but supposedly there"
                                    + " are no players left.");
                }
            }
        }
    }
    
    public void generalizeFromDB(double[] previousStatesInput, double[] results) {
        generalizer.generalize(previousStatesInput, results);
    }
    
    /**
     * Generalizers from a databases and thus ignores the checks for the game
     * being finished and the appropriate amount of players being included.
     * @param previousStatesInput
     * @param wonGame 
     */
    public void generalizeFromDB(double[] previousStatesInput, boolean wonGame) {
        System.out.println("Has won?: " + wonGame);
        double[] finalResult = new double[numOutputNodes];
        if (wonGame) {
            finalResult[0] = getWinPayoff();
        } else {
            finalResult[0] = getLossPayoff();
        }
        generalizer.generalize(previousStatesInput, finalResult);
    }

/**
 * Setter methods.
 */
    /**
     * Set as true if you want to block learning by the AI.
     * @param blockLearning 
     */
    public void setBlockLearning(boolean blockLearning) {
        this.blockLearning = blockLearning;
    }
    
/**
 * Getter methods.
 */   
    /**
     * Gets the generalizer used by the critic.
     * @return Generalizer used by the critic.
     */
    public Generalizer getGeneralizer() {
        return generalizer;
    }

    /**
     * Returns the win payoff for this Critic.
     * @return win payoff.
     */
    public double getWinPayoff() {
        return winPayoff;
    }

    /**
     * Returns the loss payoff for this Critic.
     * @return loss payoff
     */
    public double getLossPayoff() {
        return losePayoff;
    }
    
    
}
