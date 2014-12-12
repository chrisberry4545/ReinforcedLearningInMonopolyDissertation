package Model;

import Model.Spaces.Space;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class used to hold data on a series of games.
 * @author Chris Berry
 */
public class GameStats {
    
    private static Map<Integer, Integer> numberOfDealsOffered = new HashMap();
    private static Map<Integer, Integer> numberOfDealsAccepted = new HashMap();
    private static Map<Integer, Integer> numberOfPropertiesBought = new HashMap();
    private static Map<Integer, Integer> numberOfPropertiesMortgaged = new HashMap();
    private static Map<Integer, Integer> numberOfPropertiesUnMortgaged = new HashMap();
    private static Map<Integer, Integer> numberOfHousesBought = new HashMap();
    private static Map<Integer, Integer> numberOfHousesSold = new HashMap();
    private static int numPlayers;
    
    
    private static int spaceBoughtNum = 0;
    private static int houseBoughtNum = 1;
    private static int houseSoldNum = 2;
    private static int timesOfferedInDeal = 3;
    private static int timesRequestedInDeal = 4;
    private static int timesGivenAsPartOfDeal = 5;
    private static int timesAcceptedAsPartOfDeal = 6;
    private static int spaceMortgagedNum = 7;
    private static int spaceUnmortgagedNum = 8;
    
    private static Map<String, List<Integer> > fullSpaceMap = new HashMap();
    
    private static Map<Space, Integer> spacesBought = new HashMap();
    private static Map<Space, Integer> housesBought = new HashMap();
    private static Map<Space, Integer> housesSold = new HashMap();
    private static Map<Space, Integer> spacesMortgaged = new HashMap();
    private static Map<Space, Integer> spaceUnmortgaged = new HashMap();
    
    
    private static List<DealOffer> dealOffers = new ArrayList();
    private static List<DealOffer> acceptedDeals = new ArrayList();
    
    private static List<Integer> winningValues = new ArrayList();
    private static List<Integer> lostValues = new ArrayList();
    
    /**
     * Creates a new instance of game stats.
     */
    private GameStats() {
       
    }
    
    /**
     * Sets up game stats with null values in all the variable maps.
     * @param numberOfPlayers 
     */
    public static void setUpGameStats(int numberOfPlayers) {
        numPlayers = numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {
            numberOfDealsOffered.put(i,0);
            numberOfDealsAccepted.put(i,0);
            numberOfPropertiesBought.put(i,0);
            numberOfPropertiesMortgaged.put(i,0);
            numberOfPropertiesUnMortgaged.put(i,0);
            numberOfHousesBought.put(i,0);
            numberOfHousesSold.put(i,0);
        }
    }
    
    /**
     * Adds a value to the map of a given Type.
     * @param <Type> of value to add
     * @param type value to add
     * @param map map to add to.
     */
    private static <Type> void addToMap(Type type, Map<Type, Integer> map) {
        int newValue;
        if (map.containsKey(type)) {
            int oldValue = map.get(type);
            newValue = oldValue + 1;
        } else {
            newValue = 1;
        }
        
        map.put(type, newValue);
    }
    
    /**
     * Add a value to the full map of spaces.
     * @param spaceName the name of the space.
     * @param spaceNum the number of the space.
     */
    private static void addToFullSpaceMap(String spaceName, int spaceNum) {
        if (fullSpaceMap.containsKey(spaceName)) {
            List<Integer> numberList = fullSpaceMap.get(spaceName);
            int oldVal = numberList.get(spaceNum);
            int newVal = oldVal + 1;
            numberList.remove(spaceNum);
            numberList.add(spaceNum, newVal);
            fullSpaceMap.put(spaceName,numberList);
        } else {
            List<Integer> numberList = new ArrayList();
            for (int i = 0; i < spaceUnmortgagedNum + 1; i++) {
                if (i != spaceNum) {
                    numberList.add(0);
                } else {
                    numberList.add(1);
                }
            }
            fullSpaceMap.put(spaceName, numberList);
        }
    }
    
    /**
     * Add winning value to List of winning values.
     * @param winningVal the winning value.
     */
    public static void addWinningValue(int winningVal) {
        winningValues.add(winningVal);
    }
    
    /**
     * Add loosing value to List of loosing values.
     * @param loseValue the loosing value.
     */
    public static void addLoosingValue(int loseValue) {
        lostValues.add(loseValue);
    }
     
