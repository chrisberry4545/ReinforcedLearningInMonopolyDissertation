package Model.Players;

import Model.Game;
import Model.Players.TDInputGenerators.TDInputGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A random AI which also generates a database of the inputs before it takes
 * each move.
 * @author Chris Berry
 */
public class DatabaseGenRandomAI extends RandomAI {
    
    private static int lineCount = 0;
    private TDInputGenerator inputGen;
    private double[] previousInputs;
    private int actionsTaken;
    public static final int MAX_ACTIONS_TAKEN = 1;
    
    /**
     * Creates a new Database generating AI.
     * @param number the token number of the AI.
     * @param inputGen the input generator which should be used to generate
     * the inputs to be saved in a database.
     */
    public DatabaseGenRandomAI(int number, TDInputGenerator inputGen) {
        super(number);
        this.inputGen = inputGen;
        this.actionsTaken = 0;
    }
    
    /**
     * Asks the player to make there move options. The player is only allowed
     * to make a limited number of moves to reduce the size of the database.
     * The inputs are saved to the database.
     * @return true when the player has finished their turn.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        if (actionsTaken >= MAX_ACTIONS_TAKEN) {
            actionsTaken = 0;
            return true;
        } else {
           writeStateToDatabase();
           actionsTaken++;
           return super.askPlayerMoveOptions(); 
        }
    }
    
    /**
     * Asks the player how they will act from Jail. The player is only allowed
     * a limited number of moves to reduce the size of the database.
     * The inputs are saved to the database.
     * @return 
     */
    @Override
    public int askPlayerInJailOptions() {
        if (actionsTaken >= MAX_ACTIONS_TAKEN) {
            actionsTaken = 0;
            return Player.STILL_IN_JAIL_TURN_OVER;
        } else {
            writeStateToDatabase();
            actionsTaken++;
            return super.askPlayerInJailOptions();
        }
    }
    
    /**
     * Writes the current state to a database so other AIs can learn from it.
     */
    public void writeStateToDatabase() {
        lineCount++;
        double[] inputs = inputGen.getCurrentInputs(this.getNumber());
        previousInputs = inputs;
        writeToFile(inputs);
    }
    
    /**
     * Writes the inputs array to a file.
     * @param a the input array.
     */
    public static void writeToFile(double[] a){
        try {
            FileWriter fw = new FileWriter("MoveDB.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (double n: a){
                bw.write(n + ",");
            }
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
        }
    }
    
    /**
     * Player wins are handled slightly differently when saved in a database.
     * This method handles those differences.
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
            writeToFile(previousInputs);
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
        }
        System.out.println("Line count: "+ lineCount);
        
    }
    
    /**
     * Player losses are handled slightly differently when saved in a database.
     * This method handles those differences.
     */
    @Override
    public void hasLost() {
        lineCount++;
        try {
            FileWriter fw = new FileWriter("MoveDB.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("End," + false + "," + false);
            bw.newLine();
            writeToFile(previousInputs);
            bw.close();
        } catch (IOException e) {
            System.err.print("Unable to write to file " + "MoveDB.csv"+ ".");
        }
        System.out.println("Line count: "+ lineCount);
    }
}
