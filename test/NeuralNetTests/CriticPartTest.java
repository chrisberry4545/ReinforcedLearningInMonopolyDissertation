package NeuralNetTests;

import Model.Bank;
import Model.Game;
<<<<<<< .mine
=======
import Model.Players.TDInputGenerators.FirstTDPlayer;
>>>>>>> .r134
import Model.Players.Player;
<<<<<<< .mine
import Model.Players.TDInputGenerators.FirstTDPlayer;
import Model.Players.TDInputGenerators.SecondTDPlayer;
=======
import Model.Players.TDInputGenerators.SecondTDPlayer;
>>>>>>> .r134
import Model.Players.TestAI;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests a part of the critic class to ensure it is doing the correct thing.
 * @author Chris Berry
 */
public class CriticPartTest {
    
    public CriticPartTest() {
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
     * Tests the method used by the critic is outputting the correct data.
     */
    @Test
    public void criticPartTest() {
        Game.newGame();
        List<Player> playerList = new ArrayList();
        playerList.add(new TestAI(1));
        playerList.add(new TestAI(2));
        Game.getInstance().addPlayers(playerList);
        Game.getInstance().setUpPlayersForGameStart();
        Game.getInstance().getPlayers().get(0).
                forcedMoneyChange(-Game.PLAYER_STARTING_MONEY + 1,
                Bank.getInstance());
        int numOutputNodes = FirstTDPlayer.NUM_OUTPUT_NODES;
        int moneyOutputOffset = SecondTDPlayer.NUM_OUTPUT_NODES/2;
        int winnerToken = -1;
        for (Player winner : Game.getInstance().getPlayers()) {
                    winnerToken = winner.getNumber();
                }
                //Winning player gets a value of 1, the rest get a value of 0.
                if (winnerToken != -1) {
                    double[] actualResultsList =
                            new double[numOutputNodes];
                    for (int j = 0; j < numOutputNodes -
                            moneyOutputOffset; j++) {
                        if (j != winnerToken) {
                            actualResultsList[j] = 0;
                        } else {
                            actualResultsList[j] = 1;
                        }
                    }
                    for (int i = 0; i < actualResultsList.length; i++) {
                        System.out.println(i + ": " +actualResultsList[i]);
                    } 
                }
    }
        
}
