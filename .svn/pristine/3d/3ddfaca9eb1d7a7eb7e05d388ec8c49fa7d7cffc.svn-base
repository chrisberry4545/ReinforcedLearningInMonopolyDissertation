package Display;

import java.awt.Color;
import javax.swing.*;

/**
 * Creates a label for a property in the game of Monopoly.
 * @author Chris Berry
 */
public class PropertyLabel extends JLabel{
    
    /**
     * Sets up a JLabel with the properties as stated.
     * @param text of the label.
     * @param backgroundColor used in the label.
     * @param fontColor used in the label.
     * @param isMortgaged true if the property is mortgaged.
     * @param numHouses the number of houses on the property.
     */
    public PropertyLabel(String text, Color backgroundColor, Color fontColor,
            boolean isMortgaged, int numHouses) {
        this.setText(text);
        this.setOpaque(true);
        this.setBackground(backgroundColor);
        this.setForeground(fontColor);
        if (isMortgaged) {
            this.setText(text + "(Mortgaged)");
        }
        if (numHouses > 0) {
            this.setText(this.getText() + " (" + numHouses + "houses)");
        }
    }
}
