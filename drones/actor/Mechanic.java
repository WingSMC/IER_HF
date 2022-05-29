package drones.actor;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mechanic {
    private static BufferedImage mechanicImg;

    static {
        try {
            mechanicImg = ImageIO.read(new File("img", "mecha.png"));
        } catch (IOException e) {
            System.err.println("Could not load mecha.png");
        }
    }

    public static void draw(java.awt.Graphics g, int x, int y, int cellSizeW, int cellSizeH) { g.drawImage(Mechanic.mechanicImg, x, y, cellSizeW - 2, cellSizeH - 2, null, null); }
}
