// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class JCount extends JPanel {
    public static final long DEFAULT_VALUE = 100000000;
    
    private JTextField bound;
    private JLabel display;
    private Worker worker;
    
	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		bound = new JTextField(Long.toString(DEFAULT_VALUE));
		display = new JLabel(Long.toString(0));
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
		       if (worker != null) worker.interrupt();
		       worker = new Worker(Long.parseLong(bound.getText()));
		       worker.start();
           }
		});
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if(worker != null) {
		            worker.interrupt();
		            worker = null;
		        }
		    }
		});
		
		add(bound);
		add(display);
		add(startButton);
		add(stopButton);
		add(Box.createVerticalStrut(40));
		
	}
	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// --------------- Private ----------------- //
	private class Worker extends Thread {
	    private long limit;
	    
	    public Worker(long limit) {
	        this.limit = limit;
	    }
	    
	    // count to the passed bound, then exit
	    public void run() {
            for(long i = 0; i <= limit; i++) {
                
                // if we're interrupted, just stop what we're doing
                if(isInterrupted()) break;
                if(i % 10000 == 0) {
                    final long val = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            display.setText(Long.toString(val));
                        }
                    });
                }
            }
        } 
	}
}

