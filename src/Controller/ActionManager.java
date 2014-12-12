package Controller;

import Display.BoardDisplay;
import Model.Board;
import Model.Game;
import Model.Players.*;
import Model.Players.AIControllers.*;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Spaces.Site;
import Model.Spaces.SiteGroup;
import Model.Spaces.Space;
import Model.Spaces.SpaceEnums;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import monopoly.Main;

/**
 * Handles the interaction of various components of the project.
 * @author Chris Berry
 */
public class ActionManager {
    
    private static ActionManager instance = null;
    private BoardDisplay display;
    private Board board;
    private Game game;
    private int gamesOver250Rounds = 0;
    private boolean useDisplay;
    private int p0Victories = 0;
    private int p1Victories = 0;
    private int p2Victories = 0;
    private int p3Victories = 0;
    
    private int movesInJail = 0;
    private int maxMovesInJail = 10;
    
    private static int maxMoney = 0;
    
    public static int RUNTYPE_NORMALGAME = 0;
    public static int RUNTYPE_MONOPOLYSTART = 1;
    public static int RUNTYPE_RANDOMSTART = 2;
    
    
    protected ActionManager() {
    }
    
    /**
     * Starts a new Game of Monopoly from a single critic and input generator.
     * @param critic of the neural network to be used.
     * @param inputGen inputgen the td ais will use.
     * @param showGameGraphics true if the graphics should be displayed.
     * @param numHumanPlayers number of human players to include.
     * @param numRandomAIs number of random AIs to include.
     * @param numTDAIs number of TD AIs to include.
     * @param numSimpleTDAIs number of simple TD AIs to include.
     * @param numRandomDBAIs number of random Database generating AIs to include
     * @param numSmartBots number of smart bots to include
     * @param numAIControllerPlayers number of AI smartbot/td lambda hybrids to include
     * @param numTDHandcraftedFeatureAIs number of hand crafted featue td ais to include.
     * @param useExploratoryMoves true if the AI should use exploratory moves.
     * @param runType run type (see static ints).
     */
    public void startNewGame(Critic critic, TDInputGenerator inputGen,
            boolean showGameGraphics,
            int numHumanPlayers, int numRandomAIs, int numTDAIs,
            int numSimpleTDAIs, int numRandomDBAIs, int numSmartBots,
            int numAIControllerPlayers, int numTDHandcraftedFeatureAIs,
            boolean useExploratoryMoves, int runType) {
        List<Critic> criticList = new ArrayList();
        List<TDInputGenerator> inputGenList = new ArrayList();
        for (int i = 0; 
                i < numTDAIs + numSimpleTDAIs + numRandomDBAIs 
                + numAIControllerPlayers + numTDHandcraftedFeatureAIs; i++) {
            criticList.add(critic);
            inputGenList.add(inputGen);
        }
        this.startNewGame(criticList, inputGenList, showGameGraphics, 
                numHumanPlayers, numRandomAIs, numTDAIs, 
                numSimpleTDAIs, numRandomDBAIs, numSmartBots, numAIControllerPlayers,
                numTDHandcraftedFeatureAIs, useExploratoryMoves, runType);
    }
    
