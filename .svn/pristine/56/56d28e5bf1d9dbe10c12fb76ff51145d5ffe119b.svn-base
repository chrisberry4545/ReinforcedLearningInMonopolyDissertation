package Model.Players;

import Model.Board;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;

/**
 * This TD Player was designed to test if changing the inputs at different points
 * would make a difference. In the end it didn't so this was resigned.
 * @author Chris Berry
 */
public class TDPlayerV2Test extends TDPlayer {
    
    /**
     * Sets up a new TDPlayer using slightly different input variables.
     * @param setToken the player token number to use.
     * @param currentBoard the current board the player is playing on.
     * @param critic for the player to use.
     * @param inputGen for the player to use.
     * @param randomMoves true if the player should use random moves a small
     * amount of the time.
     */
    public TDPlayerV2Test(int setToken, Board currentBoard, 
            Critic critic, TDInputGenerator inputGen, boolean randomMoves) {
        super(setToken,currentBoard,critic,inputGen,randomMoves);
    }
    
    /**
     * Alerts the TD Player they have bought a property so the last set of
     * inputs can be updated.
     */
    @Override
    public void changePreviousInputsToCurrentInputs() {
        if (previousInputs != null) {
            critic.generalize(previousInputs, 
                moveEvaluator.getCurrentResultsArray(this.getNumber()));
        }
        this.previousInputs = inputGen.getCurrentInputs(this.getNumber());
    }
    
}