    /**
     * Add deal offered to list of deal offers made.
     * @param playerNumber the player number making the deal.
     * @param offer the deal offer the player made.
     */
    public static void addDealOffered(int playerNumber, DealOffer offer) {
        addToMap(playerNumber, numberOfDealsOffered);
        dealOffers.add(offer);
        
        for (Space space : offer.getOfferedProperties()) {
            addToFullSpaceMap(space.getName(), timesOfferedInDeal);
        }
        
        for (Space space : offer.getRequestedProperties()) {
            addToFullSpaceMap(space.getName(), timesRequestedInDeal);
        }
    }
    
    /**
     * Add an accepted deal to the list of accepted deals.
     * @param playerNumber player accepting the deal.
     * @param offer the deal offer the player accepted.
     */
    public static void addDealAccepted(int playerNumber, DealOffer offer) {
        addToMap(playerNumber, numberOfDealsAccepted);
        acceptedDeals.add(offer);
        for (Space space : offer.getOfferedProperties()) {
            addToFullSpaceMap(space.getName(), timesAcceptedAsPartOfDeal);
        }
        
        for (Space space : offer.getRequestedProperties()) {
            addToFullSpaceMap(space.getName(), timesGivenAsPartOfDeal);
        }
    }
    
    /**
     * Add a property the player bought to the properties bought list.
     * @param playerNumber player buying property
     * @param property the property they bought.
     */
    public static void addPropertyBought(int playerNumber, Space property) {
        addToMap(playerNumber, numberOfPropertiesBought);
        addToMap(property, spacesBought);
        addToFullSpaceMap(property.getName(), spaceBoughtNum);
    }
  
    /**
     * Add a property the player mortgaged to the properties mortgaged list.
     * @param playerNumber player number of player mortgaging property.
     * @param property the property mortgaged.
     */
    public static void addPropertyMortgaged(int playerNumber, Space property) {
        addToMap(playerNumber, numberOfPropertiesMortgaged);
        addToMap(property, spacesMortgaged);
        addToFullSpaceMap(property.getName(), spaceMortgagedNum);
    }
    
    /**
     * Add a property the player unmortgaged to the properties unmortgaged list.
     * @param playerNumber player number of player unmortgaging property.
     * @param property the property unmortgaged.
     */
    public static void addPropertyUnMortgaged(int playerNumber, Space property) {
        addToMap(playerNumber, numberOfPropertiesUnMortgaged);
        addToMap(property, spaceUnmortgaged);
        addToFullSpaceMap(property.getName(), spaceUnmortgagedNum);
    }
    
    /**
     * Adds another house bought to the list denoting how many houses have been
     * bought on each property.
     * @param playerNumber the player number of the player buying the house.
     * @param property the property the player is buying a house on.
     */
    public static void addNumberOfHousesBought(int playerNumber, Space property) {
        addToMap(playerNumber, numberOfHousesBought);
        addToMap(property, housesSold);
        addToFullSpaceMap(property.getName(), houseBoughtNum);
    }
    
    /**
     * Adds another house sold to the list denoting how many houses have been
     * sold on each property.
     * @param playerNumber the player number of the player selling the house.
     * @param property the property the player is selling a house on.
     */
    public static void addNumberOfHousesSold(int playerNumber, Space property) {
        addToMap(playerNumber, numberOfHousesSold);
        addToMap(property, housesBought);
        addToFullSpaceMap(property.getName(), houseSoldNum);
    }
    
