package Model.Cards;

import Model.Bank;
import Model.Players.Player;

/**
 * Actions for cards that give or take money away from players in the game
 * of Monopoly.
 * @author Chris Berry
 */
public class ChangeMoneyCard extends AbstractCardAction{
    
    private int moneyAmountToChange = 0;
    
    /**
     * Creates a default change money card action.
     * @param changeMoney amount money should change by. Note: use
     * negative values to deduct money from players.
     */
    public ChangeMoneyCard(int changeMoney) {
        this.moneyAmountToChange = changeMoney;
    }

/**
 * Key methods.
 */    
    /**
     * Forces the player to loose or gain the change in money specified
     * when the card was created. The player is considered to owe the bank
     * the money when it comes to bankrupting.
     * @param player who drew the card.
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        player.forcedMoneyChange(moneyAmountToChange, Bank.getInstance());
    }
    
}
