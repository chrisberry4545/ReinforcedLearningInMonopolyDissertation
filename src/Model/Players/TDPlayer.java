package Model.Players;

import Model.Bank;
import Model.Board;
import Model.Cards.Card;
import Model.DealOffer;
import Model.GameStats;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A TDPlayer in the game of Monopoly.
 * @author Chris Berry
 */
public class TDPlayer extends AI {
    
    protected Board board;
    protected Critic critic;
    //public static final int MONEY_MULTIPLYER_FOR_VALUE_OUTPUT = 1000;
    
    protected double[] previousInputs;
    
    
    private int movesMade = 0;
    private double randomChoiceChance = 0.95;
    private boolean randomMoveOn;
    protected TDInputGenerator inputGen;
    private static final int moveLimit = 1;

    private Player controllingPlayer;
    
    protected TDMoveEvaluator moveEvaluator;
    
    /**
     * Creates a new TDPlayer
     * @param setToken
     * @param currentBoard
     * @param critic
     * @param inputGen
     * @param randomMoves 
     */
    public TDPlayer(int setToken, Board currentBoard, 
            Critic critic, TDInputGenerator inputGen, boolean randomMoves) {
        //Sets up the player class with the appropriate token.
        super(setToken);
        board = currentBoard;
        this.critic = critic;
        this.inputGen = inputGen;
        this.inputGen.setBoard(board);
        
        if (this.inputGen.isUseDealSubsets()) {
            this.moveEvaluator = new TDMoveEvaluatorDealSubset(critic,inputGen,this);
        } else {
            this.moveEvaluator = new TDMoveEvaluator(critic,inputGen, this);
        }
        
        this.randomMoveOn = randomMoves;
        this.controllingPlayer = this;
    }
    
    /**
     * Sets up a version of TD Player with a controlling player.
     * @param setToken
     * @param currentBoard
     * @param critic
     * @param inputGen
     * @param randomMoves
     * @param controllingPlayer 
     */
    public TDPlayer(int setToken, Board currentBoard, 
            Critic critic, TDInputGenerator inputGen, boolean randomMoves,
            Player controllingPlayer) {
        //Sets up the player class with the appropriate token.
        super(setToken);
        board = currentBoard;
        this.critic = critic;
        this.inputGen = inputGen;
        this.inputGen.setBoard(board);
        this.controllingPlayer = controllingPlayer;
        
        if (this.inputGen.isUseDealSubsets()) {
            this.moveEvaluator = new TDMoveEvaluatorDealSubset(critic,inputGen,controllingPlayer);
        } else {
            this.moveEvaluator = new TDMoveEvaluator(critic,inputGen, controllingPlayer);
        }
        
        this.randomMoveOn = randomMoves;
    }
    
    

/**
 * Getter methods.
 */
    
    /**
     * Gets the move evaluator for this TD Player.
     * @return TDMoveEvaluator
     */
    public TDMoveEvaluator getMoveEvaluator() {
        return this.moveEvaluator;
    }
    
