/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerTests.TDPlayerTests;

import Model.Game;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Model.Spaces.Site;
import Model.Spaces.*;
import Model.Players.*;
import java.util.List;
import static org.junit.Assert.*;
import java.util.ArrayList;
/**
 * Checks that selling the TD Player selling houses leave it with the correct
 * amount.
 * @author Chris Berry
 */
public class TDAISellHousesTest extends TDTestEnvirorment{
    
    public TDAISellHousesTest() {
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
     * Checks that selling the TD Player selling houses leave it with the correct
     * amount.
     */
    @Test
    public void checksSellingHousesWorks() {
        super.setUpTDTestEnvirorment();
        
        Site site1 = Game.getInstance().getBoard().getAllSites().get(0);
        Site site2 = Game.getInstance().getBoard().getAllSites().get(4);
        Site site3 = Game.getInstance().getBoard().getAllSites().get(15);
        Site site4 = Game.getInstance().getBoard().getAllSites().get(20);
        
        site1.setOwner(this.tdPlayerOne);
        site2.setOwner(this.tdPlayerOne);
        site3.setOwner(this.tdPlayerOne);
        site4.setOwner(this.tdPlayerOne);
        
        //each site will have a different number of houses on it.
        site1.addHouseForFree();
        
        site2.addHouseForFree();
        site2.addHouseForFree();
        
        site3.addHouseForFree();
        site3.addHouseForFree();
        site3.addHouseForFree();
        
        site4.addHouseForFree();
        site4.addHouseForFree();
        site4.addHouseForFree();
        site4.addHouseForFree();
        
        //Sell 4 houses.
        int housesOwned = countHousesOwned(this.tdPlayerOne);
        int housesToSell = 4;
        int predictedHousesRemaining = housesOwned - housesToSell;
        for (int i = 0; i < housesToSell; i++) {
            tdPlayerOne.sellHouses();
        }   
        playerSoldCorrectAmountOfHouses(tdPlayerOne, predictedHousesRemaining);
        
        //Sell one house.
        housesOwned = countHousesOwned(this.tdPlayerOne);
        housesToSell = 1;
        predictedHousesRemaining = housesOwned - housesToSell;
        for (int i = 0; i < housesToSell; i++) {
            tdPlayerOne.sellHouses();
        }
        playerSoldCorrectAmountOfHouses(tdPlayerOne, predictedHousesRemaining);
        
        //Sell the rest of the houses.
        housesOwned = countHousesOwned(this.tdPlayerOne);
        housesToSell = housesOwned;
        predictedHousesRemaining = housesOwned - housesToSell;
        for (int i = 0; i < housesToSell; i++) {
            tdPlayerOne.sellHouses();
        }
        playerSoldCorrectAmountOfHouses(tdPlayerOne, predictedHousesRemaining);
    }
    
    private int countHousesOwned(Player player) {
        int housesOwned = 0;
        for (Site site : player.getSites()) {
            housesOwned += site.getHouses();
        }
        return housesOwned;
    }
    
    private void playerSoldCorrectAmountOfHouses(Player player,
            int predictedHousesLeft) {
        int actualHousesOwned = countHousesOwned(player);
        if (actualHousesOwned == predictedHousesLeft) {
            System.out.println("Test Successful: player has been left with "
                    + predictedHousesLeft + " as predicted.");
        } else {
            fail("Player has " + actualHousesOwned + " on their properties when "
                    + "it was predicted they should have been left with " +
                    predictedHousesLeft);
        }
    }
    
}
