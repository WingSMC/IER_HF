package mining;



import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.environment.grid.Location;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MiningPlanet extends jason.environment.Environment {

    private Logger logger = Logger.getLogger("jasonTeamSimLocal.mas2j." + MiningPlanet.class.getName());

    WorldModel model;
    WorldView view;

    int sleep = 0;
    boolean running = true;
    boolean hasGUI = true;

    public static final int SIM_TIME = 60; // in seconds

    Term up = Literal.parseLiteral("do(up)");
    Term down = Literal.parseLiteral("do(down)");
    Term right = Literal.parseLiteral("do(right)");
    Term left = Literal.parseLiteral("do(left)");
    Term skip = Literal.parseLiteral("do(skip)");

    public enum Move {
        UP, DOWN, RIGHT, LEFT
    };

    @Override
    public void init(String[] args) {
        hasGUI = args[2].equals("yes");
        sleep = Integer.parseInt(args[1]);
        initWorld(Integer.parseInt(args[0]));
    }


    public void setSleep(int s) { sleep = s; }

    @Override
    public void stop() {
        running = false;
        super.stop();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        boolean result = false;
        try {
            if (sleep > 0) {
                Thread.sleep(sleep);
            }

            // get the agent id based on its name
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

    private int getAgIdBasedOnName(String agName) { return (Integer.parseInt(agName.substring(5))) - 1; }

    public void initWorld(int w) {
        try {
            model = WorldModel.world3();
            clearPercepts();
            // for some random ass reason sim id is needed here, couldn't get
            // rid of it
            addPercept(Literal.parseLiteral("gsize(" + 1 + "," + model.getWidth() + "," + model.getHeight() + ")"));
            if (hasGUI) {
                view = new WorldView(model);
                view.setEnv(this);
            }
            updateAgsPercept();
            informAgsEnvironmentChanged();
        } catch (Exception e) {
            logger.warning("Error creating world " + e);
        }
    }

    // couldn't test, don't know how to stop the sim
    public void endSimulation() {
        addPercept(Literal.parseLiteral("end_of_simulation()"));
        informAgsEnvironmentChanged();
        if (view != null)
            view.setVisible(false);
        WorldModel.destroy();
    }

    private void updateAgsPercept() {
        for (int i = 0; i < model.getNbOfAgs(); i++) {
            updateAgPercept(i);
        }
    }

    private void updateAgPercept(int ag) { updateAgPercept("miner" + (ag + 1), ag); }

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
        if (model.hasObject(WorldModel.OBSTACLE, x, y)) {
            addPercept(agName, Literal.parseLiteral("cell(" + x + "," + y + ",obstacle)"));
        } else {
            if (model.hasObject(WorldModel.AGENT, x, y)) {
                addPercept(agName, Literal.parseLiteral("cell(" + x + "," + y + ",ally)"));
            }
        }
    }

}