    /**
     * Updates the inputs for the player allowing them to learn.
     */
    private void updateInputs() {
        //If previous inputs are not null then there is an action that needs
        //to be evaluated based on the current results and the previous inputs.
        if (previousInputs != null) {
            critic.generalize(previousInputs, 
                moveEvaluator.getCurrentResultsArray(controllingPlayer.getNumber()));
            //Needs to be set back to null so the same thing isn't evaluated
            //twice.
            previousInputs = null;
            movesMade = 0;
        }
    }
    
/**
 * Methods used for deciding the actions of the player.
 */
    /**
     * Gets the player's response to what they intend to do on their turn.
     * @return players choice.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        //If previous inputs are not null then there is an action that needs
        //to be evaluated based on the current results and the previous inputs.
        if (previousInputs != null) {
            updateInputs();
            movesMade = 0;
        }
        
        //Only one move allowed per turn. Roll the diec after this.
        if (movesMade >= moveLimit) {
            previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
            return true;
        }
        
        //Add a random choice element to force the AI to explore all options
        boolean result;
        if (randomMoveOn && Math.random() > randomChoiceChance) {
            
            result = playRandomOption();
        } else {
            result = playOptionConsideredBest();
        }
        movesMade++;
        return result;
    }
    
    /**
     * Chooses a random option and does this. Used to force exploration in
     * the TD AI.
     * @return true if the AI wants to finish its turn, false otherwise.
     */
    private boolean playRandomOption() {
        int choiceNumber = randomChoice(6) - 1;
        switch (choiceNumber) {
            case -1 : //The case where the best choice is to roll the dice
                //as normal.
                previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
                return true;
            case 0 : //Add a house to the most valued property. 
                Map.Entry<Double, Site> propertyMostWorthBuyingHousesOn =
                        moveEvaluator.getPropertyMostWorthBuyingHouseOn(controllingPlayer);
                if (propertyMostWorthBuyingHousesOn != null) {
                    //If null then there are no properties which you can build
                    //houses on so another option should be chosen.
                    controllingPlayer.addHouse(propertyMostWorthBuyingHousesOn.getValue());
                }
                break;
            case 1 : //Make an offer to another player.
                Map.Entry<Double, Map.Entry<Player, DealOffer> >
                    offerMostWorthMaking 
                        = this.getOfferMostWorthMaking();
                //Null if there is no offer than can be made.
                if (offerMostWorthMaking != null) {
                    makeAnOfferToAnotherPlayer(
                            offerMostWorthMaking.getValue().getValue(),
                            offerMostWorthMaking.getValue().getKey());
                }
                break;
            case 2 : //Mortgage the property with the highest value from mortaging.
                Map.Entry<Double, Space> propertyMostWorthMortgaging =
                        this.getPropertyMostWorthMortgaging();
                if (propertyMostWorthMortgaging != null) {
                    controllingPlayer.mortgageProperty(propertyMostWorthMortgaging.getValue());
                }
                break;
            case 3 : //Sell house on the property where it would have most value.
                Map.Entry<Double, Site> propetyMostWorthSellingHousesOn =
                        moveEvaluator.getPropertyMostWorthSellingHouseOn();
                if (propetyMostWorthSellingHousesOn != null) {  
                    controllingPlayer.returnHouse(propetyMostWorthSellingHousesOn.getValue(), 1);
                }
                break;
            case 4 : //UnMortgage a property which gives the highest value.
                Map.Entry<Double,Space> propertyMostWothUnMortgaging = 
                        moveEvaluator.getPropertyMostWorthUnMortgaging();
                if (propertyMostWothUnMortgaging != null) {
                    controllingPlayer.unmortgageProperty(propertyMostWothUnMortgaging.getValue());
                }
                break;
        }
        return false;
    }
    
    /**
     * The AI plays the option it considers will maximise its own reward.
     * @return true if the player has finished, false if the AI still wants to
     * make other moves.
     */
    private boolean playOptionConsideredBest() {
        //Process each of the players possible moves and see if it is worth
        //carrying them out.
        //Check if buying any houses is worth it.
        Map.Entry<Double, Site> houseMostWorthBuying 
                = moveEvaluator.getPropertyMostWorthBuyingHouseOn(controllingPlayer);
        
        //Check if offering a property is worth it.
        //Player is only allowed to make one deal a turn.
        Map.Entry<Double, Map.Entry<Player, DealOffer> >offerMostWorthMaking 
                = this.getOfferMostWorthMaking();
       
        //Check if mortgaging a property is worth it.
        Map.Entry<Double, Space> spaceMostWorthMortgaging 
                = this.getPropertyMostWorthMortgaging();
        
        //Check if selling a house is worth it.
        Map.Entry<Double, Site> houseMostWorthSelling 
                = moveEvaluator.getPropertyMostWorthSellingHouseOn();
        
        //Check if buying back a mortgaged property is worth it.
        Map.Entry<Double, Space> spaceMostWorthUnMortgaging 
                = moveEvaluator.getPropertyMostWorthUnMortgaging();
        
        //Compare these results and find the best option.
        List<Double> choiceList = new ArrayList();
        choiceList = addKeyOrBadOutputNo(houseMostWorthBuying, choiceList);
        choiceList = addKeyOrBadOutputNo(offerMostWorthMaking, choiceList);
        choiceList = addKeyOrBadOutputNo(spaceMostWorthMortgaging, choiceList);
        choiceList = addKeyOrBadOutputNo(houseMostWorthSelling, choiceList);
        choiceList = addKeyOrBadOutputNo(spaceMostWorthUnMortgaging, choiceList);
        
        //Check if doing nothing provides the best output
        double bestChoice = moveEvaluator.getThisPlayersResults();
        int choiceNumber = -1;
        for (int i = 0; i < choiceList.size(); i++) {
            //If the next choice is better than the current bestChoice then
            //that choice becomes the best choice.
            if (choiceList.get(i) > bestChoice) {
                bestChoice = choiceList.get(i);
                choiceNumber = i;
            }
        }
        switch (choiceNumber) {
            case -1 : //The case where the best choice is to roll the dice
                //as normal.
                previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
                return true;
            case 0 : //Add a house to the most valued property. 
                controllingPlayer.addHouse(houseMostWorthBuying.getValue());
                break;
            case 1 : //Make an offer to another player.
                makeAnOfferToAnotherPlayer(
                        offerMostWorthMaking.getValue().getValue(),
                        offerMostWorthMaking.getValue().getKey());
                break;
            case 2 : //Mortgage the property with the highest value from mortaging.
                controllingPlayer.mortgageProperty(spaceMostWorthMortgaging.getValue());
                break;
            case 3 : //Sell house on the property where it would have most value.
                controllingPlayer.returnHouse(houseMostWorthSelling.getValue(), 1);
                break;
            case 4 : //UnMortgage a property which gives the highest value.
                controllingPlayer.unmortgageProperty(spaceMostWorthUnMortgaging.getValue());
                break;
        }
        return false;
    }
    
