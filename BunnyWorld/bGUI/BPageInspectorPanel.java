/**
 * 
 */
package bGUI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BPageInspectorPanel extends JPanel {

    BEditor editor;
    
    JTextField nameField;
    
    public BPageInspectorPanel(BEditor editor) {
        this.editor = editor;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Page Inspector"));
        nameField = new JTextField("");
        nameField.setMaximumSize(new Dimension(200, 40));
        nameField.setPreferredSize(new Dimension(200, 40));
        nameField.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    changeName();
                }
            }

            public void keyTyped(KeyEvent e) {}
            
        });
        
        add(nameField);
        add(Box.createVerticalGlue());
    }
    
    public void setInspectorForPage(String name) {
        nameField.setEnabled(!name.equals("page1"));
        nameField.setText(name);
    }
    
    public void changeName() {
        editor.changeNameForSelectedPage(nameField.getText().trim());
    }
    
}
