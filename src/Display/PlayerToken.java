package Display;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Contains information about the players token on the display.
 * @author Chris Berry
 */
public class PlayerToken extends JLabel {
   
    public static final int T0 = 0;
    public static final int T1 = 1;
    public static final int T2 = 2;
    public static final int T3 = 3;
    public static final int T4 = 4;
    public static final int T5 = 5;
    public static final int T6 = 6;
    
    private int tokenType;
    private ImageIcon imageIcon;
    private int playerNumber;
    
    private final static String blue = "Blue.png";
    private final static String red = "Red.png";
    private final static String yellow = "Yellow.png";
    private final static String pink = "Pink.png";
    private final static String purple = "Purple.png";
    private final static String green = "Green.png";
    
    private final static String website = "https://sites.google.com/site/monopolyaisite/game/playertokens/";
    
    /**
     * Creates a new player token to use.
     * @param tokenType defines the type of token the player will use (0-6).
     * @param playerNumber the player number of the player.
     */
    public PlayerToken(int tokenType, int playerNumber) {
        this.playerNumber = playerNumber;
        this.tokenType = tokenType;
        this.imageIcon = generatePlayerImageIcon(tokenType);
        this.setIcon(imageIcon);
        this.setBounds(0, 0, imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }
    
    /**
     * Generates a player image icon which matches to the given token number.
     * @param tokenNumber for which to generate the image for.
     * @return the set up ImageIcon for the given tokenNumber.
     */
    private ImageIcon generatePlayerImageIcon(int tokenNumber) {
        Image tokenImage = null;
        String fullAddress = null;
        switch (tokenNumber) {
            case T0 :
                fullAddress = website + blue;
                break;
            case T1 :
                fullAddress = website + red;
                break;
            case T2 : 
                fullAddress = website + yellow;
                break;
            case T3 :
                fullAddress = website + pink;
                break;
            case T4 :
                fullAddress = website + purple;
                break;
            case T5 :
                fullAddress = website + green;
                break;
            default :
                System.err.println("Invalid player number at player token class");
                break;
        }
        try {
            tokenImage = ImageIO.read(new URL(fullAddress));
        } catch (IOException e) {
            System.err.println(e);
        }
        return new ImageIcon(tokenImage);
    }
    
    /**
     * Gets the ImageIcon used by the player token.
     * @return the ImageIcon used by the player token.
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    
    /**
     * Gets the player number associated with this player token.
     * @return the player number associated with this player token.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }
    
    
    
}
