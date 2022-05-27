package mining;



import jason.environment.grid.GridWorldView;

import java.awt.Color;
import java.awt.Graphics;

public class WorldView extends GridWorldView {

    MiningPlanet env = null;

    public WorldView(WorldModel model) {
        super(model, "Mining World", 600);
        setVisible(true);
        repaint();
    }

    public void setEnv(MiningPlanet env) { this.env = env; }


    // adds no. of agent to the picture
    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        Color idColor = Color.black;
        super.drawAgent(g, x, y, c, -1);
        idColor = Color.white;
        g.setColor(idColor);
        drawString(g, x, y, defaultFont, String.valueOf(id + 1));
    }

    // draws whole environment (agents and map and..)
    public static void main(String[] args) throws Exception {
        MiningPlanet env = new MiningPlanet();
        env.init(new String[] { "50", "yes" });
    }
}
