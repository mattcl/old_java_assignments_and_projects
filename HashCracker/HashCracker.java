import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HashCracker extends JFrame {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashCracker cracker = new HashCracker();

    }
    
    private JMenuItem openItem, saveItem, quitItem;
    
    private JButton startButton;
    private JButton stopButton;
    private JButton addButton;
    
    private JTextField usernameField;
    private JTextField hashField;
    private JTextField maxLengthField;
    private JTextField minLengthField;
    private JTextField numThreadsField;
    
    private JTable table;
    private CrackerTableModel tableModel;
    
    private Cracker cracker;
    
    private long startTime;
    
    public HashCracker() {
        this.setLayout(new BorderLayout());
        
        cracker = null;
        
        setUpMenuBar();
        setUpToolBar();
        setUpTable();
        setUpBottomBar();
        setUpSidePanel();
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        String targets[] = {"c5e478e7da53b70f0fabcdefa082e1d1c5a2bc6d", "66b27417d37e024c46526c2f6d358a754fc552f3", "34800e15707fae815d7c90d49de44aca97e2d759"};
        for(int i = 0; i < targets.length; i++) {
            tableModel.addRow(new User(i+"", targets[i]));
        }
    }
    
    public void addSolution(String password, String hash) {
        tableModel.setPassword(password, hash, System.currentTimeMillis() - startTime);
    }
    
    public void signalDone() {
        
    }
    
    // ------------------- Private ------------------ //
    private void startCracking() {
        
        ArrayList<String> targets = new ArrayList<String>();
        for(int i = 0; i < tableModel.getRowCount(); i++)
            if(!tableModel.getValueAt(i, 0).equals("") && !tableModel.getValueAt(i, 2).equals(""))
                if(((String) tableModel.getValueAt(i, 2)).length() == 40)
                    targets.add((String) tableModel.getValueAt(i, 2));
        
        String targs[] = targets.toArray(new String[0]);
        
        if(targs.length < 1) return;
        
        int max = Integer.parseInt(maxLengthField.getText().trim());
        int min = Integer.parseInt(minLengthField.getText().trim());
        int thr = Integer.parseInt(numThreadsField.getText().trim());
        
        if(cracker == null) {
            cracker = new Cracker(targs, max, min, thr, this);
        }
        startTime = System.currentTimeMillis();
        cracker.start();    
        
        tableModel.setEditable(false);
    }
    
    private void stopCracking() {
        if(cracker != null) {
            cracker.interrupt();
        }
        cracker = null;
    }
    
    private void addUser() {
        tableModel.addRow(new User(usernameField.getText().trim(), hashField.getText().trim()));
        usernameField.setText("");
        hashField.setText("");
    }
    
    // -------------- GUI Set Up Code --------------- //
    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Close");
        quitItem = new JMenuItem("Quit");
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(quitItem);
        
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setUpToolBar() {
        Box toolbarBox = Box.createHorizontalBox();
        toolbarBox.setPreferredSize(new Dimension(700, 40));
        
        startButton = new JButton("Crack!");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startCracking();
            }
        });
        
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopCracking();
            }
        });
        
        toolbarBox.add(Box.createHorizontalGlue());
        toolbarBox.add(startButton);
        toolbarBox.add(stopButton);
        add(toolbarBox, BorderLayout.NORTH);
    }
    
    private void setUpBottomBar() {
        Box box = Box.createVerticalBox();
        box.setPreferredSize(new Dimension(700, 80));
        
        Box nameBox = Box.createHorizontalBox();
        
        JLabel nameLabel = new JLabel("Username");
        
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, usernameField.getHeight()));
        
        nameBox.add(Box.createHorizontalStrut(10));
        nameBox.add(nameLabel);
        nameBox.add(usernameField);
        nameBox.add(Box.createHorizontalGlue());
        nameBox.add(Box.createHorizontalStrut(300));
        
        Box hashBox = Box.createHorizontalBox();
        
        JLabel hashLabel = new JLabel("Hash");
        
        hashField = new JTextField();
        hashField.setPreferredSize(new Dimension(350, hashField.getHeight()));
        
        hashBox.add(Box.createHorizontalStrut(10));
        hashBox.add(hashLabel);
        hashBox.add(hashField);
        hashBox.add(Box.createHorizontalGlue());
        hashBox.add(Box.createHorizontalStrut(300));
        
        box.add(Box.createVerticalStrut(5));
        box.add(nameBox);
        box.add(Box.createVerticalStrut(5));
        box.add(hashBox);
        
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        
        box.add(addButton);
        
        nameBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        hashBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        addButton.setAlignmentX(Box.LEFT_ALIGNMENT);
        
        add(box, BorderLayout.SOUTH);
    }
    
    private void setUpSidePanel() {
        Box box = Box.createVerticalBox();
        
        minLengthField = new JTextField("6");
        minLengthField.setPreferredSize(new Dimension(40, 30));
        minLengthField.setMaximumSize(minLengthField.getPreferredSize());
        
        maxLengthField = new JTextField("8");
        maxLengthField.setPreferredSize(new Dimension(40, 30));
        maxLengthField.setMaximumSize(maxLengthField.getPreferredSize());
        
        numThreadsField = new JTextField("5");
        numThreadsField.setPreferredSize(new Dimension(40, 30));
        numThreadsField.setMaximumSize(numThreadsField.getPreferredSize());
        
        Box lengthBox = Box.createHorizontalBox();
        
        lengthBox.add(new JLabel("Min"));
        lengthBox.add(minLengthField);
        lengthBox.add(new JLabel("Max"));
        lengthBox.add(maxLengthField);
        //lengthBox.add(Box.createHorizontalGlue());
        
        Box numThreadsBox = Box.createHorizontalBox();
        numThreadsBox.add(new JLabel("Num Threads"));
        numThreadsBox.add(numThreadsField);
        //numThreadsBox.add(Box.createHorizontalGlue());
        
        box.add(lengthBox);
        box.add(numThreadsBox);
        
        add(box, BorderLayout.EAST);
        
        
    }
    
    private void setUpTable() {
        tableModel = new CrackerTableModel();
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(CrackerTableModel.columnWidths[i]);
        }
        
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(700, 300));
        scrollpane.setMaximumSize(new Dimension(700, 300));
        add(scrollpane, BorderLayout.CENTER);
        
    }
    

}
