import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class WebFrame extends JFrame {
    public static final int DEFAULT_NUM_WORKERS = 4;
    
    public static final int URL_COL = 0;
    public static final int STAT_COL = 1;
    
    // I ended up retyping the strings so often that this was the best solution
    public static final String RUNNING_LABEL = "Running:";
    public static final String COMPLETED_LABEL = "Completed:";
    public static final String ELAPSED_LABEL = "Elapsed:";
    
    private DefaultTableModel model;
    private JTable table;
    private JButton singleFetchButton;
    private JButton concurrentFetchButton;
    private JButton stopButton;
    private JTextField numThreadsField;
    private JLabel runningLabel;
    private JLabel completedLabel;
    private JLabel elapsedLabel;
    private JProgressBar progressBar;
    
    private Launcher launcher;
    
    private int numRunningWorkers;
    private int numCompleted;
    private Object workerCountLock;
    private Object workerCreateLock;
    private Semaphore workerLimitLock;
    
    private long startTime;
    
    private ArrayList<Thread> workers;
    
    public WebFrame() {
        this("links.txt");
    }
    
    /*
     * constructor
     */
    public WebFrame(String file) {
        workerCountLock = new Object();
        workerCreateLock = new Object();
        numRunningWorkers = 0;
        numCompleted = 0;
        workers = new ArrayList<Thread>();
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setUpTable(file);
        setUpStartButtons();
        setUpFieldsAndLabels();
        setUpProgressBar();
        setUpStopButton();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    // alters the numRunningWorkers variable but the passed value
    // also updates the interface
    public void changeWorkerCount(int val) {
        synchronized(workerCountLock) {
            numRunningWorkers += val;
            if(val < 0) numCompleted++;
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runningLabel.setText(RUNNING_LABEL + numRunningWorkers);
                completedLabel.setText(COMPLETED_LABEL + numCompleted);
                if(numRunningWorkers == 0) {
                    setReadyState();
                    elapsedLabel.setText(ELAPSED_LABEL + (System.currentTimeMillis() - startTime) / 1000.0);
                }
            }
        });
    }
    
    // method invoked by a worker thread when it is finished
    public void sendCompletionNotice() {
        changeWorkerCount(-1);
        workerLimitLock.release();
        SwingUtilities.invokeLater(new Runnable() {
           public void run() { 
               progressBar.setValue(progressBar.getValue() + 1);
           }
        });
    }
    
    // method invoked by a worker thread to update a particular
    // row in the table with the passed message
    public void updateRow(final int row, final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               model.setValueAt(msg, row, STAT_COL);
           }
        });
    }
    
    public static void main(String[] args) {
        JFrame webFrame = new WebFrame();   
    }
    
    // ------------- Private ------------- //
    
    // sets up the table
    private void setUpTable(String file) {
        model = new DefaultTableModel(new String[] {"url", "status"}, 0);
        parseURLs(file);
        
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600, 300));
        add(scrollpane);
    }
    
    // sets up the start buttons
    private void setUpStartButtons() {
        singleFetchButton = new JButton("Single Thread Fetch");
        singleFetchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        singleFetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startLauncher(1);
            }
        });
        
        concurrentFetchButton = new JButton("Concurrent Fetch");
        concurrentFetchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        concurrentFetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startLauncher(Math.max(1, Integer.parseInt(numThreadsField.getText())));
            }
        });
        
        add(singleFetchButton);
        add(concurrentFetchButton);
    }
    
    // sets up the text field, labels
    private void setUpFieldsAndLabels() {
        numThreadsField = new JTextField(Integer.toString(DEFAULT_NUM_WORKERS));
        numThreadsField.setMaximumSize(new Dimension(100, numThreadsField.getHeight()));
        numThreadsField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        runningLabel = new JLabel(RUNNING_LABEL);
        runningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        completedLabel = new JLabel(COMPLETED_LABEL);
        completedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        elapsedLabel = new JLabel(ELAPSED_LABEL);
        elapsedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(numThreadsField);
        add(runningLabel);
        add(completedLabel);
        add(elapsedLabel);
    }
    
    // sets up the progress bar
    private void setUpProgressBar() {
        progressBar = new JProgressBar(0, 10);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        add(progressBar);
    }
    
    // sets up the stop button
    private void setUpStopButton() {
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchronized(workerCreateLock) {
                    if(launcher != null) {
                        launcher.interrupt();
                        launcher = null;
                    }
                    if(!workers.isEmpty())
                        for (Thread worker : workers)
                            worker.interrupt();
                }
            }
        });
        
        add(stopButton);
    }
    
    // parse URLs from file
    private void parseURLs(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
                model.addRow(new String[] {line, ""});
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    // starts the launcher thread
    private void startLauncher(int numWorkers) {
        resetInterface();
        workerLimitLock = new Semaphore(numWorkers);
        launcher = new Launcher(this);
        startTime = System.currentTimeMillis();
        launcher.start();
        setRunningState();
    }
    
    // resets the interface
    private void resetInterface() {
        numCompleted = 0;
        progressBar.setValue(0);
        progressBar.setMaximum(model.getRowCount());
        runningLabel.setText(RUNNING_LABEL);
        completedLabel.setText(COMPLETED_LABEL);
        elapsedLabel.setText(ELAPSED_LABEL);
        for(int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt("", i, STAT_COL);
        }
    }
    
    // sets the ready state for the GUI
    private void setReadyState() {
        singleFetchButton.setEnabled(true);
        concurrentFetchButton.setEnabled(true);
        stopButton.setEnabled(false);
        
        numThreadsField.setEnabled(true);
    }
    
    // sets the running state of the GUI
    private void setRunningState() {
        singleFetchButton.setEnabled(false);
        concurrentFetchButton.setEnabled(false);
        stopButton.setEnabled(true);
        
        numThreadsField.setEnabled(false);
    }
    
    
    /*
     * inner Launcher class used to spawn off workers
     */
    private class Launcher extends Thread {
        
        private WebFrame f;
        
        public Launcher(WebFrame f) {
            this.f = f;
        }
        
        public void run() {
            changeWorkerCount(1);
            int numURLs = model.getRowCount();
            workers.clear();
            for(int i = 0; i < numURLs; i++) {
                try {
                    workerLimitLock.acquire();
                } catch (InterruptedException e) {
                    break;
                }
                synchronized(workerCreateLock) {
                    Thread worker = new WebWorker((String) model.getValueAt(i, 0), i, f);
                    workers.add(worker);
                    worker.start();
                }
            }
            changeWorkerCount(-1);
        } 
    }
}