    /**
     * Starts a new Game of Monopoly from a List of critics and input generators.
     * @param critics List of critics the AIs should use.
     * @param inputGens List of input gens that the AIs should use.
     * @param showGameGraphics true if the graphics should be displayed.
     * @param numHumanPlayers number of human players to include.
     * @param numRandomAIs number of random AIs to include.
     * @param numTDAIs number of TD AIs to include.
     * @param numSimpleTDAIs number of simple TD AIs to include.
     * @param numRandomDBAIs number of random Database generating AIs to include
     * @param numSmartBots number of smart bots to include
     * @param numAIControllerPlayers number of AI smartbot/td lambda hybrids to include
     * @param numTDHandcraftedFeatureAIs number of hand crafted featue td ais to include.
     * @param useExploratoryMoves true if the AI should use exploratory moves.
     * @param runType run type (see static ints).
     */
    public void startNewGame(List<Critic> critics, 
            List<TDInputGenerator> inputGens, boolean showGameGraphics,
            int numHumanPlayers, int numRandomAIs, int numTDAIs, 
            int numSimpleTDAIs, int numRandomDBAIs, int numSmartBots,
            int numAIControllerPlayers, int numTDHandcraftedFeatureAIs,
            boolean useExploratoryMoves,
            int runType) {
        int totalPlayers = numHumanPlayers + numRandomAIs + numTDAIs +
                numSimpleTDAIs + numSmartBots + numAIControllerPlayers
                + numTDHandcraftedFeatureAIs;
        if (totalPlayers > Game.MAX_PLAYERS) {
            System.err.println("Game can only have " + Game.MAX_PLAYERS +
                    " you tried to start a game with " + totalPlayers);
            System.exit(0);
        }
        ArrayList<Player> players = new ArrayList();
        this.useDisplay = showGameGraphics;
        if (this.useDisplay) {
            display = new BoardDisplay(totalPlayers);
        }
        game = Game.newGame();
        board = game.getBoard();
        int maxTokenNumber = 0;
        int maxCriticNumber = 0;
        int maxInputGenNumber = 0;
        
        //Add human players.
        if (numHumanPlayers > 0 && !this.useDisplay) {
            System.err.println("To use human players you need to turn the "
                    + "display on");
        } else {
            for (int i = 0; i < numHumanPlayers; i++) {
                players.add(new HumanPlayer(maxTokenNumber, display));
                maxTokenNumber++;
            }
        }
        //Add random Players
        for (int i = 0; i < numRandomAIs; i++) {
            players.add(new RandomAI(maxTokenNumber));
            maxTokenNumber++;
        }
        
        //Add controller AIs
        for (int i = 0; i < numAIControllerPlayers; i++) {
            AIControllerV3_3 ai = new AIControllerV3_3(maxTokenNumber, critics.get(maxCriticNumber),
                    inputGens.get(maxInputGenNumber), board);
            players.add(ai);
            maxCriticNumber++;
            maxInputGenNumber++;
            maxTokenNumber++;
        }

        //Add AIs with handcrafted features
        for (int i = 0; i < numTDHandcraftedFeatureAIs; i++) {
            players.add(new TDPlayerHandCraftedFeatures(maxTokenNumber, board, critics.get(maxCriticNumber),
                    inputGens.get(maxInputGenNumber), useExploratoryMoves));
            maxTokenNumber++;
            maxCriticNumber++;
            maxInputGenNumber++;
        }
        
        //Add TD players.
        for (int i = 0; i < numTDAIs; i++) {
            players.add(new TDPlayer(maxTokenNumber, board, critics.get(maxCriticNumber),
                    inputGens.get(maxInputGenNumber), useExploratoryMoves));
            maxTokenNumber++;
            maxCriticNumber++;
            maxInputGenNumber++;
        }

        //Add simple TD players.
        for (int i = 0; i < numSimpleTDAIs; i++) {
            players.add(new TDPlayer(maxTokenNumber, board, critics.get(maxCriticNumber),
                    inputGens.get(maxInputGenNumber), useExploratoryMoves));
            maxTokenNumber++;
            maxCriticNumber++;
            maxInputGenNumber++;
        }
        //Add Random DB AIs
        for (int i = 0; i < numRandomDBAIs; i++) {
            inputGens.get(i).setBoard(board);
            players.add(new DatabaseGenRandomAI(maxTokenNumber, inputGens.get(maxInputGenNumber)));        
            maxInputGenNumber++;
            maxTokenNumber++;
        }
        //Add Bot AIs
        for (int i = 0; i < numSmartBots; i++) {
            players.add(new SmartBot(maxTokenNumber));
            maxTokenNumber++;
        }
        
        
        if (this.useDisplay) {
            addPropertiesToBoardDisplay();
        }
        
        if (runType == RUNTYPE_MONOPOLYSTART) {
            board = setUpGameWherePlayersOwnMonopolies(board, players);
        }
        
        if (runType == RUNTYPE_RANDOMSTART) {
            board = setUpRandomGame(board, players);
        }
        game.addPlayers(players);
        addPlayers();
        startGame();
    }
 
