package Display;

import Model.Board;
import Model.Players.Player;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;


/**
 * Creates a menu where a human player can choose to buy houses.
 * @author Chris Berry
 */
public class HouseBuildingMenu extends ActionFrame{
    
    /**
     * Creates a new House building menu.
     * @param currentPlayer the player who will use the menu.
     */
    public HouseBuildingMenu(Player currentPlayer) {
        super(currentPlayer.getSitesWhichCanBeBuiltOn());
    }
    
    /**
     * Sets up the main panel of the house building menu.
     * @param sites the sites the player can build houses on.
     * @return the set up main panel.
     */
    @Override
    public JPanel setUpMainPanel(List<? extends Space> sites) {
        List<Site> siteList = (List<Site>)sites;
        this.setTitle("Buy Houses");
        JPanel housePanel = new JPanel();
        housePanel.setLayout(new BoxLayout(housePanel, BoxLayout.Y_AXIS));
        JPanel titlesPanel = new JPanel();
        titlesPanel.setBackground(ActionFrame.topBarColor);
        titlesPanel.setLayout(new GridLayout());
        JLabel propertyLabel = new JLabel("Property", JLabel.CENTER);
        Font boldFont =new Font(propertyLabel.getFont().getName(), Font.BOLD, 
                propertyLabel.getFont().getSize());  
        propertyLabel.setFont(boldFont);
        titlesPanel.add(propertyLabel);
        
        JLabel housesLabel = new JLabel("Number of houses", JLabel.CENTER);
        housesLabel.setFont(boldFont);
        titlesPanel.add(housesLabel);
        
        JLabel housesCost = new JLabel("House Cost", JLabel.CENTER);
        housesCost.setFont(boldFont);
        titlesPanel.add(housesCost);
        
        JLabel buyHousesLabel = new JLabel("Buy Houses", JLabel.CENTER);
        buyHousesLabel.setFont(boldFont);
        titlesPanel.add(buyHousesLabel);
        housePanel.add(titlesPanel);
        for (Site site : siteList) {
            housePanel.add(createSitePanel(site));
        }
        
        return housePanel;
    }
    
    /**
     * Creates a panel for the individual site including information and buttons.
     * @param site to create a panel for.
     * @return the set up site panel.
     */
    public JPanel createSitePanel(final Site site) {
        JPanel sitePanel = new JPanel();
        sitePanel.setLayout(new GridLayout());
        JLabel propertyNameLabel = new JLabel(site.getName(), JLabel.CENTER);
        propertyNameLabel.setOpaque(true);
        propertyNameLabel.setBackground(Board.getColor(site.getColorNumber()));
        propertyNameLabel.setForeground(Board.getMatchingTextColor(site.getColorNumber()));
        sitePanel.add(propertyNameLabel);
        sitePanel.add(new JLabel("" + site.getHouses(), JLabel.CENTER));
        sitePanel.add(new JLabel("" + site.getHouseCost(), JLabel.CENTER));
        JButton buyHouseButton = new JButton("Buy House");
        buyHouseButton.setAction(new AbstractAction("Buy House") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                setPropertySelected(site);
            }
        });
        sitePanel.add(buyHouseButton);
        return sitePanel;
    } 
    
}
