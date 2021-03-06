package monopoly;

import Controller.ActionManager;
import Model.Game;
import Model.GameStats;
import Model.Players.AIControllers.*;
import Model.Players.NeuralNetwork.AbstractEvaluator;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.NeuralNetwork.Generalizer;
import Model.Players.TDInputGenerators.FifthTDPlayerVer32;
import Model.Players.TDInputGenerators.FifthTDPlayerVer36;
import Model.Players.TDInputGenerators.FourthTDPlayerVer3;
import Model.Players.TDInputGenerators.TDInputGenerator;
import Model.Players.TDInputGenerators.ThirdTDPlayer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class with the ELO Gauntlet methods within. An ElO Gauntlet is a series
 * of games against multiple opponents from which an ELO score can be calculated.
 * @author Chris Berry
 */
public class ELOGauntlet {
    
    /**
     * Starts a 2 player ELO gauntlet for the player with the given filename,
     * input gen and number of outputs.
     * @param playersFile Players File to Load from.
     * @param playersInputGen Input Generator to use for the player.
     * @param numOutputs number of outputs produced by the input generator.
     */
    public static void ELOGauntlet2Players(String playersFile, 
            TDInputGenerator playersInputGen, int numOutputs) {
        
        System.out.println("Running ELO Tests..");
        int gamesToPlay = 50;
        
        File file = new File("");
        String dirPath = file.getAbsolutePath() + File.separatorChar 
                + "2PlayerELOGauntletAIs" + File.separatorChar;
        
        AbstractEvaluator playersEvaluator = new AbstractEvaluator(playersFile);
        Generalizer playersGeneralizer = new Generalizer(playersEvaluator);
        Critic playersCritic = new Critic(playersGeneralizer, numOutputs);
        playersCritic.setBlockLearning(true);
        boolean useExploratoryMoves = false;
        
        /**
         * Vs random AI.
         */
        int numHumanPlayers = 0;
        int numRandomPlayers = 1;
        int numFullTDPlayers = 1;
        int numSimpleTDAIs = 0;
        int numDBGenRandomAIs = 0;
        int numSmartBots = 0;
        int numAIController = 0;
        int numTDHandcraftedFeatureAIs = 0;
        
        GameStats.setUpGameStats(numHumanPlayers + numRandomPlayers + numFullTDPlayers
                + numSimpleTDAIs + numDBGenRandomAIs);
        
        ActionManager am = ActionManager.getInstance();
        for (int i = 0; i < gamesToPlay; i++) {
            am.startNewGame(playersCritic, playersInputGen, false,
                                    numHumanPlayers, numRandomPlayers,
                                numFullTDPlayers, numSimpleTDAIs, numDBGenRandomAIs,
                                numSmartBots, numAIController, numTDHandcraftedFeatureAIs,
                                useExploratoryMoves, ActionManager.RUNTYPE_NORMALGAME);
        }
        int winsVRandom = am.getP1Victories();
        double percWinRandom = (double)winsVRandom/gamesToPlay;
        am.resetVictories();
        
        /**
         * Vs TD AI V3 5,000 self plays.
         */
        int tdAIV3Outputs = 1;
        String tdAIV3_5K = dirPath + "June-19-2013-lambda0-9-runs5000";
        
        int winsVTDAI5K = run2PlayerTestForTDAI(tdAIV3_5K, tdAIV3Outputs, playersCritic,
                playersInputGen, gamesToPlay, numAIController, numTDHandcraftedFeatureAIs);
        double percWinTDAI5K = (double)winsVTDAI5K/gamesToPlay;
        
        
        /**
         * Vs TD AI V3 20,000 self plays.
         */
        String tdAIV3_20K = dirPath + "June-19-2013-lambda0-9-runs20000";
        
        int winsVTDAI20K = run2PlayerTestForTDAI(tdAIV3_20K, tdAIV3Outputs, playersCritic,
                playersInputGen, gamesToPlay, numAIController, numTDHandcraftedFeatureAIs);
        double percWinTDAI20K = (double)winsVTDAI20K/gamesToPlay;
        
        /**
         * Vs TD AI V3 40,000 self plays.
         */
        String tdAIV3_40K = dirPath + "June-19-2013-lambda0-9-runs40000";
        
        int winsVTDAI40K = run2PlayerTestForTDAI(tdAIV3_40K, tdAIV3Outputs, playersCritic,
                playersInputGen, gamesToPlay, numAIController, numTDHandcraftedFeatureAIs);
        double percWinTDAI40K = (double)winsVTDAI40K/gamesToPlay;
        
        /**
         * Vs TD AI V3 100,000
         */
        String tdAIV3_100K = dirPath + "June-19-2013-lambda0-9-runs100000";
        
        int winsVTDAI100K = run2PlayerTestForTDAI(tdAIV3_100K, tdAIV3Outputs, playersCritic,
                playersInputGen, gamesToPlay, numAIController, numTDHandcraftedFeatureAIs); 
        double percWinTDAI100K = (double)winsVTDAI100K/gamesToPlay;
        
        /**
         * Vs TD Lambda0 V3.1 40,000
         */
        String tdAIV3_1_40K = dirPath + "June-27-2013-lambda0-runs40000";
        
        int winsVTDAILamda0_40K = run2PlayerTestForTDAI(tdAIV3_1_40K, tdAIV3Outputs, playersCritic,
                playersInputGen, gamesToPlay, numAIController, numTDHandcraftedFeatureAIs); 
        double percWinTDAILambda0_40K = (double)winsVTDAILamda0_40K/gamesToPlay;
        
        
        
        System.out.println("Max money: " + ActionManager.getMaxMoney());
        GameStats.printHighestWinValue();
        
        
        System.out.println("Results for " + playersFile);
        System.out.println("Wins vs Random AI: " + winsVRandom 
                + " ("  + percWinRandom + "%)");
        System.out.println("Wins vs TD V3 5,000: " + winsVTDAI5K 
                + " (" + percWinTDAI5K + "%)");
        System.out.println("Wins vs TD V3 20,000: " + winsVTDAI20K 
                + " (" + percWinTDAI20K + "%)");
        System.out.println("Wins vs TD V3 40,000: " + winsVTDAI40K
                + " (" + percWinTDAI40K + "%)");
        System.out.println("Wins vs TD V3 100,000: " + winsVTDAI100K 
                + " (" + percWinTDAI100K + "%)");
        System.out.println("Wins vs TD V3.1 40,000: " + winsVTDAILamda0_40K
                + " (" + percWinTDAILambda0_40K + "%)");
    }
    
