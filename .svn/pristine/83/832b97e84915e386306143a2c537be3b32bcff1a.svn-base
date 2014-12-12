package Model;

import Model.Cards.*;
import Model.Spaces.SpaceEnums;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods allowing chance and community Cards decks to be created.
 * @author Chris Berry
 */
public class CreateCards {
    
    public final static String GET_OUT_OF_JAIL = "Get out of Jail free.";
    private final static boolean chanceCardDeck = true;
    private final static boolean commChestDeck = false;
    
    /**
     * Creates all the chance cards.
     */
    public static List<Card> createChanceCards(){
        List<Card> chanceCards = new ArrayList();
        
        //Change money cards.
        chanceCards.add(new Card("Your building loan matures. Collect $150.",
                new ChangeMoneyCard(150), true, chanceCardDeck));
        chanceCards.add(new Card("Bank pays you dividend of $50.",
                new ChangeMoneyCard(50), true, chanceCardDeck));
        chanceCards.add(new Card("Speeding fine $15.",
                new ChangeMoneyCard(-15), true, chanceCardDeck));
        
        //Get out of jail
        chanceCards.add(new Card(GET_OUT_OF_JAIL,
                new GetOutOfJailCard(), false, chanceCardDeck));
        
        //Pay each player 50.
        chanceCards.add(new Card("You have been elected chairman of the board."
                + " Pay each player $50.",
                new ChairmanOfTheBoard(), true, chanceCardDeck));
        
        //Repairs
        chanceCards.add(new Card("Make general repairs on all your property:"
                + " For each house pay $25, for each hotel pay $100.",
                new GeneralRepairs(25, 100), true, chanceCardDeck));
        
        //Go back 3 spaces
        chanceCards.add(new Card("Go back three spaces",
                new MoveBackwards(), true, chanceCardDeck));
      
        //Movement cards
        chanceCards.add(new Card("Advance to Mayfair",
                new MovementCard(SpaceEnums.MAYFAIR_NUMBER, false), true, 
                chanceCardDeck));
        chanceCards.add(new Card("Advance to Pall Mall. If you pass 'GO' "
                + "collect $200",
                new MovementCard(SpaceEnums.PALL_MALL_NUMBER, true), true,
                chanceCardDeck));
        chanceCards.add(new Card("Advance to 'GO'. (Collect $200)",
                new MovementCard(SpaceEnums.GO_NUMBER, true), true, 
                chanceCardDeck));
        chanceCards.add(new Card("Take a trip to Kings Cross Station."
                + " If you pass 'GO' collect $200",
                new MovementCard(SpaceEnums.KINGS_CROSS_NUMBER, true), true, 
                chanceCardDeck));
        chanceCards.add(new Card("Advance to Trafalgar Square. If you pass 'GO' "
                + "collect $200",
                new MovementCard(SpaceEnums.TRAFALGAR_SQ_NUMBER, true), true, 
                chanceCardDeck));
        
        //Nearest station
        chanceCards.add(new Card("Advance to the nearest railway station. "
                + "If UNOWNED, you may buy it from the Bank. "
                + "If OWNED, pay owner twice the rental to which they are "
                + "otherwised entitled.",
                new NearestProperty(true, false), true, 
                chanceCardDeck));
        //Nearest station number 2.
        chanceCards.add(new Card("Advance to the nearest railway station. "
                + "If UNOWNED, you may buy it from the Bank. "
                + "If OWNED, pay owner twice the rental to which they are "
                + "otherwised entitled.",
                new NearestProperty(true, false), true, chanceCardDeck));
        //Nearest Util
        chanceCards.add(new Card("Advance to the nearest utility. "
                + "If UNOWNED, you may buy it from the Bank. "
                + "If OWNED, throw a dice and pay owner a total ten "
                + "times amount thrown.",
                new NearestProperty(true, true), true, chanceCardDeck));
        //Go to jail
        chanceCards.add(new Card("Go to jail. Go Directly to jail, "
                + "do not pass 'GO', do not collect $200.",
                new GoToJail(), true, chanceCardDeck));
        
        return chanceCards;
    }
    
    /**
     * Creates all of the community chest cards.
     */
    public static List<Card> createCommunityChestCards() {
        List<Card> commChest = new ArrayList();
        
        //Change Money cards.
        commChest.add(new Card("Bank error in your favour. Collect $200.", 
                new ChangeMoneyCard(200), true, commChestDeck));
        commChest.add(new Card("Doctor's fees. Pay $50.",
                new ChangeMoneyCard(-50), true, commChestDeck));
        commChest.add(new Card("Holiday fund matures. Receive $100.",
                new ChangeMoneyCard(100), true, commChestDeck));
        commChest.add(new Card("Life insurance matures. Collect $100.",
                new ChangeMoneyCard(100), true, commChestDeck));
        commChest.add(new Card("Pay school fees of $50.",
                new ChangeMoneyCard(-50), true, commChestDeck));
        commChest.add(new Card("Income tax refund. Collect $20.",
                new ChangeMoneyCard(20), true, commChestDeck));
        commChest.add(new Card("Pay hospital fees of $100.",
                new ChangeMoneyCard(-100), true, commChestDeck));
        commChest.add(new Card("Receive $25 consultancy fee.",
                new ChangeMoneyCard(25), true, commChestDeck));
        commChest.add(new Card("You inherit $100.",
                new ChangeMoneyCard(100), true, commChestDeck));
        commChest.add(new Card("From sale of stock you get $50.",
                new ChangeMoneyCard(50), true, commChestDeck));
        commChest.add(new Card("You have won second prize in a beauty"
                + " contest. Collect $10.",
                new ChangeMoneyCard(10), true, commChestDeck));
        
        //Get out of jail
        commChest.add(new Card(GET_OUT_OF_JAIL,
                new GetOutOfJailCard(), false, commChestDeck));
        
        //Repairs
        commChest.add(new Card("You are assessed for street repairs:"
                + " $40 per house, $115 per hotel.",
                new GeneralRepairs(40, 115), true, commChestDeck));
        
        //Birthday
        commChest.add(new Card("It is your birthday. Collect $10 from"
                + " every player.",
                new BirthdayPresents(), true, commChestDeck));
        
        //Advance to go
        commChest.add(new Card("Advance to 'GO'. (Collect $200)",
                new MovementCard(SpaceEnums.GO_NUMBER, true), true, 
                commChestDeck));
        
        //Go to jail
        commChest.add(new Card("Go to jail. Go Directly to jail, "
                + "do not pass 'GO', do not collect $200.",
                new GoToJail(), true, commChestDeck));
        
        return commChest;
    }
    
}