    /**
    * Returns an instance of the singleton class ActionManager
    * @return ActionManager instance
    */
    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }
    
    /**
     * Adds properties to display. 
     * Note: only called if this.useDisplay is true.
     */
    public void addPropertiesToBoardDisplay() {
        int counter = 0;
        for (Space space : board) {
            display.addSpaceToDisplay(counter,space);
            counter++;
        }
        display.repaint();
    }
    
    /**
     * Adds all players to the game and to the display if applicable.
     */
    public void addPlayers() {
        if (game.getPlayers() == null) {
            System.err.println("No players..");
        }
        for (Player player : game.getPlayers()) {
            System.out.println(player.getNumber() + " " + player.getClass());
            if (this.useDisplay) {
                display.addPlayerMarker(player.getNumber(), 0);
            }
            //Puts the player at go.
            player.setSpace(board.get(0));       
        }
        game.setUpPlayersForGameStart();
        if (this.useDisplay) {
            display.repaint();
        }
    }
    
    /**
     * Starts the game of Monopoly.
     */
    public void startGame() {
        while (!game.isFinished()) {
            Player player = game.nextPlayer();
            if (player.inJail()) {
                player.increaseJailCounter();
            }
            takeTurn(player);
            if (game.getRoundCount() >= Game.MAX_ROUNDS) {
                game.declarePlayerWithMostAssetsWinner();
            }
            if (game.isFinished()) {
                calculateVictories();
            }
            
            for (Player p : game.getPlayers()) {
                if (p.getMoney() > maxMoney) {
                    maxMoney = p.getMoney();
                }
            }
            
        }
        if (this.useDisplay) {
            display.dispose();
        }
        SiteGroup.resetSiteGroups();
    }
    
    /**
     * Calculates who won a game once the game has reached the end.
     */
    private void calculateVictories() {
        int winningPlayer = game.getPlayers().get(0).getNumber();
        switch(winningPlayer) {
            case 0 : p0Victories++;
                break;
            case 1 : p1Victories++;
                break;
            case 2 : p2Victories++;
                break;
            case 3 : p3Victories++;
                break;
            default : System.err.println("An invalid player has won");
        }
        System.out.println("P0 Victories: " + p0Victories);
        System.out.println("P1 Victories: " + p1Victories);
        System.out.println("P2 Victories: " + p2Victories);
        System.out.println("P3 Victories: " + p3Victories);
    }
    
    /**
     * Player takes their standard turn or their in jail turn depending 
     * on their current situation.
     * @param player who's turn it is.
     */
    private void takeTurn(Player player) {
        updateDisplay();
        if (!player.inJail()) {
                standardTurn(player);
        } else {
                inJailTurn(player);
        }
    }
    
    /**
     * Player takes their turn as normal.
     * @param player who's turn it is.
     */
    private void standardTurn(Player player) {
        while (!player.askPlayerMoveOptions()) {
            //This is repeated until the player indicates they are finished
            //and want to roll for their turn.
            updateDisplay();
        }
        rollDiceToMove(player);
                
    }
    
    /**
     * Player asks to take their in jail turn.
     * @param player who's turn it is.
     */
    private void inJailTurn(Player player) {
        int result = player.askPlayerInJailOptions();
        switch (result) {
            /**
             * If the player is still in jail but there turn isn't over
             * they can take another action.
             */
            case Player.STILL_IN_JAIL_TURN_NOT_OVER :
                //Player can only make a certain amount of moves in jail.
                //This is to prevent the AI causing problems
                movesInJail++;
                if (movesInJail < maxMovesInJail) {
                    takeTurn(player);
                } else {
                    movesInJail = 0;
                }
                break;
            /**
             * If the player is still in jail but there turn is over then they
             * cannot take any more actions this turn.
             */
            case Player.STILL_IN_JAIL_TURN_OVER :
                //Turn finishes in this case.
                break;
            /**
             * In the default case the player has left jail and has rolled
             * the dice giving the return result.
             */
            default : 
                movePlayer(player, result);
                break;
        }
    }
    
    /**
     * Performs the appropriate actions for a players turn.
     */
    private void rollDiceToMove(Player player) {
        int diceRoll = Player.rollForTurn();
        String diceRollToTellPlayer = " a " + String.valueOf(diceRoll);
        if (diceRoll == -1) {
            diceRollToTellPlayer = "three doubles and get sent to jail.";
            player.setInJail(true);
            int movesToJail = SpaceEnums.JAIL_NUMBER - 
                    player.getCurrentSpace().getSpaceNumber();
            movePlayer(player, movesToJail);
        } else {
            player.messagePlayer("You rolled" + diceRollToTellPlayer, "Dice Roll");
            movePlayer(player, diceRoll);
        }
    }
    
    /**
     * Moves the player by a given amount and handle the results of the move.
     * @param player to move
     * @param moveAmount 
     */
    private void movePlayer(Player player, int moveAmount) {
        int oldSpace = player.getCurrentSpace().getSpaceNumber();
        game.movePlayer(player, moveAmount);
        player.getCurrentSpace().performAction(moveAmount, player);
        int newSpace = player.getCurrentSpace().getSpaceNumber();
        if (this.useDisplay) {
            display.movePlayer(player.getNumber(), oldSpace, newSpace);
            updateDisplay();
        }
    }
    
    /**
     * Informs the action manager that the number of houses has changed on a site
     * so the display can be updated.
     * @param site which the house was added to. 
     */
    public void houseNumberChanged(Site site) {
        if (this.useDisplay) {
            display.houseAddedToSite(site);
        }
    }
    
    
    