    /**
     * Runs a single 2 player test for a TD AI.
     * @param fileName file name of opponent.
     * @param outputNodes number of output nodes to use.
     * @param playersCritic critic used by the player.
     * @param playersInputGen input gen used by the player.
     * @param gamesToPlay the number of games to play.
     * @param numAIController the number of AIController AIs to use.
     * @param numTDHandcraftedFeatureAIs the number of hand crafted feature
     * AIs to use.
     * @return the number of games won by the player.
     */
    public static int run2PlayerTestForTDAI(String fileName, int outputNodes,
            Critic playersCritic, TDInputGenerator playersInputGen,
            int gamesToPlay, int numAIController, int numTDHandcraftedFeatureAIs) {
        AbstractEvaluator otherPlayerEval = new AbstractEvaluator(fileName);
        Generalizer otherPlayerGen = new Generalizer(otherPlayerEval);
        Critic otherPlayerCritic = new Critic(otherPlayerGen, outputNodes);
        otherPlayerCritic.setBlockLearning(true);
        List<Critic> criticList = new ArrayList();
        criticList.add(playersCritic);
        criticList.add(otherPlayerCritic);
        List<TDInputGenerator> inputGenList = new ArrayList();
        ThirdTDPlayer v3InputGen = new ThirdTDPlayer();
        inputGenList.add(playersInputGen);
        inputGenList.add(v3InputGen);
        
        int winsVOtherAI = runGames(gamesToPlay, criticList, inputGenList, 2, numAIController,
                numTDHandcraftedFeatureAIs);
        ActionManager.getInstance().resetVictories();
        return winsVOtherAI;
    }
    
