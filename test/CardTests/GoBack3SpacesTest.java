package CardTests;

import Model.Board;
import Model.Cards.Card;
import Model.Cards.MoveBackwards;
import Model.Game;
import Model.Spaces.*;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the go back 3 spaces card works correctly.
 * @author Chris Berry
 */
public class GoBack3SpacesTest extends CardTestTemplate{
    
    public GoBack3SpacesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
     @Test
     public void testGoBack3Spaces() {
         setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY,1);
         Board board = Game.getInstance().getBoard();
         for (int i = 0; i < 40; i++) {
             int startingSpaceNum =
                     (int)Math.round(Math.random() * (board.size() - 1));
             testPlayer.setSpace(board.get(startingSpaceNum));
             int predictedFinishingSpaceNum = startingSpaceNum - 3;
             //Makes sure goes back round the board rather than into the
             //negatives.
             if (predictedFinishingSpaceNum < 0) {
                predictedFinishingSpaceNum += board.size();
             }
             Space predictedFinishingSpace =
                     board.get(predictedFinishingSpaceNum);
             testGoBackCase(predictedFinishingSpace);           
         }
     }
     
     public void testGoBackCase(Space predictedEndingSpace) {
         int startingSpaceNum = testPlayer.getCurrentSpace().getSpaceNumber();
         Space startingSpace = testPlayer.getCurrentSpace();
         int predictedEndSpaceNum = predictedEndingSpace.getSpaceNumber();
         if (Game.getInstance().getBoard().get(predictedEndSpaceNum).getName()
                 .equals(SpaceEnums.CHANCE.getName()) ||
                 Game.getInstance().getBoard().get(predictedEndSpaceNum).getName()
                 .equals(SpaceEnums.COMMCHEST.getName())
                 || Game.getInstance().getBoard().get(predictedEndSpaceNum).getName()
                 .equals(SpaceEnums.GOTOJAIL.getName())) {
             //Nothing to do if the player landed on a chance or commchest
             //as they could be moved anywhere.
         } else {
            Card card = new Card("Go back three spaces",
                   new MoveBackwards(), true, true);
            card.getAction().performAction(testPlayer, card);
            Space finalSpace = testPlayer.getCurrentSpace();
            int finalSpaceNum = testPlayer.getCurrentSpace().getSpaceNumber();
            if (finalSpaceNum == predictedEndSpaceNum) {
                System.out.println("The player moved from space num "
                        + startingSpaceNum +" to space number " +
                        finalSpaceNum + ", as was predicted.");
                System.out.println("This is " + startingSpace.getName() + " to " +
                        finalSpace.getName());
            } else {
                fail("The player landed on " + finalSpaceNum + " when it was "
                        + "believed he should be landing on " +
                        predictedEndSpaceNum + ", " +
                        Game.getInstance().getBoard().get(predictedEndSpaceNum).getName()
                        + ".");
            }
         }
     }
}
