package Model.Cards;

import Model.Game;
import Model.Players.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The action for the 'Birthday Present' Card from the Game of Monopoly.
 * @author Chris Berry
 */
public class BirthdayPresents extends AbstractCardAction {
    
    private int present = 10;
    /**
     * Sets up a default BirthdayPresents card.
     */
    public BirthdayPresents() {
        
    }

/**
 * Key methods.
 */    
    /**
     * Player gets a present from every other player in the game.
     * @param player to perform action on.
     * @param card which the action is being performed from.
     */
    @Override
    public void performAction(Player player, Card card) {
        List<Player> players = new ArrayList(Game.getInstance().getPlayers());
        Iterator<Player> playerIterator = players.iterator();
        while(playerIterator.hasNext()) {
            Player otherPlayer = playerIterator.next();
            if (!otherPlayer.equals(player)) {
                otherPlayer.forcedMoneyChange(-present, player);
                player.optionalMoneyChange(present);
            }
        } 
    }
    
}
