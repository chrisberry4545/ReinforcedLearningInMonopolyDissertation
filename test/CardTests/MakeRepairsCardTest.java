package CardTests;

import Model.Cards.Card;
import Model.Cards.GeneralRepairs;
import Model.Game;
import Model.Spaces.Site;
import Model.Spaces.Space;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the make repairs card works correctly.
 * @author Chris Berry
 */
public class MakeRepairsCardTest extends CardTestTemplate{
    
    public MakeRepairsCardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Checks having a repair card charges the player the correct amount of money.
     */
     @Test
     public void testRepairs() {
         runRepairTest(false, Game.PLAYER_STARTING_MONEY, 25, 100);
         runRepairTest(false, 0, 25, 100);
         for (int i = 0; i < 25; i++) {
            runRepairTest(true, Game.PLAYER_STARTING_MONEY, 25, 100);
            runRepairTest(true, 0, 25, 100);
         }         
         
     }
     /**
      * Runs a repair tests, checking the player is charged the correct amount.
      * @param includeRandomHouses true if the player should be given a random
      * number of houses.
      * @param startingMoney starting money for the players.
      * @param houseCost amount a house should cost a player.
      * @param hotelCost  amount a hotel should cost a player.
      */
     private void runRepairTest(boolean includeRandomHouses, int startingMoney,
             int houseCost, int hotelCost) {
         if (includeRandomHouses) {
             super.setUpGameWithRandomPropertysAndHouses(
                 startingMoney, 1);
         } else {
             super.setUpGameWithPlayer(startingMoney, 1);
         }
         makeRepairsTest(houseCost,hotelCost);
     }
     
     
     /**
      * Checks that the player pays the correct amount for all of its houses
      * and hotels.
      * @param houseCost amount to pay on a house.
      * @param hotelCost amount to pay on a hotel.
      */
     public void makeRepairsTest(int houseCost, int hotelCost) {
         Card card = new Card("Make general repairs on all your property:"
                + " For each house pay $25, for each hotel pay $100.",
                new GeneralRepairs(houseCost, hotelCost), true, true);
         int currentMoney = testPlayer.getMoney();
         int startingMortgageValue = 0;
         for (Space space : testPlayer.getProperties()) {
             startingMortgageValue += space.getFullMortgageValue();
         }
         //Calculate the number of houses and hotels owned by the player.
         int numHouses = 0;
         int numHotels = 0;
         for (Space space : testPlayer.getPropertiesWithHouses()) {
             Site site =(Site)space;
             if (site.getHouses() == Site.MAX_HOUSES) {
                 numHotels++;
             } else {
                 numHouses += site.getHouses();
             }
         }
         card.getAction().performAction(testPlayer, card);
         int moneyAfterCard = testPlayer.getMoney();
         
         int repairsCost = houseCost * numHouses + hotelCost * numHotels;
         int finalMortgageValue = 0;
         for (Space space : testPlayer.getProperties()) {
             finalMortgageValue += space.getFullMortgageValue();
         }
         int mortgageValueDifference =
                 startingMortgageValue - finalMortgageValue;
         int correctResult = currentMoney - repairsCost + mortgageValueDifference;
         if (correctResult < 0) {
             System.out.println("Repairs would bankrupt player.");
         } else {

            //Succesful if player is charged the correct amount of is bankrupt and
            //no longer in the game.
            if (correctResult == moneyAfterCard) {
                System.out.println("Test Succesful: The player started with " +
                       currentMoney + " and after the repair cost of " +
                       repairsCost + " and selling " + mortgageValueDifference +
                       " worth of sites and housing, "
                       + " is left with " + moneyAfterCard + ".");
            } else {
                fail("The player is left with " + moneyAfterCard  + " instead of the"
                + " " + correctResult + " the player should have been left with \n "
                       + " for " + numHouses + " houses and " + numHotels +
                       " hotels and after mortgaging to a value of " +
                       mortgageValueDifference + ".");
            }
         }
     }

}
