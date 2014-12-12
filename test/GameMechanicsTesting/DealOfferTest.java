package GameMechanicsTesting;

import GeneralTestMethods.GeneralTestMethods;
import Model.Cards.Card;
import Model.Cards.GetOutOfJailCard;
import Model.DealOffer;
import Model.Game;
import Model.Players.*;
import Model.Spaces.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the deal offers work as they should in game.
 * @author Chris Berry
 */
public class DealOfferTest extends GeneralTestMethods{
    
    public DealOfferTest() {
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
     * Tests each aspect of a deal offer and ensure the correct party receives
     * what they are supposed to.
     */
    @Test
    public void dealOfferTest() {
        receiveMoneyTest();
        askForMoneyTest();
        receiveSpacesTest();
        requestSpaceTest();
        gOJCardOfferTest();
        gOJCardRequestTest();
    }
    
    /**
     * Checks that offering cash works as expected, and the player the cash
     * is offered to receives it when the deal is accepted.
     */
    private void receiveMoneyTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        int moneyToReceive = 500;
        int startingMoney = testPlayer.getMoney();
        int expectedMoney = startingMoney + moneyToReceive;
        offer.offerCash(moneyToReceive);
        testPlayer.assessAnOffer(offer,otherPlayer);
        playerReceivedCorrectAmount(expectedMoney);
    }
    