    /**
     * Runs a number of games and gets the amount of victories obtained by
     * the challenger.
     * @param gamesToPlay number of games to play.
     * @param criticList A List containing the Critics for the players (in order).
     * @param inputGenList A List of the players input gens (In Order).
     * @param numTDPlayers The number of TD Players to be used.
     * @param numAIControllerPlayers The number of AIController players to be used.
     * @param numTDHandcraftedFeatureAIs The number of HandcraftedFeatureTDAIs to use.
     * @return 
     */
    public static int runGames(int gamesToPlay, List<Critic> criticList,
            List<TDInputGenerator> inputGenList, int numTDPlayers, int numAIControllerPlayers,
            int numTDHandcraftedFeatureAIs) {
        ActionManager am = ActionManager.getInstance();
        
        int numHumanPlayers = 0;
        int numRandomPlayers = 0;
        int numFullTDPlayers = numTDPlayers;
        int numSimpleTDAIs = 0;
        int numDBGenRandomAIs = 0;
        int numSmartBots = 0;
        int numAIController = numAIControllerPlayers;
        
        int totalPlayers = numHumanPlayers + numRandomPlayers + numFullTDPlayers
                + numSimpleTDAIs + numDBGenRandomAIs + numSmartBots + numAIController
                + numTDHandcraftedFeatureAIs;
        
        boolean useExploratoryMoves = false;
        
        System.out.println("Num TD handcrafted Players " + numTDHandcraftedFeatureAIs);
        
        for (int i = 0; i < gamesToPlay; i++) {
            am.startNewGame(criticList,inputGenList, false,
                                numHumanPlayers, numRandomPlayers,
                            numFullTDPlayers, numSimpleTDAIs, numDBGenRandomAIs,
                            numSmartBots, numAIController, numTDHandcraftedFeatureAIs,
                            useExploratoryMoves, ActionManager.RUNTYPE_NORMALGAME);
        }
        
        if (totalPlayers == 2) {
            return am.getP0Victories();
        }
         
        return am.getP0Victories() + am.getP1Victories();
    }
    
    /**
     * Starts an ELO Gauntlet for a challenger with 4 players and the challenger
     * using a standard TD Player template.
     * @param playersFile players file to load.
     * @param playersInputGen players input generator to use.
     * @param numOutputs number of output used by players input gen.
     */
    public static void ELOGauntlet4PlayersStandard(String playersFile,
            TDInputGenerator playersInputGen, int numOutputs) {
        boolean playerIsAIController = false;
        boolean playerIsUsingHandCraftedFeatures = false;
        ELOGauntlet.ELOGauntlet4Players(playersFile, playersInputGen,
                numOutputs, playerIsAIController, playerIsUsingHandCraftedFeatures);
    }
    
    /**
     * Starts an ELO Gauntlet for a challenger with 4 players and the challenger
     * playing as an 'AIController' class.
     * @param playersFile players file to load.
     * @param playersInputGen players input generator to use.
     * @param numOutputs number of output used by players input gen.
     */
    public static void ELOGauntlet4PlayerWithAIController(String playersFile,
            TDInputGenerator playersInputGen, int numOutputs) {
        boolean playerIsAIController = true;
        boolean playerIsUsingHandCraftedFeatures = false;
        ELOGauntlet.ELOGauntlet4Players(playersFile, playersInputGen,
                numOutputs, playerIsAIController, playerIsUsingHandCraftedFeatures);
    }

