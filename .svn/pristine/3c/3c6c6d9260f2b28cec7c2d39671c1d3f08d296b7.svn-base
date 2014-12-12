package CardTests;

import Model.Cards.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import Model.*;

/**
 * Checks whether the change Money cards work correctly.
 * @author Chris Berry
 */
public class ChangeMoneyCardTest extends CardTestTemplate {
    
    public ChangeMoneyCardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Tests a card on a player to see if it works.
     */
    @Test
    public void testCard() {
        normalPlayTests();
        playerWithZeroMoneyTest();
        
    }
    
    public void normalPlayTests() {
        setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY,1);
        runStandardTestsOnPlayer();
        setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY,1);
        runNegativeTests();
    }
    
    public void playerWithZeroMoneyTest() {
        setUpGameWithPlayer(0,1);
        runStandardTestsOnPlayer();
        setUpGameWithPlayer(0,1);
        runNegativeTests();
    }
    
    public void runStandardTestsOnPlayer() {
        int bankErrorMoneyChange = 200;
        runChangeMoneyTest(bankErrorMoneyChange);
        int doctorFeesMoneyChange = -50;
        runChangeMoneyTest(doctorFeesMoneyChange);
        int holidayfundMoneyChange = 100;
        runChangeMoneyTest(holidayfundMoneyChange);
        int schoolFeesMoneyChange = -50;
        runChangeMoneyTest(schoolFeesMoneyChange);
        int taxRefundMoneyChange = 20;
        runChangeMoneyTest(taxRefundMoneyChange);
        int hospitalFeesMoneyChange = -100;
        runChangeMoneyTest(hospitalFeesMoneyChange);
        int consultancyFeeMoneyChange = -25;
        runChangeMoneyTest(consultancyFeeMoneyChange);
        int inheritMoneyChange = 100;
        runChangeMoneyTest(inheritMoneyChange);
        int stockSaleMoneyChange = 50;
        runChangeMoneyTest(stockSaleMoneyChange);
        int beautyContestMoneyChange = 10;
        runChangeMoneyTest(beautyContestMoneyChange);
    }
    
    public void runNegativeTests() {
        int doctorFeesMoneyChange = -50;
        runChangeMoneyTest(doctorFeesMoneyChange);
        int schoolFeesMoneyChange = -50;
        runChangeMoneyTest(schoolFeesMoneyChange);
        int hospitalFeesMoneyChange = -100;
        runChangeMoneyTest(hospitalFeesMoneyChange);
        int consultancyFeeMoneyChange = -25;
        runChangeMoneyTest(consultancyFeeMoneyChange);
    }
    
    public void runChangeMoneyTest(int moneyToChange) {
        
        int testPlayerStartingMoney = testPlayer.getMoney();
        System.out.println("Player's current money: "+ testPlayerStartingMoney);
        System.out.println("Attempting to change this by " + moneyToChange);
        Card card = new Card("Change money card",
                new ChangeMoneyCard(moneyToChange), true, true);
        card.getAction().performAction(testPlayer, card);
        
        int testPlayerFinishingMoney = testPlayer.getMoney();
        
        if ((testPlayerFinishingMoney - testPlayerStartingMoney)
                == moneyToChange) {
            System.out.println("Test Succesfull, changed the player's money by "
                    + moneyToChange);
        } else {
            //Checks if the player has been removed from the game.
            if (!Game.getInstance().getPlayers().contains(testPlayer)) {
                System.out.println("Test Succesfull: the player is no longer " +
                        "in the game.");
                
            } else {
                fail("Money not changed by the correct amount"); 
            }
        }
    }
}
