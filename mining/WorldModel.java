package mining;



import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mining.MiningPlanet.Move;

public class WorldModel extends GridWorldModel {

    public static final int DEPOT = 8;
    public static final int GOLD = 16;
    public static final int EXCAVATOR = 32;
    public static final int ENEMY = 64;

    Location depot;
    Location startpoz;
    int goldsInDepot = 0;
    int initialNbGolds = 0;
    public final List<Location> excavators = new ArrayList<>();

    private Logger logger = Logger.getLogger("jasonTeamSimLocal.mas2j." + WorldModel.class.getName());

    private String id = "WorldModel";

    // singleton pattern
    protected static WorldModel model = null;

    // width, heigth, number of agents
    synchronized public static WorldModel create(int w, int h, int nbAgs) {
        if (model == null) {
            model = new WorldModel(w, h, nbAgs);
        }
        return model;
    }

    public static WorldModel get() { return model; }

    public static void destroy() { model = null; }

    public void setDepot(int x, int y) {
        depot = new Location(x, y);
        data[x][y] = DEPOT;
    }

    public Location getDepot() { return depot; }

    private WorldModel(int w, int h, int nbAgs) { super(w, h, nbAgs); }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String toString() { return id; }

    public int getDroneX() { return startpoz.x; }

    public int getDroneY() { return startpoz.y; }

    public Location getExcavator(int i) {
        if (i <= 0 || excavators.size() < i)
            return null;
        return excavators.get(i);
    }


    public void setExcavator(int[][] excavator_loc) {
        excavators.add(new Location(excavator_loc[0][0], excavator_loc[0][1]));
        excavators.add(new Location(excavator_loc[1][0], excavator_loc[1][1]));
        excavators.add(new Location(excavator_loc[2][0], excavator_loc[2][1]));
        excavators.add(new Location(excavator_loc[3][0], excavator_loc[3][1]));
        /*
         * data[1][2] = EXCAVATOR; data[1][4] = EXCAVATOR; data[1][6] =
         * EXCAVATOR; data[1][8] = EXCAVATOR;
         */
    }


    public boolean isAllGoldsCollected() { return goldsInDepot == initialNbGolds; }

    public void setInitialNbGolds(int i) { initialNbGolds = i; }

    public int getInitialNbGolds() { return initialNbGolds; }

    /** Actions **/
    boolean move(Move dir, int ag) throws Exception {
        Location l = getAgPos(ag);
        switch (dir) {
        case UP:
            if (isFree(l.x, l.y - 1)) {
                setAgPos(ag, l.x, l.y - 1);
            }
            break;
        case DOWN:
            if (isFree(l.x, l.y + 1)) {
                setAgPos(ag, l.x, l.y + 1);
            }
            break;
        case RIGHT:
            if (isFree(l.x + 1, l.y)) {
                setAgPos(ag, l.x + 1, l.y);
            }
            break;
        case LEFT:
            if (isFree(l.x - 1, l.y)) {
                setAgPos(ag, l.x - 1, l.y);
            }
            break;
        }
        return true;
    }

    boolean pick(int ag) { return false; }

    boolean drop(int ag) { return false; }


    /** world with gold and obstacles */
    static WorldModel world3() throws Exception {
        int x = 35;
        int y = 35;
        WorldModel model = WorldModel.create(x, y, 3);
        model.setId("Scenario 5");

        tester gen = new tester(x, y);

        boolean[][] blueprint = gen.getMatrix();
        int[] startloc = gen.getStart();
        int[] depotloc = gen.getDepot();
        model.startpoz = new Location(startloc[0], startloc[1]);

        int[][] excavator_loc = gen.generate_excavator(4);
        for (int i = 0; i < 4; i++) {
            System.out.println("excavator: " + excavator_loc[i][0] + " " + excavator_loc[i][1]);
        }

        model.setExcavator(excavator_loc);

        model.setAgPos(0, startloc[0], startloc[1]);
        model.setAgPos(1, startloc[0], startloc[1]);
        // nem rajzolódik meg
        model.setAgPos(2, depotloc[0], depotloc[1]);

        model.setDepot(depotloc[0], depotloc[1]);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (!blueprint[i][j]) {
                    model.add(WorldModel.OBSTACLE, i, j);
                }
            }
        }

        model.setInitialNbGolds(model.countObjects(WorldModel.GOLD));
        return model;
    }

}
