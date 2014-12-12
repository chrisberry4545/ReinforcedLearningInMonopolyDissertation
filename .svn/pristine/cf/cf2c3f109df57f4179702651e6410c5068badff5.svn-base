package Display;

import Model.Players.Player;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Sets up the buttons for human players to use to decide what actions to take.
 * @author Chris Berry
 */
public class PlayerButtons extends JPanel{
    
    private int playerChoice = -1;
    
    /**
     * Creates a new player button panel with all the appropriate buttons set up.
     */
    public PlayerButtons() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        
        JPanel bottemPanel = new JPanel();
        bottemPanel.setLayout(new BoxLayout(bottemPanel, BoxLayout.X_AXIS));
        
        topPanel.add(Box.createHorizontalGlue());
        JButton rollButton = setUpRollButton();
        topPanel.add(rollButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(setUpDealButton());
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(setUpBuyHouseButton());
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(setUpSellHouseButton());
        topPanel.add(Box.createHorizontalGlue());
        
        bottemPanel.add(Box.createHorizontalGlue());
        bottemPanel.add(setUpMortgagePropertyButton());
        bottemPanel.add(Box.createHorizontalGlue());
        bottemPanel.add(setUpUnMortgagePropertyButton());
        bottemPanel.add(Box.createHorizontalGlue());
        bottemPanel.add(setUpExitButton());
        bottemPanel.add(Box.createHorizontalGlue());
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        this.add(bottemPanel);
        
        topPanel.setOpaque(false);
        bottemPanel.setOpaque(false);
        this.setOpaque(false);
        
    }
    
    /**
     * Sets up the roll dice button.
     * @return the set up roll dice button.
     */
    private JButton setUpRollButton() {
        return setUpButton(Player.ROLL_DICE, "Roll Dice");
    }
    
    /**
     * Sets up the buy house button.
     * @return the set up house buying button.
     */
    private JButton setUpBuyHouseButton() {
        return setUpButton(Player.BUY_HOUSES, "Buy Houses");
    }
    
    /**
     * Sets up the house sell button.
     * @return the set up house sell button.
     */
    private JButton setUpSellHouseButton() {
        return setUpButton(Player.SELL_HOUSES, "Sell Houses");
    }
    
    /**
     * Sets up the mortgage property button.
     * @return the set up mortgage property button.
     */
    private JButton setUpMortgagePropertyButton() {
        return setUpButton(Player.MORTGAGE_PROPERTIES, "Mortgage Properties");
    }
    
    /**
     * Sets up the unmortgage property button.
     * @return the set up unmortgage property button.
     */
    private JButton setUpUnMortgagePropertyButton() {
        return setUpButton(Player.BUY_BACK_MORTGAGED_PROPERTIES,
                "Buy Back Properties");
    }
    
    /**
     * Sets up the make a deal button.
     * @return the set up make a deal button.
     */
    private JButton setUpDealButton() {
        return setUpButton(Player.OFFER_PROPERTY, "Make a Deal");
    }
    
    /**
     * Sets up the exit button.
     * @return the set up exit button.
     */
    private JButton setUpExitButton() {
        return setUpButton(Player.EXIT_GAME_STANDARD, "Exit Game");
    }
    
    /**
     * Sets up a button with the given action result and text.
     * @param actionResult the playerChoice variable will be set to this
     * when the button is clicked.
     * @param buttonText the text which should appear on the button.
     * @return the set up button.
     */
    private JButton setUpButton(final int actionResult, String buttonText) {
        JButton button = new JButton();
        button.setAction(new AbstractAction(buttonText) {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                setPlayerChoice(actionResult);
            }
        });
        return button;
        
    }
    
    /**
     * Sets the player choice to the value given.
     * @param playerChoice the value that the playersChoice should take.
     */
    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }
    
    /**
     * Gets the value of the player's last choice.
     * @return the players last choice.
     */
    public int getPlayerChoice() {
        return this.playerChoice;
    }
    
}
