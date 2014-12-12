package GameMechanicsTesting;

import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.Space;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the player move by the correct amount in game.
 * 
 * @author Chris Berry
 */
public class PlayerMoveTests extends GeneralTestMethods {
    
    private Board board;
    
    public PlayerMoveTests() {
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
     * Checks that moving the player a certain amount leaves it in the correct
     * space.
     */
     @Test
     public void playerMoveTest() {
         super.setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY, 2);
         board = Game.getInstance().getBoard();
         moveTest(board.get(0), 5, board.get(5));
         moveTest(board.get(board.size() - 1), 13,
                 board.get(12));
         moveTest(board.get(board.size() - 9), 9, board.get(0));
         moveTest(board.get(25),1,board.get(26));
         moveTest(board.get(3),30,board.get(33));
        
     }
     
     /**
      * Test fails if the player hasn't moved to the predicted end.
      * @param startingSpace space player will start in.
      * @param movement amount of spaces player will move.
      * @param predictedEnd space player is expected to finish in.
      */
     public void moveTest(Space startingSpace,int movement,Space predictedEnd) {
         testPlayer.setSpace(startingSpace);
         Game.getInstance().movePlayer(testPlayer, movement);
         Space endSpace = testPlayer.getCurrentSpace();
         if (endSpace.equals(predictedEnd)) {
             System.out.println("Test Succesfull: player started at " +
                     startingSpace.getName() + " and moved " + movement +
                     " spaces to " + predictedEnd.getName());
         } else {
             fail("Player started at " + startingSpace.getName() + " and was "
                     + "supposed to move " + movement + " to " + 
                     predictedEnd.getName() + " but instead ended up at " +
                     endSpace.getName() + ".");
         }
     }
}
