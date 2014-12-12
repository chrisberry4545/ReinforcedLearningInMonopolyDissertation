package Model.Players;

import Model.Bank;
import Model.Cards.Card;
import Model.DealOffer;
import Model.Game;
import Model.GameStats;
import Model.Spaces.Site;
import Model.Spaces.SiteGroup;
import Model.Spaces.Space;
import Model.Spaces.SpaceEnums;
import Model.Spaces.Station;
import Model.Spaces.Utility;
import java.util.*;

/**
 * Abstract class representing a Player in monopoly.
 * @author Chris Berry
 */
public abstract class Player {
    
    private Space currentSpace;
    private List<Space> properties;
    private int money;
    private List<Card> getOutOfJailCards = new ArrayList();
    private int turnsInJailCounter;
    protected int number;
    private boolean inJail;
    
    public static final int MAX_TURNS_IN_JAIL = 3;
    
    public static final int STILL_IN_JAIL_TURN_OVER = -2;
    public static final int STILL_IN_JAIL_TURN_NOT_OVER = -1;
    public static final int PAY_TO_LEAVE = 0;
    public static final int ROLL_TO_LEAVE = 1;
    public static final int USE_GET_OUT_OF_JAIL_CARD = 2;
    public static final int JAIL_BUY_HOUSES = 3;
    public static final int JAIL_MAKE_DEAL = 4;
    public static final int EXIT_GAME_JAIL = 5;
    
    public static final int ROLL_DICE = 0;
    public static final int BUY_HOUSES = 1;
    public static final int OFFER_PROPERTY = 2;
    public static final int MORTGAGE_PROPERTIES = 3;
    public static final int SELL_HOUSES = 4;
    public static final int BUY_BACK_MORTGAGED_PROPERTIES = 5;
    public static final int EXIT_GAME_STANDARD = 6;
    
    public static final int NOT_ENOUGH_MONEY = 9;
    
    public static final int LEAVE_BIDDING_PROCESS = -1;
    
    
    
    public Player(int setToken) {
        this.number = setToken;
        properties = new ArrayList();
        this.inJail = false;
    }

/**
 * Abstract methods that should be implemented in all player classes.
 */
        
    /**
     * Ask player yes/no question
     * @param property to ask about.
     * @return true if the user accepts the proposal, otherwise false.
     */
    public abstract boolean 
            askPlayerIfTheyWantToBuyProperty(Space property);
    
    /**
     * Messages the player with some information.
     * @param message to give player
     * @param title of message
     */
    public abstract void messagePlayer (String message, String title);
    
    /**
     * Asks player to choose how they will act when they are in jail.
     * @return players choice.
     */
    public abstract int askPlayerInJailOptions();
    
    /**
     * Gets the player's response to what they intend to do on their turn.
     * @return true if the player is finished.
     */
    public abstract boolean askPlayerMoveOptions();
    
    /**
     * Allows the user to select a property to build houses on.
     * @return the Site selected by the user.
     */
    public abstract Site choosePropertyForBuildingOn();
    
    /**
     * Allows user choice of properties to mortgage to avoid bankruptcy.
     */
    public abstract void mortgageProperties();
    
    /**
     * Allows user to sell houses on their property.
     */
    public abstract void sellHouses();
    
    /**
     * Shows the player's currently owned properties.
     */
    public abstract void showPlayerProperties();

    /**
     * Allows player to choose whether to accept or deny an offer.
     * @param offer Deal offered by player
     * @param playerOffering player making the offer.
     * @return true if player accepts the offer.
     */
    public abstract boolean assessAnOffer(DealOffer offer, Player playerOffering);
    
    /**
     * Allows player to make a bid on a property.
     * @param property to bid on.
     * @param minimumBid the minimum bid amount.
     * @param playersInTheRunning A List of the other players in the running.
     * @return players bid amount.
     */
    public abstract int makeABid(Space property, int minimumBid,
                                    List<Player> playersInTheRunning);
    
    /**
     * Allows the player to bid on the house.
     * @param minBid minimum bid player must make.
     * @param isHouse true if the property is a house.
     * @param playersInTheRunning List of players in the running.
     * @return the amount the player wants to bid on the property.
     */
    public abstract int makeABidOnHouse(int minBid, boolean isHouse,
                            List<Player> playersInTheRunning);
    
