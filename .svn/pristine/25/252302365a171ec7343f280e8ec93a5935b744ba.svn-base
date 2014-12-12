package Display;

import Model.Board;
import Model.Spaces.*;
import java.util.List;
import javax.swing.*;

/**
 * Sets up a resource panel for a single player.
 * @author Chris Berry
 */
public class OnePlayersResourcePanel extends JPanel {
    
    private JLabel playerName;
    private JLabel playerMoney;
    private JLabel playerGetOutOfJailCards;
    
    private JPanel standardPanel;
    private JPanel propertyPanel;
    
    /**
     * Sets up a single players resource panel.
     */
    public OnePlayersResourcePanel() {
        playerName = new JLabel("Player: ");
        playerName.setOpaque(false);
        playerMoney = new JLabel("Money: ");
        playerMoney.setOpaque(false);
        playerGetOutOfJailCards = new JLabel("Get out of jail cards: ");
        playerGetOutOfJailCards.setOpaque(false);
        standardPanel = new JPanel();
        standardPanel.setOpaque(false);
        standardPanel.setLayout(new BoxLayout(standardPanel, BoxLayout.Y_AXIS));
        propertyPanel = new JPanel();
        propertyPanel.setOpaque(false);
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
        
        standardPanel.add(playerName);
        standardPanel.add(playerMoney);
        standardPanel.add(playerGetOutOfJailCards);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(Box.createHorizontalGlue());
        this.add(standardPanel);
        this.add(Box.createHorizontalGlue());
        this.add(propertyPanel);
        this.add(Box.createHorizontalGlue());
        this.setOpaque(false);
        
    }
    
    /**
     * Updates the panel with the given statistics.
     * @param playerNumber the player number to display.
     * @param money the amount of money the player has.
     * @param gOOJCards the number of get out of jail cards the player has.
     * @param properties a list of properties the player owns.
     */
    public void changePlayer(int playerNumber, int money, int gOOJCards,
            List<Space> properties) {
        playerName.setText("Player: " + playerNumber);
        playerMoney.setText("Money: " + money);
        playerGetOutOfJailCards.setText("Get out of jail cards: " + gOOJCards);
        propertyPanel.removeAll();
        propertyPanel.add(new JLabel("Owned Properties"));
        if (properties != null) {
            for (Space property: properties) {
                int colorNumber = -1;
                int numHouses = 0;
                if (property.getClass() == Site.class) {
                    Site site = (Site)property;
                    colorNumber = site.getColorNumber();
                    numHouses = site.getHouses();
                }
                propertyPanel.add(new PropertyLabel(property.getName(),
                        Board.getColor(colorNumber),
                        Board.getMatchingTextColor(colorNumber),
                        property.isMortgaged(), numHouses));
            }
        }
        
        propertyPanel.revalidate();
        standardPanel.revalidate();
        
    }
    
    
}
