package GameMechanicsTesting;

import GeneralTestMethods.GeneralTestMethods;
import Model.*;
import Model.Spaces.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether Bankruptcy is handled correctly in game.
 * @author Chris Berry
 */
public class BankruptcyTests extends GeneralTestMethods {
    
    public BankruptcyTests() {
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
     * Checks that when the players have enough money to pay whatever they have 
     * to pay that they are not removed from the game. Checks that when the 
     * players don’t have enough money they are removed from the game. 
     * Checks that if a player has lost all of its properties as well 
     * as becoming bankrupt if he had properties at the time.
     */
    @Test
    public void bankruptcyTests() {
        //Run the tests with all amount of players.
        for (int numPlayers = 1; numPlayers < Game.MAX_PLAYERS; numPlayers++) {
            nonBankruptTest(numPlayers);
            bankruptTest(numPlayers);
            complexBankruptTest(numPlayers);
        }
    }
    
    /**
     * Checks that when the players have enough money to pay whatever they have 
     * to pay that they are not removed from the game.
     * @param numPlayers number of players in games.
     */
    private void nonBankruptTest(int numPlayers) {
         //Test with player owing exactly the money to take him to 0 to the
         //bank. In this case the player shouldn't become bankrupt.
        super.setUpGameWithPlayer(50, numPlayers);
        int startingNumPlayers = Game.getInstance().getPlayers().size();
        testPlayer.forcedMoneyChange(-50, Bank.getInstance());
        if (Game.getInstance().getPlayers().size() == startingNumPlayers) {
            System.out.println("Test Successful: Players are all still"
                    + " in the game");
        } else {
            fail("There is now only " + 
                    Game.getInstance().getPlayers().size() + " players in "
                    + "the game");
        }
    }
    
    /**
     * Checks that when the players don’t have enough money they are 
     * removed from the game. 
     * @param numPlayers number of players in games.
     */
    private void bankruptTest(int numPlayers) {
        super.setUpGameWithPlayer(50, numPlayers);
        int startingNumPlayers = Game.getInstance().getPlayers().size();
        int predictedNumPlayers = startingNumPlayers - 1;
        testPlayer.forcedMoneyChange(-51, Bank.getInstance());
        if (Game.getInstance().getPlayers().size() == predictedNumPlayers) {
            System.out.println("Test Successful: Player removed from the game");
        } else {
            fail("There is still " + 
                    Game.getInstance().getPlayers().size() + " players in "
                    + "the game");
        }
    }
    
    /**
     * Checks that if a player has lost all of its properties as well 
     * as becoming bankrupt if he had properties at the time.
     * @param numPlayers number of players in games.
     */
    private void complexBankruptTest(int numPlayers) {
        super.setUpGameWithRandomPropertysAndHouses(50, numPlayers);
        List<Space> startingProperties = testPlayer.getProperties();
        int startingNumPlayers = Game.getInstance().getPlayers().size();
        int predictedNumPlayers = startingNumPlayers - 1;
        testPlayer.forcedMoneyChange(-51, Bank.getInstance());
        boolean bankruptcyResolved = false;
        if (Game.getInstance().getPlayers().size() == predictedNumPlayers) { 
            System.out.println("Test Successful: Player removed from the game");
            bankruptcyResolved = true;
        }
        if (hasPlayerLostProperty(
                startingProperties,testPlayer.getProperties())) {
            System.out.println("Test Successful: Player has sold properties but "
                    + "is still in the game");
            bankruptcyResolved = true;
        }
        if (!bankruptcyResolved) {
            fail("There is still " + 
                    Game.getInstance().getPlayers().size() + " players in "
                    + "the game and the player hasn't sold any properties.");
        }
    }
    
    /**
     * Checks if player has lost property.
     * @param originalProperty property player had at start.
     * @param currentProperty property player had at end.
     * @return true if player hasn't lost property.
     */
    private boolean hasPlayerLostProperty(List<Space> originalProperty,
            List<Space> currentProperty) {
        if (originalProperty.equals(currentProperty) 
                || originalProperty.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
