package drones.actor;



import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
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
		int x = loc.x * cellSizeW + 1;
		int y = loc.y * cellSizeH + 1;
		int dX = cellSizeW - 2;
		int dY = cellSizeH - 2;
		g.drawImage(Excavator.excavatorImg, x, y, dX, dY, Color.LIGHT_GRAY, null);
		if (isFaulty) {
			int halfCellW = cellSizeW / 2, halfCellH = cellSizeH / 2;
			g.drawImage(Excavator.fireImg, x, y, halfCellW, halfCellH, null, null);
			g.setColor(Color.RED);
			g.drawRect(x, y, dX, dY);
		}
	}


	public boolean randomIsFaulty() {
		this.isFaulty = new Random().nextInt(100) < 10;
		System.out.println("faulty: " + this.isFaulty);
		return isFaulty;
	}

<<<<<<< HEAD
	public boolean getIsFaulty() {
		return isFaulty; 
		}
	

	public void isFaulty(boolean isFaulty) {
		this.isFaulty = isFaulty; 
		}
=======
	public void isFaulty(boolean isFaulty) { this.isFaulty = isFaulty; }
>>>>>>> 95be54bdd9c712a4b6400063754cf913c3dd5823
}
