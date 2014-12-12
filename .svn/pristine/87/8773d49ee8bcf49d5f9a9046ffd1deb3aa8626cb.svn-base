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
 * Creates a menu allowing human players to sell their houses.
 * @author Chris Berry
 */
public class HouseSellingMenu extends ActionFrame{
    
    /**
     * Creates a new house selling menu for a given player.
     * @param currentPlayer the player to create a house selling menu for.
     */
    public HouseSellingMenu(Player currentPlayer) {
        super(currentPlayer.getSitesWithHousesWhichCanBeSold());
    }
    
    /**
     * Sets up the main Panel of the house selling menu.
     * @param sites the sites the user is able to sell
     * @return the set up JPanel.
     */
    @Override
    public JPanel setUpMainPanel(List<? extends Space> sites) {
        List<Site> siteList = (List<Site>)sites;
        this.setTitle("Sell Houses");
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
        
        JLabel housesCost = new JLabel("House Selling price", JLabel.CENTER);
        housesCost.setFont(boldFont);
        titlesPanel.add(housesCost);
        
        JLabel buyHousesLabel = new JLabel("Sell Houses", JLabel.CENTER);
        buyHousesLabel.setFont(boldFont);
        titlesPanel.add(buyHousesLabel);
        housePanel.add(titlesPanel);
        for (Site site : siteList) {
            housePanel.add(createSitePanel(site));
        }
        
        return housePanel;
    }
    
    /**
     * Creates a site panel for an individual site.
     * @param site to create site panel for.
     * @return the complete JPanel.
     */
    public JPanel createSitePanel(final Site site) {
        JPanel sitePanel = new JPanel();
        sitePanel.setLayout(new GridLayout());
        JLabel siteNameLabel = new JLabel(site.getName(), JLabel.CENTER);
        siteNameLabel.setBackground(Board.getColor(site.getColorNumber()));
        siteNameLabel.setForeground(Board.getMatchingTextColor(site.getColorNumber()));
        siteNameLabel.setOpaque(true);
        sitePanel.add(siteNameLabel);
        sitePanel.add(new JLabel("" + site.getHouses(), JLabel.CENTER));
        sitePanel.add(new JLabel("" + site.getHouseSellPrice(), JLabel.CENTER));
        JButton sellHouseButton = new JButton("");
        sellHouseButton.setAction(new AbstractAction("Sell House") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                setPropertySelected(site);
            }
        });
        sitePanel.add(sellHouseButton);
        return sitePanel;
    } 
    
}
