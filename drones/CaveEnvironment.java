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

    int simId = 0;
    int sleep = 50;
    boolean running = true;
    boolean hasGUI = true;

    public enum Move {
        UP, DOWN, RIGHT, LEFT
    };

    @Override
    public void init(String[] args) {
        this.simId = Integer.parseInt(args[0]);
        sleep = Integer.parseInt(args[1]);
        hasGUI = args[2].equals("yes");
        initWorld(Integer.parseInt(args[3]));
    }

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

    public void initWorld(int size) {
        try {
            model = CaveModel.world(size);
        } catch (Exception e) {
            logger.warning("Error creating world: " + e.getMessage());
        }

        try {
            clearPercepts();
            addPercept(ASSyntax.createLiteral("pos", ASSyntax.createNumber(model.getDroneX()), ASSyntax.createNumber(model.getDroneY())));
            addPercept(ASSyntax.createLiteral("poz", ASSyntax.createNumber(model.getMechaX()), ASSyntax.createNumber(model.getMechaY())));
            addPercept(Literal.parseLiteral("gsize(" + simId + "," + model.getWidth() + "," + model.getHeight() + ")"));
            addPercept(Literal.parseLiteral("depot(" + simId + "," + model.getMechaX() + "," + model.getMechaY() + ")"));
            addPercept(Literal.parseLiteral("station(" + simId + "," + model.getDroneX() + "," + model.getDroneY() + ")"));
            addPercept(Literal.parseLiteral("excavator1(" + simId + "," + model.getExcavator(0).loc.x + "," + model.getExcavator(0).loc.y + ")"));
            addPercept(Literal.parseLiteral("excavator2(" + simId + "," + model.getExcavator(1).loc.x + "," + model.getExcavator(1).loc.y + ")"));
            addPercept(Literal.parseLiteral("excavator3(" + simId + "," + model.getExcavator(2).loc.x + "," + model.getExcavator(2).loc.y + ")"));
            addPercept(Literal.parseLiteral("excavator4(" + simId + "," + model.getExcavator(3).loc.x + "," + model.getExcavator(3).loc.y + ")"));

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
            updateAgPercept(i);
        }
    }

    private void updateAgPercept(int ag) {
        if (ag == 0)
            updateAgPerceptDrone("drone" + (ag + 1), ag);
        else if (ag == 1)
            updateAgPerceptMecha("mecha" + (ag + 1), ag);
    }

    private void updateAgPerceptDrone(String agName, int ag) {
        clearPercepts(agName);
        var l = model.getAgPos(ag);
        addPercept(agName, Literal.parseLiteral("pos(" + l.x + "," + l.y + ")"));
        updateHelper(agName, l);
    }

    private void updateAgPerceptMecha(String agName, int ag) {
        clearPercepts(agName);
        var l = model.getAgPos(ag);
        addPercept(agName, Literal.parseLiteral("poz(" + l.x + "," + l.y + ")"));
        updateHelper(agName, l);
    }

    private void updateHelper(String agName, Location l) {
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


    public void setSleep(int s) { sleep = s; }

    private int getAgIdBasedOnName(String agName) { return (Integer.parseInt(agName.substring(5))) - 1; }
}
