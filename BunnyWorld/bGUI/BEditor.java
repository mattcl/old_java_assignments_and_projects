package bGUI;

import data.*;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import script.Script;
import sun.audio.AudioStream;

import data.BDataModel;
import data.BShape;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

import java.util.*;
import java.util.List;

/**
 * 
 */

/**
 * @author Matthew Chun-Lum
 *
 */
public class BEditor extends JFrame {

    private static BDataModel currentModel;
    
    /**
     * creates a new data model
     */
    public static void createNewDataModel() {
        currentModel = new BDataModel();
    }
    
    /**
     * Sets the current data model
     * @param model
     */
    public static void setDataModel(BDataModel model) {
        currentModel = model;
    }
    
    /**
     * Static method, adds a resource to the current data model
     * @param file
     */
    public static void addResource(File file) {
        currentModel.addResource(file);
        currentModel.addResourceName(file);
    }
    
    /**
     * Static method, gets an Image from the current data model
     * @param name
     * @return
     */
    public static Image getImage(String name) {
        return currentModel.getImage(name);
    }
    
    private BShapeInspectorPanel shapeInspector;
    private BResourceInspectorPanel resourceInspector;
    private BPageInspectorPanel pageInspector;
    private BInspectorContainer inspectorContainer;
    
    private BEditorToolbar toolbar;
    private BEditorCanvas canvas;
    private BEditorCatalog catalog;
    
    private JMenuBar menuBar;
    private JMenuItem saveItem;

    private JFileChooser fileChooser;
    
    private BGame game;
    
    private JButton nextPageButton;
    private JButton prevPageButton;
    
    //public static BPageCounter pageCounter = new BPageCounter();
    //public static BShapeCounter shapeCounter = new BShapeCounter();
    
    public BEditor() {
        this(null);
    }
    
