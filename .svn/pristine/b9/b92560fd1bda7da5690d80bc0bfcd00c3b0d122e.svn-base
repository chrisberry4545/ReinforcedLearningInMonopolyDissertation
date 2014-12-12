package Model.Cards;

import Model.Game;
import Model.Players.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * The action for the 'Chairman of the Board's Card from the Game of Monopoly.
 * @author Chris Berry
 */
public class ChairmanOfTheBoard extends AbstractCardAction {
   
    private int amountToPayEachPlayer = 50;
    
    /**
     * Creates a default ChairmanOfTheBoard card action.
     */
    public ChairmanOfTheBoard() {
        
    }
/**
 * Key methods.
 */    
    /**
     * Pays each player 50 apart from the player who drew the card.
     * @param currentPlayer who drew the card.
     * @param card which the action is being performed from.
     */
    @Override
    public void performAction(Player currentPlayer, Card card) {
        List<Player> allPlayers = 
                new ArrayList(Game.getInstance().getPlayers());
        for (Player otherPlayer : allPlayers) {
            if (!otherPlayer.equals(currentPlayer)) {
                otherPlayer.optionalMoneyChange(amountToPayEachPlayer);
                currentPlayer.forcedMoneyChange(-amountToPayEachPlayer,
                        otherPlayer);
            }
        }
    }
}
