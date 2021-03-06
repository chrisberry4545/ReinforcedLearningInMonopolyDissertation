/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralDisplayTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Display.*;
import Model.Players.HumanPlayer;
import java.util.ArrayList;
import java.util.List;
import Model.Players.Player;

/**
 *
 * @author chris
 */
public class DealMakingDisplayTest {
    
    public DealMakingDisplayTest() {
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
     public void testDealMaker() {
         BoardDisplay display = new BoardDisplay(4);
         HumanPlayer p0 = new HumanPlayer(0,display);
         HumanPlayer p1 = new HumanPlayer(1,display);
         HumanPlayer p2 = new HumanPlayer(2,display);
         HumanPlayer p3 = new HumanPlayer(3,display);
         List<Player> players = new ArrayList();
         players.add(p1);
         players.add(p2);
         players.add(p3);
         
         DealMakerDisplay dealMaker = new DealMakerDisplay(p0, players, null);
     }
}
