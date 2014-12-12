package Model.Spaces;

import Model.Game;
import Model.Players.Player;

/**
 * Type of space representing the chance and community chest space on a
 * Monopoly board.
 * @author Chris Berry
 */
public class CardSpace extends Space {
    
    private boolean isCommChest;
    
    /**
     * Creates a Card space.
     * @param name of the card space.
     * @param isCommunityChest true if the card space is a community chest
     * space, false if it is a chance space.
     */
    public CardSpace(String name, boolean isCommunityChest) {
        super(name, -1, -1);
        this.isCommChest = isCommunityChest;
    }

/**
 * Key method.
 */    
    /**
     * Draws a card from the appropriate deck when the player lands on this
     * space.
     * @param diceRoll rolled by player, although has no effect on this space.
     * @param player who landed on the space.
     */
    @Override
    public void performAction(int diceRoll, Player player) {
       if (isCommChest) {
           Game.getInstance().drawFromCommunityChest(player);
       } else {
           Game.getInstance().drawFromChance(player);
       }
    }

/**
 * Getter methods.
 */    
    /**
     * Returns whether the space is a community chest or not.
     * @return true if the space is a community chest, false if it is a chance 
     * space.
     */
    public boolean isCommChest() {
        return isCommChest;
    }
    
    /**
     * The full mortgage value of a chance or community chest space is always
     * 0 as they cannot be purchased.
     * @return 0.
     */
    @Override
    public int getFullMortgageValue() {
        return 0;
    }
     
}
