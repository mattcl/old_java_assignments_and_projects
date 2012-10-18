import acm.graphics.*;
import java.awt.*;

public class Scaffold extends GRect {
	
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18+36*2;
	
	private static final int BOTTOM_LENGTH = 51;
	private static final int BOTTOM_MID = 26;
	
	private int offsetX, offsetY;
	
	public Scaffold(int x, int y) {
		super(0,0);
		offsetX = x;
		offsetY = y;
	}
	
	public void paint(Graphics g) {
		g.drawLine(offsetX,offsetY+SCAFFOLD_HEIGHT,offsetX+BOTTOM_LENGTH,offsetY+SCAFFOLD_HEIGHT);
		g.drawLine(offsetX+BOTTOM_MID, offsetY, offsetX+BOTTOM_MID, offsetY+SCAFFOLD_HEIGHT);
		g.drawLine(offsetX+BOTTOM_MID,offsetY,offsetX+BOTTOM_MID+BEAM_LENGTH,offsetY);
		g.drawLine(offsetX+BOTTOM_MID+BEAM_LENGTH,offsetY,offsetX+BOTTOM_MID+BEAM_LENGTH,offsetY+ROPE_LENGTH);
	}
}
