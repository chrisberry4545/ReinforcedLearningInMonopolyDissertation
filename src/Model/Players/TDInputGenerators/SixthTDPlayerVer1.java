package Model.Players.TDInputGenerators;

import Model.Board;
import Model.Game;
import Model.Players.Player;
import Model.Spaces.Site;
import Model.Spaces.SiteGroup;
import Model.Spaces.Station;
import Model.Spaces.Utility;
import java.util.List;

/**
 * This version uses additional nodes to try and encode the state of each
 * of the property groups in an attempt to force the AI to better understand
 * the value of giving away a Monopoly. The AI was found to be unsuccessful
 * in the end, probably due to all the noise the extra inputs created.
 * @author Chris Berry
 */
public class SixthTDPlayerVer1 extends TDInputGenerator {
    
    public static final int NUM_INPUT_NODES = 221;
    public static final int NUM_OUTPUT_NODES = 1;
    public static final int NUM_HIDDEN_NODES = 60; 
    
    private final int numberOfPlayers = 4;
    
    /**
     * This value gets returned from evaluators functions if the player cannot
     * make this move. It is filled with highly negative values so the AI
     * will not play these choices.
     * These need to be changed if the outputs are changed.
     * Color is also currently removed.
     */
    private double[] badOutput = {BAD_OUTPUT_NUM};
    /**
     * Sets up a TD Player
     * @param playerNumber sets the player number of the TD Player
     * @param currentBoard the current board the player will play on.
     * @param critic used in the TD player's neural network.
     */
    public SixthTDPlayerVer1(boolean useDealSubsets) {
        super(useDealSubsets);
    }

/**
 * Methods overriding AbstractTDPlayer methods.
 */    
    /**
     * Gets an array representing the current inputs for the neural network
     * based on the current state of the board.
     * @return 
     */
    @Override
    public double[] getCurrentInputs(int playerNumber) {
        //Players are always considered player 0 to increase rate of learning.
        if (playerNumber != 0) {
            swapPlayerNumbers(playerNumber, 0);
        }
        double[] inputs = new double[NUM_INPUT_NODES];
        int inputNumber = 0;
        //Add the nodes for all the players money.
        List<Player> allPlayers = Game.getInstance().getAllPlayersAtStart();
        if (numberOfPlayers != allPlayers.size()) {
            System.err.println("This AI only works with " + numberOfPlayers + 
                    " player games");
            System.exit(0);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            if (allPlayers.size() > i) {
                double money = allPlayers.get(i).getMoney() / 7500;

                inputs[inputNumber] = money;
            } else {
                inputs[inputNumber] = 0;
                
            }
            inputNumber++;
        }
        //22 sets of 6 nodes representing properties.
        for (Site site: super.board.getAllSites()) {
            //Adds values for each owner.
            for (int i = 0; i < numberOfPlayers; i++) {
                if (site.getOwner() != null && 
                        i == site.getOwner().getNumber()) {
                    inputs[inputNumber] = 1;
                } else {
                    inputs[inputNumber] = 0;
                }
                inputNumber++;
            }
            //Adds value for number of houses.
            double houseValue = (double) site.getHouses()/Site.MAX_HOUSES;
            inputs[inputNumber] = houseValue;
            inputNumber++;
            //Adds value based on if the place is mortgaged.
            if (!site.isMortgaged()) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //4 sets of 5 nodes representing stations.
        for (Station station : super.board.getAllStations()) {
            //Adds values for each owner.
            for (int i = 0; i < numberOfPlayers; i++) {
                if (station.getOwner() != null && 
                        i == station.getOwner().getNumber()) {
                    inputs[inputNumber] = 1;
                } else {
                    inputs[inputNumber] = 0;
                }
                inputNumber++;
            }
            if (!station.isMortgaged()) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //2 sets of 5 nodes representing utilies.
        for (Utility utility : super.board.getAllUtilities()) {
            //Adds values for each owner.
            for (int i = 0; i < numberOfPlayers; i++) {
                if (utility.getOwner() != null && 
                        i == utility.getOwner().getNumber()) {
                    inputs[inputNumber] = 1;
                } else {
                    inputs[inputNumber] = 0;
                }
                inputNumber++;
            }
            inputNumber++;
            if (!utility.isMortgaged()) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //4 node representing whether each player is in jail or not.
        //And 4 nodes represnting how many turns players are in jail for.
        for (int i = 0; i < numberOfPlayers; i++) {
            if (allPlayers.size() > i) {
                if (!allPlayers.get(i).inJail()) {
                    //If size is greater than current i or the player isn't in jail
                    //this input should be set to 0.
                    inputs[inputNumber] = 0;
                    inputNumber++;
                    //Turns in jail. 0 if player isn't in jail.
                    inputs[inputNumber] = 0;
                    inputNumber++;
                } else {
                    //Input set to 1 if the player is in jail.
                    inputs[inputNumber] = 1;
                    inputNumber++;
                    //Input also includes the number of turns player is in jail
                    //for.
                    double jailTurnValue = 
                            (double) allPlayers.get(i).getTurnsInJail(); 
                    inputs[inputNumber] = jailTurnValue;
                    inputNumber++;
                }
            } else {
                //If the player isn't in the game all of the inputs should be 0.
                inputs[inputNumber] = 0;
                inputNumber++;
                inputs[inputNumber] = 0;
                inputNumber++;
            }
        }
        //4 nodes representing the number of get out of jail cards a player
        //has.
        for (int i = 0; i < numberOfPlayers; i++) {
            if (allPlayers.size() > i) {
                double numGetOutOfJailCards =
                        allPlayers.get(i).getNumberOfGetOutOfJailCards();
                double jailCardVal = (double)numGetOutOfJailCards;
                inputs[inputNumber]  = jailCardVal;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        
        //State of each of the property groups.
        for (int colorNumber = 1; colorNumber <= Board.BLUE; colorNumber++) {
            //No one owns any properties
            if (SiteGroup.nobodyOwnsPropertys(colorNumber)) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
            //Owned by one person, others are available.
            if (SiteGroup.ownedByOnePersonOthersAvaliable(colorNumber)) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
            //Owned by more than one and others are available.
            if (SiteGroup.ownedByMoreThanOneOthersAvaliable(colorNumber)) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
            //Owned by one and others are available.
            if (SiteGroup.ownedByOnePersonOthersAvaliable(colorNumber)) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
            //Owned by one and non available.
            if (SiteGroup.playerOwnsAMonopoly(colorNumber)) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //Add an input for the turn counter.
        double turnCounterValue =
                (double) Game.getInstance().getRoundCount() / Game.MAX_ROUNDS;
        inputs[inputNumber] = turnCounterValue;
        
        //Swap player numbers back.
        if (playerNumber != 0) {
            swapPlayerNumbers(0, playerNumber);
        }
        //Return the array of inputs
        return inputs;
    }   

/**
 * Getter Method.
 */
    /**
     * Gets the number of input nodes used by the TD Player.
     * @return number of input nodes.
     */
    @Override
    public int getNumInputNodes() {
        return NUM_INPUT_NODES;
    }
    
    /**
     * Gets the number of output nodes used by the TD Player.
     * @return number of output nodes.
     */
    @Override
    public int getNumOutputNodes() {
        return NUM_OUTPUT_NODES;
    }
    
    /**
     * Gets the number of hidden nodes used by the TD Player.
     * @return number of hidden nodes.
     */
    @Override
    public int getNumHiddenNodes() {
        return NUM_HIDDEN_NODES;
    }
    
    /**
     * Gets the bad output array used by the TD player.
     * @return bad output array used.
     */
    @Override
    public double[] getBadOutputArray() {
        return badOutput;
    }    
}
