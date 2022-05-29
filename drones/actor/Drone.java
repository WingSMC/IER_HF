package drones.actor;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drone {
    private static BufferedImage droneImg;

    static {
        try {
            droneImg = ImageIO.read(new File("img", "drone.png"));
        } catch (IOException e) {
            System.err.println("Could not load excavator drone.png");
        }
    }

    public static void draw(java.awt.Graphics g, int x, int y, int cellSizeW, int cellSizeH) { g.drawImage(Drone.droneImg, x, y, cellSizeW - 2, cellSizeH - 2, null, null); }
}
