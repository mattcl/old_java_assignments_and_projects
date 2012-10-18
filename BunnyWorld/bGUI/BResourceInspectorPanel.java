/**
 * 
 */
package bGUI;

import javax.swing.*;

import data.ResourceModel;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BResourceInspectorPanel extends JPanel {
    public static final String IMAGE_VIEW = "Image";
    public static final String SOUND_VIEW = "Sound";
    
    private BEditor editor;
    private JPanel infoPanel;
    private JLabel imagePanel;
    private JPanel soundPanel;
    private JLabel nameLabel;
    
    private String soundName;
    
    public BResourceInspectorPanel(BEditor editor) {
        this.editor = editor;
        
        soundName = null;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        nameLabel = new JLabel();
        
        Box newBox3 = Box.createHorizontalBox();
        newBox3.add(Box.createHorizontalGlue());
        newBox3.add(nameLabel);
        newBox3.add(Box.createHorizontalGlue());
        
        add(newBox3);
        
        infoPanel = new JPanel(new CardLayout());
        infoPanel.setPreferredSize(new Dimension(200, 400));
        
        imagePanel = new JLabel();
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
        imagePanel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.setVerticalAlignment(JLabel.CENTER);
        
        soundPanel = new JPanel();
        soundPanel.setLayout(new BoxLayout(soundPanel, BoxLayout.Y_AXIS));
        soundPanel.setBorder(BorderFactory.createTitledBorder("Sound"));
        
        JButton playSoundButton = new JButton("Play", BIconHelper.PLAY_SOUND_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(playSoundButton);
        playSoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound();
            }
        });
        
        Box newBox = Box.createHorizontalBox();
        newBox.add(Box.createHorizontalGlue());
        newBox.add(playSoundButton);
        newBox.add(Box.createHorizontalGlue());
       
        soundPanel.add(Box.createVerticalGlue());
        soundPanel.add(newBox);
        soundPanel.add(Box.createVerticalGlue());
        
        infoPanel.add(imagePanel, IMAGE_VIEW);
        infoPanel.add(soundPanel, SOUND_VIEW);
        
        add(infoPanel);
        
        JButton removeButton = new JButton("Remove Resource", BIconHelper.TRASH_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(removeButton);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeResource();
            }
        });
        
        Box newBox2 = Box.createHorizontalBox();
        newBox2.add(Box.createHorizontalGlue());
        newBox2.add(removeButton);
        newBox2.add(Box.createHorizontalGlue());
        add(newBox2);
    }
    
    public void setResource(ResourceModel resource) {
        CardLayout layout = (CardLayout) infoPanel.getLayout();
        
        if(resource == null) {
            imagePanel.setIcon(null);
            soundName = null;
            nameLabel.setText("");
            return;
        }
        
        if(resource.getDataType().equals("AudioStream")) {
            soundName = resource.getName();
            layout.show(infoPanel, SOUND_VIEW);
        } else if(resource.getDataType().equals("Image")) {
            imagePanel.setIcon(new ImageIcon(resource.getImage()));
            layout.show(infoPanel, IMAGE_VIEW);
        }
        
        nameLabel.setText(resource.getName());
    }
    
    
    
    // ---------------- Private ------------- //
    private void removeResource() {
        editor.removeResource(nameLabel.getText());
    }
    
    private void playSound() {
        editor.playSound(soundName);
    }
    
    
    
    
}
