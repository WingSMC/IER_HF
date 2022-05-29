package drones;



import jason.asSyntax.ASSyntax;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.environment.grid.Location;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CaveEnvironment extends jason.environment.Environment {

    private Logger logger = Logger.getLogger("drones.mas2j." + this.getClass().getSimpleName());

    CaveModel model;
    CaveView view;

    int simId = 3; // type of environment
    int nbWorlds = 3;

    int sleep = 0;
    boolean running = true;
    boolean hasGUI = true;

    public enum Move {
        UP, DOWN, RIGHT, LEFT
    };

    @Override
    public void init(String[] args) {
        hasGUI = args[2].equals("yes");
        sleep = Integer.parseInt(args[1]);
        initWorld(Integer.parseInt(args[0]));
    }

    public int getSimId() { return simId; }

    public void setSleep(int s) { sleep = s; }

    @Override
    public void stop() {
        running = false;
        super.stop();
    }

    Term up = Literal.parseLiteral("do(up)");
    Term down = Literal.parseLiteral("do(down)");
    Term right = Literal.parseLiteral("do(right)");
    Term left = Literal.parseLiteral("do(left)");
    Term skip = Literal.parseLiteral("do(skip)");

    @Override
    public boolean executeAction(String ag, Structure action) {
        boolean result = false;
        try {
            if (sleep > 0) {
                Thread.sleep(sleep);
            }

            int agId = getAgIdBasedOnName(ag);
            System.out.println("name: " + ag + " id: " + agId);
            if (action.equals(up)) {
                result = model.move(Move.UP, agId);
            } else if (action.equals(down)) {
                result = model.move(Move.DOWN, agId);
            } else if (action.equals(right)) {
                result = model.move(Move.RIGHT, agId);
            } else if (action.equals(left)) {
                result = model.move(Move.LEFT, agId);
            } else if (action.equals(skip)) {
                result = true;
            } else {
                logger.info("executing: " + action + ", but not implemented!");
            }
            if (result) {
                updateAgPercept(agId);
                return true;
            }
        } catch (InterruptedException e) {
        } catch (Exception e) {
            logger.log(Level.SEVERE, "error executing " + action + " for " + ag, e);
        }
        return false;
    }

    private int getAgIdBasedOnName(String agName) { return (Integer.parseInt(agName.substring(5))) - 1; }

    public void initWorld(int w) {
        simId = w;
        try {
            model = CaveModel.world();
        } catch (Exception e) {
            logger.warning("Error creating world: " + e.getMessage());
        }

        try {
            // perceptek hozzaadasa
            clearPercepts();
            addPercept(ASSyntax.createLiteral("pos", ASSyntax.createNumber(model.getDroneX()), ASSyntax.createNumber(model.getDroneY())));
            addPercept(Literal.parseLiteral("gsize(" + simId + "," + model.getWidth() + "," + model.getHeight() + ")"));
            addPercept(Literal.parseLiteral("depot(" + simId + "," + model.getDepot().x + "," + model.getDepot().y + ")"));
            addPercept(Literal.parseLiteral("excavator1(" + simId + "," + model.getExcavator(0).x + "," + model.getExcavator(0).y + ")"));
            addPercept(Literal.parseLiteral("excavator2(" + simId + "," + model.getExcavator(1).x + "," + model.getExcavator(1).y + ")"));
            addPercept(Literal.parseLiteral("excavator3(" + simId + "," + model.getExcavator(2).x + "," + model.getExcavator(2).y + ")"));
            addPercept(Literal.parseLiteral("excavator4(" + simId + "," + model.getExcavator(3).x + "," + model.getExcavator(3).y + ")"));

            if (hasGUI) {
                view = new CaveView(model, this);
            }
            updateAgsPercept();
            informAgsEnvironmentChanged();
        } catch (Exception e) {
            logger.warning("Error initializing world: " + e);
        }
    }

    public void endSimulation() {
        addPercept(Literal.parseLiteral("end_of_simulation(" + simId + ",0)"));
        informAgsEnvironmentChanged();
        if (view != null)
            view.setVisible(false);
        CaveModel.destroy();
    }

    private void updateAgsPercept() {
        for (int i = 0; i < model.getNbOfAgs(); i++) {
            System.out.println(model.getNbOfAgs());
            System.out.println(i);
            updateAgPercept(i);
        }
    }

    private void updateAgPercept(int ag) {
        if (ag == 0)
            updateAgPercept("drone" + (ag + 1), ag);
        if (ag == 1)
            updateAgPercept("drone" + (ag + 1), ag);
        if (ag == 2)
            updateAgPercept("mechanic" + (ag + 1), ag);

    }

    private void updateAgPercept(String agName, int ag) {
        clearPercepts(agName);
        // its location
        Location l = model.getAgPos(ag);
        addPercept(agName, Literal.parseLiteral("pos(" + l.x + "," + l.y + ")"));

        // what's around
        updateAgPercept(agName, l.x - 1, l.y - 1);
        updateAgPercept(agName, l.x - 1, l.y);
        updateAgPercept(agName, l.x - 1, l.y + 1);
        updateAgPercept(agName, l.x, l.y - 1);
        updateAgPercept(agName, l.x, l.y);
        updateAgPercept(agName, l.x, l.y + 1);
        updateAgPercept(agName, l.x + 1, l.y - 1);
        updateAgPercept(agName, l.x + 1, l.y);
        updateAgPercept(agName, l.x + 1, l.y + 1);
    }


    private void updateAgPercept(String agName, int x, int y) {
        if (model == null || !model.inGrid(x, y))
            return;
        if (model.hasObject(CaveModel.OBSTACLE, x, y)) {
            addPercept(agName, Literal.parseLiteral("cell(" + x + "," + y + ",obstacle)"));
        } else {
            if (model.hasObject(CaveModel.AGENT, x, y)) {
                addPercept(agName, Literal.parseLiteral("cell(" + x + "," + y + ",ally)"));
            }
        }
    }

}
