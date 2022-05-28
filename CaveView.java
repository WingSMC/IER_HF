import jason.environment.grid.GridWorldView;

import java.awt.Color;
import java.awt.Graphics;


/** Renderer */
public class CaveView extends GridWorldView {

    private static final long serialVersionUID = 1L;

    CaveModel hmodel;

    public CaveView(CaveModel model, final CaveEnvironment env) {
        super(model, "Game of Life", 700);
        hmodel = model;
        setVisible(true);
        repaint();

        /*
         * getCanvas().addMouseListener(new MouseListener() { public void
         * mouseClicked(MouseEvent e) { int col = e.getX() / cellSizeW; int lin
         * = e.getY() / cellSizeH; if (col >= 0 && lin >= 0 && col <
         * getModel().getWidth() && lin < getModel().getHeight()) {
         * hmodel.add(LifeModel.LIFE, col, lin);
         * env.updateNeighbors(hmodel.getAgId(col, lin)); update(col, lin); } }
         * public void mouseExited(MouseEvent e) {} public void
         * mouseEntered(MouseEvent e) {} public void mousePressed(MouseEvent e)
         * {} public void mouseReleased(MouseEvent e) {} });
         */
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        c = hmodel.isAlive(x, y) ? Color.darkGray : Color.white;
        g.setColor(c);
        g.fillRect(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 1, cellSizeH - 1);
    }
}
