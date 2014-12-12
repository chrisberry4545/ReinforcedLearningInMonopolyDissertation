package SpaceFunctionalityTests;


import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.SpaceEnums;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Makes sure the correct things occur at the go and free parking spaces.
 * @author Chris Berry
 */
public class GoAndFreeParkingTests extends GeneralTestMethods{
    
    private Board board;
    
    public GoAndFreeParkingTests() {
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
     * Tests moving the player past and to 'GO' and 'Free Parking' and checks
     * that there money changes by the correct amount (or by no amount at all).
     */
     @Test
     public void siteSpaceTest() {
         //Tests stations rents are working correctly.
         standardSetUp();
         //Free parking tests
         testPlayer.setSpace(board.get(SpaceEnums.FREEPARKING_NUMBER - 10));
         testLandOnSpace(10, 0);
         testPlayer.setSpace(board.get(SpaceEnums.FREEPARKING_NUMBER - 15));
         testLandOnSpace(15, 0);
         testPlayer.setSpace(board.get(SpaceEnums.FREEPARKING_NUMBER - 19));
         testLandOnSpace(19, 0);
         testPlayer.setSpace(board.get(SpaceEnums.FREEPARKING_NUMBER));
         testLandOnSpace(board.size(), 200);
         
         //Landing past go and on go tests.
         testPlayer.setSpace(board.get(SpaceEnums.MAYFAIR_NUMBER));
         testLandOnSpace(1, 200);
         testPlayer.setSpace(board.get(SpaceEnums.MAYFAIR_NUMBER));
         testLandOnSpace(10, 200);
         testPlayer.setSpace(board.get(SpaceEnums.LIVERPOOL_ST_NUMBER));
         testLandOnSpace(5, 200);
         testPlayer.setSpace(board.get(SpaceEnums.LIVERPOOL_ST_NUMBER));
         testLandOnSpace(10, 200);
         testPlayer.setSpace(board.get(SpaceEnums.FENCHURCH_NUMBER + 1));
         testLandOnSpace(14, 200);
         testPlayer.setSpace(board.get(SpaceEnums.FENCHURCH_NUMBER + 1));
         testLandOnSpace(15, 200);
     }
     
     /**
      * Sets up a standard version of the game.
      */
     private void standardSetUp() {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         board = Game.getInstance().getBoard();
         testPlayer.setSpace(board.get(0));
     }
     
     /**
      * Tests the result of landing on the space. If the players new money
      * is the same as the expected game then the test is a success.
      * @param amountToMove the distance to move the player.
      * @param expectedGain the expected monetary gain from the move.
      */
     private void testLandOnSpace(int amountToMove, int expectedGain) {
         int startingMoney = testPlayer.getMoney();
         Game.getInstance().movePlayer(testPlayer,amountToMove);
         testPlayer.getCurrentSpace().performAction(1, testPlayer);
         if (startingMoney + expectedGain == testPlayer.getMoney()) {
             System.out.println("Test Succesfull: player has gained "
                     + "the correct amount of money");
         } else {
             fail("The player started with " + startingMoney + " and "
                     + " should of gained  " + expectedGain
                     + " but was instead "
                     + "left with " + testPlayer.getMoney() + ".");
         }
                
     }
}
