

import smovielib.Effect;
import smovielib.PixelMatrix;

/**
 * A sample effect that flips the image horizontally.
 *
 */
public class FlipHorizontalEffect implements Effect {
	public void apply(PixelMatrix image, boolean sequential) {
		int width = image.getWidth();
		for (int i = 0; i < width / 2; i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int red = image.getRed(i, j);
				int green = image.getGreen(i, j);
				int blue = image.getBlue(i, j);
				image.setRed(i, j, image.getRed(width - 1 - i, j));
				image.setGreen(i, j, image.getGreen(width - 1 - i, j));
				image.setBlue(i, j, image.getBlue(width - 1 - i, j));
				image.setRed(width - 1 - i, j, red);
				image.setGreen(width - 1 - i, j, green);
				image.setBlue(width - 1 - i, j, blue);
			}
		}
	}
	
	public String toString() {
		return "Horizontal Flip";
	}
}
