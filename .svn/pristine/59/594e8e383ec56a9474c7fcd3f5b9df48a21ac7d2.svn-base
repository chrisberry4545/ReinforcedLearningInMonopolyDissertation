package Model.Players.AIControllers;

import Model.Board;
import Model.DealOffer;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.Player;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.List;

/**
 * The AIControllerV3 can have the bot or TD player make any decision. By
 * default the TD Player makes all decisions. After testing the bot on
 * each of these decisions I was able to make AIControllerV3_1 which uses
 * the decisions which produced the best result.
 * @author Chris Berry
 */
public class AIControllerV3 extends AIControllerV1{
    
    private boolean botDecidesToBuyProperties = true;
    private boolean botHandlesInJailOptions = true;
    private boolean botHandlesNormalMove = true;
    private boolean botAssessesOffers = true;
    private boolean botMakesBids = true;
    private boolean botHandlesReceiveingProperty = true;
    private boolean botChoosesPropertyForBuildingOn = true;
    private boolean botHandlesMortgagingProperties = true;
    private boolean botHandlesSellingHouses = true;
    
    public static final int MAX_MODE_NUMBER = 9;
    
    /**
     * Creates a new AIControllerV3
     * @param tokenNumber player number to use.
     * @param critic to use.
     * @param inputGen to use.
     * @param board which the player will be playing on.
     */
    public AIControllerV3(int tokenNumber, Critic critic, 
            TDInputGenerator inputGen, Board board) {
        super(tokenNumber, critic, inputGen, board);
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @param property that player is asked if they want to buy.
     * @return true if the player buys the property, otherwise false.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        if (botDecidesToBuyProperties) {
            return bot.askPlayerIfTheyWantToBuyProperty(property);
        } else {
            return tdPlayer.askPlayerIfTheyWantToBuyProperty(property);
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @return the code for the player's decision.
     */
    @Override
    public int askPlayerInJailOptions() {
        if (botHandlesInJailOptions) {
            return bot.askPlayerInJailOptions();
        } else {
            return tdPlayer.askPlayerInJailOptions();
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @return true if the player has finished their turn, otherwise false.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        if (botHandlesNormalMove) {
            return bot.askPlayerMoveOptions();
        } else {
            return tdPlayer.askPlayerMoveOptions();
        }
        
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @param offer for player to assess
     * @param playerOffering player making the offer
     * @return 
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        if (botAssessesOffers) {
            return bot.assessAnOffer(offer, playerOffering);
        } else {
            return tdPlayer.assessAnOffer(offer, playerOffering);
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @param property for player to bid on
     * @param minimumBid minimum amount player can bid
     * @param playersInTheRunning List of players in the auction process.
     * @return amount player wants to bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid, List<Player> playersInTheRunning) {
        if (botMakesBids) {
            return bot.makeABid(property, minimumBid, playersInTheRunning);
        } else {
            return tdPlayer.makeABid(property, minimumBid, playersInTheRunning);
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @param properties the player has to decide what to do with.
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        if (botHandlesReceiveingProperty) {
            bot.receiveProperty(properties);
        } else {
            tdPlayer.receiveProperty(properties);
        }
    }
    
    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     * @return property player choose to build on.
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        if (botChoosesPropertyForBuildingOn) {
            return bot.choosePropertyForBuildingOn();
        } else {
            return tdPlayer.choosePropertyForBuildingOn();
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     */
    @Override
    public void mortgageProperties() {
        if (botHandlesMortgagingProperties) {
            bot.mortgageProperties();
        } else {
            tdPlayer.mortgageProperties();
        }
    }

    /**
     * The decisions is made by bot if the variable is set to true, else
     * the TD Player makes the decisions.
     */
    @Override
    public void sellHouses() {
        if (botHandlesSellingHouses) {
            bot.sellHouses();
        } else {
            tdPlayer.sellHouses();
        }
    }
    
    /**
     * Sets the mode for the AI, for each mode the bot covers a different choice
     * the player may be asked to face.
     * @param mode to put the AI in.
     */
    public void setMode(int mode) {
        switch (mode) {
            case 0 :
                break;
            case 1 :
                setBotDecidesToBuyProperties(false);
                break;
            case 2 :
                setBotHandlesInJailOptions(false);
                break;
            case 3 :
                setBotHandlesNormalMove(false);
                break;
            case 4 :
                setBotAssessesOffers(false);
                break;
            case 5 : setBotMakesBids(false);
                break;
            case 6 : setBotHandlesReceiveingProperty(false);
                break;
            case 7 : setBotChoosesPropertyForBuildingOn(false);
                break;
            case 8 : setBotHandlesMortgagingProperties(false);
                break;
            case 9 : setBotHandlesSellingHouses(false);
                break;
        }
    }

    /**
     * Sets whether the bot will decide to buy properties.
     * @param botDecidesToBuyProperties 
     */
    public void setBotDecidesToBuyProperties(boolean botDecidesToBuyProperties) {
        this.botDecidesToBuyProperties = botDecidesToBuyProperties;
    }

    /**
     * Sets whether the bot will handle in jail options.
     * @param botHandlesInJailOptions 
     */
    public void setBotHandlesInJailOptions(boolean botHandlesInJailOptions) {
        this.botHandlesInJailOptions = botHandlesInJailOptions;
    }

    /**
     * Sets whether the bot will handle normal moves.
     * @param botHandlesNormalMove 
     */
    public void setBotHandlesNormalMove(boolean botHandlesNormalMove) {
        this.botHandlesNormalMove = botHandlesNormalMove;
    }

    /**
     * Sets whether the bot will decide to buy properties.
     * @param botAssessesOffers 
     */
    public void setBotAssessesOffers(boolean botAssessesOffers) {
        this.botAssessesOffers = botAssessesOffers;
    }

    /**
     * Sets whether the bot will decide to make bids.
     * @param botMakesBids 
     */
    public void setBotMakesBids(boolean botMakesBids) {
        this.botMakesBids = botMakesBids;
    }

    /**
     * Sets whether the bot will handle receiving properties.
     * @param botHandlesReceiveingProperty 
     */
    public void setBotHandlesReceiveingProperty(boolean botHandlesReceiveingProperty) {
        this.botHandlesReceiveingProperty = botHandlesReceiveingProperty;
    }

    /**
     * Sets whether the bot will choose properties for building on.
     * @param botChoosesPropertyForBuildingOn 
     */
    public void setBotChoosesPropertyForBuildingOn(boolean botChoosesPropertyForBuildingOn) {
        this.botChoosesPropertyForBuildingOn = botChoosesPropertyForBuildingOn;
    }

    /**
     * Sets whether the bot will handle mortgaging properties.
     * @param botHandlesMortgagingProperties 
     */
    public void setBotHandlesMortgagingProperties(boolean botHandlesMortgagingProperties) {
        this.botHandlesMortgagingProperties = botHandlesMortgagingProperties;
    }

    /**
     * Sets whether the bot will handle selling houses.
     * @param botHandlesSellingHouses 
     */
    public void setBotHandlesSellingHouses(boolean botHandlesSellingHouses) {
        this.botHandlesSellingHouses = botHandlesSellingHouses;
    }
    
    
    
    
    
}
