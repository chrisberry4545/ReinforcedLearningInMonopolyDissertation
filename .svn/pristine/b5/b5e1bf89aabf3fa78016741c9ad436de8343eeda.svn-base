package monopoly;

import Controller.ActionManager;
import Display.BoardDisplay;
import Model.Bank;
import Model.Board;
import Model.Game;
import Model.Players.AIControllers.AIControllerV3_3;
import Model.Players.HumanPlayer;
import Model.Players.NeuralNetwork.AbstractEvaluator;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.NeuralNetwork.Generalizer;
import Model.Players.Player;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Spaces.Site;
import Model.Spaces.Space;
import Model.Spaces.SpaceEnums;
import java.util.ArrayList;
import java.util.List;

/**
 * A series of questions to ask the AI about what they would do in a number
 * of situations.
 * @author Chris Berry
 */
public class MonopolyDeficiencies {
    
    public static final int p1Number = 0;
    public static final int p2Number = 1;
    public static final int p3Number = 2;
    public static final int p4Number = 3;
   
    
    public static void runAIDeficiencyExampleOne(String playersFile,
            TDInputGenerator inputGen) {
        int roundCount = 30;
        int numPlayers = 4;
        setUpGameTypeOne(playersFile, inputGen.getNumOutputNodes(),
            numPlayers, inputGen, roundCount);        
        ActionManager.getInstance().startGame();
        setUpGameTypeTwo(playersFile, inputGen.getNumOutputNodes(),
            numPlayers, inputGen, roundCount);
        ActionManager.getInstance().startGame();
    }
    
    public static void runAIDeficiencyExampleTwo(String playersFile,
            TDInputGenerator inputGen) {
        int roundCount = 30;
        int numPlayers = 4;
        setUpGameTypeTwo(playersFile, inputGen.getNumOutputNodes(),
            numPlayers, inputGen, roundCount);
        ActionManager.getInstance().startGame();
    }
    
