package SpaceFunctionalityTests;

import GeneralTestMethods.GeneralTestMethods;
import Model.Cards.Card;
import Model.Deck;
import Model.Game;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests to make sure drawing from the chance and community chest pile is 
 * handled correctly.
 * @author Chris Berry
 */
public class ChanceAndCommChestCardDrawTest extends GeneralTestMethods{
    
    public ChanceAndCommChestCardDrawTest() {
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
      * Checks to see if cards are being put back into the pile correctly.
      */
     @Test
     public void testChanceAndCommChest() {
         super.setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY, 1);
         testPlayer.setSpace(Game.getInstance().getBoard().get(0));
         Deck chanceCards = Game.getInstance().getChanceCards();
         Deck commChest = Game.getInstance().getCommmunityChest();
         System.out.println("Chance Card Tests:");
         for (int i = 0; i < 50; i++){
             drawAndShuffleBack(chanceCards, true);
         }
         System.out.println("Community Chest Tests:");
         for (int i = 0; i < 50; i++){
             drawAndShuffleBack(commChest, false);
         }
     }

     /**
      * The card is drawn from the pile and put back onto the bottom of the 
      * pile. This checks if that was handled correctly for a given deck.
      * @param deckToUse Deck to draw from.
      * @param chance true if the chance pile is being used, false if it 
      * is the community chest.
      */
     public void drawAndShuffleBack(Deck deckToUse, boolean chance) {
         Card topCard = deckToUse.getCards().get(0);
         if (chance) {
             Game.getInstance().drawFromChance(testPlayer);
         } else {
             Game.getInstance().drawFromCommunityChest(testPlayer);
         }
         Card bottemCard = 
                 deckToUse.getCards().get(deckToUse.getCards().size() - 1);
         if (topCard.isPutAtBottom()) {
            if (topCard.equals(bottemCard)) {
                System.out.println("Test is successful: top card was " +
                        topCard.getName() + " which was put on the bottem " +
                        "after it had been used."); 
            } else {
                fail("Top card was " + topCard.getName() + " which should have "
                        + "been put back at the bottem of the deck but the bottem "
                        + "card was " + bottemCard.getName());
            }
         }
     }
}
