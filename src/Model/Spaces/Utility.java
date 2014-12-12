package Model.Spaces;

import Model.Players.Player;

/**
 * A utility space in the game of Monopoly. An Example would be Water Works.
 * @author Chris Berry
 */
public class Utility extends Space {
    
    private final int singleUtilMultiplyer = 4;
    private final int twoUtilMultiplyer = 10;
    
    /**
     * Creates a utility space.
     * @param name of the utility space.
     * @param intialCost how much the player has to pay to gain ownership
     * of the property when he lands on it.
     * @param mortgageRate amount player gets from mortgaging the property.
     */
    public Utility(String name, int intialCost, int mortgageRate) {
        super(name, intialCost, mortgageRate);
        
    }

/**
 * Key methods.
 */    
    /**
     * Player can either buy the property or is charged a certain amount
     * of rent based on what he rolled on the dice.
     * @param diceRoll amount rolled on the dice by the player.
     * @param player who landed on this space.
     */
    @Override
    public void performAction(int diceRoll, Player player) {
        super.landedOnProperty(player, getRent(diceRoll));
    }

/**
 * Getter methods.
 */    
    /**
     * Gets the rent of the utilities. Depends on the number
     * of utilities the player owns.
     * @param diceRoll
     * @return rent amount due.
     */
    public int getRent(int diceRoll) {
        if (owner != null) {
            int utilsOwned = owner.getNumberOfUtilOwned();
            switch(utilsOwned) {
                case 1 : return singleUtilMultiplyer * diceRoll;
                case 2 : return twoUtilMultiplyer * diceRoll;
            }
        }
        return 0;
    }
    
    /**
     * Gets the mortgage value of the property.
     * @return mortgage value of the property.
     */
    @Override
    public int getFullMortgageValue() {
        return super.mortgageRate;
    }

    
}
