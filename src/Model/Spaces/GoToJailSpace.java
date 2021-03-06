package Model.Spaces;

import Model.Players.Player;

/**
 * Type of space representing the go to jail space on a Monopoly board.
 * @author Chris Berry
 */
public class GoToJailSpace extends Space{
    
    /**
     * Creates a GoToJailSpace
     * @param name of the space.
     */
    public GoToJailSpace(String name) {
        super(name, -1,-1);
    }

/**
 * Key method.
 */
    /**
     * Performs the go to jail card action (sends the player to jail) when
     * it lands on this.
     * @param diceRoll the player rolled, although this has no effect in this
     * case.
     * @param player who landed on this space.
     */
    @Override
    public void performAction(int diceRoll, Player player) {
        player.sendToJail();
    }

/**
 * Getter methods.
 */    
    /**
     * This space cannot be purchased and so has a mortgage value of zero.
     * @return 0.
     */
    @Override
    public int getFullMortgageValue() {
        return 0;
    }
    
}
