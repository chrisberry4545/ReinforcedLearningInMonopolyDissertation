package Model.Cards;

import Model.Board;
import Model.Game;
import Model.Players.Player;
import Model.Spaces.Space;
import Model.Spaces.SpaceEnums;
import Model.Spaces.Station;

/**
 * The action for the various 'Move to nearest property' Cards from the 
 * Game of Monopoly.
 * @author Chris Berry
 */
public class NearestProperty extends AbstractCardAction {
    
    private boolean isUtility;
    private int utilityMultiplyer = 10;
    private int stationMultiplier = 2;
    private boolean passGoMoney;
    
    /**
     * Creates a default card action sending players to the nearest utility
     * or station.
     * @param collectGoMoney true if the player should receive money for passing
     * go while moving.
     * @param utility true if the player should be moving to a utility, false
     * if he should go to a station.
     */
    public NearestProperty(boolean collectGoMoney, boolean utility) {
        this.passGoMoney = collectGoMoney;
        this.isUtility = utility;
    }

/**
 * Key methods.
 */    
    /**
     * Finds the nearest forward utility or station to the player and 
     * moves the player to this point. If the utility/station is owned
     * by another player, the player will have to pay a larger amount.
     * @param player to move.
     * @param card for which the action is being called from.
     */
    @Override
    public void performAction(Player player, Card card) {
        if (isUtility) {
            handleUtilityCard(player);
        //Else it's the nearest station.
        } else {
            handleStationCard(player);
        }
    }
    
    /**
     * Handles the movement of the player to the nearest utility and the 
     * resulting actions when the utility version of the card is drawn.
     * @param player 
     */
    private void handleUtilityCard(Player player) {
        int spaceNum = player.getCurrentSpace().getSpaceNumber();
        int newSpace;
        int spacesToWater = SpaceEnums.WATER_COMPANY_NUMBER - spaceNum;
        int spacesToElec = SpaceEnums.ELECTRIC_COMPANY_NUMBER - spaceNum;
        //Less than 0 player has already passed it.
        if (spacesToElec <= 0 && spacesToWater > 0) {
            newSpace = SpaceEnums.WATER_COMPANY_NUMBER;
        } else {
            newSpace = SpaceEnums.ELECTRIC_COMPANY_NUMBER;
        }
        Board board = Game.getInstance().getBoard();
        Space property = board.get(newSpace);
        //If owned then need to pay the player ten times amount thrown.
        int amountOwed = Player.rollDice() * utilityMultiplyer;
        completeAction(newSpace, player, amountOwed, property);
    }
    
    /**
     * Handles the movement of the player and the actions that need to occur
     * when the player draws the station version of the card.
     * @param player which drew the card.
     */
    private void handleStationCard(Player player) {
        int spaceNum = player.getCurrentSpace().getSpaceNumber();
        int newSpace;
        int spacesToKingsX = SpaceEnums.KINGS_CROSS_NUMBER - spaceNum;
        int spacesToMarylebone = SpaceEnums.MARYLEBONE_NUMBER - spaceNum;
        int spacesToFenchurch = SpaceEnums.FENCHURCH_NUMBER - spaceNum;
        int spacesToLiverpoolSt = SpaceEnums.LIVERPOOL_ST_NUMBER - spaceNum;
        if (spacesToKingsX <= 0 && spacesToMarylebone > 0) {
            newSpace = SpaceEnums.MARYLEBONE_NUMBER;
        } else {
            if (spacesToMarylebone <= 0 && spacesToFenchurch > 0) {
                newSpace = SpaceEnums.FENCHURCH_NUMBER;
            } else {
                if (spacesToFenchurch <= 0 && spacesToLiverpoolSt > 0) {
                    newSpace = SpaceEnums.LIVERPOOL_ST_NUMBER;
                } else {
                    newSpace = SpaceEnums.KINGS_CROSS_NUMBER;
                }
            }
        }
        Board board = Game.getInstance().getBoard();
        Station property = (Station)board.get(newSpace);
        //If unowned pay owner twice the rent to which they are otherwise
        // entitled.
        int amountOwed = property.getRent() * stationMultiplier;
        completeAction(newSpace, player, amountOwed, property);
    }

    /**
     * Complete the action for either station or utility spaces.
     * @param newSpace which the player has been sent to.
     * @param player which drew the card.
     * @param amountOwed by the player.
     * @param property which the player landed on.
     */
    private void completeAction(int newSpace, Player player, int amountOwed,
            Space property) {
        Game.getInstance().movePlayerToSpaceNumber(player, newSpace,
                passGoMoney);
        property.landedOnProperty(player, amountOwed);
    }
}
