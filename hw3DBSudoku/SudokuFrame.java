import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	
    JTextArea source;
    JTextArea solution;
    JButton checkButton;
    JCheckBox autoCheck;
     
	public SudokuFrame() {
		super("Sudoku Solver");
		
		setLayout(new BorderLayout(4, 4));
		addTextAreas();
		addOptions();
		
		// Could do this:
		setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}
	
	// ----------- Private ------------ //
	
	// adds the text areas to the frame
	private void addTextAreas() {
	    source = new JTextArea(15, 20);
        source.setBorder(new TitledBorder("Puzzle"));
        
        // add an anonymous document listener to the source text area
        source.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                if(autoCheck.isSelected()) solve();
            }
    
            public void insertUpdate(DocumentEvent e) {
                if(autoCheck.isSelected()) solve();
            }
    
            public void removeUpdate(DocumentEvent e) {
                if(autoCheck.isSelected()) solve();
            }
        });
        
        solution = new JTextArea(15, 20);
        solution.setBorder(new TitledBorder("Solution"));
        solution.setEditable(false);
        
        add(source, BorderLayout.WEST);
        add(solution, BorderLayout.EAST);
	}
	
	// adds the check button and auto check box to the frame
	private void addOptions() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    
	    checkButton = new JButton("Check");
	    // add action listener to the check button
	    checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solve();
            }
	    });
	    
	    autoCheck = new JCheckBox("Auto Check");
	    autoCheck.setSelected(true);
	    autoCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(((AbstractButton) e.getSource()).isSelected()) solve();
            }
	    });
	    
	    panel.add(checkButton);
	    panel.add(autoCheck);
	    add(panel, BorderLayout.SOUTH);
	}
	
	// compute a solution and update the GUI.
	private void solve() {
	    try {
	        Sudoku sudoku = new Sudoku(Sudoku.textToGrid(source.getText()));
	        int numSolutions = sudoku.solve();
	        if(numSolutions > 0) {
    	        solution.setText(sudoku.getSolutionText());
    	        solution.append("\nsolutions: " + numSolutions + "\n");
    	        solution.append("elapsed: " + sudoku.getElapsed() + "ms");
	        } else {
	            solution.setText("No solutuions found");
	        }
	    } catch (RuntimeException e) {
	        solution.setText("Parsing problem");
	    }
	}

}
