package Model.Players.TDInputGenerators;

import Model.Board;
import Model.Game;
import Model.Players.Player;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for TD Input Generator.
 * @author Chris
 */
public abstract class TDInputGenerator {
    
    public static final double BAD_OUTPUT_NUM = -100000;
    protected Board board;
    private boolean useDealSubsets;
    
    public TDInputGenerator(boolean useDealSubsets) {
        this.useDealSubsets = useDealSubsets;
    }
    
    /**
     * Sets the board used by the TDInputGenerator.
     * @param board for the input gen to use.
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
/**
 * Abstract methods that need to be overridden.
 */
    
     /**
     * Gets the number of input nodes used by the TD AI.
     * @return number of input nodes.
     */
    public abstract int getNumInputNodes();
    
    /**
     * Gets the number of Output nodes used by the TD AI.
     * @return number of output nodes.
     */
    public abstract int getNumOutputNodes();
    
    /**
     * Gets the number of hidden nodes used by the TD AI.
     * @return number of hidden nodes.
     */
    public abstract int getNumHiddenNodes();
    
    /**
     * Gets an array of bad output values used by the TD AI. This will
     * correspond to the number of output nodes used.
     * @return an array of bad output values equal in length to the number
     * of output nodes.
     */
    public abstract double[] getBadOutputArray();
    
     /**
     * Gets an array representing the current inputs for the neural network
     * based on the current state of the board.
     * @return 
     */
    public abstract double[] getCurrentInputs(int playerNumber);        

    /**
     * Returns true if the player should use deal subsets rather than the longer
     * method.
     * @return true if AI should use deal subsets. 
     */
    public boolean isUseDealSubsets() {
        return useDealSubsets;
    }

    /**
     * Swaps the player numbers round so the inputs can always be generated from
     * the perspective of the same player.
     * @param playerNumber player number of the original player. 
     * @param playerNumberToSwapWith number of the player who should be swapped with.
     */
    protected void swapPlayerNumbers(int playerNumber, int playerNumberToSwapWith) {
        Player thisPlayer = null;
        Player playerToSwapWith = null;
        List<Player> allPlayersAtStart = Game.getInstance().getAllPlayersAtStart();
        for (Player player : allPlayersAtStart) {
            if (player.getNumber() == playerNumber) {
                thisPlayer = player;
            }
            if (player.getNumber() == playerNumberToSwapWith) {
                playerToSwapWith = player;
            }
        }
        if (thisPlayer == null || playerToSwapWith == null) {
            System.err.println("Trying to swap with players who are not " + "in the game");
            System.err.println("(player " + playerNumber + " and player " + playerNumberToSwapWith + ")");
            System.err.println("Game is finished " + Game.getInstance().isFinished());
        }
        thisPlayer.setNumber(playerNumberToSwapWith);
        playerToSwapWith.setNumber(playerNumber);
        //Swap the players in the list.
        Collections.swap(allPlayersAtStart, playerNumber, playerNumberToSwapWith);
    }
    
    
    
}
