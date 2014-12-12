/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerTests.TDPlayerTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test has been retired as the method is no longer used. The test
 * gave me the answer that getting all combinations of Sites for a deal
 * was not feasible with many Sites involved. I had to come up with a different
 * way to handle the problem, the answer to which was deal sampling - randomly
 * selecting a number of different deal offers and having the AI select which
 * one to make from these. I have left this test around for a clearer picture
 * of what I have done and what it has taught me.
 * @author Chris Berry
 */
public class PropertyCombinationTest extends TDTestEnvirorment{
    
    public PropertyCombinationTest() {
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

    @Test
    public void propertyComboTest() {
//        super.setUpTDTestEnvirorment();
//        if (TDPlayer.getAllCombinationsOfSpaces(tdPlayerOne) == null) {
//            System.out.println("Test successful with no properties");
//        }
//        Board board = Game.getInstance().getBoard();
//        //This is max site limit, 18.
//        int numSitesToUse = board.getAllSites().size() - 4;
//        for (int i = 0; i < numSitesToUse; i++) {
//            board.getAllSites().get(i).setOwner(tdPlayerOne);
//        }
////        for (int i = 0; i < board.getAllStations().size(); i++) {
////            board.getAllStations().get(i).setOwner(tdPlayerOne);
////        }
////        for (int i = 0; i < board.getAllUtilities().size(); i++) {
////            board.getAllUtilities().get(i).setOwner(tdPlayerOne);
////        }
//        
//        TDPlayer.getAllCombinationsOfSpaces(tdPlayerOne);
//        
//        System.out.println("Number of sites used: " + numSitesToUse);
//        
    }

}
