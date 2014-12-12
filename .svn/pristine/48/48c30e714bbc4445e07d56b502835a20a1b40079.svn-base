/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CardTests;

import Model.Board;
import Model.Cards.Card;
import Model.Cards.GoToJail;
import Model.Cards.MovementCard;
import Model.Cards.NearestProperty;
import Model.Game;
import Model.Spaces.*;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the cards advance to X where X is a space.
 * @author Chris Berry
 */
public class AdvanceToXTests extends CardTestTemplate{
    
    private Board board;
    
    public AdvanceToXTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testAdvanceToX() {
         setUpGameWithPlayer(Game.PLAYER_STARTING_MONEY,1);
         board = Game.getInstance().getBoard();
         testNormalAdvancement();
         testCollectGoMoneyWorks();
         testNearestStationWorks();
         testNearestUtilWorks();
         testGoToJail();
        
     }
     
     public void testNormalAdvancement() {
         for (int i = 0; i < 100; i++) {
             int startingSpaceNum =
                     (int)Math.round(Math.random() * (board.size() - 1));
             Space startingSpace = board.get(startingSpaceNum);
             testPlayer.setSpace(startingSpace);
             int finishingSpaceNum = 
                     (int)Math.round(Math.random() * (board.size() - 1));
             //Makes sure goes back round the board rather than into the
             //negatives.
             if (finishingSpaceNum < 0) {
                finishingSpaceNum += board.size();
             }
             //Handles go to jail sending the player to jail.
             if (finishingSpaceNum == SpaceEnums.GO_TO_JAIL_NUMBER) {
                 finishingSpaceNum = SpaceEnums.JAIL_NUMBER;
             }
             //Handles trying to send the player to a chance (which could send
             //them elsewhere).
             if (board.get(finishingSpaceNum).getName().equals("Chance") ||
                     board.get(finishingSpaceNum).getName()
                     .equals("Community Chest")) {
                 finishingSpaceNum++;
             }
             Space finishingSpace = board.get(finishingSpaceNum);
             boolean collectGoMoney = false;
             if (Math.random() > 0.5) {
                 collectGoMoney = true;
             }
             Card card = new Card("Advance",
                new MovementCard(finishingSpaceNum, collectGoMoney), true, true);
             card.getAction().performAction(testPlayer, card);
             if (finishingSpace.equals(testPlayer.getCurrentSpace())) {
                 System.out.println("Test Succesful: Player moved from " +
                         startingSpace.getName() + " to " +
                         finishingSpace.getName() + ".");
             } else {
                 fail("Player moved from " + startingSpace.getName() + " to " +
                         testPlayer.getCurrentSpace().getName() + ", instead of "
                         + " to " + finishingSpace.getName() + " which should "
                         + "have happend.");
             }
         }
     }
     
     public void testCollectGoMoneyWorks() {
         for (int i = 0; i < 100; i++) {
             int startingSpaceNum =
                     (int)Math.round(Math.random() * (board.size() - 1));
             Space startingSpace = board.get(startingSpaceNum);
             testPlayer.setSpace(startingSpace);
             int finishingSpaceNum = 
                     (int)Math.round(Math.random() * (board.size() - 1));
             //Makes sure goes back round the board rather than into the
             //negatives.
             if (finishingSpaceNum < 0) {
                finishingSpaceNum += board.size();
             }
             Space finishingSpace = board.get(finishingSpaceNum);
             boolean collectGoMoney = false;
             if (Math.random() > 0.5) {
                 collectGoMoney = true;
             }
             //Handles trying to send the player to a chance (which could send
             //them elsewhere).
             if (board.get(finishingSpaceNum).getName().equals("Chance") ||
                     board.get(finishingSpaceNum).getName()
                     .equals("Community Chest")) {
                 finishingSpaceNum++;
             }
             int startingMoney = testPlayer.getMoney();
             Card card = new Card("Advance",
                new MovementCard(finishingSpaceNum, collectGoMoney), true, true);
             card.getAction().performAction(testPlayer, card);
             if (finishingSpaceNum < startingSpaceNum) {
                 int finishingMoney = testPlayer.getMoney();
                 if (collectGoMoney) {
                     if (finishingMoney == startingMoney + 200) {
                     System.out.println("Test Successful: Player moved from " +
                             startingSpace.getName() + " to " +
                             finishingSpace.getName() + " and received the " +
                             " $200 for passing go, increasing his money from " +
                             startingMoney + " to " + finishingMoney);
                    } else {
                        fail("Test Failed: Player moved from " +
                                startingSpace.getName() + " (" + startingSpaceNum +
                                ")" + " to " +
                                finishingSpace.getName() + " (" + finishingSpaceNum +
                                ")" +
                                " changing his money from " +
                                startingMoney + " to " + finishingMoney);
                    }
                 }
             }
         }
     }
     
