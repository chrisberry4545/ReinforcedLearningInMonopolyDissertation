/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoardOrderTests;

import GeneralTestMethods.GeneralTestMethods;
import Model.Board;
import Model.Game;
import Model.Spaces.SpaceEnums;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Confirms that the board is in the correct order as in a real game of 
 * Monopoly.
 * @author Chris Berry
 */
public class BoardOrderTest extends GeneralTestMethods {
    
    private Board board;
    
    public BoardOrderTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void boardOrderTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        board = Game.getInstance().getBoard();
        boardNumHasCorrectName(0,SpaceEnums.GO.getName());
        boardNumHasCorrectName(1,SpaceEnums.OLDKENTRD.getName());
        boardNumHasCorrectName(2,SpaceEnums.COMMCHEST.getName());
        boardNumHasCorrectName(3,SpaceEnums.WHITECHAPRD.getName());
        boardNumHasCorrectName(4,SpaceEnums.INCOMETAX.getName());
        boardNumHasCorrectName(5,SpaceEnums.KINGSXSTN.getName());
        boardNumHasCorrectName(6,SpaceEnums.ANGEL.getName());
        boardNumHasCorrectName(7,SpaceEnums.CHANCE.getName());
        boardNumHasCorrectName(8,SpaceEnums.EUSTONRD.getName());
        boardNumHasCorrectName(9,SpaceEnums.PENTONVILLERD.getName());
        boardNumHasCorrectName(10,SpaceEnums.JAIL.getName());
        boardNumHasCorrectName(11,SpaceEnums.PALLMALL.getName());
        boardNumHasCorrectName(12,SpaceEnums.ELECTRICUTIL.getName());
        boardNumHasCorrectName(13,SpaceEnums.WHITEHALL.getName());
        boardNumHasCorrectName(14,SpaceEnums.NORTHUMBERLANDAV.getName());
        boardNumHasCorrectName(15,SpaceEnums.MARLYEBONESTN.getName());
        boardNumHasCorrectName(16,SpaceEnums.BOWST.getName());
        boardNumHasCorrectName(17,SpaceEnums.COMMCHEST.getName());
        boardNumHasCorrectName(18,SpaceEnums.MARLBOROUGHST.getName());
        boardNumHasCorrectName(19,SpaceEnums.VINEST.getName());
        boardNumHasCorrectName(20,SpaceEnums.FREEPARKING.getName());
        boardNumHasCorrectName(21,SpaceEnums.STRAND.getName());
        boardNumHasCorrectName(22,SpaceEnums.CHANCE.getName());
        boardNumHasCorrectName(23,SpaceEnums.FLEETST.getName());
        boardNumHasCorrectName(24,SpaceEnums.TRAFALGARSQ.getName());
        boardNumHasCorrectName(25,SpaceEnums.FENCHURCHST.getName());
        boardNumHasCorrectName(26,SpaceEnums.LEICESTERSQ.getName());
        boardNumHasCorrectName(27,SpaceEnums.COVENTRYST.getName());
        boardNumHasCorrectName(28,SpaceEnums.WATERUTIL.getName());
        boardNumHasCorrectName(29,SpaceEnums.PICCADILLY.getName());
        boardNumHasCorrectName(30,SpaceEnums.GOTOJAIL.getName());
        boardNumHasCorrectName(31,SpaceEnums.REGENTST.getName());
        boardNumHasCorrectName(32,SpaceEnums.OXFORDST.getName());
        boardNumHasCorrectName(33,SpaceEnums.COMMCHEST.getName());
        boardNumHasCorrectName(34,SpaceEnums.BONDST.getName());
        boardNumHasCorrectName(35,SpaceEnums.LIVERPOOLSTSTN.getName());
        boardNumHasCorrectName(36,SpaceEnums.CHANCE.getName());
        boardNumHasCorrectName(37,SpaceEnums.PARKLANE.getName());
        boardNumHasCorrectName(38,SpaceEnums.SUPERTAX.getName());
        boardNumHasCorrectName(39,SpaceEnums.MAYFAIR.getName());
        
    }
    
    private void boardNumHasCorrectName(int boardNum, String correctName) {
        String boardSpaceName = board.get(boardNum).getName();
        if (boardSpaceName.equals(correctName)) {
            System.out.println("Test Succesful: the space at board number " +
                    boardNum + " should of been " + correctName + " and is "
                    + boardSpaceName + ".");
        } else {
            fail("The space at board number " +
                    boardNum + " should of been " + correctName + " and is "
                    + boardSpaceName + ".");
        }
    }
}
