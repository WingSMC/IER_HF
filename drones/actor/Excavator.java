package drones.actor;



import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import jason.environment.grid.Location;

public class Excavator {
	public final Location loc;
	private static BufferedImage excavatorImg;
	private static BufferedImage fireImg;
	boolean isFaulty = false;

	static {
		try {
			excavatorImg = ImageIO.read(new File("img", "excavator.png"));
			fireImg = ImageIO.read(new File("img", "fire.png"));
		} catch (IOException e) {
			System.err.println("Could not load excavator/fire.png");
		}
	}

	public Excavator(Location loc) {
		isFaulty = false;
		this.loc = loc;
	}

	public void draw(java.awt.Graphics g, int cellSizeW, int cellSizeH) {
		g.drawImage(Excavator.excavatorImg, loc.x * cellSizeW + 1, loc.y * cellSizeH + 1, cellSizeW - 2, cellSizeH - 2, null, null);
		if (isFaulty) {
			int halfCellW = cellSizeW / 2, halfCellH = cellSizeH / 2;
			g.drawImage(Excavator.fireImg, loc.x * cellSizeW + 1, loc.y * cellSizeH + 1, halfCellW, halfCellH, null, null);
		}
	}


	public boolean isFaulty() {
		this.isFaulty = new Random().nextInt(100) < 40;
		System.out.println("faulty: " + this.isFaulty);
		return isFaulty; 
		}

	public void isFaulty(boolean isFaulty) {
		this.isFaulty = isFaulty; 
		}
}
