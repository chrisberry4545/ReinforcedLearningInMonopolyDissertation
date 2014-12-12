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

/**
 * Checks that the TD player mortgaging sites leaves the player with the
 * correct amount of sites.
 * @author Chris Berry
 */
public class TDMortgagePropertiesTest extends TDTestEnvirorment{
    
    public TDMortgagePropertiesTest() {
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
     * Checks that the TD player mortgaging sites leaves the player with the
     * correct amount of sites.
     */
    @Test
    public void testMortgageProperty() {
        super.setUpTDTestEnvirorment();
        List<Site> allSites = Game.getInstance().getBoard().getAllSites();
        List<Utility> allUtils = Game.getInstance().getBoard().getAllUtilities();
        List<Station> allStations = Game.getInstance().getBoard().getAllStations();
        
        //Test player with one site.
        Site site1 = allSites.get(0);
        site1.setOwner(this.tdPlayerOne);
        int numOfSites = this.tdPlayerOne.getMortgagableProperties().size();
        this.tdPlayerOne.mortgageProperties();
        testProperty(tdPlayerOne, numOfSites - 1);
        
        //Test player with a site and util.
        Site site2 = allSites.get(1);
        Utility util1 = allUtils.get(0);
        site2.setOwner(tdPlayerOne);
        util1.setOwner(tdPlayerOne);
        numOfSites = this.tdPlayerOne.getMortgagableProperties().size();
        this.tdPlayerOne.mortgageProperties();
        testProperty(tdPlayerOne, numOfSites - 1);
        
        //Test player with station and util.
        Utility util2 = allUtils.get(1);
        Station station1 = allStations.get(0);
        
        util2.setOwner(tdPlayerOne);
        station1.setOwner(tdPlayerOne);
        
        numOfSites = this.tdPlayerOne.getMortgagableProperties().size();
        this.tdPlayerOne.mortgageProperties();
        testProperty(tdPlayerOne, numOfSites - 1);
        
        //Test player with station, util and site.
        Station station2 = allStations.get(1);
        Site site3 = allSites.get(2);
        
        site3.setOwner(tdPlayerOne);
        station2.setOwner(tdPlayerOne);
        
        numOfSites = this.tdPlayerOne.getMortgagableProperties().size();
        this.tdPlayerOne.mortgageProperties();
        testProperty(tdPlayerOne, numOfSites - 1);
        
        //Test player with 4 sites, 4 stations and 2 utils.
        Site site4 = allSites.get(3);
        Site site5 = allSites.get(4);
        Site site6 = allSites.get(5);
        Site site7 = allSites.get(6);
        
        Station station3 = allStations.get(2);
        Station station4 = allStations.get(3);
        
        site4.setOwner(tdPlayerOne);
        site5.setOwner(tdPlayerOne);
        site6.setOwner(tdPlayerOne);
        site7.setOwner(tdPlayerOne);
        station3.setOwner(tdPlayerOne);
        station4.setOwner(tdPlayerOne);
        
        numOfSites = this.tdPlayerOne.getMortgagableProperties().size();
        this.tdPlayerOne.mortgageProperties();
        testProperty(tdPlayerOne, numOfSites - 1);
        
    }
    
    public void testProperty(Player player, int predictedUnmortgagedProperties) {
        if (player.getMortgagableProperties().size() 
                ==  predictedUnmortgagedProperties) {
            System.out.println("Test Successful: Player has the expected "
                    + "amount of sites remained, " + 
                    predictedUnmortgagedProperties);
        } else {
            fail("Player was predicted to have " + 
                    predictedUnmortgagedProperties +
                    " but has " + player.getMortgagableProperties().size() + 
                    " sites left.");
        }
    }
}
