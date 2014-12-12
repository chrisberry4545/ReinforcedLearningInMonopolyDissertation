package Model.Players.TDInputGenerators;

import Model.Game;
import Model.Players.Player;
import Model.Spaces.Site;
import Model.Spaces.Station;
import Model.Spaces.Utility;
import java.util.List;

/**
 * This third TD Player uses only a single output node and swaps the output as
 * appropriate. I have also increased the number of hidden nodes to 60,
 * which will hopefully improve what it can learn.
 * @author Chris Berry
 */
public class ThirdTDPlayer extends TDInputGenerator {
    
    public static final int NUM_INPUT_NODES = 125;
    public static final int NUM_OUTPUT_NODES = 1;
    public static final int NUM_HIDDEN_NODES = 60; 
    
    /**
     * This value gets returned from evaluators functions if the player cannot
     * make this move. It is filled with highly negative values so the AI
     * will not play these choices.
     * These need to be changed if the outputs are changed.
     */
    private double[] badOutput = {BAD_OUTPUT_NUM};
    /**
     * Sets up a TD Player
     * @param playerNumber sets the player number of the TD Player
     * @param currentBoard the current board the player will play on.
     * @param critic used in the TD player's neural network.
     */
    public ThirdTDPlayer() {
        super(false);
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
        //There will always be 6 nodes for this.
        List<Player> allPlayers = Game.getInstance().getAllPlayersAtStart();
        for (int i = 0; i < Game.MAX_PLAYERS; i++) {
            if (allPlayers.size() > i) {
                double money = allPlayers.get(i).getMoney();
                money = money/10000;
                inputs[inputNumber] = money;
            } else {
                inputs[inputNumber] = 0;
                
            }
            inputNumber++;
        }
        //22 sets of 4 nodes representing properties.
        for (Site site: super.board.getAllSites()) {
            //Adds values for the color.
            double colorNumber = site.getColorNumber();
            inputs[inputNumber] = colorNumber/7;
            inputNumber++;
            //Adds values for owner.
            if (site.getOwner() != null) {
                double ownerValue = (double)site.getOwner().getNumber()/Game.MAX_PLAYERS;
                inputs[inputNumber] = ownerValue;
            } else {
                inputs[inputNumber] = 1;
            }
            inputNumber++;
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
        //4 sets of 2 nodes representing stations.
        for (Station station : super.board.getAllStations()) {
            if (station.getOwner() != null) {
                double ownerValue = (double)station.getOwner().getNumber()/Game.MAX_PLAYERS;
                inputs[inputNumber] = ownerValue;
            } else {
                inputs[inputNumber] = 1;
            }
            inputNumber++;
            if (!station.isMortgaged()) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //2 sets of 2 nodes representing utilies.
        for (Utility utility : super.board.getAllUtilities()) {
            if (utility.getOwner() != null) {
                double ownerValue = (double)utility.getOwner().getNumber()/Game.MAX_PLAYERS;
                inputs[inputNumber] = ownerValue;
            } else {
                inputs[inputNumber] = 1;
            }
            inputNumber++;
            if (!utility.isMortgaged()) {
                inputs[inputNumber] = 1;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        //6 node representing whether each player is in jail or not.
        //And 6 nodes represnting how many turns players are in jail for.
        for (int i = 0; i < Game.MAX_PLAYERS; i++) {
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
                            (double) allPlayers.get(i).getTurnsInJail()/Game.MAX_TURNS_IN_JAIL; 
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
        
        //6 nodes representing the number of get out of jail cards a player
        //has.
        for (int i = 0; i < Game.MAX_PLAYERS; i++) {
            if (allPlayers.size() > i) {
                double numGetOutOfJailCards =
                        allPlayers.get(i).getNumberOfGetOutOfJailCards();
                double jailCardVal = (double)numGetOutOfJailCards/Game.MAX_NUM_OF_GET_OUT_OF_JAIL_CARDS;
                inputs[inputNumber]  = jailCardVal;
            } else {
                inputs[inputNumber] = 0;
            }
            inputNumber++;
        }
        
        //Add an input for the turn counter.
        double turnCounterValue =
                (double) Game.getInstance().getRoundCount()/Game.MAX_ROUNDS;
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