    /**
     * Checks that requesting cash works as expected, and the player requesting
     * the cash receives it when the deal is accepted.
     */
    private void askForMoneyTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        int moneyToAskFor = 500;
        int startingMoney = testPlayer.getMoney();
        int expectedMoney = startingMoney - moneyToAskFor;
        offer.requestCash(moneyToAskFor);
        testPlayer.assessAnOffer(offer,otherPlayer);
        playerReceivedCorrectAmount(expectedMoney);
    }
    
    /**
     * Checks the player is left with the predicted amount of money.
     * @param predictedAmount amount of money player is predicted to be left with.
     */
    private void playerReceivedCorrectAmount(int predictedAmount) {
        if (testPlayer.getMoney() == predictedAmount) {
            System.out.println("Player has " + predictedAmount + " which is the"
                    + " amount that was expected.");
        } else {
            fail("Player was expected to be left with " + predictedAmount +
                    " but was instead left with " + testPlayer.getMoney());
        }
    }
    
    /**
     * Checks that receiving properties works as expected, and the player the
     * offer is made to receives them when the deal is accepted.
     */
    private void receiveSpacesTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        List<Space> spacesInOffer = new ArrayList();
        //Spaces to add
        Space site1 = Game.getInstance().getBoard().getAllSites().get(0);
        Space site2 = Game.getInstance().getBoard().getAllSites().get(4);
        Space station1 = Game.getInstance().getBoard().getAllStations().get(0);
        Space util1 = Game.getInstance().getBoard().getAllUtilities().get(0);
        offer.addPropertyToOffer(site1);
        offer.addPropertyToOffer(site2);
        offer.addPropertyToOffer(station1);
        offer.addPropertyToOffer(util1);
        spacesInOffer.add(site1);
        spacesInOffer.add(site2);
        spacesInOffer.add(station1);
        spacesInOffer.add(util1);
        
        testPlayer.assessAnOffer(offer, otherPlayer);
        //Test players should automattically accept.
        if (doesPlayerOwnProperties(spacesInOffer, testPlayer)) {
            
        } else {
            fail("Player has not been given the correct properties.");
        }
    }
    
    /**
     * Checks if a player owns a list of properties.
     * @param properties to check.
     * @param player to check is the owner of.
     * @return true if the player owns all the properties.
     */
    private boolean doesPlayerOwnProperties(List<Space> properties,
            Player player) {
        for (Space property : properties) {
            if (!property.getOwner().equals(player)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks that requesting properties works as expected, and the player the
     * offer is made to requesting them when the deal is accepted.
     */
    private void requestSpaceTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        List<Space> spacesInOffer = new ArrayList();
        //Spaces to add
        Space site1 = Game.getInstance().getBoard().getAllSites().get(0);
        Space site2 = Game.getInstance().getBoard().getAllSites().get(4);
        Space station1 = Game.getInstance().getBoard().getAllStations().get(0);
        Space util1 = Game.getInstance().getBoard().getAllUtilities().get(0);
        offer.addPropertyToRequest(site1);
        offer.addPropertyToRequest(site2);
        offer.addPropertyToRequest(station1);
        offer.addPropertyToRequest(util1);
        spacesInOffer.add(site1);
        spacesInOffer.add(site2);
        spacesInOffer.add(station1);
        spacesInOffer.add(util1);
        for (Space space : spacesInOffer) {
            space.setOwner(testPlayer);
        }
        
        testPlayer.assessAnOffer(offer, otherPlayer);
        //Test players should automattically accept.
        if (!doesPlayerOwnProperties(spacesInOffer, testPlayer)) {
            System.out.println("Test succesful: test player no longer owns"
                    + " the properties");
        } else {
            fail("The player still owns some of the properties which were "
                    + "supposed to be requested in the deal");
        }
    }
    
    /**
     * Checks that receiving GOOJ Cards works as expected, and the player the
     * offer is made to receives them when the deal is accepted.
     */
    private void gOJCardOfferTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        int numberOfJailCardsToOffer = 2;
        Card getOutOfJailCard = new Card("Get out of jail",
                new GetOutOfJailCard(), false, true);
        //Other player has 2 cards.
        for (int i = 0; i < numberOfJailCardsToOffer; i++) {
            otherPlayer.addGetOutOfJailCard(getOutOfJailCard);
        }
        //Offers them to the test player.
        offer.offerGetOutOfJailCards(numberOfJailCardsToOffer);
        testPlayer.assessAnOffer(offer,otherPlayer);
        //Test player should now have 2 cards.
        if (testPlayer.getNumberOfGetOutOfJailCards() 
                == numberOfJailCardsToOffer) {
            System.out.println("Test Successful: Player has received the correct"
                    + " amount of get out of jail cards (" + 
                    numberOfJailCardsToOffer + ")");
        } else {
            fail("The player hasn't received the correct amount of get out"
                    + " of jail cards. He has " +
                    testPlayer.getNumberOfGetOutOfJailCards() +
                    " when he should have " +
                    numberOfJailCardsToOffer);
        }
    }
    
    /**
     * Checks that requesting GOOJ Cards works as expected, and the player the
     * offer is made to requesting them when the deal is accepted.
     */
    private void gOJCardRequestTest() {
        super.setUpGameWithOnePlayerAndOtherPlayer(Game.PLAYER_STARTING_MONEY);
        DealOffer offer = new DealOffer();
        Card getOutOfJailCard = new Card("Get out of jail",
                new GetOutOfJailCard(), false, true);
        int numberOfJailCardsToRequest = 2;
        //Test player has 2 cards.
        for (int i = 0; i < numberOfJailCardsToRequest; i++) {
            testPlayer.addGetOutOfJailCard(getOutOfJailCard);
        }
        //Request the get out of jail cards from the player.
        offer.requestGetOutOfJailCards(numberOfJailCardsToRequest);
        testPlayer.assessAnOffer(offer,otherPlayer);
        //Other player should now have two get out of jail cards.
        if (otherPlayer.getNumberOfGetOutOfJailCards() 
                - numberOfJailCardsToRequest == 0) {
            System.out.println("Test Successful: Player has given away the correct"
                    + " amount of get out of jail cards (" + 
                    numberOfJailCardsToRequest + ")");
        } else {
            fail("The player hasn't given away the correct amount of get out"
                    + " of jail cards. He has " +
                    testPlayer.getNumberOfGetOutOfJailCards() +
                    " when he should have 0");
        }
    }
    
    
}
