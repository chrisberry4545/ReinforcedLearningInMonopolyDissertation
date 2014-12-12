package Model.Players.AIControllers;

import Model.Board;
import Model.DealOffer;
import Model.Game;
import Model.Players.AI;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.Player;
import Model.Players.SmartBot;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Players.TDPlayer;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.EmptyStackException;
import java.util.List;

/**
 * This AI aims to combine elements of the SmartBot and TDPlayer to produce
 * an altogether better AI.
 * @author Chris Berry
 */
public class AIControllerV1 extends AI{
    
    protected TDPlayer tdPlayer;
    protected SmartBot bot;
    
    private final int roundsBeforeSwap = 65;
    
    /**
     * Creates a new AIController.
     * @param tokenNumber player token number.
     * @param critic for the player to use.
     * @param inputGen for the player to use.
     * @param board which the player will be playing on.
     */
    public AIControllerV1(int tokenNumber, Critic critic, 
            TDInputGenerator inputGen, Board board) {
        super(tokenNumber);
        boolean useRandomMoves = false;
        critic.setBlockLearning(true);
        this.bot = new SmartBot(tokenNumber, this);
        this.tdPlayer = new TDPlayer(tokenNumber, board, critic, inputGen,
               useRandomMoves, this);
    }
    
    /**
     * Checks if the swap conditions are met (in this case if the amount
     * of rounds played have exceeded a certain value). If this happens
     * then the bot will take over making decisions.
     * @return 
     */
    private boolean swapConditionMet() {
        checkPlayersAreStillEmpty();
        if (Game.getInstance().getRoundCount() > roundsBeforeSwap) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * A check just to test the child players are not gaining any property
     * or money as this should all be handled by this class now.
     */
    private void checkPlayersAreStillEmpty() {
        if (!tdPlayer.getProperties().isEmpty()) {
            System.err.println("TD player has properties");
            throw new EmptyStackException();
        }
        if (tdPlayer.getMoney() != 0) {
            System.err.println("TD player has " + tdPlayer.getMoney() + " money");
            throw new EmptyStackException();
        }
        if (!bot.getProperties().isEmpty()) {
            System.err.println("bot player has properties");
            throw new EmptyStackException();
        }
        if (bot.getMoney() != 0) {
            System.err.println("bot player has " + tdPlayer.getMoney() + " money");
            throw new EmptyStackException();
        }
        
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @param property to ask player if they want to buy.
     * @return true if the player buys the property.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        if (swapConditionMet()) {
            return bot.askPlayerIfTheyWantToBuyProperty(property);
        } else {
            return tdPlayer.askPlayerIfTheyWantToBuyProperty(property);
        }
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @return the players response code.
     */
    @Override
    public int askPlayerInJailOptions() {
        if (swapConditionMet()) {
            return bot.askPlayerInJailOptions();
        } else {
            return tdPlayer.askPlayerInJailOptions();
        }
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @return true if the players turn is over.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        if (swapConditionMet()) {
            return bot.askPlayerMoveOptions();
        } else {
            return tdPlayer.askPlayerMoveOptions();
        }
        
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @param offer to assess.
     * @param playerOffering player making the offer.
     * @return true if the player has accepted the offer.
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        if (swapConditionMet()) {
            return bot.assessAnOffer(offer, playerOffering);
        } else {
            return tdPlayer.assessAnOffer(offer, playerOffering);
        }
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @param property to make a bid on.
     * @param minimumBid minimum amount the player can bid.
     * @param playersInTheRunning List of players still in running.
     * @return amount player wants to bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid, List<Player> playersInTheRunning) {
        if (swapConditionMet()) {
            return bot.makeABid(property, minimumBid, playersInTheRunning);
        } else {
            return tdPlayer.makeABid(property, minimumBid, playersInTheRunning);
        }
    }

    /**
     * TD player makes bids on houses as this isn't implemented in the bot 
     * (it is a very rare occurance anyway).
     * @param minBid Minimum bid for the player
     * @param isHouse true if bidding on a house, false if its a hotel.
     * @param playersInTheRunning list of the players still in the running.
     * @return 
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse, List<Player> playersInTheRunning) {
        return bot.makeABidOnHouse(minBid, isHouse, playersInTheRunning);
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @param properties player must decide what to do with.
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        if (swapConditionMet()) {
            bot.receiveProperty(properties);
        } else {
            tdPlayer.receiveProperty(properties);
        }
    }

    /**
     * TD player handles this as bot does nothing in the situation.
     */
    @Override
    public void hasWon() {
        tdPlayer.hasWon();
    }

    /**
     * TD player handles this as bot does nothing in the situation.
     */
    @Override
    public void hasLost() {
        tdPlayer.hasLost();
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     * @return the Site the player chooses to build on.
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        if (swapConditionMet()) {
            return bot.choosePropertyForBuildingOn();
        } else {
            return tdPlayer.choosePropertyForBuildingOn();
        }
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     */
    @Override
    public void mortgageProperties() {
        if (swapConditionMet()) {
            bot.mortgageProperties();
        } else {
            tdPlayer.mortgageProperties();
        }
    }

    /**
     * If the swap conditions are met then the bot handles this condition,
     * otherwise the TDPlayer handles it.
     */
    @Override
    public void sellHouses() {
        if (swapConditionMet()) {
            bot.sellHouses();
        } else {
            tdPlayer.sellHouses();
        }
    }
    
    
    
}
