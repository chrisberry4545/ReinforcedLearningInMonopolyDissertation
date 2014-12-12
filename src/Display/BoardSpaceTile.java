package Display;

import Model.Board;
import Model.Spaces.Space;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

/**
 * A board space tile used to build the full Monopoly board.
 * @author Chris Berry
 */
public class BoardSpaceTile extends JPanel {
    
    private JPanel tokenArea = new JPanel();
    private JLabel houseLabel;
    private Map<PlayerToken, Component> tokenMap = new HashMap();
    
    /**
     * Creates a new Board Space tile for the given space.
     * @param space to create the tile for.
     */
    public BoardSpaceTile(Space space) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBounds(0, 0, 62, 55);
        JLabel title = new JLabel(space.getName());
        title.setOpaque(true);
        title.setBackground(Board.getColor(space.getColorNumber()));
        title.setForeground(Board.getMatchingTextColor(space.getColorNumber()));
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 9));
        this.add(title);
        
        houseLabel = new JLabel("Houses: ");
        houseLabel.setFont(new Font(houseLabel.getFont().getName(), Font.PLAIN, 8));
        this.add(houseLabel);
        
        GridLayout gridLayout = new GridLayout(2,3);
        gridLayout.setHgap(0);
        gridLayout.setVgap(0);
        tokenArea.setLayout(gridLayout);
        tokenArea.setOpaque(false);
        tokenArea.setMaximumSize(new Dimension(45,35));
        this.add(tokenArea); 
        
        this.setBackground(BoardDisplay.BOARD_COLOR);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    /**
     * Sets the house number label to a certain value.
     * @param houseNumbers number of houses to display on the property.
     */
    public void setHouseNumberLabel(int houseNumbers) {
        houseLabel.setText("Houses: " + houseNumbers);
    }
    
    /**
     * Adds a player token to the space.
     * @param token to add to the space.
     */
    public void addPlayerToken(PlayerToken token) {
        JLabel tokenLabel = new JLabel(token.getImageIcon());
        tokenArea.add(tokenLabel);
        tokenMap.put(token, tokenLabel);
    }
    
    /**
     * Removes the player token from the space.
     * @param token to remove from the space.
     */
    public void removePlayerToken(PlayerToken token) {
        Component component = tokenMap.get(token);
        tokenArea.remove(component);
    }
    
    /**
     * Gets all the tokens on this space.
     * @return Set of tokens within this space.
     */
    public Set<PlayerToken> getTokens() {
        return tokenMap.keySet();
    }
    
}
