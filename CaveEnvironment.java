import jason.asSyntax.ASSyntax;
import jason.asSyntax.Structure;

import java.util.logging.Logger;

/** MAS environment */
public class CaveEnvironment extends jason.environment.TimeSteppedEnvironment {

    private Logger logger = Logger.getLogger("drones.mas2j.CaveEnvironment");

    private CaveModel caveModel;

    /** MAS init */
    @Override
    public void init(String[] args) {
        super.init(new String[] { "1000" }); // max steps
        setOverActionsPolicy(OverActionsPolicy.ignoreSecond);
        int size = Integer.parseInt(args[0]);
        int density = Integer.parseInt(args[1]);
        caveModel = new CaveModel(size, density);
        caveModel.setView(new CaveView(caveModel, this));
        updateAgsPercept();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        int ag = getAgIdBasedOnName(agName);
        switch (action.getFunctor()) {
        case default: // skip
            return true;
        case "die":
            caveModel.dead(ag);
            return true;
        case "live":
            caveModel.alive(ag);
            return true;
        }
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
        for (int i = 0; i < caveModel.getNbOfAgs(); i++) {
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
