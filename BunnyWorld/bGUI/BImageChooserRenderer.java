/**
 * 
 */
package bGUI;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author admin
 *
 */
public class BImageChooserRenderer extends JLabel implements ListCellRenderer {

    private JComboBox chooser;
    
    /**
     * 
     */
    public BImageChooserRenderer(JComboBox imageChooser) {
        chooser = imageChooser;
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    /* (non-Javadoc)
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        String name = (String) value;
        
        if(isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        if(name != "none") {
            ImageIcon icon = new ImageIcon(BEditor.getImage(name).getScaledInstance(-1, 50, Image.SCALE_FAST));
            setIcon(icon);
            
            if(icon != null) {
                setText(name);
                setFont(list.getFont());
            }
        } else {
            setIcon(null);
            setText(name);
            setFont(list.getFont());
        }
        
        return this;
    }

}
