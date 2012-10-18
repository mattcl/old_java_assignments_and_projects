

import smovielib.Effect;
import smovielib.PixelMatrix;


/**
 * An effect that flips the frame vertically.
 *
 */
public class FlipVerticalEffect implements Effect {
	public void apply(PixelMatrix image, boolean sequential) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() / 2; j++) {
                int swapY = image.getHeight() - 1 - j;
                int red = image.getRed(i, j);
                int green = image.getGreen(i, j);
                int blue = image.getBlue(i, j);
                image.setRed(i, j, image.getRed(i, swapY));
                image.setGreen(i, j, image.getGreen(i, swapY));
                image.setBlue(i, j, image.getBlue(i, swapY));
                image.setRed(i, swapY, red);
                image.setGreen(i, swapY, green);
                image.setBlue(i, swapY, blue);
            }
        }
	}
	
	public String toString() {
		return "Vertical Flip";
	}

}
