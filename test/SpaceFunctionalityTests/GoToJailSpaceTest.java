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
 * Makes sure the correct action occurs when the player lands on the go to
 * jail space.
 * @author Chris Berry
 */
public class GoToJailSpaceTest extends GeneralTestMethods{
    
    public GoToJailSpaceTest() {
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
     * Ensures that when they jail lands on the 'Go to Jail' space they are 
     * correctly sent to jail. This is tried from 50 random spaces to ensure
     * the effect is the same.
     */
     @Test
     public void goToJailTest() {
         super.setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY, 1);
         Board board = Game.getInstance().getBoard();
         for (int i = 0; i < 50; i++) {
            testPlayer.setInJail(false);
            int startingSpaceNum = (int)Math.round(
                    Math.random() * (board.size() - 1));
            testPlayer.setSpace(board.get(startingSpaceNum));
            int spacesToMove = SpaceEnums.GO_TO_JAIL_NUMBER - startingSpaceNum;
            if (spacesToMove < 0 ) {
                spacesToMove += board.size();
            }
            System.out.println(startingSpaceNum + spacesToMove);
            Game.getInstance().movePlayer(testPlayer, spacesToMove);
            testPlayer.getCurrentSpace().performAction(1, testPlayer);
            if (testPlayer.inJail()) {
                System.out.println("Test Success: Player is in jail");
            } else {
                fail("Player has not gone to jail");
            }
            if (testPlayer.getCurrentSpace().equals(board.get(10))) {
                System.out.println("Test Success: Player is on the correct"
                        + " space for jail");
            } else {
                fail("Player isn't in the correct space for jail");
            }
         }
         
         
     }
}
