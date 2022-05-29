import jason.environment.grid.GridWorldView;

import java.awt.Color;
import java.awt.Graphics;


/** Renderer */
public class CaveView extends GridWorldView {
    CaveModel caveModel;

    public CaveView(CaveModel caveModel, final CaveEnvironment env) {
        super(caveModel, "Drone cave", 700);
        this.caveModel = caveModel;
        setVisible(true);
        repaint();
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        c = caveModel.isAlive(x, y) ? Color.darkGray : Color.white;
        g.setColor(c);
        g.fillRect(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 1, cellSizeH - 1);
    }
}
