package PlayerTests.TDPlayerTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Ensures a TD AI selects the best options from a group of results.
 * @author Chris Berry
 */
public class ChooseBestOptionTest extends TDTestEnvirorment{
    
    public ChooseBestOptionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Checks that from a list of values the TD AI is choosing the one 
     * which gives it the highest payoff.
     */
    @Test
    public void testChooseBestOption() {
        super.setUpTDTestEnvirorment();
        
        //Only the first result of the list should matter.
        Double resultsDoubleZero = 1.0;
        Double resultsDoubleOne = 12.0;
        Double resultsDoubleTwo = 6.0;
        Double resultsDoubleThree = 3.0;
        List<Double> resultsDoubleList = new ArrayList();
        resultsDoubleList.add(resultsDoubleZero);
        resultsDoubleList.add(resultsDoubleOne);
        resultsDoubleList.add(resultsDoubleTwo);
        resultsDoubleList.add(resultsDoubleThree);
       
        Map.Entry<Integer,Double> bestResult = 
                tdPlayerOne.getMoveEvaluator().pickFromResults
                (resultsDoubleList);
        
        pickedBestResults(bestResult, 1, 12);
        
        
    }
    
    /**
     * Checks the TD Player is choosing the best result.
     * @param bestResult
     * @param bestResultNum
     * @param bestValue 
     */
    private void pickedBestResults(Map.Entry<Integer,Double> bestResult,
            int bestResultNum, double bestValue) {
        if (bestResult.getKey() == bestResultNum) {
            System.out.println("Test Succesful: The TD AI has picked the correct"
                    + " best result, result number " + bestResultNum);
        } else {
            fail("The TD AI has chosen the result number " + bestResult.getKey()
                     + " instead of the actual best result which should have"
                    + " been " + bestResultNum);
        }
        pickedBestValue(bestResult.getValue(), bestValue);
    }
   
    /**
     * Checks the TD Player is choosing the best value.
     * @param bestResultValue 
     * @param bestValue 
     */
    private void pickedBestValue(double bestResultValue, double bestValue) {
        if (bestResultValue == bestValue) {
            System.out.println("Test Succesful: The TD AI has picked the correct"
                    + " best value, which is " + bestValue);
        } else {
            fail("The TD AI has chosen the value " + bestResultValue
                     + " instead of the actual best value which should have"
                    + " been " + bestValue);
        }
    }
}
