package bGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 
 */

/**
 * @author Matthew Chun-Lum
 *
 */
public class BEditorToolbar extends JPanel {
    public static final int DEFAULT_TOOLBAR_WIDTH = 800;
    public static final int DEFAULT_TOOLBAR_HEIGHT = 70;
    
    private JButton addPageButton;
    private JButton removePageButton;
    private JButton addShapeButton;
    private JButton removeShapeButton;
    private JButton moveToFrontButton;
    private JButton moveToBackButton;
    private JButton addImageButton;
    private JButton addAudioButton;
    
    private JButton playButton;
    
    private BEditor editor;
    
    /**
     * Constructor
     * @param editor
     */
    public BEditorToolbar(BEditor editor) {
        this.editor = editor;
        setPreferredSize(new Dimension(DEFAULT_TOOLBAR_WIDTH, DEFAULT_TOOLBAR_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        addPageButton = new JButton("Add Page", BIconHelper.ADD_PAGE_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(addPageButton);
        addPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPage();
            }
        });
        
        removePageButton = new JButton("Remove Page", BIconHelper.REMOVE_PAGE_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(removePageButton);
        removePageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removePage();
            }
        });
        
        // for now
        addShapeButton = new JButton("Add Shape", BIconHelper.ADD_SHAPE_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(addShapeButton);
        addShapeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addShape();
            }
        });
        
        // for now
        removeShapeButton = new JButton("Remove Shape", BIconHelper.REMOVE_SHAPE_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(removeShapeButton);
        removeShapeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeShape();
            }
        });
        
        // for now
        moveToFrontButton = new JButton("Move To Front", BIconHelper.TO_FRONT_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(moveToFrontButton);
        moveToFrontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveToFront();
            }
        });
        
        // for now
        moveToBackButton = new JButton("Move To Back", BIconHelper.TO_BACK_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(moveToBackButton);
        moveToBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveToBack();
            }
        });
        
         // for now
        addImageButton = new JButton("Add Image", BIconHelper.ADD_IMAGE_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(addImageButton);
        addImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addImage();
            }
        }); 
        
        // for now
        addAudioButton = new JButton("Add Sound", BIconHelper.ADD_AUDIO_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(addAudioButton);
        addAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSound();
            }
        });
        
        // for now
        playButton = new JButton("Play", BIconHelper.PLAY_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(playButton);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });
        
        add(addPageButton);
        add(removePageButton);
        add(Box.createHorizontalGlue());
        add(addShapeButton);
        add(removeShapeButton);
        add(moveToBackButton);
        add(moveToFrontButton);
        add(Box.createHorizontalGlue());
        add(addImageButton);
        add(addAudioButton);
        add(Box.createHorizontalGlue());
        add(playButton);
    }
    
    public void addPage() {
    	editor.addPage();
    }
    
    public void removePage() {
        editor.removeCurrentPage();
    }
    
    /**
     * Instructs the editor to add a new shape
     */
    public void addShape() {
        editor.addShape();
    }
    
    /**
     * Instructs the editor to remove the selected shape
     */
    public void removeShape() {
        editor.removeSelectedShape();
    }
    
    public void moveToFront() {
        editor.moveSelectedToFront();
    }
    
    public void moveToBack() {
        editor.moveSelectedToBack();
    }
    
    /**
     * Instructs the editor to display the file chooser for images
     */
    public void addImage() {
        editor.promptForImageFile();
    }
    
    public void addSound() {
        editor.promptForSoundFile();
    }
    
    public void playGame() {
        editor.playGame();
    }
}
