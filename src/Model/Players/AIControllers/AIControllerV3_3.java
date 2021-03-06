package Model.Players.AIControllers;

import Model.Board;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;

/**
 * A version of AIControllerV3 with the optimal modes selected. The optimal
 * modes are also selected for the individual components of an in jail turn
 * and an standard move turn.
 * @author Chris Berry
 */
public class AIControllerV3_3 extends AIControllerV3_2{
    
    /**
     * Creates a new AIControllerV3_3
     * @param tokenNumber player number to use.
     * @param critic to use.
     * @param inputGen to use.
     * @param board which the player will be playing on.
     */
    public AIControllerV3_3(int tokenNumber, Critic critic, 
            TDInputGenerator inputGen, Board board) {
        super(tokenNumber, critic, inputGen, board);
        super.setBotHandlesMortgageDecisions(true);
        super.setBotHandlesHouseBuyingDecisions(true);
        super.setBotHandlesUnMortgageDecisions(true);
        super.setBotHandlesDealMakingDecisions(false);
    }
    
    
}