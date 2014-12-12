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
import Model.Spaces.*;
/**
 * Checks to confirm a mixture of values is printed by the player for bidding 
 * on houses and properties. This confirms that the TD Player is making 
 * decisions individually for each.
 * @author Chris Berry
 */
public class TDAIMakeBidTests extends TDTestEnvirorment{
    
    public TDAIMakeBidTests() {
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
     * Checks to confirm a mixture of values is printed by the player for bidding 
     * on houses and properties. This confirms that the TD Player is making 
     * decisions individually for each.
     */
    @Test
    public void tdMakeABidTest() {
        super.setUpTDTestEnvirorment();
        
        //Make sure an appropriate bid is returned.
        
        /**
         * This should print a mixture of values.
         */
        for (int i = 0; i < 50; i++) {
            int houseBid = this.tdPlayerOne.
                    makeABidOnHouse(1, true, Game.getInstance().getPlayers());
            System.out.println(houseBid);
            int spaceToBidNumber = (int)Math.round(Math.random() *
                    (Game.getInstance().getBoard().size() - 1));
            Space spaceToBidOn = Game.getInstance().getBoard().get(spaceToBidNumber);
            int propertyBid = this.tdPlayerOne.
                    makeABid(spaceToBidOn,1, Game.getInstance().getPlayers());
            System.out.println(propertyBid);
        }
    }
    
}
