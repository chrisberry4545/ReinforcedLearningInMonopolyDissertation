package Display;

import Model.Board;
import Model.Players.Player;
import Model.Spaces.Space;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;


/**
 * Allows a human player to select which properties to mortgage.
 * @author Chris Berry
 */
public class MortgagePropertiesMenu extends ActionFrame{
    
    /**
     * Creates a new mortgage property menu.
     * @param currentPlayer to set up the menu for.
     */
    public MortgagePropertiesMenu(Player currentPlayer) {
        super(currentPlayer.getMortgagableProperties());
    }
    
    /**
     * Sets up the main panel for the mortgage properties menu.
     * @param spaces List of spaces to set up panels for.
     * @return the set up JPanel.
     */
    @Override
    public JPanel setUpMainPanel(List<? extends Space> spaces) {
        JPanel mortgagePanel = new JPanel();
        this.setTitle("Mortgage Properties");
        mortgagePanel.setLayout(new BoxLayout(mortgagePanel, BoxLayout.Y_AXIS));
        JPanel titlesPanel = new JPanel();
        titlesPanel.setBackground(ActionFrame.topBarColor);
        titlesPanel.setLayout(new GridLayout());
        JLabel propertyLabel = new JLabel("Property", JLabel.CENTER);
        Font boldFont =new Font(propertyLabel.getFont().getName(), Font.BOLD, 
                propertyLabel.getFont().getSize());  
        propertyLabel.setFont(boldFont);
        titlesPanel.add(propertyLabel);
        
        JLabel housesLabel = new JLabel("Payment for mortgaging", JLabel.CENTER);
        housesLabel.setFont(boldFont);
        titlesPanel.add(housesLabel);
        
        JLabel buyHousesLabel = new JLabel("Mortgage Property", JLabel.CENTER);
        buyHousesLabel.setFont(boldFont);
        titlesPanel.add(buyHousesLabel);
        mortgagePanel.add(titlesPanel);
        for (Space space : spaces) {
            mortgagePanel.add(createSitePanel(space));
        }
        
        return mortgagePanel;
    }
    
    /**
     * Creates the space panel for an individual space.
     * @param space to make the panel for.
     * @return the fully set up JPanel.
     */
    public JPanel createSitePanel(final Space space) {
        JPanel sitePanel = new JPanel();
        sitePanel.setLayout(new GridLayout());
        JLabel spaceName = new JLabel(space.getName(), JLabel.CENTER);
        spaceName.setOpaque(true);
        spaceName.setBackground(Board.getColor(space.getColorNumber()));
        spaceName.setForeground(Board.getMatchingTextColor(space.getColorNumber()));
        sitePanel.add(spaceName);
        sitePanel.add(new JLabel("" + space.getMortgageRate(), JLabel.CENTER));
        JButton buyHouseButton = new JButton("");
        buyHouseButton.setAction(new AbstractAction("Mortgage Property") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                setPropertySelected(space);
            }
        });
        sitePanel.add(buyHouseButton);
        return sitePanel;
    } 
    
}