    /**
     * Starts an ELO Gauntlet for a challenger with 4 players and the challenger
     * playing as an 'TDPlayerHandCraftedFeatures' class.
     * @param playersFile players file to load.
     * @param playersInputGen players input generator to use.
     * @param numOutputs number of output used by players input gen.
     */    
    public static void ELOGauntlet4PlayerWithHandCraftedFeatureTDAI(String playersFile,
            TDInputGenerator playersInputGen, int numOutputs) {
        boolean playerIsAIController = false;
        boolean playerIsUsingHandCraftedFeatures = true;
        ELOGauntlet.ELOGauntlet4Players(playersFile, playersInputGen,
                numOutputs, playerIsAIController, playerIsUsingHandCraftedFeatures);
    }
     
    /**
     * Starts a 4 Player ELO Gauntlet, where the challenger goes against a number
     * of opponents and the results are output to the console.
     * @param playersFile to load.
     * @param playersInputGen challenger's input generator.
     * @param numOutputs number of outputs to use.
     * @param playerIsAIController true if the player should be a member of the
     * 'AIController' class.
     * @param playerIsUsingHandCraftedFeatures true if the player should be a
     * member of the 'TDPlayerHandCraftedFeatures' class.
     */
    public static void ELOGauntlet4Players(String playersFile, 
            TDInputGenerator playersInputGen, int numOutputs,
            boolean playerIsAIController, boolean playerIsUsingHandCraftedFeatures) {
        System.out.println("Running 4 Player ELO Tests..");
        int gamesToPlay = 100;
        File file = new File("");
        String dirPath = file.getAbsolutePath() + File.separatorChar 
                + "ELOGauntletAIs" + File.separatorChar;
        
        if (playerIsAIController && playerIsUsingHandCraftedFeatures) {
            System.err.println("Cannot use AI controller and hand crafted"
                    + " features for this test");
            System.exit(0);
        }
        
        AbstractEvaluator playersEvaluator = new AbstractEvaluator(playersFile);
        Generalizer playersGeneralizer = new Generalizer(playersEvaluator);
        Critic playersCritic = new Critic(playersGeneralizer, numOutputs);
        playersCritic.setBlockLearning(true);
        boolean useExploratoryMoves = false;
        
        /**
         * Vs random AI.
         */
        int numHumanPlayers = 0;
        int numRandomPlayers = 2;
        int numFullTDPlayers = 0;
        int numSimpleTDAIs = 0;
        int numDBGenRandomAIs = 0;
        int numSmartBots = 0;
        int numAIController = 0;
        int numTDHandcraftedFeatureAIs = 0;
        
        if (!playerIsAIController && !playerIsUsingHandCraftedFeatures) {
            numFullTDPlayers = 2;
        } else {
            if (playerIsAIController) {
                numAIController = 2;
            }
            if (playerIsUsingHandCraftedFeatures) {
                numTDHandcraftedFeatureAIs = 2;
            }
        }
        
        GameStats.setUpGameStats(numHumanPlayers + numRandomPlayers + numFullTDPlayers
                + numSimpleTDAIs + numDBGenRandomAIs + numSmartBots + numAIController);
        
        ActionManager am = ActionManager.getInstance();
        
        
        for (int i = 0; i < gamesToPlay; i++) {
            am.startNewGame(playersCritic, playersInputGen, false,
                                    numHumanPlayers, numRandomPlayers,
                                numFullTDPlayers, numSimpleTDAIs, numDBGenRandomAIs,
                                numSmartBots, numAIController, numTDHandcraftedFeatureAIs,
                                useExploratoryMoves, ActionManager.RUNTYPE_NORMALGAME);
        }
        int winsVRandom = am.getP2Victories() + am.getP3Victories();
        double percWinRandom = (double)winsVRandom/gamesToPlay;
        am.resetVictories();
        
        if (!playerIsAIController && !playerIsUsingHandCraftedFeatures) {
            numFullTDPlayers = 4;
        } else {
            numFullTDPlayers = 2;
        }
        
        /**
         * Vs TD AI V5 5,000 Money divided by 7500, Monopoly starts 50%.
         */
        int tdAIV5Outputs = 1;
        String tdAIV5_5K = "July-14-2013-4PlayerMoneyDiv7500MonopolyStarts-runs5000";
        TDInputGenerator otherPlayerGen = new FourthTDPlayerVer3();
        System.out.println("Running 2nd set of games..");
        int winsVTDAI5K = runTestForTDAIs(dirPath + tdAIV5_5K, tdAIV5Outputs, playersCritic,
                playersInputGen, gamesToPlay, numFullTDPlayers, otherPlayerGen, numAIController,
                numTDHandcraftedFeatureAIs);
        double percWinTDAI5K = (double)winsVTDAI5K/gamesToPlay;
        
        /**
         * Vs TD AI V5.1 5,000 DB Leaner Money divided by 7500, Monopoly starts 50%.
         */
        String tdAIV5_1_5K = "July-15-2013-4PlayerMoneyDBLearnerMonopolyAndNormalStart-runs5000";
        otherPlayerGen = new FourthTDPlayerVer3();
        System.out.println("Running 3rd set of games..");
        int winsVTDAI5_1_5K = runTestForTDAIs(dirPath + tdAIV5_1_5K, tdAIV5Outputs, playersCritic,
                playersInputGen, gamesToPlay, numFullTDPlayers, otherPlayerGen, numAIController,
                numTDHandcraftedFeatureAIs);
        double percWinTDAI5_1_5K = (double)winsVTDAI5_1_5K/gamesToPlay;
        
        /**
         * Vs TD AI V5.1 5,000 DB Leaner Money divided by 7500, Monopoly starts 50%.
         */
        String tdAIV5_32_5K = "July-16-2013-4PlayerMoneyDBLearnerMonopolyAndNormalStart80Hidden-runs5000";
        otherPlayerGen = new FifthTDPlayerVer32(false);
        System.out.println("Running 4th set of games..");
        int winsVTDAI5_32_5K = runTestForTDAIs(dirPath + tdAIV5_32_5K, tdAIV5Outputs, playersCritic,
                playersInputGen, gamesToPlay, numFullTDPlayers, otherPlayerGen, numAIController,
                numTDHandcraftedFeatureAIs);
        double percWinTDAI5_32_5K = (double)winsVTDAI5_32_5K/gamesToPlay;
        
        /**
         * Vs TD AI V5.1 5,000 DB Leaner Money divided by 7500, Monopoly starts 50%.
         */
        String tdAIV5_36_5K = "July-16-2013-4PlayerMoneyDBLearnerMonopolyAndNormalStart160Hidden-runs5000";
        otherPlayerGen = new FifthTDPlayerVer36(false);
        System.out.println("Running 5th set of games..");
        int winsVTDAI5_36_5K = runTestForTDAIs(dirPath + tdAIV5_36_5K, tdAIV5Outputs, playersCritic,
                playersInputGen, gamesToPlay, numFullTDPlayers, otherPlayerGen, numAIController,
                numTDHandcraftedFeatureAIs);
        double percWinTDAI5_36_5K = (double)winsVTDAI5_36_5K/gamesToPlay;
        
        System.out.println("Max money: " + ActionManager.getMaxMoney());
        GameStats.printHighestWinValue();
        
        
        System.out.println("Results for " + playersFile);
        System.out.println("Wins vs Random AI: " + winsVRandom 
                + " ("  + percWinRandom + "%)");
        System.out.println("Wins vs TD V5: " + winsVTDAI5K
                + " ("  + percWinTDAI5K + "%)");
        System.out.println("Wins vs TD V5.1: " + winsVTDAI5_1_5K
                + " ("  + percWinTDAI5_1_5K + "%)");
        System.out.println("Wins vs TD V5.32: " + winsVTDAI5_32_5K
                + " (" + percWinTDAI5_32_5K + "%");
        System.out.println("Wins vs TD V5.36: " + winsVTDAI5_36_5K
                + " (" + percWinTDAI5_36_5K + "%");
        
        try {
            FileWriter fw = new FileWriter(playersFile + "ELOResults.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Results for " + playersFile);
            bw.newLine();
            bw.write("Wins vs Random AI: " + winsVRandom 
                + " ("  + percWinRandom + "%)");
            bw.newLine();
            bw.write("Wins vs TD V5: " + winsVTDAI5K
                + " ("  + percWinTDAI5K + "%)");
            bw.newLine();
            bw.write("Wins vs TD V5.1: " + winsVTDAI5_1_5K
                + " ("  + percWinTDAI5_1_5K + "%)");
            bw.newLine();
            bw.write("Wins vs TD V5.32:" + winsVTDAI5_32_5K
                + " ("  + percWinTDAI5_32_5K + "%)");
            bw.newLine();
            bw.write("Wins vs TD V5.36: " + winsVTDAI5_36_5K
                + " ("  + percWinTDAI5_1_5K + "%)");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
            e.printStackTrace();
        }
        
    }
    