    /**
     * Sets up a basic game used in these Queries.
     * @param playersFile to load player's network from.
     * @param numOutputs number of outputs used.
     * @param numPlayers number of players in the game.
     * @param inputGen the input gen to be used by the player.
     * @param roundCount the round count to start game at.
     * @return 
     */
    public static Game setUpBasicGame(String playersFile, int numOutputs,
            int numPlayers, TDInputGenerator inputGen, int roundCount) {
        AbstractEvaluator playersEvaluator = new AbstractEvaluator(playersFile);
        Generalizer playersGeneralizer = new Generalizer(playersEvaluator);
        Critic playersCritic = new Critic(playersGeneralizer, numOutputs);
        playersCritic.setBlockLearning(true);
        
        Game game = Game.newGame();
        Board board = game.getBoard();
        
        List<Player> players = new ArrayList();
        BoardDisplay display = new BoardDisplay(numPlayers);
        players.add(new HumanPlayer(0, display));
        int maxTokenNumber = 1;
        //Add TD players.
        for (int i = 0; i < numPlayers - 1; i++) {
            players.add(new AIControllerV3_3(maxTokenNumber,playersCritic,
                    inputGen, board));
            maxTokenNumber++;
        }
        
        ActionManager am = ActionManager.getInstance();
        game.addPlayers(players);
        game.setRoundCount(roundCount);
        am.setGame(game);
        am.setUseDisplay(true);
        am.setDisplay(display);
        am.setBoard(game.getBoard());
        am.addPropertiesToBoardDisplay();
        am.addPlayers();
        
        //Sets the current player to the player before the human so the
        //human player gets the first go.
        int playerBeforePlayersPosition = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().get(i).getNumber() == 0) {
                if (i == 0) {
                    playerBeforePlayersPosition = numPlayers - 1;
                } else {
                    playerBeforePlayersPosition = i - 1;
                }
            }
        }
        
        game.setCurrentPlayerIndex(playerBeforePlayersPosition);
        return game;
    }
    
    /**
     * Sets up the game where all players own a Monopoly.
     * @param playersFile players file to load.
     * @param numOutputs number of outputs to use.
     * @param numPlayers number of players in the game.
     * @param inputGen input gen to use.
     * @param roundCount the round count to start game at.
     * @return the set up game.
     */
    public static Game setUpGameTypeOne(String playersFile, int numOutputs,
            int numPlayers, TDInputGenerator inputGen, int roundCount) {
        
        Game game = setUpBasicGame(playersFile, numOutputs, numPlayers, inputGen,
                roundCount);
        Board board = game.getBoard();
        
        Player pinkOwner = game.getPlayers().get(p1Number);
        Player orangeOwner = game.getPlayers().get(p2Number);
        Player otherNonMonopolyPlayer = game.getPlayers().get(p3Number);
        Player currentPlayer = game.getPlayers().get(p4Number);
        
        for (Site site : board.getAllSites()) {
            if (site.getColorNumber() == Board.PINK) {
                site.setOwner(pinkOwner);
            }
            if (site.getColorNumber() == Board.ORANGE) {
                site.setOwner(orangeOwner);
            }
            if (site.getColorNumber() == Board.BLUE) {
                site.setOwner(otherNonMonopolyPlayer);
            }
            if (site.getColorNumber() == Board.GREEN) {
                site.setOwner(currentPlayer);
            }
        }
        //Gives human player a station to trade.
        for (Player p : game.getPlayers()) {
            if (p.getNumber() == 0) {
                board.get(SpaceEnums.FENCHURCH_NUMBER).setOwner(p);
                p.forcedMoneyChange(-1000, Bank.getInstance());
            }
        }
        
        
        return game;
    }

    /**
     * Sets up the game for the second query.
     * @param playersFile players file to load.
     * @param numOutputs number of outputs to use.
     * @param numPlayers number of players in the game.
     * @param inputGen input gen to use.
     * @param roundCount the round count to start game at.
     * @return the set up game.
     */    
    public static Game setUpGameTypeTwo(String playersFile, int numOutputs,
            int numPlayers, TDInputGenerator inputGen, int roundCount) {
        Game game = setUpBasicGame(playersFile, numOutputs, numPlayers, inputGen,
                roundCount);
        Board board = game.getBoard();
        
        Player human = null;
        Player p1 = null;
        Player p2 = null;
        Player p3 = null;
        
        for (Player player : game.getPlayers()) {
            switch (player.getNumber()) {
                case 0 : 
                    human = player;
                    break;
                case 1 :
                    p1 = player;
                    break;
                case 2 :
                    p2 = player;
                    break;
                case 3 :
                    p3 = player;
                    break;
            }
        }
        
        for (Site site : board.getAllSites()) {
            if (site.getColorNumber() == Board.PINK) {
                site.setOwner(p1);
            }
            if (site.getColorNumber() == Board.ORANGE) {
                site.setOwner(p2);
            }
            if (site.getColorNumber() == Board.GREEN) {
                site.setOwner(p3);
            }
        }
        
        board.get(SpaceEnums.WHITEHALL_NUMBER).setOwner(null);
        
        
        board.get(SpaceEnums.BOW_ST_NUMBER).setOwner(p3);
        board.get(SpaceEnums.BOND_ST_NUMBER).setOwner(human);
        board.get(SpaceEnums.PICCADILLY_NUMBER).setOwner(human);
        board.get(SpaceEnums.MAYFAIR_NUMBER).setOwner(human);
        
        
        
        return game;
    }
    
    /**
     * Sets up the game for the third query.
     * @param playersFile players file to load.
     * @param numOutputs number of outputs to use.
     * @param numPlayers number of players in the game.
     * @param inputGen input gen to use.
     * @param roundCount the round count to start game at.
     * @return the set up game.
     */    
    public static Game setUpGameTypeThree(String playersFile, int numOutputs,
            int numPlayers, TDInputGenerator inputGen, int roundCount) {
        Game game = setUpBasicGame(playersFile, numOutputs, numPlayers, inputGen,
                roundCount);
        Board board = game.getBoard();
       
        int p1AndP2Money = 300;
        int p3AndP4Money = 1500;
        
        Player p1 = game.getPlayers().get(p1Number);
        Player p2 = game.getPlayers().get(p2Number);
        Player p3 = game.getPlayers().get(p3Number);
        Player p4 = game.getPlayers().get(p4Number);
        
        //P1
        board.get(SpaceEnums.PALL_MALL_NUMBER).setOwner(p1);
        board.get(SpaceEnums.WHITEHALL_NUMBER).setOwner(p1);
        board.get(SpaceEnums.NORTHUMBERLANDAV_NUMBER).setOwner(p1);
        p1.optionalMoneyChange(p1AndP2Money);
        
        //P2
        board.get(SpaceEnums.BOW_ST_NUMBER).setOwner(p2);
        board.get(SpaceEnums.MARLBOUROUGH_NUMBER).setOwner(p2);
        board.get(SpaceEnums.VINE_ST_NUMBER).setOwner(p2);
        p2.optionalMoneyChange(p1AndP2Money);
        
        //P3
        board.get(SpaceEnums.TRAFALGAR_SQ_NUMBER).setOwner(p3);
        board.get(SpaceEnums.FLEET_ST_NUMBER).setOwner(p3);
        board.get(SpaceEnums.REGENT_ST_NUMBER).setOwner(p3);
        p3.optionalMoneyChange(p3AndP4Money);
        
        //P4
        board.get(SpaceEnums.STRAND_NUMBER).setOwner(p4);
        board.get(SpaceEnums.BOND_ST_NUMBER).setOwner(p4);
        board.get(SpaceEnums.OXFORD_ST_NUMBER).setOwner(p4);
        p4.optionalMoneyChange(p3AndP4Money);
        
        return game;
    }
    
    
    
}
