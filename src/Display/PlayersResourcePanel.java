package Display;

import Model.Game;
import Model.Players.Player;
import java.awt.Color;
import java.util.List;
import javax.swing.*;

/**
 * Sets up a JPanel containing all of the players resource panels.
 * @author Chris Berry
 */
public class PlayersResourcePanel extends JPanel{
    
    private OnePlayersResourcePanel[] playerPanels;  
    private JLabel roundCounter = new JLabel("Rounds: 0/" + Game.MAX_ROUNDS);
    
    /**
     * Sets up a player resource panels containing a number of single player
     * resource panels equal to the number of players.
     * @param numPlayers the number of players in the game.
     */
    public PlayersResourcePanel(int numPlayers) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(roundCounter);
        this.add(Box.createVerticalGlue());
        playerPanels = new OnePlayersResourcePanel[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            OnePlayersResourcePanel resourcePanel = new OnePlayersResourcePanel();
            playerPanels[i] = resourcePanel;
            this.add(resourcePanel);
            this.add(Box.createVerticalGlue());
        }
        this.setOpaque(false);
    }
    
    /**
     * Updates the resource panel for all the players.
     * @param players a list of all players in the game.
     * @param currentPlayerNum the number of the current player.
     */
    public void updatePlayerStats(List<Player> players, int currentPlayerNum) {
        roundCounter.setText("Rounds: " + Game.getInstance().getRoundCount() + "/"
                + Game.MAX_ROUNDS);
        for (Player p : players) {
            playerPanels[p.getNumber()].changePlayer(p.getNumber(),
                    p.getMoney(), p.getNumberOfGetOutOfJailCards(),
                    p.getProperties());
            if (currentPlayerNum == p.getNumber()) {
                playerPanels[p.getNumber()].setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                playerPanels[p.getNumber()].setBorder(null);
            }
                    
        }
    }
    
}
