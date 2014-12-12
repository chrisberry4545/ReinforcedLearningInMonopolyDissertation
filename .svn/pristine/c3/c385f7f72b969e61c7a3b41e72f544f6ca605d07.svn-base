package Display;

import Model.Board;
import Model.DealOffer;
import Model.Players.Player;
import Model.Spaces.Space;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.*;

/**
 * The display used to ask a human player whether they want to accept a deal
 * offer made to them by another player.
 * @author Chris Berry
 */
public class DealOfferDisplay extends JFrame{
    
    private boolean isDisposed = false;
    private boolean dealAccepted = false;
    
    /**
     * Creates a new Deal Offer display.
     * @param dealOffer the offer to show the player.
     * @param playerOffering the player making the offer.
     * @param parentFrame the parent frame of this new frame.
     */
    public DealOfferDisplay(DealOffer dealOffer, Player playerOffering, JFrame parentFrame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(setUpTopPanel());
        
        mainPanel.add(setUpMoneyPanel(dealOffer));
        mainPanel.add(setUpPropertyPanel(dealOffer));
        mainPanel.add(setUpButtons());
        
        this.setTitle("Player " + playerOffering.getNumber() + " makes you an offer.");
        
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parentFrame);
        this.setVisible(true);
    }
    
    /**
     * Disposes this frame and sets a variable marking this frame as disposed.
     */
    @Override
    public void dispose() {
        this.isDisposed = true;
        super.dispose();
    }
    
    /**
     * Sets up the top panel for this frame, containing the two labels denoting
     * the columns "offered to you" and "requested from you".
     * @return the completed JPanel.
     */
    private JPanel setUpTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout());
        topPanel.add(new JLabel("Offered to you", JLabel.CENTER));
        topPanel.add(new JLabel("Requested from you", JLabel.CENTER));
        return topPanel;
    }
    
    /**
     * Sets up the money panel showing how much cash has been offered and 
     * requested.
     * @param offer the offer that has been made to the player.
     * @return the fully set up JPanel.
     */
    private JPanel setUpMoneyPanel(DealOffer offer) {
        JPanel moneyPanel = new JPanel();
        moneyPanel.setLayout(new GridLayout());
        moneyPanel.add(new JLabel("Money offered: " + offer.getOfferedCash(), JLabel.CENTER));
        moneyPanel.add(new JLabel("Money requested: " + offer.getRequestedCash(), JLabel.CENTER));
        return moneyPanel;
    }
    
    /**
     * Sets up the property panel, showing all the properties offered and
     * requested from the player.
     * @param offer the offer that has been made to the player.
     * @return the fully set up JPanel.
     */
    private JPanel setUpPropertyPanel(DealOffer offer) {
        JPanel propertyPanel = new JPanel();
        propertyPanel.setLayout(new GridLayout());
        
        propertyPanel.add(setUpPropertyListPanel(offer.getOfferedProperties()));
        propertyPanel.add(setUpPropertyListPanel(offer.getRequestedProperties()));
        
        return propertyPanel;
    }
    
    /**
     * Sets up a property list panel, containing a list of properties.
     * @param list of properties to display.
     * @return set up JPanel.
     */
    private JPanel setUpPropertyListPanel(Collection<Space> list) {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,
                BoxLayout.Y_AXIS));
        for (Space space : list) {
            JLabel spaceLabel = new JLabel(space.getName(), JLabel.CENTER);
            spaceLabel.setBackground(Board.getColor(space.getColorNumber()));
            spaceLabel.setOpaque(true);
            spaceLabel.setForeground(Board.getMatchingTextColor(space.getColorNumber()));
            listPanel.add(spaceLabel);
        }
        return listPanel;
    }
    
    /**
     * Sets up all the individual buttons and returns a JPanel containing them.
     * @return JPanel containing all the buttons.
     */
    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(setUpAcceptButton());
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(setUpRefuseButton());
        buttonPanel.add(Box.createHorizontalGlue());
        return buttonPanel;
    }
    
    /**
     * Sets up a refuse deal button, allowing the player to refuse a deal.
     * @return the set up refuse deal button.
     */
    private JButton setUpRefuseButton() {
        JButton refuseButton = new JButton();
        final DealOfferDisplay frame = this;
        refuseButton.setAction(new AbstractAction("Refuse Offer") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setDealAccepted(false);
                frame.dispose();
                
            }
        });
        return refuseButton;
    }
    
    /**
     * Sets up the accept button, allowing the player to accept a deal.
     * @return the set up accept button.
     */
    private JButton setUpAcceptButton() {
        JButton acceptButton = new JButton();
        final DealOfferDisplay frame = this;
        acceptButton.setAction(new AbstractAction("Accept Offer") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setDealAccepted(true);
                frame.dispose();
                
            }
        });
        return acceptButton;
    }

    /**
     * Returns whether the deal has been accepted or not.
     * @return true if the deal has been accepted. Null if the player hasn't
     * made a decision yet.
     */
    public boolean isDealAccepted() {
        return dealAccepted;
    }

    /**
     * Returns whether the frame has been disposed or not.
     * @return true if the frame has been disposed.
     */
    public boolean isDisposed() {
        return isDisposed;
    }

    /**
     * Sets whether the player has accepted a deal or not.
     * @param dealAccepted the player's response to the deal.
     */
    public void setDealAccepted(boolean dealAccepted) {
        this.dealAccepted = dealAccepted;
    }
    
    
    
}
