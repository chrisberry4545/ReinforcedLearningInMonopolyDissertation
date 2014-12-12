package SpaceFunctionalityTests;


import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test to ensure the correct action occurs when a player lands on a tax space.
 * @author Chris Berry
 */
public class TaxSpacesTest extends GeneralTestMethods{
    
    private Board board;
    
    public TaxSpacesTest() {
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
     * Tests that the player landing on certain tax spaces means he looses
     * the correct amount of cash.
     */
     @Test
     public void siteSpaceTest() {
         //Tests stations rents are working correctly.
         standardSetUp();
         //Player lands on income tax.
         testLandOnTax(board.get(SpaceEnums.INCOME_TAX_NUMBER), 200);
         //Player lands on super tax.
         testLandOnTax(board.get(SpaceEnums.SUPER_TAX_NUMBER), 100);
     }
     
     /**
      * Sets up a standard game instance.
      */
     private void standardSetUp() {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         board = Game.getInstance().getBoard();
         testPlayer.setSpace(board.get(0));
     }
     
     /**
      * Tests that the player looses the expected amount of money from landing
      * on a given tax space.
      * @param taxSpace player will land on.
      * @param expectedCost amount player should loose from this.
      */
     private void testLandOnTax(Space taxSpace, int expectedCost) {
         int startingMoney = testPlayer.getMoney();
         Game.getInstance().movePlayerToSpaceNumber(testPlayer,
                 taxSpace.getSpaceNumber(), false);
         taxSpace.performAction(1, testPlayer);
         if (startingMoney - expectedCost == testPlayer.getMoney()) {
             System.out.println("Test Succesfull: player has payed "
                     + "the correct amount of tax");
         } else {
             fail("The player started with " + startingMoney + " and "
                     + " should of paid tax of " + expectedCost
                     + " but was instead "
                     + "left with " + testPlayer.getMoney() + ".");
         }
                
     }
}
