package bGUI;



import java.io.File;
/**
 * 
 */


/**
 * @author Chidozie Nwobilor, Junichi Tsutsui, Matthew Chun-Lum
 *
 */
public class Bunny {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BEditor editor;
        
        if(args.length > 0) {
            String targetDirectory = args[0];
            editor = new BEditor(targetDirectory); 
        } else {
            editor = new BEditor();
        }
        
    }

}