     public void testNearestStationWorks() {
         //Player is at go.
         nearestStationOrUtilTest(0,SpaceEnums.KINGS_CROSS_NUMBER, true);
         //Player is at first station.
         nearestStationOrUtilTest(SpaceEnums.KINGS_CROSS_NUMBER,
                 SpaceEnums.MARYLEBONE_NUMBER, true);
         //Player is just past first station.
         nearestStationOrUtilTest(6,SpaceEnums.MARYLEBONE_NUMBER, true);
         //Player is at 2nd station.
         nearestStationOrUtilTest(SpaceEnums.MARYLEBONE_NUMBER,
                 SpaceEnums.FENCHURCH_NUMBER, true);
         //Player is just before 3rd station.
         nearestStationOrUtilTest(24,SpaceEnums.FENCHURCH_NUMBER, true);
         //Player is after 3rd station.
         nearestStationOrUtilTest(27,SpaceEnums.LIVERPOOL_ST_NUMBER, true);
         //Last station
         nearestStationOrUtilTest(SpaceEnums.LIVERPOOL_ST_NUMBER,
                 SpaceEnums.KINGS_CROSS_NUMBER, true);
         //After the last station
         nearestStationOrUtilTest(37,SpaceEnums.KINGS_CROSS_NUMBER, true);
         //Player is at the last space before go.
         nearestStationOrUtilTest(board.size()-1,
                 SpaceEnums.KINGS_CROSS_NUMBER, true);

     }
     
     private void nearestStationOrUtilTest(int startingSpace,
             int nearestStationOrUtil, boolean station) {
         testPlayer.setSpace(board.get(startingSpace));
         int startingMoney = testPlayer.getMoney();
         Card card = new Card("Advance to the nearest railway station. "
                + "If UNOWNED, you may buy it from the Bank. "
                + "If OWNED, pay owner twice the rental to which they are "
                + "otherwised entitled.",
                new NearestProperty(true, false), true, true);
         if (!station) {
             card = new Card("Advance to the nearest utility. "
                + "If UNOWNED, you may buy it from the Bank. "
                + "If OWNED, throw a dice and pay owner a total ten "
                + "times amount thrown.",
                new NearestProperty(true, true), true, true);
         }
         card.getAction().performAction(testPlayer, card);
         int newSpaceNumber = testPlayer.getCurrentSpace().getSpaceNumber();
         if (newSpaceNumber == nearestStationOrUtil) {
             System.out.println("Test Successful: player moved from " + 
                     startingSpace + "("+ board.get(startingSpace).getName() + ")"
                     + "to " +
                     newSpaceNumber + "(" + board.get(newSpaceNumber).getName()
                     + ")" +
                     ", the nearest station/util");
         } else {
             fail("Player hasn't moved to the correct station. He should be"
                     + " at " + board.get(nearestStationOrUtil).getName()
                     + " but instead at " 
                     + board.get(newSpaceNumber).getName());
         }
         int finishingMoney = testPlayer.getMoney();
         int distanceTravelled = newSpaceNumber - startingSpace;
         //If distance travelled is less than 0 then the player should have
         //passed go and therefore gained $200.
         if (distanceTravelled < 0) {
             if (finishingMoney - 200 == startingMoney) {
                 System.out.println("Test Succesful: Player past go and now has "
                         + finishingMoney + " after starting with " 
                         + startingMoney + " before passing go.");
             } else {
                 fail("Player hasn't gained the correct amount of money for"
                         + " passing go. He started with " + startingMoney +
                         " and ended with " + finishingMoney + ".");
             }
         }
     }
     
     public void testNearestUtilWorks() {
         //Nearest Util
         //Player is at go.
         nearestStationOrUtilTest(0,SpaceEnums.ELECTRIC_COMPANY_NUMBER, false);
         //Player is after go but before first util
         nearestStationOrUtilTest(5,SpaceEnums.ELECTRIC_COMPANY_NUMBER, false);
         //Player is on the first util
         nearestStationOrUtilTest(SpaceEnums.ELECTRIC_COMPANY_NUMBER,
                 SpaceEnums.WATER_COMPANY_NUMBER, false);
         //Player is after the first util
         nearestStationOrUtilTest(22,SpaceEnums.WATER_COMPANY_NUMBER, false);
         //Player is space before water company.
         nearestStationOrUtilTest(SpaceEnums.WATER_COMPANY_NUMBER - 1,
                 SpaceEnums.WATER_COMPANY_NUMBER, false);
         //Player is on water company.
         nearestStationOrUtilTest(SpaceEnums.WATER_COMPANY_NUMBER,
                 SpaceEnums.ELECTRIC_COMPANY_NUMBER, false);
         //player is on space after water company.
         nearestStationOrUtilTest(SpaceEnums.WATER_COMPANY_NUMBER  + 1,
                 SpaceEnums.ELECTRIC_COMPANY_NUMBER, false);
         //player is just before go.
         nearestStationOrUtilTest(board.size() - 1,
                 SpaceEnums.ELECTRIC_COMPANY_NUMBER, false);
         
     }
     
     public void testGoToJail() {
         testPlayer.setInJail(false);
         int startingMoney = testPlayer.getMoney();
         Card card = new Card("Go to jail. Go Directly to jail, "
                + "do not pass 'GO', do not collect $200.",
                new GoToJail(), true, true);
         card.getAction().performAction(testPlayer, card);
         int playerFinalMoney = testPlayer.getMoney();
         if (playerFinalMoney != startingMoney) {
             fail("Player's money has changed from " + startingMoney + " to "
                     + playerFinalMoney + " in the course of the card taking "
                     + "effect.");
         }
         if (testPlayer.inJail()) {
             System.out.println("Test Successful: Player is now in jail");
         } else {
             fail("Player not put in jail succesfully");
         }
     }

}