/**
 * Generate board methods.
 */    
    
    public static Board setUpGameWherePlayersOwnMonopolies(Board monopolyBoard,
            List<Player> players) {
        for (Player p : players) {
            monopolyBoard = givePlayerMonopolyOnBoard(monopolyBoard, p);
        }
        //Sets round count to mid way through the game.
        Game.getInstance().setRoundCount(30);
        return monopolyBoard;
    } 
    
    /**
     * Set up a board where each player has a monopoly on a single random color.
     * @param monopolysBoards the board players are playing on.
     * @param player the player to give a Monopoly to.
     * @return the board.
     */
    public static Board givePlayerMonopolyOnBoard(Board monopolysBoard, Player player) {
        Random random = new Random();
        int colorChosen = random.nextInt(Game.NUMBER_OF_COLORS) + 1;
        for (Site site : monopolysBoard.getAllSites()) {
            if (site.getColorNumber() == colorChosen) {
                site.setOwner(player);
            }
        }
        return monopolysBoard;
    }
    
    /**
     * Sets a random game of Monopoly where all players own a random amount of
     * properties.
     * @param boardToRandomise
     * @param players
     * @return 
     */
    public static Board setUpRandomGame(Board boardToRandomise, List<Player> players) {
        Random random = new Random();
        for (Player player: players) {
            int startingSpace = random.nextInt(boardToRandomise.size());
            player.setSpace(boardToRandomise.get(startingSpace));
            
            int numberOfProperties = random.nextInt(boardToRandomise.size());
            for (int i = 0; i < numberOfProperties; i++) {
                int propertyNumber = random.nextInt(boardToRandomise.size());
                Space property = boardToRandomise.get(propertyNumber);
                property.setOwner(player);
                if (property.getClass().equals(Site.class)) {
                    Site site = (Site) property;
                    if (site.canBuildOnThisSite()) {
                        int numHouses = random.nextInt(Site.MAX_HOUSES);
                        for (int j = 0; j < numHouses; j++) {
                            site.addHouseForFree();
                        }
                    }
                }
            }   
        }
        return boardToRandomise;
    }
    
    /**
     * Updates the game display to reflect any changes which may have occured.
     */
    private void updateDisplay() {
        if (display != null) {
            display.updatePlayerStats(game.getAllPlayersAtStart(),
                    game.getCurrentPlayer().getNumber());
        }
    }

/**
 * Getter methods.
 */    
    /**
     * Returns true if the current game is finished otherwise returns false.
     * @return true if the game is finished
     */
    public boolean isCurrentGameFinished() {
        return game.isFinished();
    }    
    
    /**
     * Gets the number of games that have gone over 250 rounds.
     * @return number of games that have gone over 250 rounds.
     */
    public int getGamesOver250Rounds() {
        return gamesOver250Rounds;
    }
    
    /**
     * Gets the amount of time Player 0 has won since the last reset.
     * @return number of times Player 0 has won since the last reset.
     */
    public int getP0Victories() {
        return p0Victories;
    }

    /**
     * Gets the amount of time Player 1 has won since the last reset.
     * @return number of times Player 1 has won since the last reset.
     */
    public int getP1Victories() {
        return p1Victories;
    }
    
    /**
     * Gets the amount of time Player 2 has won since the last reset.
     * @return number of times Player 2 has won since the last reset.
     */
    public int getP2Victories() {
        return p2Victories;
    }
    
    /**
     * Gets the amount of time Player 3 has won since the last reset.
     * @return number of times Player 3 has won since the last reset.
     */
    public int getP3Victories() {
        return p3Victories;
    }
    
    /**
     * Gets the maximum amount of money obtained in any game.
     * @return 
     */
    public static int getMaxMoney() {
        return maxMoney;
    }
    
/**
 * Setter Methods.
 */    
        
    /**
     * Sets all the players victories back to 0.
     */
    public void resetVictories() {
        p0Victories = 0;
        p1Victories = 0;
        p2Victories = 0;
        p3Victories = 0;
    }

    /**
     * Sets the game to use to a given game.
     * @param game to use.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Sets whether a visual display should be used.
     * @param useDisplay true if the display should be used.
     */
    public void setUseDisplay(boolean useDisplay) {
        this.useDisplay = useDisplay;
    }
    
    
    /**
     * Sets the display to the given display.
     * @param display to use.
     */
    public void setDisplay(BoardDisplay display) {
        this.display = display;
    }

    /**
     * Sets the board to use to the given board.
     * @param board to use.
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    
    
    
    
    
    
}
