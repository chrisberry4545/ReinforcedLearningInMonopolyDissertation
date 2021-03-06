package monopoly;

import Model.Players.TDInputGenerators.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Main method for running the game of Monopoly with Reinforced Learning AIs.
 * @author Chris Berry
 */
public class Main {

    private static final int STARTING_RUNS = 8750;
    
    private static final String FILE_NAME_TO_SAVE_TO = "Aug22-OneOffMonopolyInputs-";
    
    private static final String FILE_TO_LOAD = FILE_NAME_TO_SAVE_TO + STARTING_RUNS;
    
    private static final int OTHER_NET_RUNS = 15000;
    
    private static final String OTHER_NET_NAME = "July-24-2013-160HiddenNodes-V2DealSubset";
    
    private static final String SECOND_FILE_TO_LOAD = OTHER_NET_NAME + OTHER_NET_RUNS;
    
    public static int AIMode = 0;
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int runType = 6;
        TDInputGenerator inputGen = new FifthTDPlayerVer32(true);
        System.out.println("Network is using " + inputGen.getNumHiddenNodes() 
                + " Hidden Nodes...");
        switch (runType) {
            case 0 :
                double lambda = 0.9;
                int runsBeforeSave = 250;
                int totalNumberOfRuns = 1250;
                boolean feedResultsToNet = false;
                int numberOfNetworks = 1;
                int numHumanPlayers = 0;
                int numRandomPlayers = 0;
                int numFullTDPlayers = 4;
                int numSimpleTDAIs = 0;
                int numDBGenRandomAIs = 0;
                int numSmartBots = 0;
                int numAIController = 0;
                int numTDHandcraftedFeatureAIs = 0;
                boolean differentRunTypes = true;
                boolean displayGraphics = false;
                boolean newNetwork = false;
                boolean fixedWeights = false;
                boolean blockLearning = false;
                boolean useWinTypeMatters = false;
            
            StandardRun.standardRun(inputGen, lambda, FILE_NAME_TO_SAVE_TO, FILE_TO_LOAD,
                SECOND_FILE_TO_LOAD, STARTING_RUNS, runsBeforeSave, totalNumberOfRuns,
                numberOfNetworks, numHumanPlayers, numRandomPlayers,
                numFullTDPlayers, numSimpleTDAIs,  numDBGenRandomAIs,
                numSmartBots, numAIController, numTDHandcraftedFeatureAIs,
                differentRunTypes, displayGraphics, newNetwork,
                fixedWeights, blockLearning, feedResultsToNet, useWinTypeMatters);
            
                break;
            case 1 :
                System.out.println("Sending " + FILE_TO_LOAD + " to ELO Gauntlet");
                ELOGauntlet.ELOGauntlet4PlayersStandard(FILE_TO_LOAD, inputGen, inputGen.getNumOutputNodes());
                break;
            case 2 :
                System.out.println("Sending " + FILE_TO_LOAD + " to Query tests");
                int roundCount = 30;
                MonopolyQueries.runAllQueryTests(FILE_TO_LOAD, inputGen, roundCount);
                break;
            case 3 :
                System.out.println("Sending " + FILE_TO_LOAD + " to Bot Test");
                ELOGauntlet.TestVsBot1(FILE_TO_LOAD, inputGen, inputGen.getNumOutputNodes());
                
                break;
            case 4 :
                System.out.println("Sending " + FILE_TO_LOAD + " to ELO Gauntlet");
                System.out.println("Player will use AI Controller");
                ELOGauntlet.ELOGauntlet4PlayerWithAIController(FILE_TO_LOAD, inputGen, inputGen.getNumOutputNodes());
                break;
            case 5 :
                System.out.println("Sending " + FILE_TO_LOAD + " to ELO Gauntlet");
                System.out.println("Player will use Hand Crafted TD AI Features");
                ELOGauntlet.ELOGauntlet4PlayerWithHandCraftedFeatureTDAI(FILE_TO_LOAD, inputGen, inputGen.getNumOutputNodes());
                break;
            case 6 :
                String bestPlayerFile = "July-24-2013-160HiddenNodes-V2DealSubset15000";
                int playerStartingRuns = 15000;
                TDInputGenerator inputGenWeb = new FifthTDPlayerVer36(true);
                double bestLambda = 0.9;
                int runsBeforeSaveIrrelevent = 5000;
                int totalNumberOfRunsIrelevent = 5000;
                boolean feedResultsToNet2 = false;
                int numberOfNetworks2 = 1;
                int numHumanPlayers2 = 1;
                int numRandomPlayers2 = 0;
                int numFullTDPlayers2 = 0;
                int numSimpleTDAIs2 = 0;
                int numDBGenRandomAIs2 = 0;
                int numSmartBots2 = 0;
                int numAIController2 = 3;
                int numTDHandcraftedFeatureAIs2 = 0;
                boolean differentRunTypes2 = false;
                boolean displayGraphics2 = true;
                boolean newNetwork2 = false;
                boolean fixedWeights2 = false;
                boolean blockLearning2 = false;
                boolean useWinTypeMatters2 = false;   
            
                URL website = null;
                try {
                    website = new URL("https://sites.google.com/site/monopolyaisite/game/networks/" + bestPlayerFile);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(bestPlayerFile);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } catch (MalformedURLException e) {
                    System.err.println(e);
                } catch (IOException e) {
                    System.err.println(e);
                }
            
            StandardRun.standardRun(inputGenWeb, bestLambda, bestPlayerFile, bestPlayerFile,
                SECOND_FILE_TO_LOAD, playerStartingRuns, runsBeforeSaveIrrelevent, 
                totalNumberOfRunsIrelevent,
                numberOfNetworks2, numHumanPlayers2, numRandomPlayers2,
                numFullTDPlayers2, numSimpleTDAIs2,  numDBGenRandomAIs2,
                numSmartBots2, numAIController2, numTDHandcraftedFeatureAIs2,
                differentRunTypes2, displayGraphics2, newNetwork2,
                fixedWeights2, blockLearning2, feedResultsToNet2, useWinTypeMatters2);
                break;
            case 7 :
                String bestPlayer = "July-24-2013-160HiddenNodes-V2DealSubset15000";
                TDInputGenerator bestInputGen = new FifthTDPlayerVer36(true);
                MonopolyDeficiencies.runAIDeficiencyExampleOne(bestPlayer, 
                        bestInputGen);
                break;
            case 8 :
                String bestPlayer2 = "July-24-2013-160HiddenNodes-V2DealSubset15000";
                TDInputGenerator bestInputGen2 = new FifthTDPlayerVer36(true);
                MonopolyDeficiencies.runAIDeficiencyExampleTwo(bestPlayer2, 
                        bestInputGen2);
                break;
                
        }
    }
    
}
