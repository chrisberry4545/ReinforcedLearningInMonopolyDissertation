package Model.Cards;

import Model.Game;
import Model.Players.Player;

/**
 * The action for the various 'Move Back X Spaces' Cards from the 
 * Game of Monopoly.
 * @author Chris Berry
 */
public class MoveBackwards extends AbstractCardAction {
    
    private int spacesToMoveBack = 3;
    
    /**
     * Creates a default move backwards 3 spaces action.
     */
    public MoveBackwards() {
        
    }
 
/**
 * Key methods.
 */    
    /**
     * Moves the player back a 3 spaces
     * @param player who the action is being performed on.
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        Game.getInstance().movePlayer(player, -spacesToMoveBack);
        player.getCurrentSpace().performAction(0, player);
    }
}
