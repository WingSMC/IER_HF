import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.grid.Location;

import java.util.logging.Logger;

public class CaveEnvironment extends jason.environment.TimeSteppedEnvironment {

    private Logger logger = Logger.getLogger("game-of-life.mas2j." + CaveEnvironment.class.getName());

    private CaveModel model;

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(new String[] { "3000" }); // set step timeout
        setOverActionsPolicy(OverActionsPolicy.ignoreSecond);
        model = new CaveModel(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        model.setView(new CaveView(model, this));
        updateAgsPercept();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        int ag = getAgIdBasedOnName(agName);
        var loc = model.getAgPos(ag);
        if (loc.x == 0 || loc.x == model.getWidth() - 1 || loc.y == 0 || loc.y == model.getHeight() - 1) {
            model.alive(ag);
            return true;
        }

        String actId = action.getFunctor();
        if (actId.equals("skip")) {
            return true;
        } else if (actId.equals("die")) {
            model.dead(ag);
        } else if (actId.equals("live")) {
            model.alive(ag);
        }
        return true;
    }

    @Override
    protected void stepStarted(int step) {}

    private long sum = 0;

    @Override
    protected void stepFinished(int step, long time, boolean timeout) {
        long mean = (step > 0 ? sum / step : 0);
        logger.info("step " + step + " finished in " + time + " ms. mean = " + mean);
        sum += time;
    }

    int getAgIdBasedOnName(String agName) { return (Integer.parseInt(agName.substring(4))) - 1; }

    @Override
    protected void updateAgsPercept() {
        for (int i = 0; i < model.getNbOfAgs(); i++) {
            updateAgPercept(i);
        }
    }

    void updateAgPercept(int ag) {
        String name = "cell" + (ag + 1);
        updateAgPercept(name, ag);
    }

    void updateAgPercept(String agName, int ag) {
        clearPercepts(agName);
        addPercept(agName, ASSyntax.createLiteral("alive_neighbors", ASSyntax.createNumber(3)));
        addPercept(agName, ASSyntax.createLiteral("step", ASSyntax.createNumber(getStep())));
    }
}
