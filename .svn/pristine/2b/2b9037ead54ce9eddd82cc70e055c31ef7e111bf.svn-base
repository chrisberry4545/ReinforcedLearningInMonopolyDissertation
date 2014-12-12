package Model.Players;

import Model.Bank;
import Model.DealOffer;
import Model.Game;
import Model.Spaces.Site;
import Model.Spaces.SiteGroup;
import Model.Spaces.Space;
import java.util.ArrayList;
import java.util.List;

/**
 * The SmartBot is an AI built from the ground with scripted actions in each
 * situation. The idea was to create something which could be trained against
 * an AI to improve learning. It is also used as a testing device.
 * @author Chris Berry
 */
public class SmartBot extends AI {
    
    private final int propertyBiddingLowerLimit = 200;
    private final int propertyBuyingLowerLimit = 200;
    private final int roundsBeforeStayInJail = 40;
    private final int wontPayToLeaveJailMoney = 200;
    private final int houseBuyingLowerLimit = 200;  
    private final int mortgageLimit = 500;
    private final int unmortgageLimit = 1200;
    private final int monopolyUnmortgageLimit = 100;
    
    private Player controllingPlayer;
    
    /**
     * Creates a new SmartBot AI
     * @param tokenNumber the token number the bot will use.
     */
    public SmartBot(int tokenNumber) {
        super(tokenNumber);
        this.controllingPlayer = this;
    }
    
