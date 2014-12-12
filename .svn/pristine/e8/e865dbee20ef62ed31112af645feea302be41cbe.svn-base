package Model.Spaces;

import Model.Players.Player;

/**
 * A Station space in the game of Monopoly. An example would be Kings Cross.
 * @author Chris Berry
 */
public class Station extends Space {
    
    private final int[] stationRents = {25,50,100,200};
    
    /**
     * Creates a station space.
     * @param name of the station.
     * @param intialCost intial cost of buying the station.
     * @param mortgageRate amount of money gained when the station is mortgaged.
     */
    public Station(String name, int intialCost, int mortgageRate) {
        super(name, intialCost, mortgageRate);
    }
 
/**
 * Key method.
 */    
    /**
     * Performs the appropriate action when the space is landed on.
     * @param diceRoll the player rolled when landing on the space, although
     * this has no effect in this case.
     * @param player who landed on this space.
     */
    @Override
    public void performAction(int diceRoll, Player player) {
        super.landedOnProperty(player, getRent());
    }

/**
 * Getter methods.
 */    
    /**
     * Gets the rent of the station.
     * @return rent of the station.
     */
    public int getRent() {
        if (owner != null) {
            return stationRents[owner.getNumberOfStationsOwned() - 1];
        }
        return 0;
    }
    
    /**
     * Gets the mortgage rate of the station.
     * @return mortgage rate of the station.
     */
    @Override
    public int getFullMortgageValue() {
        return super.mortgageRate;
    }
    
}
