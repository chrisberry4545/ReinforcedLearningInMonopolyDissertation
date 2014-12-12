package PlayerTests.TDPlayerTests;

import Model.Game;
import Model.Players.TDPlayer;
import Model.Players.TDInputGenerators.FirstTDPlayer;
import Model.Players.NeuralNetwork.AbstractEvaluator;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.NeuralNetwork.Generalizer;
import Model.Players.Player;
import Model.Players.TestAI;
import java.util.ArrayList;
import java.util.List;
import Model.Players.TDInputGenerators.TDInputGenerator;

/**
 * Class used to set up a basic test environment for testing the TD AIs.
 * @author Chris Berry
 */
public class TDTestEnvirorment {
    
    protected TDPlayer tdPlayerOne;
    protected TestAI otherPlayer;
    protected Critic critic;
    
    /**
     * Sets up a test environment to use for TD player testing.
     */
    public void setUpTDTestEnvirorment() {
        double lambda = 0.0;      
        AbstractEvaluator evaluator = 
                new AbstractEvaluator(lambda, FirstTDPlayer.NUM_INPUT_NODES, 
                FirstTDPlayer.NUM_HIDDEN_NODES, 
                FirstTDPlayer.NUM_OUTPUT_NODES);
        evaluator.restartNetwork();
        TDInputGenerator inputGen = new FirstTDPlayer();
        Generalizer generalizer = new Generalizer(evaluator);
        critic = new Critic(generalizer,FirstTDPlayer.NUM_OUTPUT_NODES);
        Game.newGame();
        List<Player> allPlayers = new ArrayList();
        tdPlayerOne = new TDPlayer(0, Game.getInstance().getBoard(),critic,inputGen, true);
        tdPlayerOne.optionalMoneyChange(Game.PLAYER_STARTING_MONEY);
        otherPlayer = new TestAI(1);
        otherPlayer.optionalMoneyChange(Game.PLAYER_STARTING_MONEY);
        allPlayers.add(tdPlayerOne);
        allPlayers.add(otherPlayer);
        Game.getInstance().addPlayers(allPlayers);
    }
    
    
}