    /**
     * The player is given a List of properties.
     * @param properties for the player to be given.
     */
    public abstract void receiveProperty(List<Space> properties);
    
    /**
     * Informs the player they have won the game.
     */
    public abstract void hasWon();
    
    /**
     * Informs the other players they have lost and generalizes appropriately.
     */
    public abstract void hasLost();

/**
 * Dice Methods.
 */    
    /**
     * Rolls two dice and returns their outcome. If a double is rolled the
     * dice can be rolled again. If 3 doubles are rolled a value of -1 is
     * returned and the player should be sent to jail.
     * @return value of player's dice rolls or -1 if they rolled 3 doubles.
     */
    public static int rollForTurn() {
        int dice1;
        int dice2;
        int total = 0;
        for (int i = 0; i < 3; i++) {
            dice1 = rollDice();
            dice2 = rollDice();
            total += dice1 + dice2;
            //If dice aren't the same (E.g no double is rolled).
            if (dice1 != dice2) {
                return total;
            }
        }
        //If no total is returned then the player has rolled 3 doubles
        //and should be sent to jail. This is notified with a return of -1.
        return -1;
    }
    
    /**
     * Rolls a single 6 sided dice and returns the result.
     * @return the result of rolling a 6 sided dice.
     */
    public static int rollDice() {
        return 1 + (int)(Math.random() * ((6 - 1) + 1));
    }
    
/**
 * Money change methods.
 */    
    /**
     * Changes a players money by a given amount.
     * Returns false if the player can't afford this.
     * @param amount
     * @return 
     */
    private boolean changeMoney(int amount) {
        int newAmount = money + amount;
        if (newAmount >= 0) {
            this.money = newAmount;
            return true;
        }
        return false;
    }
    
    /**
     * Used when the user has an optional money change, such as buying a
     * property. Returns true if successful.
     * @param amount 
     * @return true if the user can afford the change.
     */
    public boolean optionalMoneyChange(int amount) {
        return changeMoney(amount);
    }
    
    /**
     * Used when the user is forced to pay money, such as paying rent.
     * Handles bankruptcy and mortgaging.
     * Returns true if successful.
     * @param amount 
     * @param owed the player the money is owed to.
     * @return true if the user can afford the change.
     */
    public boolean forcedMoneyChange(int amount, Player owed) {
        boolean enoughMoney = changeMoney(amount);
        if (enoughMoney) {
            return true;
        } else {
            //Let user mortgage properties but only if there
            //are enough properties to mortgage.
            if (getMortgagableProperties().size() > 0) {
                mortgageProperties();
                //If this sorts it the player can continue.
                if (forcedMoneyChange(amount, owed)) {
                    return true;
                }
            }
            //Allows users to try and sell houses
            if (this.getSitesWithHousesWhichCanBeSold().size() > 0) {
                sellHouses();
                //They can keep trying this until all houses are sold.
                if (forcedMoneyChange(amount, owed)) {
                    return true;
                }
            }
        }
        //If they still can't pay they are declared bankrupt.
        Game.getInstance().declareBankruptcy(this, owed);
        return false;
    }

/**
 * Game interaction methods.
 */    
    
    /**
     * The player unmortgages a property.
     * @param property to unmortgage.
     * @return true if it was successfully unmortgaged.
     */
    public boolean unmortgageProperty(Space property) {
        boolean result = property.unMortgageProperty();
        if (result) {
            GameStats.addPropertyUnMortgaged(this.getNumber(), property);
        }
        return result;
    }
    
    /**
     * The player mortgages the property.
     * @param property to mortgage.
     */
    public void mortgageProperty(Space property) {
        property.mortgageProperty();
        GameStats.addPropertyMortgaged(this.getNumber(), property);
    }
    
    /**
     * The player adds a house to the property.
     * @param property to add house to.
     * @return the code generated when attempting to add a house to the property.
     * (See static values).
     */
    public int addHouse(Site property) {
        int result = property.addHouse();
        if (result != Player.NOT_ENOUGH_MONEY) {
            GameStats.addNumberOfHousesBought(this.getNumber(), property);
        }
        return result;
    }
    
