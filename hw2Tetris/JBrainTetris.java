import java.awt.*;
import javax.swing.*;

import java.util.*;
import javax.swing.event.*;


public class JBrainTetris extends JTetris {

    private Brain brain;
    private Brain.Move bestMove;
    private int lastCount;
    
    private JCheckBox brainMode;
    private JCheckBox animate;
    private JPanel little;
    private JSlider adversary;
    private JLabel randomIndicator;
    
    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        bestMove = null;
        lastCount = count;
    }
    
    /**
     * Overrides pickNextPiece() so that the "adversary" can select
     * the worst possible next piece.
     */
    public Piece pickNextPiece() {
        if(random.nextInt(100) >= adversary.getValue()) {
            randomIndicator.setText("ok");
            return super.pickNextPiece();
        }
        
        randomIndicator.setText("*ok*");
        return getWorstPiece();
    }
    
    /**
     * Overrides tick(), Enables the brain to play tetris.
     */
    public void tick(int verb) {
        if(brainMode.isSelected()) {
            board.undo();
            
            if(lastCount != count || bestMove == null) {
                bestMove = brain.bestMove(board, currentPiece, HEIGHT, bestMove);
                lastCount = count;
            }
            
            if(verb == DOWN && bestMove != null) {
                if(bestMove.x < currentX) super.tick(LEFT);
                else if(bestMove.x > currentX) super.tick(RIGHT);
                
                if(!bestMove.piece.equals(currentPiece)) super.tick(ROTATE);
                else if(!animate.isSelected() && bestMove.x == currentX) {
                    super.tick(DROP);
                    super.tick(DOWN);
                }
            }
        }
        super.tick(verb);
    }
    
    /**
    Override - Adds "Brain check box and Adversary panel"
    
    Creates the panel of UI controls -- controls wired
    up to call methods on the JTetris. This code is very repetitive.
   */
   public JComponent createControlPanel() {
       JComponent panel = super.createControlPanel();
       
       panel.add(new JLabel("Brain:"));
       
       
       brainMode = new JCheckBox("Brain active");
       animate = new JCheckBox("Animate fall");
       animate.setSelected(true);
       animate.setEnabled(false);
       panel.add(brainMode);
       panel.add(animate);
       
       little = new JPanel();
       little.add(new JLabel("Adversary: "));
       adversary = new JSlider(0, 100, 0);
       adversary.setPreferredSize(new Dimension(100, 15));
       little.add(adversary);
       panel.add(little);
       
       randomIndicator = new JLabel("");
       panel.add(randomIndicator);
       
       brainMode.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
              updateAnimateBox();
          }
       });
       
       return panel;
   }
    
    /**
    Creates a frame with a JTetris. Taken from JTetris.java
   */
   public static void main(String[] args) {
       // Set GUI Look And Feel Boilerplate.
       // Do this incantation at the start of main() to tell Swing
       // to use the GUI LookAndFeel of the native platform. It's ok
       // to ignore the exception.
       try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch (Exception ignored) { }
       
       JBrainTetris tetris = new JBrainTetris(16);
       JFrame frame = JBrainTetris.createFrame(tetris);
       frame.setVisible(true);
   }

   // ------------ Private Methods -------------- //
   
   /*
    * returns the worst possible piece given a particular board configuration
    */
   private Piece getWorstPiece() {
       java.util.List<Piece> pieces = Arrays.asList(Piece.getPieces());
       Brain.Move worst = brain.bestMove(board, pieces.get(0), HEIGHT, null);
       for(Piece piece : pieces) {
           if(piece.equals(worst.piece)) continue;
           Brain.Move working = brain.bestMove(board, piece, HEIGHT, null);
           if(working.score > worst.score)
               worst = working;
       }
       return worst.piece;
   }
   
   /*
    * enables the Animate fall check box. There's no sense in
    * letting the user check/un-check a box that will have no effect
    * when the brain is not enabled.
    */
   private void updateAnimateBox() {
       if(brainMode.isSelected()) animate.setEnabled(true);
       else animate.setEnabled(false);
   }
}
