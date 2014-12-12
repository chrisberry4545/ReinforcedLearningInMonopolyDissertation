package Model;

import Model.Spaces.Space;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a deal offer in a game of Monopoly. Players can request or offer
 * cash, properties or get out of jail cards.
 * @author Chris Berry
 */
public class DealOffer {
    
    private Set<Space> offeredProperty;
    private Set<Space> requestedProperty;
    private int offeredCash;
    private int requestedCash;
    private int offeredGetOutOfJailCards;
    private int requestedGetOutOfJailCards;
    
    /**
     * Sets up a blank deal offer.
     */
    public DealOffer() {
        offeredProperty = new HashSet();
        requestedProperty = new HashSet();
        offeredCash = 0;
        requestedCash = 0;
        offeredGetOutOfJailCards = 0;
        requestedGetOutOfJailCards = 0;
    }
    
    /**
     * Sets up a deal offer which is a copy of a deal offer.
     * @param offer deal offer to copy.
     */
    public DealOffer(DealOffer offer) {
        this.offeredProperty = new HashSet(offer.getOfferedProperties());
        this.requestedProperty = new HashSet(offer.getRequestedProperties());
        this.offeredCash = offer.getRequestedCash();
        this.requestedCash = offer.getRequestedCash();
        this.offeredGetOutOfJailCards = offer.getNumberOfferedGetOutOfJailCards();
        this.requestedGetOutOfJailCards = offer.getNumberRequestedGetOutOfJailCards();
    }
 
/**
 * Methods for adding assets to offer or request in the deal.
 */    
    /**
     * Adds a property you own to the deal which will be offered to the other 
     * player.
     * @param property to add as an offer to the other player.
     */
    public void addPropertyToOffer(Space property) {
        offeredProperty.add(property);
    }
    
    /**
     * Adds a property to request from the other player in the deal.
     * @param property to request from other player.
     */
    public void addPropertyToRequest(Space property) {
        requestedProperty.add(property);
    }
    
    /**
     * Offer cash to other player in the deal.
     * @param amount to offer.
     */
    public void offerCash(int amount) {
        offeredCash += amount;
    }
    
    /**
     * Cash requested by the player making the deal.
     * @param amount to request in the deal.
     */
    public void requestCash(int amount) {
        requestedCash += amount;
    }
    
    /**
     * Add a number of get out of jail cards to offer to the other player.
     * @param amount of get out of jail cards to add.
     */
    public void offerGetOutOfJailCards(int amount) {
        offeredGetOutOfJailCards += amount;
    }
    
    /**
     * Request a number of get out of jail cards from the other player.
     * @param amount of get out of jail cards to request from the other player.
     */
    public void requestGetOutOfJailCards(int amount) {
        requestedGetOutOfJailCards += amount;
    }
    

/**
 * Getter methods.
 */    
    /**
     * Gets the properties offered by the other player.
     * @return List of properties offered by the other player in the deal.
     */
    public Set<Space> getOfferedProperties() {
        return offeredProperty;
    }
    
    /**
     * Gets the properties requested by the other player.
     * @return List of properties requested by the other player in the deal.
     */
    public Set<Space> getRequestedProperties() {
        return requestedProperty;
    }
    
    /**
     * Gets the cash offered by the other player in the trade.
     * @return amount of cash offered by other player in the trade.
     */
    public int getOfferedCash() {
        return offeredCash;
    }
    
    /**
     * Gets the cash requested by the other player in the trade.
     * @return amount of cash requested by other player in the trade.
     */
    public int getRequestedCash() {
        return requestedCash;
    }
    
    /**
     * Gets number get out of jail cards offered by the other player in the
     * trade.
     * @return number of get out jail cards offered by the other player.
     */
    public int getNumberOfferedGetOutOfJailCards() {
        return offeredGetOutOfJailCards;
    }
    
    /**
     * Gets number of requested get out of jail cards offered by the other 
     * player in the trade.
     * @return number of get of jail cards requested by the other player.
     */
    public int getNumberRequestedGetOutOfJailCards() {
        return requestedGetOutOfJailCards;
    }    
    
    /**
     * Prints all the information of a deal offer to the terminal.
     */
    public void printDealOffer() {
        System.out.println("Offered assets");
        
        System.out.println("Offered Cash.. " + this.getOfferedCash());
        
        System.out.println("Offered Properties..");
        for (Space space: this.getOfferedProperties()) {
            System.out.print(space.getName() + ", ");
        }
        System.out.println();
        
        System.out.println("Offered GOOJ Cards.. " + this.getNumberOfferedGetOutOfJailCards());
        
        System.out.println("Requested assets");
        
        System.out.println("Requested Cash.. " + this.getRequestedCash());
        
        System.out.println("Requested Properties..");
        for (Space space: this.getRequestedProperties()) {
            System.out.print(space.getName()+ ", ");
        }
        System.out.println();
        
        System.out.println("Requested GOOJ Cards.. " + this.getNumberRequestedGetOutOfJailCards());
    }
}