    /**
     * Returns a house on the property to the bank.
     * @param property to return house on.
     * @param numToSell number of houses to return.
     */
    public void returnHouse(Site property, int numToSell) {
        property.returnHouses(1);
        GameStats.addNumberOfHousesSold(this.getNumber(), property);
    }
    
/**
 * Property and asset methods.
 */    
     /**
     * Adds the property to the player's portfolio.
     * @param property to add to the player.
     */
    public void addProperty(Space property) {
        properties.add(property);
    }
    
    /**
     * Removes the property from the player.
     * @param property to remove from player.
     */
    public void removeProperty(Space property) {
        properties.remove(property);
    }
        
    /**
     * Lets the player by the property if they have enough money.
     * @param property to buy
     * @return true if the player has enough money to successfully buy the property.
     */
    public boolean buyProperty(Space property) {
        int cost = property.getIntialCost();
        if (this.getMoney() >= cost) {
            this.forcedMoneyChange(-cost, Bank.getInstance());
            property.setOwner(this);
            GameStats.addPropertyBought(this.getNumber(), property);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gives all of a player's properties and get out of jail cards to another
     * player.
     * @param playerOwed player to give assets to.
     */
    public void giveAllAssetsToAnotherPlayer(Player playerOwed) {
        //Attempt to give player all your propertys.
        List<Space> propertiesCopy = new ArrayList(this.getProperties());
        playerOwed.receiveProperty(propertiesCopy);
        //Give the player all your get out of jail free cards.
        while(getOutOfJailCards.size() > 0) {
                Card removedCard = this.removeGetOutOfJailCard();
                playerOwed.addGetOutOfJailCard(removedCard);
        }     
    }
    
    /**
     * Gets the value of all of the player's assets. Properties have the value
     * as their normal purchase value (half if they are currently mortgaged), 
     * and houses/hotels are worth the value paid for them.
     * @return value of all a player's assets.
     */
    public int getValueOfAllAssets() {
        int valueOfAllAssets = 0;
        for (Space property : this.getProperties()) {
            valueOfAllAssets += property.getValue();
        }
        return valueOfAllAssets;
    }
    

/**
 * Deal Methods.
 */
     /**
     * Handles the transactions involved in a deal between two players.
     * @param spaceOffered
     * @param playerOffering
     * @param priceOffered 
     */
    public void acceptDealFromPlayer(DealOffer deal, Player playerOffering) {
        //Handle money.
        int amountOffered = deal.getOfferedCash();
        int amountRequested = deal.getRequestedCash();
        int amountToPlayerOffering = amountRequested - amountOffered;
        playerOffering.forcedMoneyChange(amountToPlayerOffering, this);
        this.forcedMoneyChange(-amountToPlayerOffering, playerOffering);
        //Handle get out of jail cards.
        for (int i = 0; i < deal.getNumberOfferedGetOutOfJailCards(); i++) {
            Card cardRemoved = playerOffering.removeGetOutOfJailCard();
            this.addGetOutOfJailCard(cardRemoved);
        }
        for (int i = 0; i < deal.getNumberRequestedGetOutOfJailCards(); i++) {
            Card cardRemoved = this.removeGetOutOfJailCard();
            playerOffering.addGetOutOfJailCard(cardRemoved);
        }
        //Handle property.
        for (Space property : deal.getOfferedProperties()) {
            property.setOwner(this);
        }
        for (Space property : deal.getRequestedProperties()) {
            property.setOwner(playerOffering);
        }
    }

/**
 * Jail Methods.
 */
    /**
     * Adds one get out of jail card to the player.
     */
    public void addGetOutOfJailCard(Card getOutOfJailCard) {
        if (getOutOfJailCards.size() < Game.MAX_NUM_OF_GET_OUT_OF_JAIL_CARDS) {
            getOutOfJailCards.add(getOutOfJailCard);
        }
    }
    
    /**
     * Removes a get out of jail card from the player.
     * @return card removed from the player.
     */
    public Card removeGetOutOfJailCard() {
        if (getOutOfJailCards.size() > 0) {
            Card cardToRemove 
                    = getOutOfJailCards.get(getOutOfJailCards.size() - 1);
            getOutOfJailCards.remove(cardToRemove);
            return cardToRemove;
        } else {
            System.err.println("You can't have less than 0 get out of jail"
                    + " cards.");
            return null;
        }
    }
    
    /**
     * Gives the player back their get out of jail card and sets them back
     * in jail.
     */
    public void reverseGetOutOfJailCard(Card getOutOfJailCard) {
        addGetOutOfJailCard(getOutOfJailCard);
        setInJail(true);
    }
    
    /**
     * Puts the card on the bottom of the appropriate deck.
     * @param card to put on bottom of a deck.
     */
    public void putGetOutOfJailCardBackInDeck(Card card) {
        if (card.fromChanceCardDeck()) {
            Game.getInstance().putCardOnBottomOfDeck(true, card);
        } else {
            Game.getInstance().putCardOnBottomOfDeck(false, card);
        }
    }    
    
    /**
     * The player will try and leave jail.
     * @return true if the player can afford to.
     */
    public boolean payToLeaveJail() {
        if (changeMoney(-Game.LEAVE_JAIL_COST)) {
            setInJail(false);
            return true;
        }
        return false;
    }
    
    /**
     * Attempt to roll a double to leave jail.
     * @return value rolled if a double is successfully rolled, or -1
     * otherwise.
     */
    public int rollToLeaveJail() {
        int dice1 = rollDice();
        int dice2 = rollDice();
        if (dice1 == dice2) {
            setInJail(false);
            return dice1 + dice2;
        }
        
        return -1;
    }
    
    /**
     * Attempt to use a get out of jail card to leave jail.
     * @return the card if it was successfully used.
     */
    public Card useGetOutOfJailCard() {
        if (getOutOfJailCards.size() > 0) {
            Card card = removeGetOutOfJailCard();
            putGetOutOfJailCardBackInDeck(card);
            setInJail(false);
            return card;
        }
        return null;
    }
    
    /**
     * Increases the jail counter for every turn the player is in jail.
     * If this exceeds the maximum number of turns in jail, the player is 
     * released from jail.
     */
    public void increaseJailCounter() {
        if (turnsInJailCounter > MAX_TURNS_IN_JAIL) {
            turnsInJailCounter = 0;
            //Player gets charged 50 anyway.
            this.forcedMoneyChange(-Game.LEAVE_JAIL_COST, Bank.getInstance());
            setInJail(false);
        } else {
            turnsInJailCounter++;
        }
    }
    
    /**
     * Moves the player to jail and sets the in jail variable to true.
     */
    public void sendToJail() {
        //Move player to jail and collect no money.
        Game.getInstance().movePlayerToSpaceNumber(this,SpaceEnums.JAIL_NUMBER,
                false);
        this.setInJail(true);
    }
    
/**
 * Getter methods.
 */    
    /**
     * Returns the number of turns a player is in jail for.
     * @return Returns number of turns player is in jail for.
     */
    public int getTurnsInJail() {
        return turnsInJailCounter;
    }
    /**
     * Gets the number of GOJCs owned by the player.
     * @return number of GOJCs.
     */
    public int getNumberOfGetOutOfJailCards() {
        return getOutOfJailCards.size();
    }
    
    /**
     * Returns if the user is in jail or not.
     * @return true if the user is in jail.
     */
    public boolean inJail() {
        return inJail;
    }
    
    /**
     * Gets the amount of money the player has.
     * @return amount of money the player has.
     */
    public int getMoney () {
        return money;
    }
    
    /**
     * Gets number of utilities owned by the player.
     * @return number of utilities owned by the player.
     */
    public int getNumberOfUtilOwned() {
        return getNumberOfCardTypeOwned(Utility.class);
    }
    
    /**
     * Gets the number of stations the player owns.
     * @return number of stations owned by the player.
     */
    public int getNumberOfStationsOwned() {
        return getNumberOfCardTypeOwned(Station.class);
    }
    
    /**
     * Gets the number of properties owned of a particular class.
     * @param propertyType counts the number of properties of this type.
     * @return number of this card type owned.
     */
    public int getNumberOfCardTypeOwned(Class propertyType) {
        int cardTypes = 0;
        for (Space property : properties) {
            if (property.getClass().equals(propertyType)) {
                cardTypes++;
            }
        }
        return cardTypes;
    }
    
    /**
     * Gets the player's token number.
     * @return player's token number.
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Gets the player's current space.
     * @return the player's current space.
     */
    public Space getCurrentSpace() {
        return currentSpace;
    }
    
    /**
     * Gets a list of all 'Spaces' (properties) owned by the player.
     * @return list of sites owned by the player.
     */
    public List<Space> getProperties() {
        return properties;
    }
    
    /**
     * Gets all sites owned by the Player.
     * @return gets List of all sites owned by the player.
     */
    public List<Site> getSites() {
        List<Site> sites = new ArrayList();
        for (Space property : properties) {
            if (property.getClass().equals(Site.class)) {
                sites.add((Site)property);
            }
        }
        return sites;
    }
    
    /**
     * Gets the sites the player owns a Monopoly on.
     * @return List of sites where player owns all sites of that colour.
     */
    public List<Site> getMonopolySites() {
        List<Site> monopolySites = new ArrayList();
        for(Site site : getSites()) {
            if (SiteGroup.playerOwnsAMonopoly(site.getColorNumber())) {
                monopolySites.add(site);
            }
        }
        return monopolySites;
    }
    
    /**
     * Gets a list of sites which the player can build on.
     * @return List of sites which can be built on.
     */
    public List<Site> getSitesWhichCanBeBuiltOn() {
        List<Site> sites = new ArrayList();
        for (Space property : properties) {
            if (property.getClass().equals(Site.class)) {
                Site site = (Site)property;
                if (site.canBuildOnThisSite()) {
                    sites.add((Site)property);
                }
            }
        }
        return sites;
    }
    
    /**
     * Gets a lists of sites which this player can sell houses on.
     * @return List of sites which houses can be sold on.
     */
    public List<Site> getSitesWithHousesWhichCanBeSold() {
        List<Site> sitesWithHousesWhichCanBeSold = new ArrayList();
        for (Space property : properties) {
            if (property.getClass().equals(Site.class)) {
                Site site = (Site)property;
                if (site.canSellHouseOnSite()) {
                    sitesWithHousesWhichCanBeSold.add(site);
                }
            }
        }
        return sitesWithHousesWhichCanBeSold;
    }
     
    /**
     * Gets the properties currently mortgaged.
     * @return List of the currently mortgaged properties.
     */
    public List<Space> getMortgagedProperties() {
        List<Space> mortgagedProperties = new ArrayList();
        for (Space property : getProperties()) {
            if (property.isMortgaged()) {
                mortgagedProperties.add(property);
            }   
        }
        return mortgagedProperties;
    }
    
    /**
     * Returns a List of properties not already mortgaged.
     * @return List of properties that are currently active (not mortgaged).
     */
    public List<Space> getMortgagableProperties() {
        List<Space> propertiesToMortgage = new ArrayList();
        for (Space property : properties) {
            if (!property.isMortgaged() ) {
                propertiesToMortgage.add(property);
            }
        }
        return propertiesToMortgage;
    }
    
    /**
     * Returns a list of properties with houses on them.
     * @return list of properties with houses in them.
     */
    public List<Space> getPropertiesWithHouses() {
        List<Space> propertiesWithHouses = new ArrayList();
        for (Site site : this.getSites()) {
            //Propertys will be sites in this case.
            if (site.getHouses() > 0) {
                propertiesWithHouses.add(site);
            }
        }
        return propertiesWithHouses;
    }    
    
    public List<String> getPropertyNameStrings() {
        List<String> propertyNames = new ArrayList();
        for (Space space : this.getProperties()) {
            propertyNames.add(space.getName());
        }
        return propertyNames;
    }

/**
 * Setter methods.
 */    
    /**
     * Sets the players space to the newSpace.
     * @param newSpace for player to be at.
     */
    public void setSpace(Space newSpace) {
        this.currentSpace = newSpace;
    }
    
    /**
     * Sets the user in or out of jail.
     * @param goToJail true if the player should be in jail.
     */
    public void setInJail(boolean goToJail) {
        this.inJail = goToJail;
    }
    
    /**
     * Sets the player number to playerNumber.
     * @param playerNumber 
     */
    public void setNumber(int playerNumber) {
        this.number = playerNumber;
    }
    
    
}
