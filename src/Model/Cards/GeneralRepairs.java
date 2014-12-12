package Model.Cards;

import Model.Bank;
import Model.Players.Player;
import Model.Spaces.Site;

/**
 * The action for the various 'Generals Repairs' Cards from the Game of Monopoly.
 * @author Chris Berry
 */
public class GeneralRepairs extends AbstractCardAction {
    
    private int costPerHouse;
    private int costPerHotel;
    
    /**
     * Creates a default GeneralRepairs Card Action.
     * @param perHouseCost amount repairs will cost for each house the player
     * owns.
     * @param perHotelCost amount repairs will cost for each hotel the player 
     * owns.
     */
    public GeneralRepairs(int perHouseCost, int perHotelCost) {
        costPerHouse = perHouseCost;
        costPerHotel = perHotelCost;
    }
    
/**
 * Key methods.
 */    
    /**
     * Charges the player an amount based upon the number of houses and hotels
     * they own.
     * @param player 
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        int amountToPay = 0;
        for (Site site : player.getSites()){
            if (site.getHouses() == site.getMaxHouses()) {
                amountToPay += costPerHotel;
            } else {
                amountToPay += site.getHouses() * costPerHouse;
            }
        }
        player.forcedMoneyChange(-amountToPay, Bank.getInstance());
    }
    
}
