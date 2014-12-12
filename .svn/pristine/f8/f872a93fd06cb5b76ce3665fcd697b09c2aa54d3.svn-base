package Model.Cards;

import Model.Game;
import Model.Players.Player;

/**
 * The action for the various 'Move to space' cards from the Game of Monopoly.
 * @author Chris Berry
 */
public class MovementCard extends AbstractCardAction{
    
    private int spaceToMoveTo;
    private boolean passGoMoney;
    
    /**
     * Creates a movement card action moving player to a new space at the given
     * number and only collecting pass
     * @param spaceNumber which the player should move to.
     * @param collectGoMoney true if the player should receieve the money for
     * passing go.
     */
    public MovementCard(int spaceNumber, boolean collectGoMoney) {
        this.spaceToMoveTo = spaceNumber; 
        this.passGoMoney = collectGoMoney;
    }

/**
 * Key methods.
 */    
    /**
     * The player is moved to the appropriate space and receives the appropriate
     * money if he passes go. The action for landing on the space is then 
     * performed.
     * @param player who drew the card.
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        Game.getInstance().movePlayerToSpaceNumber(player, spaceToMoveTo,
                passGoMoney);
        //Dice roll doesn't matter.
        Game.getInstance().getBoard().get(spaceToMoveTo).performAction(1, player);
    }
    
}
