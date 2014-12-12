/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BankTests;

import GeneralTestMethods.GeneralTestMethods;
import Model.Bank;
import Model.Board;
import Model.Game;
import Model.Spaces.Site;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the limit to the bank's houses and hotels works correctly.
 * @author Chris Berry
 */
public class BankTests extends GeneralTestMethods {
    
    private Board board;
    
    public BankTests() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void bankTests() {
         //Add houses/hotels with a cost
         hotelsAndHousesNormalTest(false);
         //Add houses/hotels for free
         hotelsAndHousesNormalTest(true);
         //Test to make sure hotels/houses can be removed when they are not
         //supposed to be.
         notEnoughHousesTest();
     }
     
     private void hotelsAndHousesNormalTest(boolean addForFree) {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         board = Game.getInstance().getBoard();
         Set<Site> sitesToTest = new HashSet();
         for (int i = 0; i < 6; i++) {
             int siteToPick = (int) Math.round
                     (Math.random() * (board.getAllSites().size()-1));
             Site sitePicked = board.getAllSites().get(siteToPick);
             sitePicked.setOwner(testPlayer);
             sitesToTest.add(sitePicked);
         }
         for (int i = 0;  i < Site.MAX_HOUSES - 1; i++) {
             for (Site site : sitesToTest) {
                 houseAddTest(site, addForFree);
             }
         }
         //At this point all houses will have one less than the max so adding
         //another should remove a hotel from the bank.
         for (Site site : sitesToTest) {
             hotelAddTest(site, addForFree);
         }
         //Hotel remove test
         for (Site site : sitesToTest) {
             hotelRemoveTest(site, addForFree);
         }
         //House remove test
         for (int i = 0; i < Site.MAX_HOUSES - 1; i++) {
             for (Site site : sitesToTest) {
                 houseRemoveTest(site, addForFree);
             }
         }
     }
     
     private void houseAddTest(Site site, boolean addForFree) { 
        int currentHousesLeft = Bank.getInstance().getHousesLeft();
        int predictedHousesLeft = currentHousesLeft - 1;
        if (addForFree) {
            site.addHouseForFree();
        } else {
            site.addHouse();
        }
        if (Bank.getInstance().getHousesLeft() == predictedHousesLeft) {
            System.out.println("Test Succesful: House succesfully taken "
                         + "from the bank");
        } else {
            fail("House not taken from the bank");
        }
     }
     
     private void hotelAddTest(Site site, boolean addForFree) {
        int currentHotelsLeft = Bank.getInstance().getHotelsLeft();
        int predictedHotelsLeft = currentHotelsLeft - 1;
        if (addForFree) {
            site.addHouseForFree();
        } else {
            site.addHouse();
        }
        if (Bank.getInstance().getHotelsLeft() == predictedHotelsLeft) {
            System.out.println("Test Succesful: Hotel succesfully taken "
                         + "from the bank");
        } else {
            fail("Hotel not taken from the bank");
        }
     }
     
     private void hotelRemoveTest(Site site, boolean addForFree) {
        int currentHotelsLeft = Bank.getInstance().getHotelsLeft();
        int predictedHotelsLeft = currentHotelsLeft + 1;
        if (addForFree) {
            site.returnHousesForFree(1);
        } else {
            site.returnHouses(1);
        }
        if (Bank.getInstance().getHotelsLeft() == predictedHotelsLeft) {
            System.out.println("Test Succesful: Hotel succesfully given back "
                         + "to the bank");
        } else {
            fail("Hotel not taken from the bank");
        }
     }
     
     private void houseRemoveTest(Site site, boolean addForFree) {
        int currentHousesLeft = Bank.getInstance().getHousesLeft();
        int predictedHousesLeft = currentHousesLeft + 1;
        if (addForFree) {
            site.returnHousesForFree(1);
        } else {
            site.returnHouses(1);
        }
        if (Bank.getInstance().getHousesLeft() == predictedHousesLeft) {
            System.out.println("Test Succesful: House succesfully given back "
                         + "to the bank");
        } else {
            fail("House not taken from the bank");
        }
     }
     
     private void notEnoughHousesTest() {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         int numHouses = Bank.getInstance().getHousesLeft();
         for (int i = 0; i < numHouses; i++) {
             if (!Bank.getInstance().removeHouse()) {
                 fail("House not succesfully removed");
             }
         }
         //At this point the bank should have 0 houses left and attempting
         //to remove a house should fail.
         if (!Bank.getInstance().removeHouse()) {
             System.out.println("Test succesful: No more houses can be removed "
                     + "from the bank");
         } else {
             fail("A house was removed from the bank when it should not have"
                     + " happend");
         }
         
         int numHotels = Bank.getInstance().getHotelsLeft();
         for (int i = 0; i < numHotels; i++) {
             if (!Bank.getInstance().removeHotel()) {
                 fail("Hotel not succesfully removed, the bank still has " +
                         Bank.getInstance().getHotelsLeft() + " hotels left.");
             }
         }
         //At this point the bank should have 0 hotels left and attempting
         //to remove a hotel should fail.
         if (!Bank.getInstance().removeHotel()) {
             System.out.println("Test succesful: No more hotels can be removed "
                     + "from the bank");
         } else {
             fail("A hotel was removed from the bank when it should not have "
                     + "happend");
         }
     }
     
     
}
