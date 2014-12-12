package Model.Players.AIControllers;

import Model.Board;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;

/**
 * A version of AIControllerV3 with the optimal modes selected. The optimal
 * modes were selected from tests run against the SmartBot.
 * @author Chris Berry
 */
public class AIControllerV3_1 extends AIControllerV3{
        
    /**
     * Creates a new AIControllerV3_1
     * @param tokenNumber player number to use.
     * @param critic to use.
     * @param inputGen to use.
     * @param board which the player will be playing on.
     */
    public AIControllerV3_1(int tokenNumber, Critic critic, 
            TDInputGenerator inputGen, Board board) {
        super(tokenNumber, critic, inputGen, board);
        super.setBotDecidesToBuyProperties(false);
        super.setBotHandlesInJailOptions(false);
        super.setBotHandlesNormalMove(true);
        super.setBotAssessesOffers(true);
        super.setBotMakesBids(false);
        super.setBotHandlesReceiveingProperty(false);
        super.setBotChoosesPropertyForBuildingOn(false);
        super.setBotHandlesMortgagingProperties(false);
        super.setBotHandlesSellingHouses(false);
    }
}