    /**
     * Prints the current statistics to the console.
     */
    public static void printStats() {
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Stats for player " + i + ":");
            System.out.println("Number of deals offered: " 
                    + numberOfDealsOffered.get(i));
            System.out.println("Number of deals accepted: " 
                    + numberOfDealsAccepted.get(i));
            System.out.println("Number of properties bought: " 
                    + numberOfPropertiesBought.get(i));
            System.out.println("Number of properties mortgaged: " 
                    + numberOfPropertiesMortgaged.get(i));
            System.out.println("Number of properties unmortgaged: " 
                    + numberOfPropertiesUnMortgaged.get(i));
            System.out.println("Number of houses bought: " 
                    + numberOfHousesBought.get(i));
            System.out.println("Number of houses sold: " 
                    + numberOfHousesSold.get(i));
        }
    }
    
    /**
     * Prints the win values which have been collected up to this point to 
     * the console.
     */
    public static void printWinValues() {
        System.out.println("Win values...");
        for (Integer i : winningValues) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }
    
    /**
     * Print the highest win value that has been experienced up to this point
     * to the console.
     */
    public static void printHighestWinValue() {
        int highestWinValue = 0;
        for (Integer i : winningValues) {
            if (i > highestWinValue) {
                highestWinValue = i;
            }
        }
        System.out.println("Highest win value: " + highestWinValue);
    }
    
    /**
     * Prints the loss values which have been collected up to this point to 
     * the console.
     */
    public static void printLossValues() {
        System.out.println("Lost values...");
        for (Integer i : lostValues) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    /**
     * Prints the purchase stats to the console.
     */  
    public static void printAllPurchaseNumberStats() {
        printMapContents(spacesBought, "Spaces bought");
        printMapContents(housesBought, "Houses bought");
        printMapContents(housesSold, "Houses sold");
        printMapContents(spacesMortgaged, "Spaces mortgaged");
        printMapContents(spaceUnmortgaged, "Spaces unmortgaged");
    }
    
    /**
     * Prints the contents of a map.
     * @param map to print content for.
     * @param message the message to go with the entry.
     */
    public static void printMapContents(Map<Space, Integer> map, String message) {
        for (Map.Entry<Space, Integer> entry : map.entrySet()) {
            System.out.print(entry.getKey().getName());
            System.out.println(": " + entry.getValue() + " " + message);
        }
    }
    
    /**
     * Writes a string to a file with the given file name.
     * @param fileName file name to write the string to.
     * @param toWrite String to write to the file.
     */
    public static void writeToCSV(String fileName, String toWrite) {       
       File file = new File(fileName);
        // if file doesnt exists, then create it
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(toWrite);
            bw.close();        
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Gets the CSV String representation of the deals which have been completed.
     * @return CSV String representation of completed deals.
     */
    public static String getCompletedDealCSVString() {
        return getDealCSVString(acceptedDeals);
    }
    
    /**
     * Gets the CSV String representation of the deals which have been offered
     * to other players.
     * @return CSV String of offered deals.
     */
    public static String getOfferedDealCSVString() {
        return getDealCSVString(dealOffers);
    }
    
    /**
     * Transforms the List of deal offers to something which can be output
     * to a CSV file easily.
     * @param list of dealOffers to convert to a CSV String
     * @return a formatted string which can be saved easily as a CSV file.
     */
    public static String getDealCSVString(List<DealOffer> list) {
        String fullStr = "";
        fullStr += "Offered Cash,";
        fullStr += "Offered Get out of Jail cards,";
        fullStr += "Offered Properties,";
        
        fullStr += "Requested Cash,";
        fullStr += "Requested Get out of Jail cards,";
        fullStr += "Requested properties,";
        
        for (DealOffer offer : list) {
            fullStr += offer.getOfferedCash() + ",";
            fullStr += offer.getNumberOfferedGetOutOfJailCards() + ",";
            Space[] offeredProperties = (Space[])offer.getOfferedProperties().toArray();
            for (int i = 0; i < offeredProperties.length; i++) {
                String name = offeredProperties[i].getName();
                if (name.contains(",")) {
                    name = name.replace(",", "");
                }
                fullStr += name;
                fullStr += " ; ";
            }
            fullStr += ",";
            
            fullStr += offer.getRequestedCash();
            fullStr += offer.getNumberRequestedGetOutOfJailCards();
            Space[] requestedProperties = (Space[])offer.getRequestedProperties().toArray();
            for (int i = 0; i < requestedProperties.length; i++) {
                String name = requestedProperties[i].getName();
                if (name.contains(",")) {
                    name = name.replace(",", "");
                }
                fullStr += name;
                fullStr += " ; ";
            }
            fullStr += ",";
        }
        return fullStr;
    }
    
    /**
     * Gets a CSV string for the full space map.
     * @return CSV String for the full space map.
     */
    public static String getCSVString() {
        String fullStr = "";
        fullStr += "Space,";
        fullStr += "Spaces bought,";
        fullStr += "Houses bought,";
        fullStr += "Houses sold,";
        fullStr += "Times offered in deal,";
        fullStr += "Times requested in deal,";
        fullStr += "Times given as part completed deal,";
        fullStr += "Times accepted as part of completed deal,";
        fullStr += "Times mortgaged,";
        fullStr += "Time un-mortgaged,";
        fullStr += "\n";
        
        for (Map.Entry<String, List<Integer> > entry : fullSpaceMap.entrySet()) {
            if (entry.getKey().contains(",")) {
                String entryKey = entry.getKey();
                entryKey = entryKey.replace(",", "");
                fullStr += entryKey + ",";
            } else {
                fullStr += entry.getKey() + ",";
            }
            
            for (Integer value : entry.getValue()) {
                fullStr += value + ",";
            }
            fullStr += "\n";
        }
        return fullStr;
    }
    
    public static String getCSVMapString(Map<Space, Integer> map) {
        String fullStr = "";
        for (Map.Entry<Space, Integer> entry : map.entrySet()) {
            fullStr += entry.getKey().getName() + "," + entry.getValue() + "\n"; 
        }
        return fullStr;
    }
     
}
