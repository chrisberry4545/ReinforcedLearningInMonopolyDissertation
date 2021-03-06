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
 * Test to make sure the appropriate action is taken when a player lands on
 * a site.
 * @author Chris Berry
 */
public class SiteSpaceTest extends GeneralTestMethods{
    
    public SiteSpaceTest() {
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
     * Tests every site with a random number of houses and makes sure the correct
     * rent is paid by players landing on them, whether their owners are in jail
     * or not. Tests some sites to ensure they are charging the correct rent
     * for the number of houses on them.
     */
     @Test
     public void siteSpaceTest() {
         //Tests site rents are working correctly.
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         Board board = Game.getInstance().getBoard();
         testPlayer.setSpace(board.get(0));
         for (Site site: board.getAllSites()) {
             site.setOwner(otherPlayer);
             int numHouses = (int)Math.round(Math.random() * Site.MAX_HOUSES);
             for (int i = 0; i < numHouses; i++) {
                 site.addHouseForFree();
             }
             testSiteRent(site);
             testInJailSiteRent(site);
         }
         
         //Test sites with houses are producing the correct rent.
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
         Board board2 = Game.getInstance().getBoard();
         Site oldKentRd = (Site)board2.get(1);
         Site whitehall = (Site)board2.get(13);
         Site marlboroughSt = (Site)board2.get(18);
         int[] houses1 = {2, 10,30,90,160,250};
         int[] houses2 = {10,50,150,450,625,750};
         int[] houses3 = {14,70,200,550,750,950};
         //No Houses
         checkRentIsCorrect(houses1,oldKentRd, 0);
         checkRentIsCorrect(houses2,whitehall, 0);
         checkRentIsCorrect(houses3,marlboroughSt, 0);
         for(int i = 1; i < Site.MAX_HOUSES; i++) {
            oldKentRd.addHouseForFree();
            checkRentIsCorrect(houses1,oldKentRd, i);
            
            whitehall.addHouseForFree();
            checkRentIsCorrect(houses2,whitehall, i);
            
            marlboroughSt.addHouseForFree();
            checkRentIsCorrect(houses3,marlboroughSt, i);
         }
     }
     
     /**
      * Ensure that the Site is charging the correct rent for the number of
      * houses on it.
      * @param houseRents house rent for property.
      * @param site to examine.
      * @param housesOnSite number of houses on site.
      */
     private void checkRentIsCorrect(int[] houseRents, Site site,
             int housesOnSite) {
         int correctRent = houseRents[housesOnSite];
         int chargedRent = site.getRent();
         if (correctRent == chargedRent) {
             System.out.println("The rent is as expected");
         } else {
             fail("A rent of " + correctRent + " was expected but the class "
                     + "is saying its rent is " + chargedRent + " for the "
                     + "property " + site.getName());
         }
     }
     
     /**
      * Test that when player lands on the site, he pays the correct amount
      * of rent.
      * @param site to check rent for.
      */
     private void testSiteRent(Site site) {
         int startingMoney = testPlayer.getMoney();
         int rent = site.getRent();
         Game.getInstance().movePlayerToSpaceNumber(testPlayer,
                 site.getSpaceNumber(), false);
         site.performAction(1, testPlayer);
         if (startingMoney - rent == testPlayer.getMoney()) {
             System.out.println("Test Succesfull: player has payed "
                     + "the correct amount of rent");
         } else {
             fail("The player started with " + startingMoney + " and paid a"
                     + " should of paid rent of " + rent + " but was instead "
                     + "left with " + testPlayer.getMoney() + ".");
         }
                
     }
     
     /**
      * Checks that site is still paid correctly even if the owner is in
      * jail.
      * @param site to check rent for.
      */
     private void testInJailSiteRent(Site site) {
         site.getOwner().setInJail(true);
         testSiteRent(site);
         site.getOwner().setInJail(false);
     }
}
