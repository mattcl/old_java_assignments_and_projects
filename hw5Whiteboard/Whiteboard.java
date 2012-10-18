import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.*;
import java.io.*;
import java.net.*;

import java.util.*;
/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class Whiteboard extends JFrame {
    
    private static final Random generator = new Random();
    
    private static final int NORMAL_MODE = 0;
    private static final int SERVER_MODE = 1;
    private static final int CLIENT_MODE = 2;
    
    private static int nextID = 0;
    
    public static int getNextIDNumber() {
        return nextID++;
    }
    
    private Box controlGroup;
    
    private JLabel addLabel;
    private JLabel modeLabel;
    
    private JTextField textField;
    
    private JTable table;
    private WhiteboardTableModel tableModel;
    
    private JComboBox fontSelector;
    private JButton rectAddButton, 
                    ovalAddButton, 
                    lineAddButton, 
                    textAddButton,
                    moveFrontButton, 
                    moveBackButton, 
                    removeButton,
                    serverModeButton,
                    clientModeButton;
    
    private JMenuBar menuBar;
    private JFileChooser fileChooser;
    
    private ArrayList<JComponent> disableGroup;
    
    private HashMap<String, Integer> fontMap;
    private boolean justUpdatedField;
    
    private Canvas canvas;
    
    // Networking stuff
    private ClientHandler clientHandler;
    private ServerAccepter serverAccepter;
    private int currentMode;
    
    private ArrayList<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
    
    public Whiteboard() {
        setLayout(new BorderLayout());
        
        controlGroup = Box.createVerticalBox();
        disableGroup = new ArrayList<JComponent>();
        currentMode = NORMAL_MODE;
        
        setUpFileMenu();
        setUpServerClientGroup();
        setUpAddButtonGroup();
        setUpFontGroup();
        setUpManipulationGroup();
        setUpTable();
        alignControls();
        
        add(controlGroup, BorderLayout.WEST);
        
        setUpCanvas();
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    /**
     * Enables the canvas to set the values for the text input
     * field and the font combo box
     * @param text
     * @param font
     */
    public void updateFontGroup(String text, String font) {
        int index = fontMap.get(font);
        fontSelector.setSelectedIndex(index);
        textField.setText(text);
        justUpdatedField = true;
    }
    
    /**
     * Adds the shape to the table
     * @param shape
     */
    public void addToTable(DShape shape) {
        tableModel.addModel(shape.getModel());
        updateTableSelection(shape);
    }
    
    /**
     * moves the specified shape to the last position in the table
     * @param shape
     */
    public void didMoveToBack(DShape shape) {
        tableModel.moveModelToBack(shape.getModel());
        updateTableSelection(shape);
    }
    
    /**
     * moves the specified shape to the first position in the table
     * @param shape
     */
    public void didMoveToFront(DShape shape) {
        tableModel.moveModelToFront(shape.getModel());
        updateTableSelection(shape);
    }
    
    /**
     * removes the shape from the table
     * @param shape
     */
    public void didRemove(DShape shape) {
        tableModel.removeModel(shape.getModel());
        updateTableSelection(null);
    }
    
    /**
     * Clears the table of all models
     */
    public void clearTable() {
        updateTableSelection(null);
        tableModel.clear();
    }
    
    /**
     * Selects an appropriate row in the table
     */
    public void updateTableSelection(DShape selected) {
        table.clearSelection();
        if(selected != null) {
            int index = tableModel.getRowForModel(selected.getModel());
            table.setRowSelectionInterval(index, index);
        }
    }
    
    /**
     * Adds the ouptut stream to the list of outputs ("clients")
     * @param out
     */
    public synchronized void addOutput(ObjectOutputStream out) {
        outputs.add(out);
    }
    
    /**
     * Starts the whiteboard instance as a server
     */
    public void doServer() {
        String result = JOptionPane.showInputDialog("Run server on port", "39587");
        if(result != null) {
            disableControls(SERVER_MODE);
            modeLabel.setText(getModeString(SERVER_MODE));
            currentMode = SERVER_MODE;
            serverAccepter = new ServerAccepter(Integer.parseInt(result.trim()));
            serverAccepter.start();
        }
    }
    
    /**
     * Starts the whiteboard instance as a client
     */
    public void doClient() {
        String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:39587");
        if(result != null) {
            String[] parts = result.split(":");
            disableControls(CLIENT_MODE);
            modeLabel.setText(getModeString(CLIENT_MODE));
            currentMode = CLIENT_MODE;
            clientHandler = new ClientHandler(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            clientHandler.start();
        }
    }
    
    /**
     * Sends the command for the given model to all clients
     * @param command
     * @param model
     */
    public void doSend(int command, DShapeModel model) {
        Message message = new Message();
        message.setCommand(command);
        message.setModel(model);
        sendRemote(message);
    }
    
    public String getXMLStringForMessage(Message message) {
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(message);
        encoder.close();
        return memStream.toString();
    }
    
    /**
     * Passes the message on to all clients
     * @param message
     */
    public synchronized void sendRemote(Message message) {
        String xmlString = getXMLStringForMessage(message);
        Iterator<ObjectOutputStream> it = outputs.iterator();
        while(it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
                out.writeObject(xmlString);
                out.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                it.remove();
            }
        }
    }
    
    /**
     * Reads a message and executes the contained command
     * @param message
     */
    public void processMessage(final Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DShape shape = canvas.getShapeWithID(message.getModel().getID());
                switch(message.getCommand()) {
                    case Message.ADD:
                        if(shape == null)
                            canvas.addShape(message.getModel());
                        break;
                    case Message.REMOVE: 
                        if(shape != null)
                            canvas.markForRemoval(shape);
                        break;
                    case Message.BACK: 
                        if(shape != null)
                            canvas.moveToBack(shape);
                        break;
                    case Message.FRONT: 
                        if(shape != null)
                            canvas.moveToFront(shape);
                        break;
                    case Message.CHANGE:
                        if(shape != null)
                            shape.getModel().mimic(message.getModel());
                        updateTableSelection(shape);
                        break;
                    default: break;
                }
            }
        });
    }
    
    /**
     * @return true if the current instance is not a client
     */
    public boolean isNotClient() {
        return currentMode != CLIENT_MODE;
    }
    
    /**
     * 
     * @return true if the current instance is a server
     */
    public boolean isServer() {
        return currentMode == SERVER_MODE;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Whiteboard whiteboard = new Whiteboard();

        // for easy testing
        Whiteboard whiteboard2 = new Whiteboard();
    }   
    
    // ----------------------- Private ---------------------- //
    
    /**
     * adds a shape to the canvas
     */
    private void addShape(DShapeModel model) {
        if(model instanceof DLineModel) 
            ((DLineModel) model).modifyWithPoints(new Point(Canvas.INITIAL_SHAPE_POS, Canvas.INITIAL_SHAPE_POS),
                                                  new Point(Canvas.INITIAL_SHAPE_POS + Canvas.INITIAL_SHAPE_SIZE, Canvas.INITIAL_SHAPE_POS + Canvas.INITIAL_SHAPE_SIZE));
        else
            model.setBounds(Canvas.INITIAL_SHAPE_POS, Canvas.INITIAL_SHAPE_POS, Canvas.INITIAL_SHAPE_SIZE, Canvas.INITIAL_SHAPE_SIZE);
        
        canvas.addShape(model);
    }
    
    /**
     * Handles a change in the text input field
     * @param e
     */
    private void handleTextChange(DocumentEvent e) {
        if(canvas.hasSelected() && canvas.getSelected() instanceof DText)
            canvas.setTextForSelected(textField.getText());
    }
    
    /**
     * Saves the state of the canvas using an XML encoder
     */
    private void saveCanvas() {
        int retVal = fileChooser.showSaveDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION)
            canvas.saveCanvas(fileChooser.getSelectedFile());
    }
    
    /**
     * Opens a saved canvas state using a XML decoder
     */
    private void openCanvas() {
        int retVal = fileChooser.showOpenDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            canvas.openCanvas(fileChooser.getSelectedFile());
            for(DShape shape : canvas.getShapes())
                doSend(Message.ADD, shape.getModel());
        }
    }
    
    /**
     * Saves a canvas state to a PNG file
     */
    private void saveImage() {
        int retVal = fileChooser.showSaveDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION)
            canvas.saveImage(fileChooser.getSelectedFile());
    }
    
    /**
     * Returns the string associated with the passed mode
     * @param mode
     * @return
     */
    private String getModeString(int mode) {
        switch(mode) {
            case NORMAL_MODE: return "Normal Mode";
            case SERVER_MODE: return "Server Mode";
            case CLIENT_MODE: return "Client Mode";
            default: return "Error";
        }
    }
    
    private void disableControls(int mode) {
        clientModeButton.setEnabled(false);
        serverModeButton.setEnabled(false);
        if(mode == CLIENT_MODE)
            for(JComponent comp : disableGroup)
                comp.setEnabled(false);
    }
    
    // --------- Methods for generating GUI elements ---------- //
    
    // creates the file menu for opening/saving the current state of the canvas
    private void setUpFileMenu() {
        menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem saveItem = new JMenuItem("Save Canvas");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveCanvas();
            }
        });
        
        JMenuItem openItem = new JMenuItem("Open Canvas");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCanvas();
            }
        });
        
        JMenuItem saveImageItem = new JMenuItem("Save Image");
        saveImageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
        
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveImageItem);
        
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
        
        fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File("."));
        
        disableGroup.add(openItem);
    }
    
    // sets up the buttons and label for the server-client options
    private void setUpServerClientGroup() {
        Box panel = Box.createHorizontalBox();
        
        modeLabel = new JLabel(getModeString(NORMAL_MODE));
        
        serverModeButton = new JButton("Set Server Mode");
        serverModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doServer();
            }
        });
        
        clientModeButton = new JButton("Set Client Mode");
        clientModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doClient();
            }
        });
        
        panel.add(serverModeButton);
        panel.add(clientModeButton);
        panel.add(Box.createHorizontalStrut(40));
        panel.add(modeLabel);
        
        controlGroup.add(panel);
    }
    
    // creates the add label and the buttons to add various elements to the canvas
    // also sets up the set color button
    private void setUpAddButtonGroup() {
        Box panel = Box.createHorizontalBox();
        
        addLabel = new JLabel("Add");
        
        rectAddButton = new JButton("Rect");
        rectAddButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               addShape(new DRectModel());
           }
        });
        
        ovalAddButton = new JButton("Oval");
        ovalAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addShape(new DOvalModel());
            }
         });
        
        lineAddButton = new JButton("Line");
        lineAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addShape(new DLineModel());
            }
        });
        
        textAddButton = new JButton("Text");
        textAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addShape(new DTextModel());
            }
        });
        
        panel.add(addLabel);
        panel.add(rectAddButton);
        panel.add(ovalAddButton);
        panel.add(lineAddButton);
        panel.add(textAddButton);
        
        controlGroup.add(panel, BorderLayout.WEST);
        
        JButton setColorButton = new JButton("Set Color");
        setColorButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(canvas.hasSelected()) {
                   Color newColor = JColorChooser.showDialog(Whiteboard.this, "Select Shape Color", canvas.getSelected().getColor());
                   if(newColor != null && !newColor.equals(canvas.getSelected().getColor())) {
                       canvas.setSelectedColor(newColor);
                   }
               }
           }
        });
        
        controlGroup.add(setColorButton, BorderLayout.WEST);
        
        disableGroup.add(rectAddButton);
        disableGroup.add(ovalAddButton);
        disableGroup.add(lineAddButton);
        disableGroup.add(textAddButton);
        disableGroup.add(setColorButton);
    }
    
    // creates the text area for setting the text on a text element
    // also creates the font selection 
    private void setUpFontGroup() {
        Box panel = Box.createHorizontalBox();
        
        textField = new JTextField("");
        textField.setMaximumSize(new Dimension(200, 20));
        textField.setPreferredSize(new Dimension(200, 20));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
    
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(e);
            }
    
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(e);
            }
        });
        
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fonts[] = ge.getAvailableFontFamilyNames();
        fontMap = new HashMap<String, Integer>();
        for(int i = 0; i < fonts.length; i++) {
            fontMap.put(fonts[i], i);
        }
        
        fontSelector = new JComboBox(fonts);
        fontSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(canvas.hasSelected() && canvas.getSelected() instanceof DText)
                    canvas.setFontForSelected((String) fontSelector.getSelectedItem());
            }
        });
        
        panel.add(textField);
        panel.add(fontSelector);
        
        controlGroup.add(panel, BorderLayout.WEST);
    }
    
    //creates the move-to button set and the remove button
    private void setUpManipulationGroup() {
        Box panel = Box.createHorizontalBox();
        
        moveFrontButton = new JButton("Move To Front");
        moveFrontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(canvas.hasSelected())
                    canvas.moveSelectedToFront();
            }
        });
        
        moveBackButton = new JButton("Move To Back");
        moveBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(canvas.hasSelected())
                    canvas.moveSelectedToBack();
            }
        });
        
        removeButton = new JButton("Remove Shape");
        removeButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(canvas.hasSelected())
                   canvas.markSelectedShapeForRemoval();
           }
        });
        
        panel.add(moveFrontButton);
        panel.add(moveBackButton);
        panel.add(removeButton);
        
        controlGroup.add(panel, BorderLayout.WEST);
        
        disableGroup.add(textField);
        disableGroup.add(fontSelector);
        disableGroup.add(moveFrontButton);
        disableGroup.add(moveBackButton);
        disableGroup.add(removeButton);
    }
    
    // sets up the table
    private void setUpTable( ) {
        tableModel = new WhiteboardTableModel();
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(300, 200));
        controlGroup.add(scrollpane, BorderLayout.WEST);
    }
    
    // sets up the canvas
    private void setUpCanvas() {
        canvas = new Canvas(this);
        add(canvas, BorderLayout.CENTER);
    }
    
    // aligns the controls
    private void alignControls() {
        for(Component comp : controlGroup.getComponents())
            ((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
    }
    
    // ------------------ Client Server Handling --------------- //
    
    // Adapted from Handout 32
    
    // Thread that handles incoming messages from a server instance
    private class ClientHandler extends Thread {
        private String name;
        private int port;
        
        public ClientHandler(String name, int port) {
            this.name = name;
            this.port = port;
        }
        
        public void run() {
            try {
                Socket toServer = new Socket(name, port);
                ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
                
                while(true) {
                    String xmlString = (String) in.readObject();
                    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
                    Message message = (Message) decoder.readObject();
                    
                    processMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Thread that handles incoming connections
    private class ServerAccepter extends Thread {
        private int port;
        
        public ServerAccepter(int port) {
            this.port = port;
        }
        
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while(true) {
                    Socket toClient = null;
                    toClient = serverSocket.accept();
                    final ObjectOutputStream out = new ObjectOutputStream(toClient.getOutputStream());
                    if(!outputs.contains(out)) {
                        Thread worker = new Thread(new Runnable() {
                            public void run() {
                                for(DShape shape : canvas.getShapes())
                                    try {
                                        out.writeObject(getXMLStringForMessage(new Message(Message.ADD, shape.getModel())));
                                        out.flush();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                            }
                        });
                        worker.start();
                    }
                    addOutput(out);
                 // Send the models already in the canvas to the client
                  
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Struct object for network commmunication
    public static class Message {
        public static final int ADD    = 0;
        public static final int REMOVE = 1;
        public static final int FRONT  = 2;
        public static final int BACK   = 3;
        public static final int CHANGE = 4;
        
        public int command;
        public DShapeModel model;
        
        public Message() {
            command = -1;
            model = null;
        }
        
        public Message(int command, DShapeModel model) {
            this.command = command;
            this.model = model;
        }
        
        public int getCommand() {
            return command;
        }
        
        public void setCommand(int cmd) {
            command = cmd;
        }
        
        public DShapeModel getModel() {
            return model;
        }
        
        public void setModel(DShapeModel model) {
            this.model = model;
        }
    }

}