    /**
     * Randomly picks a number between 0 to the numberOfOptions.
     * @param numberOfOptions to pick between.
     * @return the number chosen randomly.
     */
    private int randomChoice(int numberOfOptions) {
        Random random = new Random();
        int choiceNumber = random.nextInt(numberOfOptions);
        return choiceNumber;
    }
    
    /**
     * Asks player to choose how they will act when they are in jail.
     * @return players choice.
     */
    @Override
    public int askPlayerInJailOptions() {
        List<Double> resultsOfMove = new ArrayList();
        updateInputs();
        //Establish the 3 options.
        //Results of doing nothing
        double currentResults 
                = moveEvaluator.getThisPlayersResults();
        resultsOfMove.add(currentResults);
        //Use a get out of jail card.
        Card getOutofJailCard = controllingPlayer.useGetOutOfJailCard();
        if (getOutofJailCard != null) {
            double usedGetOutOfJailCard 
                    = moveEvaluator.getThisPlayersResults();
            resultsOfMove.add(usedGetOutOfJailCard);
            //Resets it back to normal.
            controllingPlayer.reverseGetOutOfJailCard(getOutofJailCard);
        } else {
            resultsOfMove.add(TDInputGenerator.BAD_OUTPUT_NUM);
        }
        //Pay the $50.
        if (controllingPlayer.optionalMoneyChange(-50)) {
            controllingPlayer.setInJail(false);
            double payToGetOut 
                    = moveEvaluator.getThisPlayersResults(); 
            resultsOfMove.add(payToGetOut);
            //Need to reset this once the results have been got.
            controllingPlayer.optionalMoneyChange(50);
            controllingPlayer.setInJail(true);
        } else {
            resultsOfMove.add(TDInputGenerator.BAD_OUTPUT_NUM);
        }
        //Try adding a house.
        Map.Entry<Double,Site> bestSiteForHouse 
                = moveEvaluator.getPropertyMostWorthBuyingHouseOn(controllingPlayer);
        //Only if this is the best choice.
        if (bestSiteForHouse != null) {
            resultsOfMove.add(
                moveEvaluator.getResultsOfBuyingHouse
                    (bestSiteForHouse.getValue(), controllingPlayer, controllingPlayer));
        } else {
            resultsOfMove.add(TDInputGenerator.BAD_OUTPUT_NUM);
        }
        
        //Check if offering a property is worth it.
        //Player is only allowed to make one deal a turn.
        Map.Entry<Double, Map.Entry<Player, DealOffer> >offerMostWorthMaking 
                = this.getOfferMostWorthMaking();
        if (offerMostWorthMaking != null) {
            resultsOfMove.add(offerMostWorthMaking.getKey());
        } else {
            resultsOfMove.add(TDInputGenerator.BAD_OUTPUT_NUM);
        }
        /**
         * Selects and returns the appropriate action for the player based
         * on which of the avaliable results gave the best probability of 
         * winning.
         */
        switch(moveEvaluator.pickFromResults(resultsOfMove).getKey()) {
            //Roll to leave.
            case 0 : 
                int diceRoll = controllingPlayer.rollToLeaveJail();
                previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
                //Returns -1 if a double isn't rolled.
                if (diceRoll == -1) {
                    return Player.STILL_IN_JAIL_TURN_OVER;
                } else {
                    return diceRoll;
                }
            //Use get out of jail card.
            case 1 : 
                if (controllingPlayer.useGetOutOfJailCard() != null) {
                    previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
                    return Player.rollDice();
                } else {
                    //Don't have a get out of jail card.
                    return Player.STILL_IN_JAIL_TURN_NOT_OVER;
                }
            //Pay to leave jail.
            case 2 : 
                if (controllingPlayer.payToLeaveJail()) {
                    previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
                    return Player.rollDice();
                } else {
                    return Player.STILL_IN_JAIL_TURN_NOT_OVER;
                }
            //If buying a house is the best option add a house to the site
            //where it would be most valuable.
            case 3 : 
                controllingPlayer.addHouse(bestSiteForHouse.getValue());
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
            //Player's best option is to make a deal.
            case 4 :
                makeAnOfferToAnotherPlayer(
                        offerMostWorthMaking.getValue().getValue(),
                        offerMostWorthMaking.getValue().getKey());
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
            //No valid action selected.
                
            default : 
                System.err.println("No valid option selected from jail for"
                        + " TD AI " + controllingPlayer.getNumber());
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
            
        }
    }
    
