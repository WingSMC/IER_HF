package drones.actor;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Depot {
	private static BufferedImage depotImg;

	static {
		try {
			depotImg = ImageIO.read(new File("img", "depot.png"));
		} catch (IOException e) {
			System.err.println("Could not load excavator depot.png");
		}
	}

	public static void draw(java.awt.Graphics g, int x, int y, int cellSizeW, int cellSizeH) { g.drawImage(Depot.depotImg, x, y, cellSizeW - 2, cellSizeH - 2, null, null); }
}
