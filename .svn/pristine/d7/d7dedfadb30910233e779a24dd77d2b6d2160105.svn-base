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
 * Tests the player receives properties correctly from the receive property
 * method (which is called when another player becomes bankrupted by this
 * player and must give this player all of its property).
 * @author Chris Berry
 */
public class TDReceivePropertyTest extends TDTestEnvirorment{
    
    public TDReceivePropertyTest() {
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
     * Tests the player receives properties correctly from the receive property
     * method (which is called when another player becomes bankrupted by this
     * player and must give this player all of its property).
     */
    @Test
    public void receivePropertyTDPlayerTest() {
        super.setUpTDTestEnvirorment();
        List<Site> allSites = Game.getInstance().getBoard().getAllSites();
        List<Utility> allUtils = Game.getInstance().getBoard().getAllUtilities();
        List<Station> allStations = Game.getInstance().getBoard().getAllStations();
        
        List<Space> listToGiveToPlayer = new ArrayList();
        //Test player getting given a long list of properties.
        Site site1 = allSites.get(0);
        Site site2 = allSites.get(1);
        Site site3 = allSites.get(2);
        Site site4 = allSites.get(3);
        Site site5 = allSites.get(4);
        Site site6 = allSites.get(5);
        Site site7 = allSites.get(6);
        Utility util1 = allUtils.get(0);
        Utility util2 = allUtils.get(1);
        Station station1 = allStations.get(0);
        Station station2 = allStations.get(1);
        Station station3 = allStations.get(2);
        Station station4 = allStations.get(3);
        
        listToGiveToPlayer.add(site1);
        listToGiveToPlayer.add(site2);
        listToGiveToPlayer.add(site3);
        listToGiveToPlayer.add(site4);
        listToGiveToPlayer.add(site5);
        listToGiveToPlayer.add(site6);
        listToGiveToPlayer.add(site7);
        listToGiveToPlayer.add(util1);
        listToGiveToPlayer.add(util2);
        listToGiveToPlayer.add(station1);
        listToGiveToPlayer.add(station2);
        listToGiveToPlayer.add(station3);
        listToGiveToPlayer.add(station4);
        
        //Give player all other properties.
        for (Space property : listToGiveToPlayer) {
            property.setOwner(this.otherPlayer);
        }
        
        this.tdPlayerOne.receiveProperty(listToGiveToPlayer);
        
        //Check the right players now own the property.
        for (Space property : listToGiveToPlayer) {
            Player owner = property.getOwner();
            if (!owner.equals(tdPlayerOne)) {
                fail("The property is owned by player " + owner.getNumber() +
                        " when it should be owned by " +
                        tdPlayerOne.getNumber());
            }
        }
        if (!this.otherPlayer.getProperties().isEmpty()) {
            fail("Other player still has " + otherPlayer.getProperties().size() +
                    " properties when he should have 0");
        }
        System.out.println("Test successful: player now owns all the correct"
                + " properties");
        
    }
    
}