    /**
     * Ask player if they want to buy a specific property.
     * @param property to ask about.
     * @return true if the user accepts the proposal, otherwise false.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        //A list used to store all results.
        List<Double> allResults = new ArrayList();
        //Gets a copy of the results of not doing anything.
        double resultsWithoutBuying =  moveEvaluator.getThisPlayersResults();
        allResults.add(resultsWithoutBuying);
        //Gets a copy of the results when the property is bought.
        allResults.add(
                moveEvaluator.getResultsOfBuyingProperty(property, property.getIntialCost(),
                controllingPlayer, controllingPlayer));
        //If the best result is 0 (the first one), it is best for the player
        //not to buy the property. Else if it is the second one, it is best
        //for them to buy the property.
        switch(moveEvaluator.pickFromResults(allResults).getKey()) {
            case 0 : return false;
            case 1 :
                controllingPlayer.buyProperty(property);
                changePreviousInputsToCurrentInputs();
                return true;
                //There are only two options so default should never be reached
                //except in the case of an error.
            default : System.err.println("invalid option selected when"
                    + " player asked if they want to buy the property.");
                    return false;
        }
    }
    
    /**
     * Alerts the TD Player they have bought a property so the last set of
     * inputs can be updated.
     */
    public void changePreviousInputsToCurrentInputs() {
        this.previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
    }
    
    public Map.Entry<Double, Map.Entry<Player, DealOffer> > getOfferMostWorthMaking() {
        return this.moveEvaluator.getOfferMostWorthMaking();
    }
    
    public Map.Entry<Double, Space> getPropertyMostWorthMortgaging() {
        return this.moveEvaluator.getPropertyMostWorthMortgaging();
    }
    
     /**
     * Assesses an offer made by another player and accepts it if the AI
     * believes it is an acceptable offer.
     * @param offer made by the other player.
     * @param playerOffering the player making the offer.
     * @return true if player accepts the offer.
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        List<Double> results = new ArrayList();
        //Adds the results of not accepting the offer to the list.
        results.add(moveEvaluator.getThisPlayersResults());
        //Adds the results of accepting the offer to the list.
        results.add(moveEvaluator.getValueOfOfferForThisPlayer(offer, controllingPlayer, playerOffering));
        if (moveEvaluator.pickFromResults(results).getKey() == 1) {
            //If the best option is accepting the offer then accept it.
            controllingPlayer.acceptDealFromPlayer(offer, playerOffering);
            changePreviousInputsToCurrentInputs();
            GameStats.addDealAccepted(controllingPlayer.getNumber(), offer);
            return true;
        }
        //If the offer isn't worth accepting then nothing needs to happen.
        return false;
    }
    
    /**
     * Note: for the time being offers won't include get out of jail cards.
     * Note: at the moment, only monetary offers are made.
     */
    public void makeAnOfferToAnotherPlayer(DealOffer dealOffer,
            Player playerToMakeOfferTo) {
        playerToMakeOfferTo.assessAnOffer(dealOffer, controllingPlayer);
        GameStats.addDealOffered(controllingPlayer.getNumber(), dealOffer);
    }
    
