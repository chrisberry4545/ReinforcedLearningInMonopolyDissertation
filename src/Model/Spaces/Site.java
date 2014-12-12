package Model.Spaces;

import Controller.ActionManager;
import Model.Bank;
import Model.Players.Player;
import java.util.EmptyStackException;

/**
 * A site a player can buy. An example of this would be Mayfair.
 * @author Chris Berry
 */
public class Site extends Space {
    
    public static final int HOUSE_ADDED = 0;
    public static final int TOO_MANY_HOUSES = 1;
    public static final int MUST_BUY_HOUSES_ON_OTHER_PROPERTIES = 2;
    public static final int BANK_HASNT_ENOUGH_PROPERTIES = 3;
    
    private int noOfHouses;
    private int[] rentRates;
    private int houseCost;
    private int houseSellPrice;
    
    public static final int MAX_HOUSES = 5;
    
    /**
     * Creates a new Site space in the game.
     * @param name of the site.
     * @param intialCost amount the player has to pay the bank to become the
     * owner of the space.
     * @param mortgageRate amount player will receive if they are the owner
     * of the site and mortgage the site.
     * @param rentPerNumberOfHouses an array of the amount of rent received
     * for each number of houses on a site.
     * @param houseCost the cost of buying a single house on the site.
     * @param color the color group the site sits within.
     */
    public Site(String name, int intialCost, int mortgageRate,
            int[] rentPerNumberOfHouses, int houseCost, int colorNumber) {
        super(name, intialCost, mortgageRate);
        if (rentPerNumberOfHouses.length <= MAX_HOUSES) {
            System.err.println(name + " has too few house values");
        }
        this.noOfHouses = 0;
        this.rentRates = rentPerNumberOfHouses;
        this.houseCost = houseCost;
        this.houseSellPrice = houseCost/2;
        this.colorNumber = colorNumber;
        SiteGroup.addSite(this);
    }

/**
 * Key method.
 */    
    /**
     * Allows the player to buy a property if it not already owned. If it is
     * owned they will be charged the rent rate of the property.
     * @param diceRoll the amount the player rolled to land here.
     */
    @Override
    public void performAction(int diceRoll, Player player) {  
        super.landedOnProperty(player, getRent());
    }
 
/**
 * House methods.
 */    
    /**
     * Adds one house to this site. Returns false if unsuccessful.
     * @return Player.NOT_ENOUGH_MONEY if the player hasn't got enough money.
     * Site.BANK_HASNT_ENOUGH_PROPERTIES if the bank hasn't got enough
     * houses to fulfil the request.
     * Site.HOUSE_ADDED if the house is successfully added.
     * Site.TOO_MANY_HOUSES if there are already too many houses on the Site.
     */
    public int addHouse() {
        if (owner.optionalMoneyChange(-houseCost)) {
            int result = addHouseForFree();
            //refund the owner if a house wasn't bought.
            if (result == BANK_HASNT_ENOUGH_PROPERTIES ||
                    result == TOO_MANY_HOUSES) {
                owner.optionalMoneyChange(houseCost);
            }
            ActionManager.getInstance().houseNumberChanged(this);
            return result;
        } else {
            //Player can't afford the house.
            return Player.NOT_ENOUGH_MONEY;
        }
    }
    
    /**
     * Adds a house to the site without the owner paying anything to do so.
     * @return a public static int variable based on the outcome:
     * Site.BANK_HASNT_ENOUGH_PROPERTIES if the bank hasn't got enough
     * houses to fulfil the request.
     * Site.HOUSE_ADDED if the house is successfully added.
     * Site.TOO_MANY_HOUSES if there are already too many houses on the Site.
     */
    public int addHouseForFree() {
        if (noOfHouses + 1 <= MAX_HOUSES) {
            //Last house is hotel.
            if (noOfHouses + 1 == MAX_HOUSES) {
                if (!Bank.getInstance().removeHotel()) {
                        return BANK_HASNT_ENOUGH_PROPERTIES;
                }
            } else {
                if (!Bank.getInstance().removeHouse()) {
                        return BANK_HASNT_ENOUGH_PROPERTIES;
                } 
            }
            //If bank has enough houses can continue as normal.
            this.noOfHouses++;
            return HOUSE_ADDED;
        }
        return TOO_MANY_HOUSES;
    }
    
