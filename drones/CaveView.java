package drones;



import jason.environment.grid.GridWorldView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class CaveView extends GridWorldView {
    private Logger logger = Logger.getLogger("drones.mas2j." + this.getClass().getSimpleName());
    CaveEnvironment env = null;
    CaveModel model;

    public CaveView(CaveModel model, CaveEnvironment env) {
        super(model, "Drones", 600);
        this.model = model;
        this.env = env;
        setVisible(true);
        repaint();
    }

    @Override
    public void initComponents(int width) {
        super.initComponents(width);
        JPanel args = new JPanel();
        args.setLayout(new BoxLayout(args, BoxLayout.Y_AXIS));

        JPanel sp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sp.setBorder(BorderFactory.createEtchedBorder());

        args.add(sp);

        JPanel msg = new JPanel();
        msg.setLayout(new BoxLayout(msg, BoxLayout.Y_AXIS));
        msg.setBorder(BorderFactory.createEtchedBorder());


        JPanel s = new JPanel(new BorderLayout());
        s.add(BorderLayout.WEST, args);
        s.add(BorderLayout.CENTER, msg);
        getContentPane().add(BorderLayout.SOUTH, s);
    }

    @Override
    public void paint(Graphics g) {
        logger.info("paint");
        super.paint(g);
        var excavators = ((CaveModel) model).excavators;
        excavators.stream().forEach(e -> {
            int x = e.x;
            int y = e.y;
            g.setColor(Color.YELLOW);
            g.fillOval(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 2, cellSizeH - 2);
        });
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        Color idColor = Color.black;
        super.drawAgent(g, x, y, c, -1);
        idColor = Color.white;
        g.setColor(idColor);
        drawString(g, x, y, defaultFont, String.valueOf(id + 1));
    }

    @Override
    public void draw(Graphics g, int x, int y, int object) {
        super.draw(g, x, y, object);
        switch (object) {
        case CaveModel.DEPOT:
            g.setColor(Color.RED);
            g.fillOval(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 2, cellSizeH - 2);
            return;
        case CaveModel.EXCAV:
            g.setColor(Color.YELLOW);
            g.fillOval(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 2, cellSizeH - 2);
            return;
        case CaveModel.AGENT:
            return;
        }
    }
}
