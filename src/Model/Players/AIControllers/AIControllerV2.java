package Model.Players.AIControllers;

import Model.Board;
import Model.DealOffer;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.Player;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Spaces.Site;
import Model.Spaces.SiteGroup;
import Model.Spaces.Space;
import java.util.List;

/**
 * The AI ControllerV2 uses a different player for each of its decisions.
 * I decided based on experience of playing the AI and bot which would be
 * better at handling which decision.
 * @author Chris Berry
 */
public class AIControllerV2 extends AIControllerV1{
    
    public AIControllerV2(int tokenNumber, Critic critic, 
            TDInputGenerator inputGen, Board board) {
        super(tokenNumber, critic, inputGen, board);
    }


    /**
     * Uses TD Player to assess whether to buy a property or not.
     * @param property
     * @return true if the player wants to buy the property.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        return tdPlayer.askPlayerIfTheyWantToBuyProperty(property);
    }

    /**
     * If the player owns a Monopoly then the bot makes a choice, otherwise
     * the player does.
     * @return the players choice.
     */
    @Override
    public int askPlayerInJailOptions() {
        if (doesPlayerOwnMonopoly()) {
            return bot.askPlayerInJailOptions();
        } else {
            return tdPlayer.askPlayerInJailOptions(); 
        }
        
    }
    
    /**
     * Returns true if the player owns a Monopoly. Otherwise returns false.
     * @return true if player owns a Monopoly.
     */
    public boolean doesPlayerOwnMonopoly() {
        for (int i = Board.BROWN; i <= Board.BLUE; i++) {
            if (SiteGroup.playerOwnsAMonopoly(i)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return true if player is one property off owning a Monopoly.
     * @return true if player is one property off owning a Monopoly.
     */
    public boolean isPlayerOneOffMonopoly() {
        for (int i = Board.BROWN; i <= Board.BLUE; i++) {
            if (SiteGroup.playerIsOneOffMonopoly(i, this)) {
                return true;
            }
        }
        return false;
    }

    /**
     * If player owns a Monopoly then the bot makes a move, otherwise the 
     * player does.
     * @return 
     */
    @Override
    public boolean askPlayerMoveOptions() {
        if (this.doesPlayerOwnMonopoly()) {
            return bot.askPlayerMoveOptions();
        } else {
            return tdPlayer.askPlayerMoveOptions();
        }
    }

    /**
     * If player is one off a Monopoly then the bot handles the deals,
     * otherwise the td player handles assessing the offer.
     * @param offer to assess
     * @param playerOffering player making the offer
     * @return true if the player accepts the offer.
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        if (this.isPlayerOneOffMonopoly()) {
            return bot.assessAnOffer(offer, playerOffering);
        } else {
            return tdPlayer.assessAnOffer(offer, playerOffering);
        }
    }

    /**
     * Asks the player to make a bid on a property. This is handled by the TD Player.
     * @param property to bid on.
     * @param minimumBid minimum amount the player can bid.
     * @param playersInTheRunning other players left in the bidding.
     * @return the amount the player wants to bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid, List<Player> playersInTheRunning) {
        return tdPlayer.makeABid(property, minimumBid, playersInTheRunning);
    }

    /**
     * TD Player decides whether to bid on a house or not (as the bot doesn't
     * do this anyway).
     * @param minBid minimum amount player can bid.
     * @param isHouse true if the player is bidding on a house, false if it is a hotel.
     * @param playersInTheRunning list of players in the running.
     * @return 
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse, List<Player> playersInTheRunning) {
        return tdPlayer.makeABidOnHouse(minBid, isHouse, playersInTheRunning);
    }

    /**
     * The bot decides what to do when the player receives a property.
     * @param properties 
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        bot.receiveProperty(properties);
    }

    /**
     * Asks the player to choose a property to build on. Handled by TD Player.
     * @return Site TD player chose to build on.
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        return tdPlayer.choosePropertyForBuildingOn();
    }

    /**
     * TD player handles mortgaging properties.
     */
    @Override
    public void mortgageProperties() {
        tdPlayer.mortgageProperties();
    }

    /**
     * Bot handles selling houses.
     */
    @Override
    public void sellHouses() {
        bot.sellHouses();
    }
    
    
    
}
