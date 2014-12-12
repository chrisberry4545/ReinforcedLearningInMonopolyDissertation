/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CardTests;

import GeneralTestMethods.GeneralTestMethods;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Test template used for the card tests.
 * @author Chris Berry
 */
public class CardTestTemplate extends GeneralTestMethods {
    
    public CardTestTemplate() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("* Setting up test: setUp() method");
    }
    
    @After
    public void tearDown() {
        System.out.println("* Tearing down test: setUp() method");
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
}
