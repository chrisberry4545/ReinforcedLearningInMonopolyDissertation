package CardTests;

import Model.Cards.Card;
import Model.Cards.ChairmanOfTheBoard;
import Model.Game;
import Model.Players.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the pay other players cards work correctly.
 * @author Chris Berry
 */
public class PayOtherPlayerTest extends CardTestTemplate{
    
    public PayOtherPlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Tests that paying other players works correctly from a card that
     * forces the player to do this. Tested from a position which would bankrupt
     * the player and from a normal game state.
     */
     @Test
     public void testPayOtherPlayers() {
         //Test with normal starting money.
         payOthersTest(Game.PLAYER_STARTING_MONEY);
         //Test with bankrupt player.
         payOthersTest(0);
     }
    
     /**
      * Tests that paying other players work correctly.
      * @param startingMoney players starting money.
      */
     public void payOthersTest(int startingMoney) {
         for (int numOfOtherPlayers = 1;
                 numOfOtherPlayers < Game.MAX_PLAYERS - 1;
                 numOfOtherPlayers++) {
             setUpGameWithPlayer(startingMoney,numOfOtherPlayers);
             Card card = new Card("You have been elected chairman of the board."
                + " Pay each player $50.",
                new ChairmanOfTheBoard(), true, true);
             Map<Player, Integer> playerMoney = new HashMap();
             for (Player player : Game.getInstance().getPlayers()) {
                 playerMoney.put(player,player.getMoney());
             }
             card.getAction().performAction(testPlayer, card);
             int counter = 0;
             int numPlayers = Game.getInstance().getPlayers().size();
             for (Player player : Game.getInstance().getPlayers()) {
                 int changeAmount = player.getMoney() - playerMoney.get(player);
                 if (player.equals(testPlayer)) {
                     if (player.getMoney() == 
                             playerMoney.get(player)  - (50 * (numPlayers - 1))) {
                         System.out.println("Player (who took card)"
                                 + " has lost the correct "
                                 + "amount");
                     } else {
                         fail("Player who picked card has had their money changed"
                                 + " by the incorrect "
                                 + "amount; changing by " +
                                 changeAmount + " instead of -50");
                     }
                 } else { //Other player
                     if (player.getMoney() == playerMoney.get(player) + 50) {
                         System.out.println("Other player " + player.getNumber() +
                                 " has gained the correct amount");
                     } else {
                         fail("Other player has changed by the incorrect amount; " +
                                 "changing by " + changeAmount + " instead of 50");
                     }
                 }
                 counter++;
             }       
         }
     }
}
