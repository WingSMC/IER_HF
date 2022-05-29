package drones;



import jason.environment.grid.GridWorldView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import drones.actor.Drone;
import drones.actor.Mechanic;


public class CaveView extends GridWorldView {
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
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        x = x * cellSizeW + 1;
        y = y * cellSizeH + 1;

        switch (id) {
        case 1:
            Drone.draw(g, x, y, cellSizeW, cellSizeH);
            return;

        default:
            Mechanic.draw(g, x, y, cellSizeW, cellSizeH);
            return;
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int object) {
        switch (object) {
        case CaveModel.DEPOT:
            g.setColor(Color.YELLOW);
            g.fillOval(x * cellSizeW + 1, y * cellSizeH + 1, cellSizeW - 2, cellSizeH - 2);
            return;
        case CaveModel.EXCAV:
            var excav = model.getExcavator(x, y);
            if (excav != null)
                excav.draw(g, cellSizeW, cellSizeH);
            return;
        case CaveModel.AGENT:
            return;
        }
    }
}