    /**
     * Creates a new SmartBot only used to make decisions for the controlling
     * player. A player set up like this will only make actions for its owner
     * and not effect the game itself.
     * @param tokenNumber the token number the bot will use.
     * @param controllingPlayer the player the bot will take actions for.
     */
    public SmartBot(int tokenNumber, Player controllingPlayer) {
        super(tokenNumber);
        this.controllingPlayer = controllingPlayer;
    }

/**
 * Other abstract methods AI's cover.
 */    
    /**
     * Ask player if they want to buy a property.
     * @param property to ask about.
     * @return true if the user accepts the proposal, otherwise false.
     */
    @Override
    public boolean 
            askPlayerIfTheyWantToBuyProperty(Space property) {
        int moneyLeft = controllingPlayer.getMoney() - property.getValue();
        if (moneyLeft > propertyBuyingLowerLimit) {
            controllingPlayer.buyProperty(property);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Asks player to choose how they will act when they are in jail.
     * @return players choice.
     */
    @Override
    public int askPlayerInJailOptions() {
        this.checkIfShouldMortgageProperties();
        this.checkIfShouldBuyHouses();
        this.checkIfShouldUnMortgageProperties();
        this.checkIfDealShouldBeMade();
        if (Game.getInstance().getRoundCount() > roundsBeforeStayInJail
                || controllingPlayer.getMoney() < wontPayToLeaveJailMoney) {
            int diceRoll = controllingPlayer.rollToLeaveJail();
            //Returns -1 if a double isn't rolled.
            if (diceRoll == -1) {
                //Turn finishes in this case.
                return Player.STILL_IN_JAIL_TURN_OVER;
            } else {
                return diceRoll;
            }
        }
            
        if (controllingPlayer.payToLeaveJail()) {
            return Player.rollDice();
        } else {
            System.err.println("AI trying to pay to leave jail when it can't"
                    + "afford to.");
            return Player.STILL_IN_JAIL_TURN_NOT_OVER;
        }    
            
    }
    
    /**
     * Gets the player's response to what they intend to do on their turn.
     * @return players choice.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        this.checkIfShouldMortgageProperties();
        this.checkIfShouldBuyHouses();
        this.checkIfShouldUnMortgageProperties();
        this.checkIfDealShouldBeMade();
        return true;
    }
    
    /**
     * Gets the player to assess a deal offer.
     * @param offer which to assess.
     * @param playerOffering player who has made the offer.
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        //Returns false if any of the requested spaces are ones the player
        //wants to keep.
        for (Space requestedSpace : offer.getRequestedProperties()) {
            if (this.doesPlayerWantToKeepHoldOfSite(requestedSpace)) {
                return false;
            }
        }
        
        //Returns true if the player wants the property offered.
        for (Space offeredSpace : offer.getOfferedProperties()) {
            if (doesPlayerWantToGainSpace(offeredSpace)) {
                controllingPlayer.acceptDealFromPlayer(offer, playerOffering);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Asks the player if they would like to make a bid on a property.
     * @param property to bid on.
     * @param minimumBid minimum amount a player can bid.
     * @param playersInTheRunning A List of the other players in the running.
     * @return the player's bid, or a value representing the player wishes
     * to leave the bidding process.
     */
    @Override
    public int makeABid(Space property, int minimumBid,
                                List<Player> playersInTheRunning) {
        int newMinBid = minimumBid + 25;
        if (controllingPlayer.getMoney() < newMinBid) {
            return Player.LEAVE_BIDDING_PROCESS;
        }
        if (controllingPlayer.getMoney() - minimumBid > propertyBiddingLowerLimit 
                && minimumBid < property.getValue() * 2) {
            return minimumBid + 25;
        }
        return Player.LEAVE_BIDDING_PROCESS;
    }
    
    /**
     * This AI does not bid on houses at the moment.
     * @param minBid the minimum a player can bid on a house.
     * @param isHouse true if the player is bidding on a house, false if it
     * is a hotel.
     * @param playersInRunning A List of the other players in the running.
     * @return the player's bid, or a value representing the player wishes
     * to leave the bidding process.
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse,
                                List<Player> playersInTheRunning) {
        return Player.LEAVE_BIDDING_PROCESS;
    }
    /**
     * The player receives a list of properties and becomes the owner of them
     * all. Sometimes he will be required to pay a mortgage fee on these if
     * they are already mortgaged.
     * @param properties that player has received.
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        for (Space property : properties) {
            property.setOwner(controllingPlayer);
            if (!controllingPlayer.optionalMoneyChange(-property.getMortgageRate())) {
                controllingPlayer.unmortgageProperty(property);
                controllingPlayer.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                        Bank.getInstance());
            }
        }
    }
    
    /**
     * This AI doesn't have to respond to winning.
     */
    @Override
    public void hasWon() {
        //Nothing to do.
    }
    
    /**
     * This AI doesn't have to respond to loosing.
     */
    @Override
    public void hasLost() {
        //Nothing to do.
    }
    
    /**
     * If, after paying for the house, a player would still have more money than
     * the house buying lower limit the player will buy a house on a property.
     */
    public void checkIfShouldBuyHouses() {
        List<Site> sites = controllingPlayer.getSitesWhichCanBeBuiltOn();
        for (Site s : sites) {
            if (controllingPlayer.getMoney() - s.getHouseCost() > houseBuyingLowerLimit) {
                s.addHouse();
            }
        }
    }
    
    /**
     * The player checks if his money is below the mortgage limit. If it is
     * it firstly tries to mortgage all its utilties and stations. If this
     * doesn't resolve the problem the AI then mortagages any propertys it does
     * not own a Monopoly on.
     */
    public void checkIfShouldMortgageProperties() {
        //Firstly attempt to sell all non site properties.
        if (controllingPlayer.getMoney() < mortgageLimit) {
            for (Space space : controllingPlayer.getProperties()) {
                if (space.getClass() != Site.class) {
                    space.mortgageProperty();
                }
            }
        }
        
        //If money is still to small.
        if (controllingPlayer.getMoney() < mortgageLimit) {
            for (Site site : controllingPlayer.getSites()) {
                if (!SiteGroup.playerOwnsAMonopoly(site.getColorNumber())) {
                    site.mortgageProperty();
                }
            }
        }
    }
    
    /**
     * Buys back any property mortgaged while the player has more money than
     * the unmortgage limit.
     */
    public void checkIfShouldUnMortgageProperties() {
        int counter = 0;
        
        for (Space space : controllingPlayer.getMortgagedProperties()) {
            if (SiteGroup.playerOwnsAMonopoly(space.getColorNumber())
                    && controllingPlayer.getMoney() - space.getMortgageRate() 
                            > this.monopolyUnmortgageLimit ) {
                space.unMortgageProperty();
            }
        }
        
        List<Space> mortgagedProperties = controllingPlayer.getMortgagedProperties();
        while (counter < mortgagedProperties.size() 
                && controllingPlayer.getMoney() > unmortgageLimit) {
            mortgagedProperties.get(counter).unMortgageProperty();
            counter++;
        }
    }
    
    /**
     * The bot checks if it should make a deal. It checks if there are any spaces
     * it wants (ones that will give it a Monopoly) and then establishes spaces
     * the other player will want. It will try and then make a deal between these
     * two.
     */
    public void checkIfDealShouldBeMade() {
        List<Space> thisPlayersRequests = findSpacesPlayerWants(controllingPlayer);
        for (Space spaceToRequest : thisPlayersRequests) {
            Player otherPlayer = spaceToRequest.getOwner();
            for (Space spaceToOffer : findSpacesPlayerWants(otherPlayer)) {
                if (spaceToOffer.getOwner().equals(controllingPlayer)
                        && spaceToOffer.getColorNumber() != spaceToRequest.getColorNumber()) {
                    DealOffer offer = new DealOffer();
                    offer.addPropertyToRequest(spaceToRequest);
                    offer.addPropertyToOffer(spaceToOffer);
                    otherPlayer.assessAnOffer(offer, controllingPlayer);
                }
            }
        }
    }
    
    /**
     * The bot looks for a space it wants. These are spaces that obtaining will]
     * result in the player having a Monopoly.
     * @param player to find spaces they want.
     * @return a List of Spaces the player wants to obtain.
     */
    private List<Space> findSpacesPlayerWants(Player player) {
        List<Integer> colorsPlayerWants = new ArrayList();
        for (Site site : player.getSites()) {
            if (SiteGroup.playerIsOneOffMonopoly(site.getColorNumber(), player)) {
                colorsPlayerWants.add(site.getColorNumber());
            }
        }
        List<Player> players = new ArrayList(Game.getInstance().getPlayers());
        players.remove(player);
        
        List<Space> allProperties = new ArrayList();
        for (Player p : players) {
            allProperties.addAll(p.getProperties());
        }
        
        List<Space> spacesPlayerWants = new ArrayList();
        for (Space space : allProperties) {
            if (colorsPlayerWants.contains(space.getColorNumber())) {
                spacesPlayerWants.add(space);
            }
        }
        return spacesPlayerWants;
    }
    
    /**
     * If the player is one off holding a Monopoly on a site group this method
     * will return false, else if the Player is happy trading the site true will
     * be returned.
     * @param space
     * @return true if the player is happy trading the site.
     */
    private boolean doesPlayerWantToKeepHoldOfSite(Space space) {
        if (SiteGroup.playerIsOneOffMonopoly(space.getColorNumber(), controllingPlayer)
                || SiteGroup.playerOwnsAMonopoly(space.getColorNumber())) {
            return true;
        }
        return false;
    }
    
    /**
     * If the player needs the space to complete a Monopoly it wants the space
     * and true is returned. Otherwise false is returned.
     * @param space
     * @return true if the player wants the space.
     */
    private boolean doesPlayerWantToGainSpace(Space space) {
        if (SiteGroup.playerIsOneOffMonopoly(space.getColorNumber(), controllingPlayer)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the first site that can be built on.
     * @return 
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        if (controllingPlayer.getSitesWhichCanBeBuiltOn().isEmpty()) {
            return null;
        }
        return controllingPlayer.getSitesWhichCanBeBuiltOn().get(0);
    }

    
    /**
     * Mortgages all the players properties.
     */
    @Override
    public void mortgageProperties() {
        for (Space space : controllingPlayer.getMortgagableProperties()) {
            space.mortgageProperty();
        }
    }

    
    /**
     * Sells all of a players houses.
     */
    @Override
    public void sellHouses() {
        while (controllingPlayer.getSitesWithHousesWhichCanBeSold().size() > 0) {
            for (Site site : controllingPlayer.getSitesWithHousesWhichCanBeSold()) {
                site.returnHouses(1);
            }
        }
    }
    
    
    
}
