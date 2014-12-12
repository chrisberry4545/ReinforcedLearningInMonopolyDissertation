package CardTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Model.*;
import Model.Cards.*;
import java.util.ArrayList;

/**
 * Tests whether the chance square works correctly.
 * @author Chris Berry
 */
public class ChanceTester {
    
    public ChanceTester() {
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
    // @Test
    // public void hello() {}
    
    /**
     * Checks the two decks are not the same and are therefore being shuffled
     * properly.
     */
    @Test
    public void testChance() {
        Deck deck1 = new Deck(true, CreateCards.createCommunityChestCards());
        deck1.shuffleDeck();
        ArrayList<String> cardNames1 = new ArrayList();
        int deck1size = deck1.getSize();
        for(int i = 0; i < deck1size; i++) {
            Card card = deck1.drawCard();
            cardNames1.add(card.getName());
        }
        
        Deck deck2 = new Deck(true, CreateCards.createCommunityChestCards());
        deck2.shuffleDeck();
        ArrayList<String> cardNames2 = new ArrayList();
        int deck2size = deck2.getSize();
        for(int i = 0; i < deck2size; i++) {
            Card card = deck2.drawCard();
            cardNames2.add(card.getName());
        }
        
        System.out.println("Cards 1 size: " + cardNames1.size());
        System.out.println("Cards 2 size: " + cardNames2.size());
        
        for (int i = 0; i < cardNames1.size(); i++) {
            System.out.println("Cards 1: " + cardNames1.get(i));
            System.out.println("Cards 2: " + cardNames2.get(i));
        }
        
    }
    
}
