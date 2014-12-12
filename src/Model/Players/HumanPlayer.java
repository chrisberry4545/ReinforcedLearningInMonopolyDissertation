package Model.Players;

import Display.*;
import Model.Bank;
import Model.DealOffer;
import Model.Game;
import Model.Spaces.Site;
import Model.Spaces.Space;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * A Human player able to take part in the game of Monopoly.
 * @author Chris Berry
 */
public class HumanPlayer extends Player{
    
    private BoardDisplay display;
    private final int waitTime = 100;
    
    /**
     * Creates a new Human player
     * @param setToken the token number the player will use.
     * @param display the board display used for the game.
     */
    public HumanPlayer(int setToken, BoardDisplay display) {
        super(setToken);
        this.display = display;
    }
    
    
    /**
     * Ask player yes/no question
     * @param message to ask.
     * @param title of message.
     * @return true if the user accepts the proposal, otherwise false.
     */
    public boolean askPlayerYesOrNoQuestion(String message, String title) {
        int response = JOptionPane.showConfirmDialog(display,
                message, title, JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
    
    /**
     * Asks the player if they want to buy a certain property.
     * @param property to ask the player if they want to buy.
     * @return true if the player buys the property, false if they don't.
     */
    @Override
    public boolean askPlayerIfTheyWantToBuyProperty(Space property) {
        boolean choice = askPlayerYesOrNoQuestion("Do you want to buy: "
                    + property.getName() + " for " 
                + property.getIntialCost() + " ?",
                "Buy Property.");
        if (this.getMoney() > property.getIntialCost()) {
            if (choice == true) {
                super.buyProperty(property);
                this.messagePlayer("You bought " + property.getName()
                                       + " for " + property.getIntialCost(),
                                    "You bought the property");
                return true;
            } else {
                return false;
            } 
        } else {
            Game.getInstance().startAuction(property, true);
            this.messagePlayer("You landed on "  + property.getName() 
                    + "but you can't afford to buy it",
                    "Landed on Property");
            return false;
        }
    }
    
    /**
     * Asks the player to pick from a number of options and returns the result.
     * @param options for player to pick from.
     * @param message to accompany the options.
     * @param title for the message box.
     * @return the choice the player made.
     */
    public int askPlayerToPickFromOptions(Object[] options, String message,
            String title) {
            int n = JOptionPane.showOptionDialog(display,
                    message, title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            return n;
    }
    
    /**
     * Messages the player with some information.
     * @param message to give player
     * @param title of message
     */
    @Override
    public void messagePlayer (String message, String title) {
        JOptionPane.showMessageDialog(display,
                message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Asks player to choose how they will act when they are in jail.
     * @return players choice.
     */
    @Override
    public int askPlayerInJailOptions() {
        Object[] options = {"Pay to leave jail", "Roll To leave",
                    "Use get out of jail card", "Buy houses", "Make a deal",
                        "Exit game"};
        int n = JOptionPane.showOptionDialog(display,
                    "In Jail",
                    "In Jail",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);   
        switch (n) {
            case Player.PAY_TO_LEAVE :
                if (this.payToLeaveJail()) {
                    this.messagePlayer("You succesfully leave jail.",
                            "Out of jail");
                    return Player.rollDice();
                } else {
                    this.messagePlayer("You don't have enough money "
                            + "to leave jail.", "Still in jail");
                    return Player.STILL_IN_JAIL_TURN_NOT_OVER;
                }
            case Player.ROLL_TO_LEAVE :
                int diceRoll = this.rollToLeaveJail();
                //Returns -1 if a double isn't rolled.
                if (diceRoll == -1) {
                    this.messagePlayer("You didn't roll a double.",
                            "Still in jail");
                    //Turn finishes in this case.
                    return Player.STILL_IN_JAIL_TURN_OVER;
                } else {
                    this.messagePlayer("You succesfully leave jail.",
                            "Out of jail");
                    return diceRoll;
                }
            case Player.USE_GET_OUT_OF_JAIL_CARD :
                if (this.useGetOutOfJailCard() != null) {
                    this.messagePlayer("Used get out of jail card",
                            "Out of Jail");
                    return Player.rollDice();
                } else {
                    this.messagePlayer("You don't have a get out of jail"
                            + " card", "Still in jail");
                    return Player.STILL_IN_JAIL_TURN_NOT_OVER;
                }
            case Player.JAIL_BUY_HOUSES :
                this.showHouseBuyingOptions();
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
            case Player.JAIL_MAKE_DEAL :
                this.makeAnOfferToAnotherPlayer();
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
            case Player.EXIT_GAME_STANDARD :
                if (askPlayerIfTheyWantToExit()) {
                    System.exit(0);
                }
                return Player.STILL_IN_JAIL_TURN_OVER;
            default : System.err.println("Invalid move selected from jail");
                return Player.STILL_IN_JAIL_TURN_NOT_OVER;
        }
    }
    
    /**
     * Gets the player's response to what they intend to do on their turn.
     * @return players choice.
     */
    @Override
    public boolean askPlayerMoveOptions() {
        while (display.getPlayerSelection() == -1) {
            synchronized(this) {
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
        
        int n = display.getPlayerSelection();
        switch(n) {
            case Player.ROLL_DICE : 
                display.setPlayerSelection(-1);
                return true;
            case Player.BUY_HOUSES :
                //Brings up options again after player makes a choice.
                showHouseBuyingOptions();
                break;
            case Player.OFFER_PROPERTY :
                this.makeAnOfferToAnotherPlayer();
                //Player can still continue their turn.
                break;
            case Player.MORTGAGE_PROPERTIES :
                this.mortgageProperties();
                //Player can still continue their turn.
                break;
            case Player.SELL_HOUSES :
                this.sellHouses();
                //Player can still continue their turn.
                break;
            case Player.BUY_BACK_MORTGAGED_PROPERTIES :
                this.buyBackMortgagedProperty();
                //Player can still continue their turn.
                break;
            case Player.EXIT_GAME_STANDARD :
                if (askPlayerIfTheyWantToExit()) {
                    System.exit(0);
                }
                break;
        }
        display.setPlayerSelection(-1);
        return false;
    }
    
    /**
     * Shows the house buying options for the current player.
     */
    private void showHouseBuyingOptions() {
        HouseBuildingMenu houseBuildingMenu = new HouseBuildingMenu(this);
        Site site = (Site)getPropertyFromActionFrame(houseBuildingMenu);
        if (site != null) {
                String title = "";
                String message = "";
                switch (site.addHouse()) {
                    case Site.HOUSE_ADDED :
                        message = "Added house to " + site.getName() +
                        ". This now has " + site.getHouses();
                        title = "Added house";
                        break;
                    case Site.TOO_MANY_HOUSES : 
                        message = "You already have the maximum amount of "
                                + "houses";
                        break;
                    case Player.NOT_ENOUGH_MONEY :
                        message = "You don't have enough money for another house";
                        title = "Not enough money";
                        break;
                }
                messagePlayer(message, title);
        }
    }
        
    
    /**
     * Allows user to select from a list of possible to build on and
     * returns the site selected.
     * @return the Site selected by the user.
     */
    @Override
    public Site choosePropertyForBuildingOn() {
        List<Site> sitesCanBeBuiltOn = getSitesWhichCanBeBuiltOn();
        return (Site)selectFromListOfProperties(sitesCanBeBuiltOn,
                "Choose a Property", "Choose a property to build on");
    }
    
    /**
     * Allows the player to sell houses on properties they own.
     */
    @Override
    public void sellHouses() {
        HouseSellingMenu menu = new HouseSellingMenu(this);
        Site site = (Site)getPropertyFromActionFrame(menu);
        if (site != null) {
            super.returnHouse(site, 1);
        }
    }
    
    /**
     * Gets the player to mortgage some properties.
     */    
    @Override
    public void mortgageProperties() {
        MortgagePropertiesMenu mortgagePropertiesMenu = new MortgagePropertiesMenu(this);
        Space selectedSpace = getPropertyFromActionFrame(mortgagePropertiesMenu);
        if (selectedSpace != null) {
            super.mortgageProperty(selectedSpace);
        }
    }
    
    /**
     * Allows the player to buy back a mortgaged property.
     */
    public void buyBackMortgagedProperty() {
        UnMortgagePropertiesMenu menu = new UnMortgagePropertiesMenu(this);
        Space selectedSpace = getPropertyFromActionFrame(menu);
        if (selectedSpace != null) {
            super.unmortgageProperty(selectedSpace);
        }
    }
    
    /**
     * Show the properties the player currently owns.
     */
    @Override
    public void showPlayerProperties() {
        String spacesOwned = "";
        for (Space space :this.getProperties()) {
            spacesOwned += space.getName();
            if (space.getClass().equals(Site.class)) {
                Site site = (Site) space;
                spacesOwned += " (" + site.getHouses() + ") ";
            }
            spacesOwned += '\n';
        }
        messagePlayer(spacesOwned, "Properties Owned");
    }
    
    /**
     * Asks the player to choose a property to offer to another player.
     */
    public void makeAnOfferToAnotherPlayer() {
        DealMakerDisplay dealDisplay =  new DealMakerDisplay(this, 
                Game.getInstance().getAllPlayersButCurrent(), display);
        while (!dealDisplay.isDisposed() && dealDisplay.getDealOffer() == null) {
            //Do nothing until theres a result.
            synchronized(this) {
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
        dealDisplay.dispose();
        if (dealDisplay.getDealOffer() != null) {
            Player otherPlayer = dealDisplay.getPlayerToOfferTo();
            DealOffer offer = dealDisplay.getDealOffer();
            boolean offerAccepted = otherPlayer.assessAnOffer(offer, this);
            if (offerAccepted) {
                this.messagePlayer("Your offer was accepted!", "Offer accepted");
            } else {
                this.messagePlayer("Your offer was refused!", "Offer refused");
            }
        }
    }
    
    /**
     * Gets the player to assess an offer.
     * @param offer for player to assess.
     * @param playerOffering player making the offer.
     * @return true if the player has accepted the offer, false if they don't.
     */
    @Override
    public boolean assessAnOffer(DealOffer offer, Player playerOffering) {
        DealOfferDisplay dealDisplay = new DealOfferDisplay(offer, playerOffering, display);
        while (!dealDisplay.isDisposed()) {
            synchronized(this) {
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
            
        if (dealDisplay.isDealAccepted()) {
            acceptDealFromPlayer(offer, playerOffering);
            return true;
        }
        return false;
    }
    
    /**
     * The player decides what to do when they bankrupt another player and 
     * receive all of their properties.
     * @param properties the properties received from the other player.
     */
    @Override
    public void receiveProperty(List<Space> properties) {
        for (Space property : properties) {
            property.setOwner(this);
            Object[] options = {"Pay Mortgage", "Pay Interest"};
            int n = askPlayerToPickFromOptions(options,
                    "Do you want to pay the mortgage price on "
                    + property.getName() + " of: "+ property.getMortgageRate() + '\n'
                    + "or do you want to pay the interest rate of: "
                    + property.getMortgageRepaymentRate(),
                    "Pay mortgage or interest");
            switch (n) {
                case 0 :
                    //If they can't afford the mortgage just let them mortgage it.
                    if (this.getMoney() > property.getMortgageRate()) {
                        property.unMortgageProperty();          
                    } else {
                        this.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                                Bank.getInstance());
                        messagePlayer("You can't afford to pay the mortgage rate"
                                + " so you will just pay the fee instead.",
                                "Can't afford mortgage repayment..");
                    }
                    break;
                case 1 :
                    this.forcedMoneyChange(-property.getMortgageRepaymentRate(),
                                Bank.getInstance());
            }
        }
    }
    
    /**
     * The player makes a bid on a property.
     * @param property to make a bid on.
     * @param minimumBid minimum amount the player must bid.
     * @param playersInBidding List of the other players in the bidding.
     * @return the amount the player wants to bid.
     */
    @Override
    public int makeABid(Space property, int minimumBid,
                                List<Player> playersInBidding) {
        //Player needs to have enough money.
        if (this.getMoney() >= minimumBid &&
                askPlayerYesOrNoQuestion("Do you want to bid on " + property.getName(),
                "Bid on property? - Minimum Bid: " + minimumBid)) {
            return showIntSpinner(minimumBid, this.getMoney());
        }
        return Player.LEAVE_BIDDING_PROCESS;
    }
    
    /**
     * The player is asked if they want to bid on a house or hotel when a house
     * shortage occurs.
     * @param minBid the minimum amount the player can bid.
     * @param isHouse true if bidding on a house, false if it is a hotel.
     * @param playersInBidding List of players still in bidding.
     * @return the players bid.
     */
    @Override
    public int makeABidOnHouse(int minBid, boolean isHouse,
                            List<Player> playersInBidding) {
        String houseOrHotel = "";
        if (isHouse) {
            houseOrHotel = "house";    
        } else {
            houseOrHotel = "hotel";
        }
        if (this.getSitesWhichCanBeBuiltOn().isEmpty()) {
            return Player.LEAVE_BIDDING_PROCESS;
        } 
        if (!isHouse) {
            boolean canBuildHotel = false;
            for (Site site : this.getSitesWhichCanBeBuiltOn()) {
                if (site.getHouses() == Site.MAX_HOUSES - 1) {
                    canBuildHotel = true;
                }
                if (!canBuildHotel) {
                    return Player.LEAVE_BIDDING_PROCESS;
                }
            }
        }
        if (this.getMoney() > minBid 
                && askPlayerYesOrNoQuestion("Do you want to bid on this " +
                houseOrHotel, "Place a bid?" )) {
            return showIntSpinner(minBid, this.getMoney());
        }
        return Player.LEAVE_BIDDING_PROCESS;
    }
    
    /**
     * Gives users dialog to select a property from a List of Property Names.
     * @param names List of the property names.
     * @return the space selected by the user.
     */
    private Space selectFromListOfProperties(List<? extends Space> names,
            String title, String message) {
        List<String> siteNames = new ArrayList();
        for (Space sites : names) {
            siteNames.add(sites.getName());
        }
        if (siteNames.size() > 0) {
            Object[] options = siteNames.toArray();
            int n = JOptionPane.showOptionDialog(display,
                    message, title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n != -1) {
                return names.get(n);
            } else {
                return null;
            }
        }
        return null;
    }
    
    /**
     * Allows the player to select an integer and returns this value.
     * @return int chosen by the player.
     */
    public int selectAPrice() {
        int maxPrice = 0;
        for (Player p : Game.getInstance().getPlayers()) {
            if (p.getMoney() > maxPrice) {
                maxPrice = p.getMoney();
            }
        }
        return showIntSpinner(1, maxPrice);
    }
    
    /**
     * Shows an Int Spinner for the player to select an Integer value.
     * @param minValue minimum value player can select.
     * @param maxValue maximum value player can select.
     * @return 
     */
    private int showIntSpinner(int minValue, int maxValue) {
        SpinnerNumberModel sModel = 
                new SpinnerNumberModel(minValue, minValue, maxValue, 1);
        JSpinner spinner = new JSpinner(sModel);
        JOptionPane.showMessageDialog(display, spinner);
        if (sModel.getNumber().intValue() < 1) {
            messagePlayer("Please choose a valid value", 
                    "Select a valid value");
            //Recursively calls itself until the user selects a valid value.
            sModel.setValue(selectAPrice());
        }
        Integer value = (Integer)sModel.getValue();
        return value;
    }
   
    
    /**
     * Informs the player they have won the game.
     */
    @Override
    public void hasWon() {
        this.messagePlayer("You have won", "You're the winner!");
    }
    
    /**
     * Informs the other players they have lost and generalizes appropriately.
     */
    @Override
    public void hasLost() {
        this.messagePlayer("You have Lost", "You lost the game!");
    }
    
    /**
     * Asks the player if they really want to exit the game.
     * @return true if the player selects yes, false if they don't want to exit.
     */
    public boolean askPlayerIfTheyWantToExit() {
        Object[] options = {"No","Yes"};
        int n = JOptionPane.showOptionDialog(display,
                    "Do you really want to quit the game?",
                    "Exit Game?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
        if (n == 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Gets the property selected from an action frame and keeps trying until
     * the player makes a selection.
     * @param frame to check for property selection in.
     * @return the property the player selected.
     */
    public Space getPropertyFromActionFrame(ActionFrame frame) {
        while (!frame.isDisposed()
                && frame.getPropertySelected() == null) {
            //Wait until the button is pressed.
            synchronized(this) {
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
        frame.dispose();
        return frame.getPropertySelected();
    }  
}