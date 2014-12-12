package Model.Spaces;

import Model.Bank;
import Model.Players.Player;

/**
 * A Tax space in the Game of Monopoly. An example would be Super Tax.
 * @author Chris Berry
 */
public class Tax extends Space {
    
    private int taxCost;
    
    /**
     * Creates a new Tax space.
     * @param name of the tax space.
     * @param taxAmount amount the player will be taxed when he lands on this
     * space.
     */
    public Tax(String name, int taxAmount) {
        super(name, -1, -1);
        this.taxCost = taxAmount;
        this.setOwner(Bank.getInstance());
    }

/**
 * Key method.
 */    
    /**
     * Charges the player the appropriate amount for landing on this space.
     * The player owes the Bank this money.
     * @param diceRoll the roll of the dice made by the player, however, 
     * this is irrelevant in this case 
     * @param player who landed on this space.
     */
    @Override
    public void performAction(int diceRoll, Player player) {
        String message = "You must pay " + taxCost;
        if (name.equals(SpaceEnums.JAIL.getName()) &&
                player.inJail()) {
            message = "You are in Jail";
        }
        if (name.equals(SpaceEnums.JAIL.getName()) &&
                !player.inJail()) {
            message = "Just visiting Jail";
        }
        if (name.equals(SpaceEnums.GO.getName())) {
            message = "Landed on Go";
        }
        if (name.equals(SpaceEnums.FREEPARKING.getName())) {
            message = "Nothing happens";
        }
        player.messagePlayer(message, name);
        player.forcedMoneyChange(-taxCost, Bank.getInstance());
    }

/**
 * Getter methods.
 */    
    /**
     * The mortgage value of a tax space is 0, as it can't be purchased.
     * @return 0
     */
    @Override
    public int getFullMortgageValue() {
        return 0;
    }
    
}
