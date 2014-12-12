package Model.Players;

import Model.Game;
import Model.Players.TDInputGenerators.TDInputGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A bot which plays the same as a Smart bot but generates a database of
 * the moves it takes so other AIs can learn from this.
 * @author Chris Berry
 */
public class DatabaseGenBotAI extends SmartBot {
    
    private static int lineCount = 0;
    private TDInputGenerator inputGen;
    private double[] previousInputs;
    
    /**
     * Creates a new Database generating 'SmartBot' AI.
     * @param number the token number for the AI.
     * @param inputGen the input generator the AI will use to build a database.
     */
    public DatabaseGenBotAI(int number, TDInputGenerator inputGen) {
        super(number);
        this.inputGen = inputGen;
    }
    
    /**
     * Before each move is taken the state of the game is written to a database.
     * @return true when the player has finished their turn.
     */
    @Override
    public boolean askPlayerMoveOptions() {
           writeStateToDatabase();
           return super.askPlayerMoveOptions(); 
    }
    
    /**
     * Before each move is taken the state of the game is written to a database.
     * @return the result of the players turn.
     */
    @Override
    public int askPlayerInJailOptions() {
            writeStateToDatabase();
            return super.askPlayerInJailOptions();
    }
    
    /**
     * Writes the current state of the game to a database.
     */
    public void writeStateToDatabase() {
        lineCount++;
        double[] inputs = inputGen.getCurrentInputs(this.getNumber());
        previousInputs = inputs;
        DatabaseGenRandomAI.writeToFile(inputs);
    }
    
    /**
     * When the player has won the state has to be written to the database
     * in a different manner.
     */
    @Override
    public void hasWon() {
        lineCount++;
        try {
            FileWriter fw = new FileWriter("MoveDB.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            boolean gameHitMaxRounds = false;
            if (Game.getInstance().getRoundCount() == Game.MAX_ROUNDS) {
                gameHitMaxRounds = true;
            }
            bw.write("End," + true +"," + String.valueOf(gameHitMaxRounds));
            bw.newLine();
            DatabaseGenRandomAI.writeToFile(previousInputs);
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
        }
        System.out.println("Line count: "+ lineCount);
        
    }
    
    /**
     * When the player has lost the state has to be written to the database
     * in a different manner.
     */
    @Override
    public void hasLost() {
        lineCount++;
        try {
            FileWriter fw = new FileWriter("MoveDB.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("End," + false + "," + false);
            bw.newLine();
            DatabaseGenRandomAI.writeToFile(previousInputs);
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
        }
        System.out.println("Line count: "+ lineCount);
    }
    
}
