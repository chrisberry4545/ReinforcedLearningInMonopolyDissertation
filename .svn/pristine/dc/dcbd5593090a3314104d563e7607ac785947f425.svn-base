package Model.Players;

import Model.DealOffer;
import Model.Game;
import Model.Players.NeuralNetwork.CriticPackage.Critic;
import Model.Players.TDInputGenerators.TDInputGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * TD Move Evaluator with a new method of deciding what is the best deal to make.
 * Unlike the previous version, the AI only cares about his own payoff and making
 * sure the other AI will accept. The other method worked well in 2 player games
 * but will not be as good in 4 player games where minimising another's result
 * could mean worsening the players own payoff from a deal.
 * @author Chris Berry
 */
public class TDMoveEvaluatorDealSubset extends TDMoveEvaluator{

    /**
     * Cash increments reflects the value of cash the AI will use when deciding
     * how much to pay for something. It will assess the payoffs of paying
     * from 0 to its maximum amount of money and every step in between which
     * is of the appropriate increment.
     */
    private final int offersToConsider = 40;
    private final int propertiesInOffer = 4;
    
    /**
     * Creates a new TDMoveEvaluator using Deal subsets as a method of deciding
     * the best deal to make.
     * @param critic to be used by the player.
     * @param inputGen to be used by the player.
     * @param owningPlayer player who owns the move evaluator.
     */
    public TDMoveEvaluatorDealSubset(Critic critic,
            TDInputGenerator inputGen, Player owningPlayer) {
        super(critic, inputGen, owningPlayer);
    }
       
    /**
     * Gets the best offer a player can make from a random selection of deals.
     * Note: for the time being offers won't include get out of jail cards.
     * @return Map.Entry<Double, Map.Entry <Player, DealOffer > > which in order
     * represent: the value of the deal happening, the player the deal should
     * be made with, and the deal itself.
     * Returns null if the best option is to do nothing
     */
    @Override
    public Map.Entry<Double, Map.Entry<Player, DealOffer> >
            getOfferMostWorthMaking() {
        double bestOfferValue = 0;
        DealOffer bestOffer = null;
        Player bestOfferPlayerToOfferTo = null;
        for (int numOffers = 0; numOffers < offersToConsider; numOffers++) {
            Random random = new Random();
            Player currentPlayer = super.getOwningPlayer();
            int numPropertiesToOffer = random.nextInt(propertiesInOffer);
            if (currentPlayer.getProperties().size() < numPropertiesToOffer) {
                numPropertiesToOffer = currentPlayer.getProperties().size();
            }
            
            int playerNumToOfferTo = random.nextInt(Game.getInstance().getPlayers().size());
            Player playerToOfferTo = Game.getInstance().getPlayers().get(playerNumToOfferTo);

            int numPropertiesToRequest = random.nextInt(propertiesInOffer);
            if (playerToOfferTo.getProperties().size() < numPropertiesToRequest) {
                numPropertiesToRequest = playerToOfferTo.getProperties().size();
            }
            
            DealOffer offer = new DealOffer();

            for (int i = 0; i < numPropertiesToOffer; i++) {
                int propertyNumber 
                        = random.nextInt(currentPlayer.getProperties().size());
                offer.addPropertyToOffer(currentPlayer.getProperties().get(propertyNumber));
            }

            for (int i = 0; i < numPropertiesToRequest; i++) {
                int propertyNumber
                        = random.nextInt(playerToOfferTo.getProperties().size());
                offer.addPropertyToRequest(playerToOfferTo.getProperties().get(propertyNumber));
            }

            //Money
            if (random.nextInt(2) == 0) {
                if (currentPlayer.getMoney() > 0) {
                    int money = random.nextInt(currentPlayer.getMoney());
                    offer.offerCash(money);
                }
            } else {
                if (playerToOfferTo.getMoney() > 0) {
                    int money = random.nextInt(playerToOfferTo.getMoney());
                    offer.requestCash(money);
                }
            }
            
            double valueOfOffer = 
                    super.getValueOfOfferForThisPlayer(offer, playerToOfferTo, currentPlayer);
            double otherPlayersValuation =
                    super.getValueOfOfferForOtherPlayer(offer, playerToOfferTo, currentPlayer);
            double otherPlayersCurrentResults = super.getCurrentResults(playerToOfferTo.getNumber());
            if (otherPlayersValuation > otherPlayersCurrentResults
                    && valueOfOffer > bestOfferValue) {
                bestOfferValue = valueOfOffer;
                bestOffer = offer;
                bestOfferPlayerToOfferTo = playerToOfferTo;
            }
            
            
            
        }
        
        if (bestOffer == null || bestOfferPlayerToOfferTo == null) {
            return null;
        }
        Map<Player,DealOffer> playerOfferMap = new HashMap();
        playerOfferMap.put(bestOfferPlayerToOfferTo, bestOffer);
        Map<Double, Map.Entry<Player,DealOffer> > resultMap = new HashMap();
        resultMap.put(bestOfferValue, super.generateMapEntry(playerOfferMap));
        
        
        
        
        return super.generateMapEntry(resultMap);
    }
    
}
