/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerTests.TDPlayerTests;

import Model.DealOffer;
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
 * Sets up a large deal offer and checks if the correct actions
 * are carried out when the offer is handled by TD Players.
 * @author Chris Berry
 */
public class TDAIMakeAnOfferTest extends TDTestEnvirorment{
    
    public TDAIMakeAnOfferTest() {
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
    public void makeAnOfferTest() {
       super.setUpTDTestEnvirorment();
       
       //Make deal offer.
       DealOffer offer = new DealOffer();
       Space site1 = Game.getInstance().getBoard().getAllSites().get(0);
       Space site2 = Game.getInstance().getBoard().getAllSites().get(4);
       Space site3 = Game.getInstance().getBoard().getAllSites().get(9);
       Space util1 = Game.getInstance().getBoard().getAllUtilities().get(0);
       Space station1 = Game.getInstance().getBoard().getAllStations().get(1);
       Space station2 = Game.getInstance().getBoard().getAllStations().get(3);
       List<Space> sitesToTrade = new ArrayList();
       sitesToTrade.add(site1);
       sitesToTrade.add(site2);
       sitesToTrade.add(site3);
       sitesToTrade.add(util1);
       sitesToTrade.add(station1);
       sitesToTrade.add(station2);
       
       //Set owner and add to deal.
       for (Space space : sitesToTrade) {
           space.setOwner(this.tdPlayerOne);
           offer.addPropertyToOffer(space);
       }
       //Add some cash
       int startingCash = tdPlayerOne.getMoney();
       int cashToOffer = 100;
       offer.offerCash(cashToOffer);
       
       otherPlayer.setAcceptAllOffers(true);
       
       tdPlayerOne.makeAnOfferToAnotherPlayer(offer, otherPlayer);
       
       if (tdPlayerOne.getMoney() != startingCash - cashToOffer) {
           fail("Player started with " + startingCash + " and offered an"
                   + " amount of " + cashToOffer + " but was left with "
                   + tdPlayerOne.getMoney());
       }
       
       for (Space space : sitesToTrade) {
           //If other player doesn't own the site then error.
           if (!space.getOwner().equals(otherPlayer)) {
               fail("Other Player doesn't own the site " + space.getName()
                       + "when he should");
           }
       }
       
       //Set all properties back
       for (Space space : sitesToTrade) {
           space.setOwner(this.tdPlayerOne);
       }
       
       startingCash = tdPlayerOne.getMoney();
       otherPlayer.setAcceptAllOffers(false);
       tdPlayerOne.makeAnOfferToAnotherPlayer(offer, otherPlayer);
       
       for (Space space : sitesToTrade) {
           if (!space.getOwner().equals(tdPlayerOne)) {
               fail("TD Player doesn't own the site " + space.getName()
                       + "when he should");
           }
       }
       
       if (tdPlayerOne.getMoney() != startingCash) {
           fail("Player started with " + startingCash +  " but was left with "
                   + tdPlayerOne.getMoney() + " when the other player should "
                   + "have rejected the deal.");
       }
       
       System.out.println("Test Successful: all resources traded correctly");
       
    }
    
}
