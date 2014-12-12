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
 * Sets up a frame allowing a human player to select properties they want
 * to unmortgage.
 * @author Chris Berry
 */
public class UnMortgagePropertiesMenu extends ActionFrame{
    
    /**
     * Sets up a frame allowing a human player to select properties they want
     * to unmortgage.
     * @param currentPlayer player to set up frame for.
     */
    public UnMortgagePropertiesMenu(Player currentPlayer) {
        super(currentPlayer.getMortgagedProperties());
    }
    
    /**
     * Sets up the main panel of the frame for the given spaces.
     * @param spaces the spaces to allow the player to unmortgage.
     * @return the set up JPanel.
     */
    @Override
    public JPanel setUpMainPanel(List<? extends Space> spaces) {
        JPanel mortgagePanel = new JPanel();
        this.setTitle("Buy back mortgaged properties");
        mortgagePanel.setLayout(new BoxLayout(mortgagePanel, BoxLayout.Y_AXIS));
        JPanel titlesPanel = new JPanel();
        titlesPanel.setBackground(ActionFrame.topBarColor);
        titlesPanel.setLayout(new GridLayout());
        JLabel propertyLabel = new JLabel("Property", JLabel.CENTER);
        Font boldFont =new Font(propertyLabel.getFont().getName(), Font.BOLD, 
                propertyLabel.getFont().getSize());  
        propertyLabel.setFont(boldFont);
        titlesPanel.add(propertyLabel);
        
        JLabel housesLabel = new JLabel("Payment for Unmortgaging", JLabel.CENTER);
        housesLabel.setFont(boldFont);
        titlesPanel.add(housesLabel);
        
        JLabel buyHousesLabel = new JLabel("UnMortgage Property", JLabel.CENTER);
        buyHousesLabel.setFont(boldFont);
        titlesPanel.add(buyHousesLabel);
        mortgagePanel.add(titlesPanel);
        for (Space space : spaces) {
            mortgagePanel.add(createSitePanel(space));
        }
        
        return mortgagePanel;
    }
    
    /**
     * Creates a site panel for the given site.
     * @param space to create panel for.
     * @return the set up panel.
     */
    public JPanel createSitePanel(final Space space) {
        JPanel sitePanel = new JPanel();
        sitePanel.setLayout(new GridLayout());
        JLabel spaceName = new JLabel(space.getName(), JLabel.CENTER);
        spaceName.setOpaque(true);
        spaceName.setBackground(Board.getColor(space.getColorNumber()));
        spaceName.setForeground(Board.getMatchingTextColor(space.getColorNumber()));
        sitePanel.add(spaceName);
        int mortgageRepayment = space.getMortgageRepaymentRate() + space.getMortgageRate();
        sitePanel.add(new JLabel("" + mortgageRepayment, JLabel.CENTER));
        JButton buyHouseButton = new JButton("");
        buyHouseButton.setAction(new AbstractAction("Unmortgage Property") {
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
