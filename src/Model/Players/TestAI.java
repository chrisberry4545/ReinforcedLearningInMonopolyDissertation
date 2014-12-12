package Model.Players;

import Model.Bank;
import Model.DealOffer;
import Model.Game;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Test AI used for running tests. Takes specific actions every time which makes
 * automated testing simpler to perform.
 * @author Chris Berry
 */
public class TestAI extends AI{
    
    private Random random;
    private int bidAmount;
    private boolean acceptAllOffers = true;
    
    /**
     * Creates a new TestAI.
     * @param playerNumber to use for the test AI.
     */
    public TestAI(int playerNumber) {
        super(playerNumber);
        random = new Random();
        this.bidAmount = 0;
    }
   
/**
 * Overriding methods.
 */    
    /**
     * Randomly chooses yes or no.
     * @param property to ask about.
     * @return true if the user accepts the proposal, otherwise false.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        return false;
    }
    
    /**
     * Randomly chooses a jail option.
     * @return players choice.
     */
    @Override
    public int askPlayerInJailOptions() {
        int choice = randomOptionChoice(4);
        return choice;        
    }
    
    /**
     * Randomly chooses a move option.
     * @return true if the player is finished.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        //One less than player as there is no need to view properties.
        return true;
    }
    
    /**
     * Randomly chooses a property to build houses on.
     * @return 
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        if (!this.getSites().isEmpty()) {
            return this.getSites().get(0);
        } else {
            System.err.println("The test AI " + this.number + " doesn't "
                    + "have any properties to build on");
        }
        return null;
    }
    
    /**
     * Mortgage a property randomly and see if the debt is cleared, else
     * mortgage another property.
     * @param properties 
     */
    @Override
    public void mortgageProperties() {
        List<Space> properties = getMortgagableProperties();
        //Only needs to happen if there are properties avaliable to mortgage.
        if (properties.size() > 0) {
            int choice = randomOptionChoice(properties.size());
            Space property = properties.get(choice);
            super.mortgageProperty(property);
        }
    }
    
    /**
     * Randomly chooses to make a bid or not, if it chooses to make a bid,
     * it will return a random amount up to the player's max money.
     * @param property to bid on.
     * @param minimumBid the minimum amount valid to bid.
     * @return the AI's bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid,
                               List<Player> playersInBidding) {
        System.out.println("Should be making a bid of " + bidAmount);
        if (this.getMoney() < this.bidAmount || this.bidAmount == 0) {
            return Player.LEAVE_BIDDING_PROCESS;
        }
        return this.bidAmount;
    }
    
    /**
     * Randomly chooses to make a bid or not on a house.
     * @param minBid minimum player can bid.
     * @param isHouse true if the player is bidding on a house.
     * @param playersInBidding List of players in the bidding.
     * @return the amount bid by the player.
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse,
                        List<Player> playersInBidding) {
        return makeABid(null, minBid, null);
    }
    
    /**
     * The AI sells a house on each of the properties it owns. Selling a random
     * amount caused stack overflow errors as the AI would often choose low 
     * amounts so this solution tries to avoid that.
     */
    @Override
    public void sellHouses() {
        for (Space property : getPropertiesWithHouses()) {
            Site site = (Site)property;
            //A single house is sold on each property if they have any houses.
            if (site.getHouses() > 0) {
                super.returnHouse(site,1);
            }
        }
    }
    
    /**
     * The AI randomly chooses whether to buy the property or not.
     * @param spaceOffered
     * @param playerOffering
     * @param priceOffered 
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        if (this.acceptAllOffers) {
            acceptDealFromPlayer(offer,playerOffering);
            return true;
        }
        //Otherwise don't accept.
        return false;
    }
    
    /**
     * Randomly choose to pay the mortgage or to pay the interest.
     * @param property
     * @return 
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        Iterator<Space> propertiesIterator = properties.iterator();
        while (propertiesIterator.hasNext()) {
            Space property = propertiesIterator.next();
            property.setOwner(this);
            int n = randomOptionChoice(2);
            switch (n) {
                case 0 :
                    //If they can't afford the mortgage just let them mortgage it.
                    if (!this.buyProperty(property)) {
                        this.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                                Bank.getInstance());                   
                    }
                    break;
                case 1 :
                    this.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                                Bank.getInstance());
            }
        }
    }
    
    /**
     * Used when the user has an optional money change, such as buying a
     * property. Returns true if successful.
     * @param amount 
     * @return true if the user can afford the change.
     */
    @Override
    public boolean optionalMoneyChange(int amount) {
        System.out.println("Attempting to change money by: " + amount );
        return super.optionalMoneyChange(amount);
    }
    
