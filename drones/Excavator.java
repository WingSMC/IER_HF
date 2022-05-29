package drones;



import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import jason.environment.grid.Location;

public class Excavator {
	public final Location loc;
	private static BufferedImage image;
	boolean isFaulty = false;

	static {
		try {
			image = ImageIO.read(new File("resources", "excavator.png"));
		} catch (IOException e) {
			System.err.println("Could not load excavator image.");
		}
	}

	public Excavator(Location loc) {
		isFaulty = new Random().nextInt(100) < 40;
		this.loc = loc;
	}

	public void draw(java.awt.Graphics g, int cellSizeW, int cellSizeH) {
		var color = isFaulty ? Color.RED : Color.GREEN;
		g.setColor(color);
		g.drawImage(Excavator.image, loc.x * cellSizeW + 1, loc.y * cellSizeH + 1, cellSizeW, cellSizeH, color, null);
		g.drawRect(loc.x * cellSizeW + 1, loc.y * cellSizeH + 1, cellSizeW, cellSizeH);
	}


	public boolean isFaulty() { return isFaulty; }

	public void isFaulty(boolean isFaulty) { this.isFaulty = isFaulty; }
}
