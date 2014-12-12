package SpaceFunctionalityTests;


import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test to make sure the appropriate action is taken when the player lands on
 * a station.
 * @author Chris Berry
 */
public class StationSpaceTest extends GeneralTestMethods{
    
    private Board board;
    
    public StationSpaceTest() {
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
     * Tests that all stations are charging the correct rent when a player holds
     * 1, 2, 3, or all 4 stations.
     */
     @Test
     public void siteSpaceTest() {
         //Tests stations rents are working correctly.
         standardSetUp();
         checkTheNumberOfStationsIsCorrect();
         //Test other player owning all stations
         for (Space util: board.getAllStations()) {
             util.setOwner(otherPlayer);
         }
         testStationRents(board.getAllStations());
         
         //Test other player owning 3 stations.
         testPlayerOwningNumberOfStations(3);
         //Test other player owning 2 stations.
         testPlayerOwningNumberOfStations(2);
         //Test other player owning 1 station.
         testPlayerOwningNumberOfStations(1);
         
         //Test normal landing on station without owner.
         testPlayerOwningNumberOfStations(0);
     }
     
     /**
      * Tests the correct things are happening for different numbers of stations.
      * @param numStations number of stations to use.
      */
     private void testPlayerOwningNumberOfStations(int numStations) {
         standardSetUp();
         List<Station> stationsToTest = new ArrayList();
         for (int i = 0; i < numStations; i++) {
             Station testStation = board.getAllStations().get(i);
             testStation.setOwner(otherPlayer);
             stationsToTest.add(testStation);
         }
         testStationRents(stationsToTest);
     }
     
     /**
      * Tests the rent of the stations for the different number of stations
      * a player could hold.
      * @param stations to test.
      */
     private void testStationRents(List<Station> stations) {
         int numStations = stations.size();
         int expectedRent = 0;
         switch (numStations) {
             case 0 : expectedRent = 0;
                 break;
             case 1 : expectedRent = 25;
                 break;
             case 2 : expectedRent = 50;
                 break;
             case 3 : expectedRent = 100;
                 break;
             case 4 : expectedRent = 200;
                 break;
             default : fail("Number of stations should be between 1 and 4");
         }
         for(Station station : stations) {
             testStationRent(station, expectedRent);
         }
     }
     
     /**
      * Checks the number of stations on the board is correct.
      */
     private void checkTheNumberOfStationsIsCorrect() {
         if (board.getAllStations().size() == 4) {
             System.out.println("Test Succesful: The board has 2 stations");
         } else {
             fail("The board has " + board.getAllStations().size() +
                     " when it should have 4 stations");
         }
     }
     
     /**
      * Sets up a standard game instance.
      */
     private void standardSetUp() {
         super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY*10);
         board = Game.getInstance().getBoard();
         testPlayer.setSpace(board.get(0));
     }
     
     /**
      * Tests that the station rent is the same as the expected rent.
      * @param station to test.
      * @param expectedRent expected rent palyer should pay when landing on the
      * space.
      */
     private void testStationRent(Space station, int expectedRent) {
         int startingMoney = testPlayer.getMoney();
         Game.getInstance().movePlayerToSpaceNumber(testPlayer,
                 station.getSpaceNumber(), false);
         station.performAction(1, testPlayer);
         if (startingMoney - expectedRent == testPlayer.getMoney()) {
             System.out.println("Test Succesfull: player has payed "
                     + "the correct amount of rent");
         } else {
             fail("The player started with " + startingMoney + " and paid a"
                     + " should of paid rent of " + expectedRent
                     + " but was instead "
                     + "left with " + testPlayer.getMoney() + ".");
         }
                
     }
}
