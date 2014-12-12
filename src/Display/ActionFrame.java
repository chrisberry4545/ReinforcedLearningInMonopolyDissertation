package Display;

import Model.Spaces.Space;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;

/**
 * An abstract class Frame used for human players to make certain inputs.
 * Examples include buying/selling houses and mortgaging properties.
 * @author Chris Berry
 */
public abstract class ActionFrame extends JFrame {
    
    private boolean disposed = false;
    private Space propertySelected = null;
    protected static final Color topBarColor = new Color(195,195,195);
    
    /**
     * Creates a new Action Frame.
     * @param spaces which should be displayed in the frame.
     */
    public ActionFrame(List<? extends Space> spaces) {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),
                BoxLayout.Y_AXIS));
        
        this.getContentPane().add(setUpMainPanel(spaces));
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Sets up the main Panel for the Action Frame.
     * @param spaces which should be displayed in the frame.
     * @return Set up JPanel.
     */
    public abstract JPanel setUpMainPanel(List<? extends Space> spaces);
    
    /**
     * Creates a cancel button for the frame. The button disposes the frame.
     * @return the created cancel button.
     */
    public JButton createCancelButton() {
        JButton cancelButton = new JButton();
        final JFrame frame = this;
        cancelButton.setAction(new AbstractAction("Cancel") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            } 
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });;
        return cancelButton;
    }
    
    /**
     * Disposes the current frame and sets a variable 'disposed' so that
     * other classes can monitor if this JFrame has been disposed.
     */
    @Override
    public void dispose() {
        this.disposed = true;
        super.dispose();
    }
    
    /**
     * Sets selected property selected to a certain value.
     * @param space which has been selected.
     */
    public void setPropertySelected(Space space) {
        propertySelected = space;
    }
    
    /**
     * Gets the last property selected by the player.
     * @return the last property selected by the player.
     */
    public Space getPropertySelected() {
        return propertySelected;
    }
    
    /**
     * Checks if the frame has been disposed.
     * @return true if the frame has been disposed.
     */
    public boolean isDisposed() {
        return this.disposed;
    }

    
}
