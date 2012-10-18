package bGUI;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.BShape;

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
public class BShapeInspectorPanel extends JPanel {
    private BEditor editor;
    
    private JTextField nameField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField locationXField;
    private JTextField locationYField;
    private JTextField displayTextField;
    private JTextArea scriptArea;
    
    private JComboBox imageChooser;
    private JComboBox fontChooser;
    
    private JCheckBox hiddenCheckBox;
    private JCheckBox movableCheckBox;
    
    private JCheckBox boldCheckBox;
    private JCheckBox itallicCheckBox;
    
    private JPanel colorPanel;
    
    private LinkedList<JComponent> controlGroup;
    
    private HashMap<String, Integer> fontMap;
    
    private Object lock = new Object();
    
    public BShapeInspectorPanel(BEditor editor) {
        this.editor = editor;
        
        controlGroup = new LinkedList<JComponent>();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setUpNameBox();
        setUpLocationInfoBox();
        setUpDisplayPropertiesBox();
        alignComponents(this);
        setControlGroupEnabled(false);
    }
    
    /**
     * Sets the fields in the inspector to display information about
     * the passed shape
     * @param shape
     */
    public void setInspectorForShape(final BShape shape) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(shape != null) {     
                    
                    setControlGroupEnabled(true);
                    nameField.setText(shape.getName());
                    widthField.setText(Integer.toString(shape.getBounds().width));
                    heightField.setText(Integer.toString(shape.getBounds().height));
                    locationXField.setText(Integer.toString(shape.getBounds().x));
                    locationYField.setText(Integer.toString(shape.getBounds().y));
                    colorPanel.setBackground(shape.getColor());
                    displayTextField.setText(shape.getText());
                    
                    boldCheckBox.setSelected(shape.getFont().isBold());
                    itallicCheckBox.setSelected(shape.getFont().isItalic());
                    
                    
                    fontChooser.setSelectedIndex(fontMap.get(shape.getFont().getName()));

                    scriptArea.setText(shape.getScriptBlock());
                    
                    hiddenCheckBox.setSelected(shape.getHidden());
                    movableCheckBox.setSelected(shape.getMovable());
                    imageChooser.setSelectedItem(shape.getImageName());
                } else {
                    nameField.setText("");
                    widthField.setText("");
                    heightField.setText("");
                    locationXField.setText("");
                    locationYField.setText("");
                    displayTextField.setText("");
                    scriptArea.setText("");
                    colorPanel.setBackground(Color.white);
                  
                    boldCheckBox.setSelected(false);
                    itallicCheckBox.setSelected(false);
                    
                    hiddenCheckBox.setSelected(false);
                    movableCheckBox.setSelected(false);
                  
                    setControlGroupEnabled(false);
                }
            }
        });
    }
    
    /**
     * Adds an image name to the chooser
     * @param imageName
     */
    public void addImageNameToImageChooser(String imageName) {
        imageChooser.addItem(imageName);
    }
    
    public void removeImageNameFromImagechooser(String imageName) {
        imageChooser.removeItem(imageName);
    }
    
    public JComboBox getImageChooser() {
    	return imageChooser;
    }
    
    // ------------ Private ----------- //
    
    /**
     * Prompts the editor to update the text on the selected
     * shape.
     */
    private void handleDisplayTextChange() {
        editor.changeTextForSelected(displayTextField.getText());
    }
    
    /**
     * Prompts the editor to update the font for the selected shape
     */
    private void handleDisplayFontChange() {
        editor.changeFontForSelected((String) fontChooser.getSelectedItem(), boldCheckBox.isSelected(), itallicCheckBox.isSelected());
    }
    
    /**
     * Prompts the editor to update the color for the selected shape
     * @param newColor
     */
    private void handleColorChange(Color newColor) {
        editor.changeColorForSelected(newColor);
        colorPanel.setBackground(newColor);
    }
    
    /**
     * Changes the image on the selected shape
     */
    private void handleImageChange() {
        editor.changeImageForSelected((String) imageChooser.getSelectedItem());
    }
    
    /**
     * Changes the bounds for the selected shape
     */
    public void handleBoundsChange() {
        try {
            int x = Integer.parseInt(locationXField.getText().trim());
            int y = Integer.parseInt(locationYField.getText().trim());
            int width = Math.abs(Integer.parseInt(widthField.getText().trim()));
            int height = Math.abs(Integer.parseInt(heightField.getText().trim()));
            editor.changeBoundsForSelected(new Rectangle(x, y, width, height));
        } catch (Exception ignored) {}
    }
    
    /**
     * Changes the name for the selected shape
     */
    public void handleNameChange() {
        editor.changeNameForSelected(nameField.getText().trim());
    }
    
    /**
     * enables or disables the control group based on the passed variable
     * @param val
     */
    private void setControlGroupEnabled(boolean val) {
        for(JComponent comp : controlGroup)
            comp.setEnabled(val);
    }
    
    private void handleScriptChange() {
        editor.changeScriptForSelected(scriptArea.getText().trim());
    }
    
    private void handleHiddenChange() {
        editor.changeHiddenForSelected(hiddenCheckBox.isSelected());
    }
    
    private void handleMovableChange() {
        editor.changeMovableForSelected(movableCheckBox.isSelected());
    }
    
    // ---------- GUI SET UP ---------- //
    
    /**
     * Sets up the field to view/edit the name of a shape
     */
    private void setUpNameBox() {
        Box nameBox = Box.createVerticalBox();
        nameBox.setBorder(BorderFactory.createTitledBorder("Shape name"));
        
        nameField = new JTextField("");
        nameField.setPreferredSize(new Dimension(200, 20));
        nameField.setMaximumSize(nameField.getPreferredSize());
        nameField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ignored) {}

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    handleNameChange();
            }

            public void keyTyped(KeyEvent eignored) {}
        });
        
        nameBox.add(nameField);
        add(nameBox);
        
        controlGroup.add(nameField);
    }
    
    /**
     * Sets up the location and size displays
     */
    private void setUpLocationInfoBox() {
        
        // size fields
        Box sizeBox = Box.createHorizontalBox();
        sizeBox.setBorder(BorderFactory.createTitledBorder("Size"));
        
        widthField = new JTextField("");
        widthField.setPreferredSize(new Dimension(50, 20));
        widthField.setMaximumSize(widthField.getPreferredSize());
        widthField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ignored) {}

            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		handleBoundsChange();
            }

            public void keyTyped(KeyEvent eignored) {}
        });
        
        heightField = new JTextField("");
        heightField.setPreferredSize(new Dimension(50, 20));
        heightField.setMaximumSize(heightField.getPreferredSize());
        heightField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ignored) {}

            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		handleBoundsChange();
            }

            public void keyTyped(KeyEvent eignored) {}
        });
        
        
        sizeBox.add(new JLabel("Width"));
        sizeBox.add(widthField);
        sizeBox.add(new JLabel("Height"));
        sizeBox.add(heightField);
        
        alignComponents(sizeBox);
        
        add(sizeBox);
        
        controlGroup.add(widthField);
        controlGroup.add(heightField);
        
        // location fields
        Box locationBox = Box.createHorizontalBox();
        locationBox.setBorder(BorderFactory.createTitledBorder("Location"));
        
        locationXField = new JTextField("");
        locationXField.setPreferredSize(new Dimension(50, 20));
        locationXField.setMaximumSize(locationXField.getPreferredSize());
        locationXField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ignored) {}

            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		handleBoundsChange();
            }

            public void keyTyped(KeyEvent eignored) {}
        });
        
        
        locationYField = new JTextField("");
        locationYField.setPreferredSize(new Dimension(50, 20));
        locationYField.setMaximumSize(locationYField.getPreferredSize());
        locationYField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ignored) {}

            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		handleBoundsChange();
            }

            public void keyTyped(KeyEvent eignored) {}
        });
        
        
        locationBox.add(new JLabel("X"));
        locationBox.add(locationXField);
        locationBox.add(new JLabel("Y"));
        locationBox.add(locationYField);
        
        add(locationBox);
        
        controlGroup.add(locationXField);
        controlGroup.add(locationYField);
    }
    
    /**
     * Sets up the controls for the rest of the display
     * properties: color, image, text, hidden, movable
     */
    private void setUpDisplayPropertiesBox() {
        // color picker
        Box colorBox = Box.createHorizontalBox();
        colorBox.setBorder(BorderFactory.createTitledBorder("Color"));
        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(100, 20));
        colorPanel.setMaximumSize(new Dimension(200, 20));
        colorPanel.setBackground(Color.white);
        colorPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(BShapeInspectorPanel.this, "Select Shape Color", colorPanel.getBackground());
                handleColorChange(newColor);
            }
        });
        
        colorBox.add(colorPanel);
        
        add(colorBox);
        
        controlGroup.add(colorPanel);
        
        // image picker
        Box imageBox = Box.createHorizontalBox();
        imageBox.setBorder(BorderFactory.createTitledBorder("Image"));
        
        String[] temp = {"none"};
        imageChooser = new JComboBox(temp);
        
        BImageChooserRenderer renderer = new BImageChooserRenderer(imageChooser);
        renderer.setPreferredSize(new Dimension(200, 70));
        
        imageChooser.setRenderer(renderer);
        imageChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleImageChange();
            }
        });
        
        imageBox.add(imageChooser);
        add(imageBox);
        
        controlGroup.add(imageChooser);
        
        // set shape display text
        Box textBox = Box.createVerticalBox();
        textBox.setBorder(BorderFactory.createTitledBorder("Display Text"));
        
        displayTextField = new JTextField("");
        displayTextField.setPreferredSize(new Dimension(200, 20));
        displayTextField.setMaximumSize(displayTextField.getPreferredSize());
        displayTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // nothing here
            }

            public void insertUpdate(DocumentEvent e) {
                handleDisplayTextChange();
            }

            public void removeUpdate(DocumentEvent e) {
                handleDisplayTextChange();
            }
        });
        
        Box innerTextBox = Box.createHorizontalBox();
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fonts[] = ge.getAvailableFontFamilyNames();
        fontMap = new HashMap<String, Integer>();
        for(int i = 0; i < fonts.length; i++) {
            fontMap.put(fonts[i], i);
        }
        
        fontChooser = new JComboBox(fonts);
        fontChooser.setMaximumSize(new Dimension(270, 40));
        fontChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDisplayFontChange();
            }
        });
        
        innerTextBox.add(new JLabel("Font"));
        innerTextBox.add(fontChooser);
        
        Box innerTextBox2 = Box.createHorizontalBox();
        
        boldCheckBox = new JCheckBox("Bold");
        boldCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDisplayFontChange();
            }
        });
        
        itallicCheckBox = new JCheckBox("Itallic");
        itallicCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDisplayFontChange();
            }
        });
        
        innerTextBox2.add(boldCheckBox);
        innerTextBox2.add(itallicCheckBox);
        
        textBox.add(displayTextField);
        textBox.add(innerTextBox);
        textBox.add(innerTextBox2);
        
        alignComponents(textBox);
        
        add(textBox);
        
        controlGroup.add(boldCheckBox);
        controlGroup.add(itallicCheckBox);
        controlGroup.add(displayTextField);
        controlGroup.add(fontChooser);
        
        // Hidden / Movable check-boxes
        Box propertiesBox = Box.createHorizontalBox();
        propertiesBox.setBorder(BorderFactory.createTitledBorder("Options"));
        
        hiddenCheckBox = new JCheckBox("Hidden");
        hiddenCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleHiddenChange();
            }
        });
        
        movableCheckBox = new JCheckBox("movable");
        movableCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleMovableChange();
            }
        });
        
        propertiesBox.add(hiddenCheckBox);
        propertiesBox.add(movableCheckBox);
        add(propertiesBox);
        
        controlGroup.add(hiddenCheckBox);
        controlGroup.add(movableCheckBox);
   
        Box scriptBox = Box.createHorizontalBox();
        scriptBox.setBorder(BorderFactory.createTitledBorder("Script"));
        
        scriptArea = new JTextArea("");
        scriptArea.setLineWrap(true);
        scriptArea.setPreferredSize(new Dimension(280, 80));
        scriptArea.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                
            }

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleScriptChange();
                }
            }

            public void keyTyped(KeyEvent e) {}
        });
        
        JScrollPane scrollPane = new JScrollPane(scriptArea);
        scriptBox.add(scrollPane);
        add(scriptBox);
        
        controlGroup.add(scriptArea);
        
    }
    
    /**
     * aligns all of the components to the left
     */
    private void alignComponents(JComponent component) {
        for(Component comp : component.getComponents())
            ((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
    }
    
    
}
