package CardTests;

import Model.Cards.Card;
import Model.Cards.GetOutOfJailCard;
import Model.Game;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the get out of jail card functions correctly.
 * @author Chris Berry
 */
public class GetOutOfJailCardTest extends CardTestTemplate{
    
    public GetOutOfJailCardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
     @Test
     public void testGetOutOfJail() {
         setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY,1);
         Card card = new Card("Get out of jail",
                new GetOutOfJailCard(), false, true);
         int startingGOJCards = testPlayer.getNumberOfGetOutOfJailCards();
         card.getAction().performAction(testPlayer, card);
         int finishingGOJCards = testPlayer.getNumberOfGetOutOfJailCards();
         if (startingGOJCards + 1 == finishingGOJCards) {
             System.out.println("Test Succesful: player had " + startingGOJCards
                     + " get out of jail card and now has " + finishingGOJCards);
         } else {
             fail("Player doesn't seem to have been given the correct amount"
                     + " of get out of jail cards.");
         }
         
     }
}
