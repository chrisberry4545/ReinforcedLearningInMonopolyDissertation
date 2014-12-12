package Model;

import Model.Players.RandomAI;

/**
 * The Bank in Monopoly. Extends RandomAI as the bank doesn't need to perform
 * most of the options performed by the AI but shares many aspects of the player.
 * @author Chris Berry
 */
public class Bank extends RandomAI {
    
    private static Bank instance;
    private int housesLeft;
    private int hotelsLeft;
    private final int totalHouses = 35;
    private final int totalHotels = 13;
    
    /**
     * Sets up a basic instance of the Bank used in the game of Monopoly.
     */
    protected Bank() {
        super(Game.MAX_PLAYERS);
        this.housesLeft = totalHouses;
        this.hotelsLeft = totalHotels;
        this.optionalMoneyChange(100000);
    }
    
    /**
     * Resets the bank so it becomes a new instance.
     */
    public static void resetBank() {
        instance = null;
        instance = new Bank();
    }
    
/**
 * Remove and add hotels and houses.
 */     
    /**
     * Removes a house from the bank.
     * @return true if successful.
     */
    public boolean removeHouse() {
        if (this.housesLeft > 0) {
            this.housesLeft--;
            return true;
        } else {
            Game.getInstance().startAuction(1, true);
            return false;
        }
    }
    
    /**
     * Removes a hotel from the bank.
     * @return true if successful.
     */
    public boolean removeHotel() {
        if (this.hotelsLeft > 0) {
            //Hotels are traded for 4 houses.
            this.housesLeft += 4;
            this.hotelsLeft--;
            return true;
            //One or less hotel left go to hotel shortage bidding.
        } else {
            Game.getInstance().startAuction(1, false);
            return false;
        }
    }
    
    /**
     * Returns 1 house to the bank.
     */
    public void returnHouse() {
        this.housesLeft++;
    }
    
    /**
     * Returns 1 hotel to the bank.
     */
    public void returnHotel() {
        this.hotelsLeft++;
    }
    
/**
 * Get methods.
 */    
    /**
    * Returns an instance of the singleton class Bank
    * @return Bank instance
    */
    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }
    /**
     * Returns the number of houses still held by the bank.
     * @return number of houses held by the bank.
     */
    public int getHousesLeft() {
        return housesLeft;
    }
    
    /**
     * Returns the number of hotels still held by the bank.
     * @return hotels held by the bank.
     */
    public int getHotelsLeft() {
        return hotelsLeft;
    }      
    
}
