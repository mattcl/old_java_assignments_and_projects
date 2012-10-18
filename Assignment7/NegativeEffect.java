import smovielib.Effect;
import smovielib.PixelMatrix;


/**
 * An effect that does a film-style negative.
 *
 */
public class NegativeEffect implements Effect {
	public void apply(PixelMatrix image, boolean sequential) {
	    for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRed(i, j, 255 - image.getRed(i, j));
                image.setGreen(i, j, 255 - image.getGreen(i, j));
                image.setBlue(i, j, 255 - image.getBlue(i, j));
            }
        }
	}

	public String toString() {
		return "Negative";
	}
}