    /**
     * The player is asked how much they want to bid for a property. The TD
     * Player examines the results of him and other players winning the property
     * for the next bid increment amount. If a better result is provided by
     * the AI bidding on the property, it does so, otherwise it leaves the
     * bidding process.
     * @param property to bid on.
     * @param minimumBid minimum amount the player can bid.
     * @param playersInTheProcess other players in the bidding process.
     * @return the amount the player wants to bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid,
            List<Player> playersInTheProcess) {
        int newMinBid = minimumBid + TDMoveEvaluator.CASH_INCREMENTS;
        //Leave the bidding process if the player hasn't got enough to make
        //a bid.
        if (newMinBid > controllingPlayer.getMoney()) {
            return Player.LEAVE_BIDDING_PROCESS;
        }
        double resultsOfThisPlayerBuying = 
                moveEvaluator.getResultsOfBuyingProperty(property, newMinBid, 
                controllingPlayer, controllingPlayer);
        //Gets the average result of other players buying.
        double totalResultOfOthersBuying = 0;
        for (Player player : playersInTheProcess) {
            totalResultOfOthersBuying = totalResultOfOthersBuying + 
                    moveEvaluator.getResultsOfBuyingProperty(property, newMinBid, player,
                    controllingPlayer);
        }
        
        double averageValueOfOthersBuying = totalResultOfOthersBuying / playersInTheProcess.size();
        
        //If the result of this player buying is greater than the average
        //amount for the other players getting the property, then this player
        //should bid.
        if (resultsOfThisPlayerBuying > averageValueOfOthersBuying) {
            return newMinBid; 
        }
        return Player.LEAVE_BIDDING_PROCESS;  
    }
    
    /**
     * The AI considers the value of owning another house/hotel on one of its
     * properties. If this is more than the minimum bid the AI will place a bid
     * equal to this on the property.
     * @param minBid minimum amount that can be bid
     * @param isHouse true if bidding on a house, false if its a hotel.
     * @param playersInProcess a List of the players still in the bidding process.
     * @return The amount to bid
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse, 
            List<Player> playersInProcess) {
        int newMinBid = minBid + TDMoveEvaluator.CASH_INCREMENTS;
        //Leave the bidding process if the player hasn't got enough to make
        //a bid.
        if (newMinBid > controllingPlayer.getMoney()) {
            return Player.LEAVE_BIDDING_PROCESS;
        }
        //If player doesn't own any propertys worth buying a property on there
        //is no reason for this to happen.
        double currentPlayerResults 
                = moveEvaluator.getThisPlayersResults();
        Map.Entry<Double, Site> propertyMostWorthBuyingHouseOn =
                moveEvaluator.getPropertyMostWorthBuyingHouseOn(controllingPlayer);
        if (propertyMostWorthBuyingHouseOn != null &&
                propertyMostWorthBuyingHouseOn.getKey() > currentPlayerResults) {
            Site bestSiteToBuildOn = propertyMostWorthBuyingHouseOn.getValue();
            double resultsOfThisPlayerBuyingHouse = moveEvaluator
                    .getResultsOfBuyingHouse(bestSiteToBuildOn, 
                    controllingPlayer, controllingPlayer);
            
            
            double totalResultOfOthersBuying = 0;
            for (Player player : playersInProcess) {
                Map.Entry<Double, Site> otherPlayersBestSiteToBuildOn 
                            = moveEvaluator.getPropertyMostWorthBuyingHouseOn(player);
                if (otherPlayersBestSiteToBuildOn != null) {
                    Site otherPlayersBestSite = otherPlayersBestSiteToBuildOn.getValue();
                    double resultsOfOtherPlayerGettingHouse 
                            = moveEvaluator.getResultsOfBuyingHouse
                            (otherPlayersBestSite, player, controllingPlayer);
                    totalResultOfOthersBuying = totalResultOfOthersBuying + 
                            resultsOfOtherPlayerGettingHouse;
                }
            }

            double averageValueOfOthersBuying = totalResultOfOthersBuying / playersInProcess.size();

            //If the result of this player buying is greater than the average
            //amount for the other players getting the property, then this player
            //should bid.
            if (resultsOfThisPlayerBuyingHouse > averageValueOfOthersBuying) {
                return newMinBid; 
            }
        }
        return Player.LEAVE_BIDDING_PROCESS;  
    }
    
    /**
     * The AI picks between paying the mortgage + interest or just paying the
     * interest.
     * @param properties 
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        //Compare value of paying mortgage and interest to that of just paying
        //the interest for each property.
        for (Space property : properties) {
            property.setOwner(controllingPlayer);
            List<Double> unmortgageOrNot = new ArrayList();
            //Add results of unmortgaging the property.
            unmortgageOrNot.add(
                    moveEvaluator.getResultsOfUnMortgagingProperty(property,controllingPlayer));
            //Otherwise only the mortgage repayment rate is payed.
            controllingPlayer.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                    Bank.getInstance());
            unmortgageOrNot.add(moveEvaluator.getThisPlayersResults());
            //Set inputs back.
            controllingPlayer.optionalMoneyChange(property.getMortgageRepaymentRate());
            if (moveEvaluator.pickFromResults(unmortgageOrNot).getKey() == 0) {
                //0 means chosen to pay for property.
                controllingPlayer.unmortgageProperty(property);
            } else {
                controllingPlayer.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                        Bank.getInstance());
            }
        }
        changePreviousInputsToCurrentInputs();
    }
    
    /**
     * The AI selects the property most worth building a house on.
     * @return Site most worth building a house on.
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        return moveEvaluator.getPropertyMostWorthBuyingHouseOn(controllingPlayer).getValue();
    }
    
    /**
     * Mortgages the property seen by the AI as that which is most worth
     * mortgaging. Used when the AI is forced to mortgage some properties.
     */
    @Override
    public void mortgageProperties() {
        //If there are mortgagable properties avaliable.
        if (!controllingPlayer.getMortgagableProperties().isEmpty()) {
            controllingPlayer.mortgageProperty(this.getPropertyMostWorthMortgaging()
                    .getValue());
        }
    }
    
