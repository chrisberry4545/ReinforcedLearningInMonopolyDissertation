package Model.Cards;

/**
 * Represents each card from the chance and community chest piles in the game
 * of Monopoly.
 * @author Chris Berry
 */
public class Card {
    
    private String name;
    private AbstractCardAction action;
    private boolean shuffleBack;
    private boolean fromChanceCardDeck;
    
    /**
     * Sets up a default card for use in the community chest or chance deck in
     * a game of Monopoly.
     * @param name of the card.
     * @param action to perform when the card is drawn.
     * @param putAtBottom true if the card should be put back on the bottom of 
     * deck
     * after it has been used.
     */
    public Card(String name, AbstractCardAction action, boolean putAtBottom,
            boolean fromChanceDeck) {
        this.name = name;
        this.action = action;
        this.shuffleBack = putAtBottom;
        this.fromChanceCardDeck = fromChanceDeck;
    }

/**
 * Getter Methods.
 */    
    /**
     * Gets the name of the card.
     * @return the card name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the action associated with the card.
     * @return the AbstractCardAction associated with the card.
     */
    public AbstractCardAction getAction() {
        return action;
    }
    
    /**
     * Returns true if the card should be put back at the bottom of the pack
     * after it has been drawn.
     * @return true if the card should be put back at the bottom of the deck.
     */
    public boolean isPutAtBottom() {
        return shuffleBack;
    }
    
    /**
     * Returns true if the card is from the chance card deck.
     * @return true if card from chance card deck. False if it's from
     * community chest deck.
     */
    public boolean fromChanceCardDeck() {
        return this.fromChanceCardDeck;
    }
}
