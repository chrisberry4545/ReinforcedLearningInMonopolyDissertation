package SpaceFunctionalityTests;


import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test to ensure the correct action occurs when a player lands on a utility
 * space.
 * @author Chris Berry
 */
public class UtilSpaceTest extends GeneralTestMethods{
    
    private Board board;
    
    public UtilSpaceTest() {
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
     * Tests a player pays the correct rent for any dice roll between 1-35,
     * and whether the other player owns the water company, electric company 
     * or both.
     */
     @Test
     public void siteSpaceTest() {
         //Tests utils rents are working correctly.
         standardSetUp();
         checkTheNumberOfUtilsIsCorrect();
         //Test other player owning all utils
         for (Space util: board.getAllUtilities()) {
             util.setOwner(otherPlayer);
         }
         //Test for all dice rolls to 35 (the maximum).
         testAllDiceRollsForUtils(board.getAllUtilities(), 10);
         
         //Test other player owning electric company.
         standardSetUp();
         Utility elecCompany = board.getAllUtilities().get(0);
         elecCompany.setOwner(otherPlayer);
         List<Utility> elecCompanyList = new ArrayList();
         elecCompanyList.add(elecCompany);
         testAllDiceRollsForUtils(elecCompanyList, 4);
         
         //Test other player owning water company.
         standardSetUp();
         Utility waterCompany = board.getAllUtilities().get(1);
         waterCompany.setOwner(otherPlayer);
         List<Utility> waterCompanyList = new ArrayList();
         waterCompanyList.add(waterCompany);
         testAllDiceRollsForUtils(waterCompanyList, 4);
     }
     
     /**
      * Tests a utility is charging the correct amount for any dice roll,
      * 1 to 36.
      * @param utils
      * @param multiplyer 
      */
     private void testAllDiceRollsForUtils(List<Utility> utils, int multiplyer) {
         //Test for all dice rolls to 35 (the maximum).
         for (Utility util : utils) {
             for (int i = 1; i < 36; i++) {
                 int expectedRent = multiplyer * i;
                 testUtilRent(util, i, expectedRent);
             }
         }
     }
     
     /**
      * Checks the number of utilities on the board is correct.
      */
     private void checkTheNumberOfUtilsIsCorrect() {
         if (board.getAllUtilities().size() == 2) {
             System.out.println("Test Succesful: The board has 2 utils");
         } else {
             fail("The board has " + board.getAllUtilities().size() +
                     " when it should have 2 utils");
         }
     }
     
     /**
      * Sets up a standard game.
      */
     private void standardSetUp() {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         board = Game.getInstance().getBoard();
         testPlayer.setSpace(board.get(0));
     }
     
     /**
      * Tests the rent of landing on a utility is as expected.
      * @param util to land on.
      * @param diceRoll dice roll thrown by the player.
      * @param expectedRent amount expected that player should pay.
      */
     private void testUtilRent(Space util, int diceRoll, int expectedRent) {
         int startingMoney = testPlayer.getMoney();
         
         Game.getInstance().movePlayerToSpaceNumber(testPlayer,
                 util.getSpaceNumber(), false);
         util.performAction(diceRoll, testPlayer);
         if (startingMoney - expectedRent == testPlayer.getMoney()) {
             System.out.println("Test Succesfull: player has payed "
                     + "the correct amount of rent");
         } else {
             fail("The player started with " + startingMoney + " and paid a"
                     + " should of paid rent of " + expectedRent
                     + " but was instead "
                     + "left with " + testPlayer.getMoney() + ".");
         }
                
     }
}