     /**
     * Sells a house on the property most worth selling a house on.
     */
    @Override
    public void sellHouses() {
        //Should only happen if the player has houses they can sell.
        if (!controllingPlayer.getSitesWithHousesWhichCanBeSold().isEmpty()) {
            controllingPlayer.returnHouse(moveEvaluator.getPropertyMostWorthSellingHouseOn()
                    .getValue(), 1);
        } else {
            System.err.println("The player doesn't have any houses to sell");
        }
    }
    
     /**
     * TD Player updates the network with the appropriate win values.
     */
    @Override
    public void hasWon() {
        confirmInputsAreNonNull();
        GameStats.addWinningValue(controllingPlayer.getValueOfAllAssets());
        critic.generalize(previousInputs, true);
    }
    
    /**
     * Informs the other players they have lost and generalizes appropriately.
     */
    @Override
    public void hasLost() {
        confirmInputsAreNonNull();
        GameStats.addLoosingValue(controllingPlayer.getValueOfAllAssets());
        critic.generalize(previousInputs, false);
    }
    
    private void confirmInputsAreNonNull() {
        if (previousInputs == null) {
            previousInputs = inputGen.getCurrentInputs(controllingPlayer.getNumber());
        }
    }

    
/**
 * Private methods for ease of use.
 */
    /**
     * As I still need to populate the lists used in the askPlayerMoveOptions()
     * method, this adds the badOutputNumber to the list when the entry is null.
     * This situation occurs when the current results are better than the 
     * results of attempting an option and so the AI shouldn't perform them.
     * The entry results are added to the list.
     * @param entry to add to list.
     * @param list to add to.
     * @return A List version of the results.
     */
    private List<Double> addKeyOrBadOutputNo(Map.Entry entry,
            List<Double> list) {
        if (entry != null) {
            if (entry.getKey().getClass().equals(Double.class)) {
                Double d = (Double)entry.getKey();
                list.add(d);
            } else {
                System.err.println("Bad use of addKeyOrBadOutputNo method "
                        + "in TD Player. Should be a map entry with a double "
                        + "key value.");
                System.exit(0);
            }
        } else {
            list.add(TDInputGenerator.BAD_OUTPUT_NUM);
        }
        return list;
    }
    
}