    /**
     * Used when the user is forced to pay money, such as paying rent.
     * Handles bankruptcy and mortgaging.
     * Returns true if successful.
     * @param amount 
     * @param owed the player the money is owed to.
     * @return true if the user can afford the change.
     */
    @Override
    public boolean forcedMoneyChange(int amount, Player owed) {
        System.out.println("Attempting to change money by: " + amount +
                " and give the change to P" + owed.getNumber());
        return super.forcedMoneyChange(amount,owed);
    }
    
    /**
     * Nothing to do if random AI wins.
     */
    @Override
    public void hasWon() {
        //Nothing to do.
    }
    
    /**
     * Informs the other players they have lost and generalizes appropriately.
     */
    @Override
    public void hasLost() {
        //Nothing to do.
    }    

/**
 * Other methods.
 */    
/**
     * The AI selects a random property to sell to another random player
     * at a random price (which the player can afford).
     */
    public void makeAnOfferToAnotherPlayer() {
        DealOffer offer = new DealOffer();
        //Randomly choose a player.
        List<Player> players = Game.getInstance().getPlayers();
        int playerChoice = randomOptionChoice(players.size());
        Player chosenPlayer = players.get(playerChoice);
        //Randomly choose some of its own properties to offer.  
        //Makes a copy so I can remove things.
        List<Space> allProperties = new ArrayList(getProperties());
        if (getProperties().size() > 0) {
            int numberOfPropertiesToOffer = 
                randomOptionChoice(allProperties.size());
            for (int i=0; i < numberOfPropertiesToOffer; i++) {
                int propertyChoice = randomOptionChoice(allProperties.size());
                //Adds the property choice and removes it so it's not added twice.
                offer.addPropertyToOffer(allProperties.get(propertyChoice));
                allProperties.remove(propertyChoice);
            }
        }
        //Randomly chooses some of the other players properties to request.
        List<Space> otherPlayersProperties = 
                new ArrayList(chosenPlayer.getProperties());
        if (otherPlayersProperties.size() > 0) {
            int numOfPropertiesToRequest = 
                    randomOptionChoice(otherPlayersProperties.size());
            for (int i=0; i < numOfPropertiesToRequest; i++) {
                int propertyChoice = 
                        randomOptionChoice(otherPlayersProperties.size());
                //Adds the request choice and removes from the local list so its not added twice.
                offer.addPropertyToRequest(otherPlayersProperties.get(propertyChoice));
                otherPlayersProperties.remove(propertyChoice);
            }
        }
        //Randomly chooses money to request and offer.
        int amountToCharge = randomOptionChoice(chosenPlayer.getMoney());
        offer.requestCash(amountToCharge);
        int amountToOffer = randomOptionChoice(this.getMoney());
        offer.offerCash(amountToOffer);
        chosenPlayer.assessAnOffer(offer, this);
    }
    
    /**
     * Randomly choose a property to buy back and attempt to buy it back.
     */
    public void buyBackMortgagedProperty() {
        List<Space> mortgagedProperties = getMortgagedProperties();
        if (mortgagedProperties.size() > 0) {
            int choice = randomOptionChoice(mortgagedProperties.size());
            super.unmortgageProperty(mortgagedProperties.get(choice));
        }
    }    
/**
 * Random Choice methods.
 */    
     /**
     * Randomly selects a number between 0 and the number of options.
     * @param numberOfOptions
     * @return 
     */
    public int randomOptionChoice(int numberOfOptions) {
        if (numberOfOptions > 0) {
            int choice = random.nextInt(numberOfOptions);
            return choice;
        }
        return 0;
    }
/**
 * Setter methods.
 */    
    /**
     * The player always bids his bid amount in an auction.
     * @param bidAmount 
     */
    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }    
    
    public void setAcceptAllOffers(boolean acceptOffers) {
        this.acceptAllOffers = acceptOffers;
    }
}
