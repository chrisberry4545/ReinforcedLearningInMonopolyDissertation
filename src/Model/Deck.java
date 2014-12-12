package Model;

import Model.Cards.Card;
import java.util.Collections;
import java.util.List;
/**
 * A deck of community chest cards or chance cards.
 * @author Chris Berry
 */
public class Deck {
    
    private List<Card> cards;
    private boolean isCommunityChest;
    
    /**
     * Sets up a deck for community chest or chance cards.
     * @param communityChest true if this is community chest or false if it
     * is for chance cards.
     * @param cards to include in the deck.
     */
    public Deck(boolean communityChest, List<Card> cards) {
        this.cards = cards;
        this.isCommunityChest = communityChest;
        
    }

/**
 * Deck operations.
 */    
    /**
     * Draws a card from the deck.
     * @return 
     */
    public Card drawCard() {
        Card cardDrawn = cards.get(0);
        cards.remove(0);
        return cardDrawn;
    }
    
    /**
     * Puts a card at the bottom of the deck.
     * @param card 
     */
    public void putCardAtBottomOfPile(Card card) {
        cards.add(card);
    }
    
    /**
     * Shuffles the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
    

/**
 * Getter methods.
 */    
    /**
     * Gets the size of the deck.
     * @return 
     */
    public int getSize() {
        return cards.size();
    }
    
    /**
     * Gets a List of the cards in the deck.
     * @return a List of cards in the deck.
     */
    public List<Card> getCards() {
        return cards;
    }
    
    /**
     * Checks whether the deck is the community chest.
     * @return true if it is the community chest. False if it's the chance deck.
     */
    public boolean isCommunityChest() {
        return isCommunityChest;
    }
}