    /**
     * Default constructor, sets up the GUI and other variables
     */
    public BEditor(String targetDirectory) {
    	currentModel = new BDataModel();
        setLayout(new BorderLayout());
       
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        
        
        // set up the GUI
        setUpMenuBar();
        setUpToolbar();
        setUpCatalogPanel();
        setUpInspectorContainer();
        setUpCanvas();
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
               
        addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent event) {
    			boolean ok = doCheckQuit();
    			if (!ok) return;
    			System.exit(0);
    		}
        });
        
        addPage();
        canvas.setDirty(false);
        
        if(targetDirectory != null) {
            boolean success = false;
            File directory = new File(targetDirectory);
            if(directory.canRead()) {
                String indexPath = directory.getAbsolutePath() + File.separatorChar + "index.xml";
                File index = new File(indexPath);
                if(index.canRead()) {
                    doOpen(index);
                    success = true;
                }
            }
            if(!success) {
                JOptionPane.showMessageDialog(this, "Invalid path argument!\nDefault document loaded.");
            }
        }
        
        this.setTitle("Bunny World");
        
    }
    
    /**
     * 
     */
    public void playGame() {
        if(currentModel.getPage("page1") == null) return;
        game = new BGame("Bunny World", currentModel.clone(), this);
        
        this.setVisible(false);
    }
    
    /**
     * Displays and image chooser and gets the file
     */
    public void promptForImageFile() {
        int retVal = fileChooser.showOpenDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            BEditor.addResource(fileChooser.getSelectedFile());
            shapeInspector.addImageNameToImageChooser(fileChooser.getSelectedFile().getName());
            catalog.addNewResource(currentModel.getResourcesMap().get(fileChooser.getSelectedFile().getName()));
            showPropertiesForResource(fileChooser.getSelectedFile().getName());
            canvas.setDirty(true);
        }
    }
    
    public void promptForSoundFile() {
        int retVal = fileChooser.showOpenDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            BEditor.addResource(fileChooser.getSelectedFile());
            catalog.addNewResource(currentModel.getResourcesMap().get(fileChooser.getSelectedFile().getName())); 
            showPropertiesForResource(fileChooser.getSelectedFile().getName());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Gets the image names recognized by the document
     * @return an array of image names
     */
    public String[] getImageNames() {
        // for now
        String[] temp = {""};
        return temp;
    }
    
    public void addPage() {
    	BPage page = new BPage();
    	canvas.setSelected(null);
    	canvas.setCurrentPage(page);
    	canvas.setDirty(true);
    	page.setName(currentModel.getPageCounter().getPageName());
    	currentModel.addPage(page.getModel());
    	catalog.addNewPage(page.getModel());
    	selected(null);
    	inspectorContainer.changeInspector(BInspectorContainer.PAGE_INSPECTOR);
        pageInspector.setInspectorForPage(page.getName());
    }
    
    public void removeCurrentPage() {
        if(canvas.getCurrentPage() == null) return;
        if(canvas.getCurrentPage().getName().equals("page1")) {
            JOptionPane.showMessageDialog(this, "You cannot remove page1!", "Cannot Remove", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Object[] options = {"Remove page", "Cancel"};
        int ans = JOptionPane.showOptionDialog(this, 
                "Are you sure you want to remove this page?\nThis action cannot be undone.",
                "Remove Page?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                BIconHelper.WARNING_ICON,
                options,
                options[1]);

        if(ans == JOptionPane.YES_OPTION) {
            canvas.setSelected(null);
            selected(null);
            catalog.removePage(canvas.getCurrentPageModel());
            currentModel.removePage(canvas.getCurrentPageModel());
            canvas.setCurrentPage(null);
            canvas.repaint();
            canvas.setDirty(true);
        }
    }
    
    /**
     * Adds a new shape to the current page
     */
    public void addShape() {
        if(canvas.getCurrentPage() != null) {
            BShape shape = new BShape();
            canvas.addShape(shape);
            shape.setName(currentModel.getShapeCounter().getShapeName());
            currentModel.addShape(shape.getModel());
            catalog.addNewShape(shape, canvas.getCurrentPageModel());
            selected(shape);
        }
    }
    
    /**
     * Removes the selected shape from the canvas
     */
    public void removeSelectedShape() {
        if(canvas.hasSelected()) {
            BShape shape = canvas.getSelected();
            canvas.removeShape(shape);
            selected(null);
            catalog.removeShape(shape);
            canvas.setDirty(true);
        }
    }
    
    /**
     * Moves the selected shape to the front
     */
    public void moveSelectedToFront() {
        if(canvas.hasSelected()) {
            canvas.getCurrentPage().moveToFront(canvas.getSelected());
            canvas.repaint(canvas.getSelected().getBigBounds());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Moves the selected shape to the back
     */
    public void moveSelectedToBack() {
        if(canvas.hasSelected()) {
            canvas.getCurrentPage().moveToBack(canvas.getSelected());
            canvas.repaint(canvas.getSelected().getBigBounds());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the text for the canvas' selected shape
     * @param text
     */
    public void changeTextForSelected(String text) {
        if(canvas.hasSelected()) {
            canvas.getSelected().setText(text);
            canvas.repaint(canvas.getSelected().getBigBounds());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the font for the selected shape
     * @param fontName
     */
    public void changeFontForSelected(String fontName, boolean bold, boolean itallic) {
        if(canvas.hasSelected()) {
            BShape shape = canvas.getSelected();
            shape.setFont(fontName);
            shape.setFontBold(bold);
            shape.setFontItalics(itallic);
            canvas.repaint(shape.getBigBounds());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the color for the selected shape
     * @param newColor
     */
    public void changeColorForSelected(Color newColor) {
        if(canvas.hasSelected()) {
            canvas.getSelected().setColor(newColor);
            canvas.repaint((canvas.getSelected().getBigBounds()));
            canvas.setDirty(true);
        }
    }
    
    public void changeImageForSelected(String imageName) {
        if(canvas.hasSelected()) {
            canvas.getSelected().setImageName(imageName);
            canvas.repaint(canvas.getSelected().getBigBounds());
            catalog.shapeChanged(canvas.getSelected());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the bounds for the selected shape with the passed bounds
     * @param bounds
     */
    public void changeBoundsForSelected(Rectangle bounds) {
        if(canvas.hasSelected()) {
            BShape selected = canvas.getSelected();
            Rectangle oldBounds = selected.getBigBounds();
            selected.setBounds(bounds);
            canvas.repaint(oldBounds);
            canvas.repaint(selected.getBigBounds());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the name for the selected object
     * @param name
     */
    public void changeNameForSelected(String name) {
        if(canvas.hasSelected()) {
        	name = Script.makeScriptSafe(name);
            canvas.getSelected().setName(name);
            catalog.shapeChanged(canvas.getSelected());
            canvas.setDirty(true);
        }
    }
    
    public void changeNameForSelectedPage(String name) {
        if(canvas.getCurrentPage() != null && !name.equals("page1")) {
            canvas.getCurrentPageModel().setName(name);
            catalog.pageChanged(canvas.getCurrentPageModel());
            canvas.setDirty(true);
        }
    }
    
    /**
     * Changes the current page for the canvas to the passed page
     * @param page
     */
    public void changePage(BPageModel pageModel) {
        BPage page = new BPage(pageModel);
        page.setDrawKnobs(true);
        canvas.setCurrentPage(page);
        canvas.setSelected(null);
        catalog.setSelectedObject(page.getModel());
        inspectorContainer.changeInspector(BInspectorContainer.PAGE_INSPECTOR);
        pageInspector.setInspectorForPage(pageModel.getName());
        
    }
    
    public void changeSelectedShape(BShape shape) {
        if(canvas.getCurrentPage() != null) {
            canvas.setSelected(shape);
            selected(shape);
            catalog.setSelectedObject(shape);
        }
    }
    
    public void changeScriptForSelected(String script) {
        if(canvas.hasSelected()) {
            canvas.getSelected().getModel().setScript(script);
            canvas.setDirty(true);
        }
    }
    
    public void changeHiddenForSelected(boolean val) {
        if(canvas.hasSelected()){
            canvas.getSelected().setHidden(val);
            canvas.setDirty(true);
        }
    }
    
    public void changeMovableForSelected(boolean val) {
        if(canvas.hasSelected()){
            canvas.getSelected().setMovable(val);
            canvas.setDirty(true);
        }
    }
    
    /**
     * Method for telling the editor that we have selected
     * the current shape
     * @param shape
     */
    public void selected(BShape shape) {
        inspectorContainer.changeInspector(BInspectorContainer.SHAPE_INSPECTOR);
        shapeInspector.setInspectorForShape(shape);
        if(shape == null) {
            catalog.setSelectedObject(canvas.getCurrentPageModel());
            inspectorContainer.changeInspector(BInspectorContainer.PAGE_INSPECTOR);
            if(canvas.getCurrentPage() != null) {
                pageInspector.setInspectorForPage(canvas.getCurrentPageModel().getName());
            } else {
                pageInspector.setInspectorForPage("");
            }
        } else {
            catalog.setSelectedObject(shape);
        }
    }

    public void removeResource(String resourceName) {
        
        if(currentModel.resourceIsInUse(resourceName)) {
            Object[] options = {"Remove resource", "Cancel"};
            int ans = JOptionPane.showOptionDialog(this, 
                    "Are you sure you want to remove this resource?\nIt is currently in use by at least one shape.",
                    "Remove resource?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    BIconHelper.WARNING_ICON,
                    options,
                    options[1]);
            
            if(ans == JOptionPane.NO_OPTION) return;
        }
        ResourceModel resource = currentModel.getResourcesMap().get(resourceName);
        catalog.removeResource(resource);
        if(resource.getDataType().equals("Image"))
            shapeInspector.removeImageNameFromImagechooser(resourceName);
        currentModel.removeResource(resourceName);
        canvas.repaint();
        canvas.setDirty(true);
        inspectorContainer.changeInspector(BInspectorContainer.SHAPE_INSPECTOR);
        removeResourceFile(resourceName);
    }
    
    public void playSound(String soundName) {
        if(currentModel.getResourcesMap().containsKey(soundName))
            currentModel.playAudio(soundName);
    }
    
    public void showPropertiesForResource(String resourceName) {
        canvas.setSelected(null);
        canvas.repaint();
        shapeInspector.setInspectorForShape(null);
        inspectorContainer.changeInspector(BInspectorContainer.RESOURCE_INSPECTOR);
        resourceInspector.setResource(currentModel.getResourcesMap().get(resourceName));
    }
    
    public void disableSave() {
    	saveItem.setEnabled(false);
    }
    
    public void enableSave() {
    	saveItem.setEnabled(true);
    }
    
    // -------------- Private ------------ //
    private void nextPage() {
        if(canvas.getCurrentPage() != null) {
            BPageModel model = catalog.getNextPage(canvas.getCurrentPageModel());
            if(model != null)
                changePage(model);
        }
    }

    private void previousPage() {
        if(canvas.getCurrentPage() != null) {
            BPageModel model = catalog.getPreviousPage(canvas.getCurrentPageModel());
            if(model != null)
                changePage(model);
        }
    }
    
    /**
     * writes the resources to the directory
     */
    private void writeResourcesToFile() {
        Collection<ResourceModel> resources = currentModel.getResourcesMap().values();
        Iterator<ResourceModel> it = resources.iterator();
        while(it.hasNext()) {
            try {
                ResourceModel r = it.next();
                String newFilePath = currentModel.getDocDirectory() + File.separatorChar + r.getName();
                File resourceFile = new File(newFilePath);
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resourceFile));
                if(r.getDataType().equals("Image")) saveImage(r, outputStream);
                else if(r.getDataType().equals("AudioStream")) saveAudio(r, outputStream);
                r.setFilePath(newFilePath);
                outputStream.close();
            } catch (IOException e) {
                System.out.println("uhoh");
            } 
        }   
    }
    
    /**
     * helps to write images out
     * @param r
     * @param os
     */
    public void saveImage(ResourceModel r, OutputStream os) {
        BufferedImage buffImage = (BufferedImage)r.getImage();
        try {
            ImageIO.write(buffImage, "png", os);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * helps to write audio out
     * @param r
     * @param os
     */
    public void saveAudio(ResourceModel r, OutputStream os) {
        try {
            File f = new File(r.getFilePath());
            if(!f.exists()) {
                AudioFileFormat format = AudioSystem.getAudioFileFormat(f);
                AudioFileFormat.Type type = format.getType();
                AudioSystem.write(AudioSystem.getAudioInputStream(f), type, os);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * write the bdatamodel out to index.xml
     */
    private void writeDataToFile() {
        try {
            String newFilePath = currentModel.getDocDirectory() + File.separatorChar + "index.xml";
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(newFilePath)));
            e.writeObject(currentModel);
            e.close();
        } catch (IOException e) {
            System.out.println("uhoh");
        }
    }
    
    /**
     * opens the specified file of the user
     */
    private void handleOpen() {
        doCheckQuit();
        canvas.setDirty(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int status = fileChooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            boolean success = false;
            File dest = fileChooser.getSelectedFile();
            if(dest.isDirectory()) {
                String path = dest.getAbsolutePath() + File.separatorChar + "index.xml";
                File index = new File(path);
                if(index.canRead()) {
                    if(index.getName().endsWith(".xml"))
                        success = true;
                        doOpen(index);
                }
            }
            if(!success) {
                JOptionPane.showMessageDialog(this, "Could not open document from that directory!");
            }
        }
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
    
    /**
     * opens the selected xml index file.
     * @param file
     */
    private void doOpen(File file) {
        try { 
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
            BDataModel result = (BDataModel)d.readObject();
            d.close();
            currentModel = result;
            currentModel.setDocDirectory(file.getParent());
            currentModel.loadResources();
            List<String> resources = currentModel.getResourceNames();
            shapeInspector.getImageChooser().removeAllItems();
            shapeInspector.addImageNameToImageChooser("none");
            for(int i=0; i<resources.size(); i++) {
            	if(resources.get(i).endsWith(".jpeg") || resources.get(i).endsWith(".gif"))
            		shapeInspector.addImageNameToImageChooser(resources.get(i));
            }
            BPage newCurrent = new BPage();
            newCurrent.setModel(currentModel.getPage("page1"));
            canvas.setCurrentPage(newCurrent);
            catalog.loadFromDataModel(currentModel);
            selected(null);
        } catch (IOException e) {
            System.out.println("uhoh");
        }
    }
    
    // Asks if they want to save, and if so does it.
    // Returns false if they cancel.
    public boolean saveOk() {
        int result = JOptionPane.showConfirmDialog(
                this, "Save changes first?", "Save?",
                JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            handleSave();
        }
        
        return (result != JOptionPane.CANCEL_OPTION);
    }
    
    // Checks if quitting is ok, and saves if needed.
    // Returns false if they cancel.
    public boolean doCheckQuit() {
        if (canvas.getDirty()) {
            boolean ok = saveOk();
            if (!ok) return false;
        }
        return true;
    }
    
    /**
     * Handles the save button, checks if the directory is null, and creates a new diectory if needed
     * saves the resources and bdatamodel
     */
    private void handleSave() {
        if(currentModel.getDocDirectory()==null) {
            handleSaveAs();
            return;
        }
        writeResourcesToFile();
        writeDataToFile();
        canvas.setDirty(false);
    }
    
    /**
     * prompts the user to enter a file name then calls its doSaveAs
     */
    private void handleSaveAs() {
        int status = fileChooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File dest = fileChooser.getSelectedFile();
            doSaveAs(dest);
        }
    }
    
    /**
     * makes a directory if needed and saves the resources and the bdatamodel
     * @param file
     */
    private void doSaveAs(File file) {
        file.mkdir();
        currentModel.setDocDirectory(file.getAbsolutePath());
        currentModel.setSaved(true);
        writeResourcesToFile();
        writeDataToFile();
        canvas.setDirty(false);
    }
    
    private void removeResourceFile(String resourceName) {
    	if(currentModel.getSaved()) {
    		File file = new File(currentModel.getDocDirectory() + File.separatorChar + resourceName);
    		if(file.exists()) file.delete();
    	}
    }
    
    private void createNewDocument() {
    	currentModel = new BDataModel();
        shapeInspector.getImageChooser().removeAllItems();
        shapeInspector.addImageNameToImageChooser("none");
        catalog.loadFromDataModel(currentModel);
        addPage();
        
        selected(null);
    }
    
    // ------------ GUI SET UP ----------- //
    
    /**
     * Sets up the top menu bar for saving/opening, etc.
     */
    private void setUpMenuBar() {
        menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleSave();
        	}
        });
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleSaveAs();
        	}
        });
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleOpen();
        	}
        });
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		createNewDocument();
        	}
        });
        
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(openItem);
        
        JMenu aboutMenu = new JMenu("Bunny World");
        JMenuItem aboutItem = new JMenuItem("About");
        
        aboutItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AboutBox about = new AboutBox();
        	}
        });
        
        aboutMenu.add(aboutItem);
        
        menuBar.add(aboutMenu);
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
    }
	
    /**
     * Sets up the Toolbar (NORTH). We may want this, so it's here
     */
    private void setUpToolbar() {
        toolbar = new BEditorToolbar(this);
        add(toolbar, BorderLayout.NORTH);
    }
    
    /**
     * Sets up the catalog panel (WEST)
     * will not be implemented for the early demo
     */
    private void setUpCatalogPanel() {
        catalog = new BEditorCatalog(this);
        catalog.loadFromDataModel(currentModel);
        add(catalog, BorderLayout.WEST);
    }
    
    /**
     * Sets up the shape inspector panel (EAST) for editing/adding shapes
     */
    private void setUpInspectorContainer() {
        resourceInspector = new BResourceInspectorPanel(this);
        shapeInspector = new BShapeInspectorPanel(this);
        pageInspector = new BPageInspectorPanel(this);
        
        inspectorContainer = new BInspectorContainer(this, shapeInspector, resourceInspector, pageInspector);
        inspectorContainer.changeInspector(BInspectorContainer.SHAPE_INSPECTOR);
        add(inspectorContainer, BorderLayout.EAST);
    }
    
    /**
     * Sets up the canvas portion, placing it in the
     * center of the border layout
     */
    private void setUpCanvas() {
        canvas = new BEditorCanvas(this);
        canvas.setMaximumSize(canvas.getPreferredSize());
        canvas.setBackground(Color.gray);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        
        nextPageButton = new JButton("Next Page", BIconHelper.NEXT_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(nextPageButton);
        nextPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        });
        
        prevPageButton = new JButton("Previous Page", BIconHelper.PREVIOUS_ICON);
        BIconHelper.setPropertiesOfButtonToAcceptIcon(prevPageButton);
        prevPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousPage();
            }
        });
        
        controlPanel.add(prevPageButton);
        controlPanel.add(nextPageButton);
        controlPanel.add(Box.createHorizontalGlue());
        
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(canvas.getPreferredSize());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.gray);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(canvas);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(controlPanel);
        
        add(centerPanel, BorderLayout.CENTER);
    }
}