    /**
     * Runs Tests for the player against the TD AIs.
     * @param otherPlayerFile name of the file that the player should play against.
     * @param outputNodes the number of output nodes to be used.
     * @param playersCritic the challenger's critic.
     * @param playersInputGen the challenger's input gen.
     * @param gamesToPlay the number of games to play.
     * @param numTDPlayers the number of TD Players to use.
     * @param otherPlayersInputGen the other players input generator.
     * @param numAIControllerPlayers the number of AIController Players to use.
     * @param numTDHandcraftedFeatureAIs the number of TDHandCraftedFeatureAIs to use.
     * @return the number of games won by the challenger.
     */
    private static int runTestForTDAIs(String otherPlayerFile, int outputNodes,
            Critic playersCritic, TDInputGenerator playersInputGen, int gamesToPlay,
            int numTDPlayers, TDInputGenerator otherPlayersInputGen, int numAIControllerPlayers,
            int numTDHandcraftedFeatureAIs) {
        AbstractEvaluator otherPlayerEval = new AbstractEvaluator(otherPlayerFile);
        Generalizer otherPlayerGen = new Generalizer(otherPlayerEval);
        Critic otherPlayerCritic = new Critic(otherPlayerGen, outputNodes);
        otherPlayerCritic.setBlockLearning(true);
        List<Critic> criticList = new ArrayList();
        criticList.add(playersCritic);
        criticList.add(playersCritic);
        List<TDInputGenerator> inputGenList = new ArrayList();
        inputGenList.add(playersInputGen);
        inputGenList.add(playersInputGen);
        
        int numTDPlayersToCreate = numTDPlayers - 2 + numAIControllerPlayers 
                + numTDHandcraftedFeatureAIs;
        for (int i = 0; i < numTDPlayersToCreate; i++) {
            criticList.add(otherPlayerCritic);
            inputGenList.add(otherPlayersInputGen);
        }
        int winsVOtherAI = runGames(gamesToPlay, criticList, inputGenList, numTDPlayers,
                numAIControllerPlayers, numTDHandcraftedFeatureAIs);
        ActionManager.getInstance().resetVictories();
        return winsVOtherAI;
    }
    
    
    /**
     * Runs a single set of games to test the basic skill of a newly made player.
     * @param playersFile players file to load.
     * @param playersInputGen players input gen to load.
     * @param numOutputs number of outputs used by a players input generator.
     */
    public static void ELOTest4Players(String playersFile, 
            TDInputGenerator playersInputGen, int numOutputs) {
        System.out.println("Running 4 Player ELO Tests..");
        int gamesToPlay = 100;
        File file = new File("");
        String dirPath = file.getAbsolutePath() + File.separatorChar 
                + "ELOGauntletAIs" + File.separatorChar;
        
        AbstractEvaluator playersEvaluator = new AbstractEvaluator(playersFile);
        Generalizer playersGeneralizer = new Generalizer(playersEvaluator);
        Critic playersCritic = new Critic(playersGeneralizer, numOutputs);
        playersCritic.setBlockLearning(true);
        
        
        /**
         * Vs TD AI V5.1 5,000 DB Leaner Money divided by 7500, Monopoly starts 50%.
         */
        String tdAIV5_32_5K = "July-16-2013-4PlayerMoneyDBLearnerMonopolyAndNormalStart80Hidden-runs5000";
        TDInputGenerator otherPlayerGen = new FifthTDPlayerVer32(false);
        
        int winsVTDAI5_32_5K = runTestForTDAIs(dirPath + tdAIV5_32_5K, 1, playersCritic,
                playersInputGen, gamesToPlay, 4, otherPlayerGen, 0, 0);
        double percWinTDAI5_32_5K = (double)winsVTDAI5_32_5K/gamesToPlay;
        
        
        System.out.println("Max money: " + ActionManager.getMaxMoney());
        GameStats.printHighestWinValue();
        
        
        System.out.println("Results for " + playersFile);
        System.out.println("Wins vs TD V5.32: " + winsVTDAI5_32_5K
                + " (" + percWinTDAI5_32_5K + "%");
       
    }
    
        
    /**
     * Runs a series of tests vs the SmartBot and outputs the results.
     * @param playersFile players file to load.
     * @param playersInputGen players input gen to load.
     * @param numOutputs number of outputs used by a players input generator.
     */
    public static void TestVsBot1(String playersFile, TDInputGenerator playersInputGen,
            int numOutputs) {
        System.out.println("Running tests vs Bot");
        int gamesToPlay = 100;
        
        AbstractEvaluator playersEvaluator = new AbstractEvaluator(playersFile);
        Generalizer playersGeneralizer = new Generalizer(playersEvaluator);
        Critic playersCritic = new Critic(playersGeneralizer, numOutputs);
        playersCritic.setBlockLearning(true);
        boolean useExploratoryMoves = false;
        
        ActionManager am = ActionManager.getInstance();
        
        int numHumanPlayers = 0;
        int numRandomPlayers = 0;
        int numFullTDPlayers = 0;
        int numSimpleTDAIs = 0;
        int numDBGenRandomAIs = 0;
        int numBotAIs = 2;
        int numAIController = 2;
        int numTDHandcraftedFeatureAIs = 0;
        
        List<Integer> modeToWins = new ArrayList();
        for (int j = 0; j <= AIControllerV3_2.MAX_MODE_NUM; j++) {
            for (int i = 0; i < gamesToPlay; i++) {
                am.startNewGame(playersCritic,playersInputGen, false,
                                    numHumanPlayers, numRandomPlayers,
                                numFullTDPlayers, numSimpleTDAIs, numDBGenRandomAIs,
                                numBotAIs, numAIController, numTDHandcraftedFeatureAIs,
                                useExploratoryMoves, ActionManager.RUNTYPE_NORMALGAME);
                System.out.println("Number of rounds " + Game.getInstance().getRoundCount());
            }
            int totalWins = am.getP0Victories() + am.getP1Victories();
            modeToWins.add(totalWins);
            System.out.println("Victories vs bot: " + totalWins);
            am.resetVictories();
            Main.AIMode++;
        }
        
        System.out.println("Total wins with each mode: ");
        for (int i = 0; i < modeToWins.size(); i++) {
            System.out.println("Mode " + i + " wins: " + modeToWins.get(i));
        }
        
    }
    
}
