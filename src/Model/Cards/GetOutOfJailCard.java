package Model.Cards;

import Model.Players.Player;

/**
 * The action for the 'Get out of Jail Free' Cards from the Game of Monopoly.
 * @author Chris Berry
 */
public class GetOutOfJailCard extends AbstractCardAction {
    
    /**
     * Creates a default get out of jail card action.
     */
    public GetOutOfJailCard() {
    }
   
/**
 * Key method.
 */    
    /**
     * Gives the player a get out of jail card to use at a later time.
     * @param player 
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        player.addGetOutOfJailCard(card);
    }
}