    /**
     * Allows the player to sell a number of houses on his site and
     * reclaim the money.
     * @param numberToSell Number of houses to sell
     */
    public void returnHouses(int numberToSell) {
        returnHousesForFree(numberToSell);
        ActionManager.getInstance().houseNumberChanged(this);
        for (int i = 0; i < numberToSell; i++) {
            //Gives player appropriate money back.
            owner.optionalMoneyChange(houseSellPrice);
        }
    }
    
    /**
     * Returns a house to the bank with the owner gaining no money from the 
     * transaction.
     * @param numberToReturn number of houses to return.
     */
    public void returnHousesForFree(int numberToReturn) {
        for (int i = 0; i < numberToReturn; i++) {
            if (noOfHouses < MAX_HOUSES) {
                Bank.getInstance().returnHouse();
            } else {
                Bank.getInstance().returnHotel();
            }
            this.noOfHouses--;
        }
    }

/**
 * Checks methods for various conditions.
 */    
    /**
     * Returns true if the owner can afford to buy a house on this site.
     * @return 
     */
    public boolean canOwnerAffordHouse() {
        if (owner.getMoney() - houseCost >= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if the site currently has less than the maximum houses
     * on it.
     * @return true if the house has less than the maximum number of houses
     * on it, false if it has a number of houses more or equal to the maximum 
     * number of houses.
     */
    public boolean lessThanTheMaximumHouses() {
        if (noOfHouses < MAX_HOUSES) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if the property can be built on.
     * False if the property can't be built on.
     * @return true if the property can be built on.
     */
    public boolean canBuildOnThisSite() {
        if (SiteGroup.playerOwnsAMonopoly(this.getColorNumber())
                && SiteGroup.otherPropertiesHaveEnoughHouses(this)
                && canOwnerAffordHouse()
                && lessThanTheMaximumHouses()) {
            return true;
        }
        return false;
    }
    
    /**
     * Can sell houses on sites if this property has at least 1 house on it,
     * and the other sites in the group have the right amount of houses
     * @return true if house can be sold on the property.
     */
    public boolean canSellHouseOnSite() {
        if (this.getHouses() > 0
                && SiteGroup.canSellHouseOnProperty(this)) {
            return true;
        }
        return false;
    }
 
/**
 * Getter methods.
 */
        
    /**
     * Gets the current rent for this site.
     * @return current rent.
     */
    public int getRent() {
        //Rent is doubled if the user has a monopoly but hasn't built any
        //houses yet.
        if (SiteGroup.playerOwnsAMonopoly(this.getColorNumber())
                && noOfHouses == 0) {
            return rentRates[noOfHouses]*2;
        }
        return rentRates[noOfHouses];
    }
    
    /**
     * Gets the value of this asset. Properties have the value
     * as their normal purchase value (half if they are currently mortgaged), 
     * and houses/hotels are worth the value paid for them.
     * @return 
     */
    @Override
    public int getValue() {
        if (this.isMortgaged()) {
            return Math.round(this.intialCost/2);
        } else {
            int value = this.intialCost +
                    (this.getHouseCost() * this.getHouses());
            return value;
        } 
    }
    
    /**
     * Gets the mortgage value of site including the value of selling all the
     * houses on the site.
     * @return full mortgage value of the site including the value of selling
     * all houses on the property.
     */
    @Override
    public int getFullMortgageValue() {
        int fullMortgageValue = 
                super.mortgageRate + (this.houseSellPrice * this.getHouses());
        return fullMortgageValue;
    }
    
    /**
     * Gets the money received from selling a house on this property.
     * @return money received from selling a house here.
     */
    public int getHouseSellPrice() {
        return this.houseSellPrice;
    }
        
    /**
     * Gets the number of houses. 5 Houses means the site has a hotel
     * built upon it.
     * @return The number of houses.
     */
    public int getHouses() {
        return noOfHouses;
    }
    
    /**
     * Gets the maximum amount of houses which can be built on the property.
     * @return the maximum number of houses which can be built on the property.
     */
    public int getMaxHouses() {
        return MAX_HOUSES;
    }
    
    /**
     * Gets the cost of adding a new house.
     * @return Gets the cost of adding a new house.
     */
    public int getHouseCost() {
        return houseCost;
    }
    
    
}
