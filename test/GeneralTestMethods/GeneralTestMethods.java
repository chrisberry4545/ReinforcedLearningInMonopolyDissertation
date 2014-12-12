package GeneralTestMethods;

import Model.Board;
import Model.Game;
import Model.Players.Player;
import Model.Players.TestAI;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.ArrayList;
import java.util.List;


/**
 * A template class used to setup a basic game of Monopoly for testing.
 * @author Chris Berry
 */
public class GeneralTestMethods {
    protected Player testPlayer;
    protected Player otherPlayer;
    
    /**
     * Sets up a game with 1 player and a number of other players.
     * @param startingMoney players starting money.
     * @param numberOfOtherPlayers number of other players to add.
     */
    public void setUpGameWithPlayer(int startingMoney, int numberOfOtherPlayers) {
        testPlayer = new TestAI(0);
        setUpOtherPlayers(startingMoney, numberOfOtherPlayers);
    }
    
    /**
     * Sets up a game with one player and a single other player.
     * @param startingMoney starting money for both players.
     */
    public void setUpGameWithOnePlayerAndOtherPlayer(int startingMoney) {
        testPlayer = new TestAI(0);
        List<Player> players = new ArrayList();
        players.add(testPlayer);
        otherPlayer = new TestAI(1);
        otherPlayer.optionalMoneyChange(startingMoney);
        players.add(otherPlayer);
        testPlayer.optionalMoneyChange(startingMoney);
        Game game = Game.newGame();
        game.addPlayers(players);
    }
    
    /**
     * Adds the other players to the game.
     * @param startingMoney starting money for the players.
     * @param numberOfOtherPlayers number of other players to add.
     */
    private void setUpOtherPlayers(int startingMoney, int numberOfOtherPlayers) {
        List<Player> players = new ArrayList();
        players.add(testPlayer);
        for (int i = 0; i < numberOfOtherPlayers; i++) {
            Player otherPlayer = new TestAI(i+1);
            otherPlayer.optionalMoneyChange(startingMoney);
            players.add(otherPlayer);
        }
        testPlayer.optionalMoneyChange(startingMoney);
        Game game = Game.newGame();
        game.addPlayers(players);
    }
    
    /**
     * Sets up a random game, with players owning random properties and houses.
     * @param startingMoney starting money for the players.
     * @param numberOfOtherPlayers number of players to include in the game.
     */
    public void setUpGameWithRandomPropertysAndHouses(int startingMoney,
            int numberOfOtherPlayers) {
        testPlayer = new TestAI(0);
        setUpOtherPlayers(startingMoney, numberOfOtherPlayers);
        Board board = Game.getInstance().getBoard();
        int numSites = board.getAllSites().size();
        int numStations = board.getAllStations().size();
        int numUtils = board.getAllUtilities().size();
        int numSitesToAdd = (int)Math.round(Math.random() * (numSites - 1));
        int numStationsToAdd = (int)Math.round(Math.random() * (numStations - 1));
        int numUtilsToAdd = (int)Math.round(Math.random() * (numUtils - 1));
        
        //Add some random sites.
        for (int i = 0; i < numSitesToAdd; i++) {
            int numToPick = (int)Math.round(Math.random() * (numSites-1));
            Space space = board.getAllSites().get(numToPick);
            Site site = (Site)space;
            site.setOwner(testPlayer);
            int numHouses = (int)Math.round(Math.random() * Site.MAX_HOUSES);
            for (int j = 0; j < numHouses; j++) {
                site.addHouseForFree();
            }
        }
        
        //Add some random Stations.
        for (int i = 0; i < numStationsToAdd; i++) {
            int numToPick = (int)Math.round(Math.random() * (numStations-1));
            Space space = board.getAllStations().get(numToPick);
            space.setOwner(testPlayer);
        }
        
        //Add some random utils
        for (int i = 0; i < numUtilsToAdd; i++) {
            int numToPick = (int)Math.round(Math.random() * (numUtils - 1));
            Space space = board.getAllUtilities().get(numToPick);
            space.setOwner(testPlayer);
        }
    }
}
