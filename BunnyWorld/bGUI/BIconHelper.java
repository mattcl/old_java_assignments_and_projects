/**
 * 
 */
package bGUI;

import java.net.URL;

import javax.swing.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BIconHelper {
    
    public static final ImageIcon ADD_PAGE_ICON     = createImageIcon("add_page.png"); 
    public static final ImageIcon REMOVE_PAGE_ICON  = createImageIcon("delete_page.png");
    public static final ImageIcon ADD_SHAPE_ICON    = createImageIcon("add.png");
    public static final ImageIcon REMOVE_SHAPE_ICON = createImageIcon("delete.png");
    public static final ImageIcon TO_FRONT_ICON     = createImageIcon("up.png");
    public static final ImageIcon TO_BACK_ICON      = createImageIcon("download.png");
    public static final ImageIcon WARNING_ICON      = createImageIcon("warning_64.png");
    public static final ImageIcon ADD_IMAGE_ICON    = createImageIcon("promotion.png");
    public static final ImageIcon ADD_AUDIO_ICON    = createImageIcon("music.png");
    public static final ImageIcon PLAY_ICON         = createImageIcon("puzzle.png");
    public static final ImageIcon TRASH_ICON        = createImageIcon("trash_can.png");
    public static final ImageIcon PLAY_SOUND_ICON   = createImageIcon("Forward.png");
    public static final ImageIcon NEXT_ICON         = createImageIcon("next.png");
    public static final ImageIcon PREVIOUS_ICON     = createImageIcon("back.png");
    public static final ImageIcon BUNNY_OF_DEATH    = createImageIcon("death.gif");
    
    /**
     * Gets a new image icon from the given image name. Looks in the resources folder
     * @param imageName
     * @return
     */
    public static ImageIcon createImageIcon(String imageName) {
        URL imageURL = Bunny.class.getResource("../resources/" + imageName);
        return new ImageIcon(imageURL);
    }
    
    /**
     * Changes the properties of the passed button to the desired properties for icon display
     * @param button
     */
    public static void setPropertiesOfButtonToAcceptIcon(JButton button) {
        button.setIconTextGap(2);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setOpaque(false);
        button.setBorderPainted(false);
    }
}
