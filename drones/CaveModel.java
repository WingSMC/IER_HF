package drones;



import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import drones.CaveEnvironment.Move;

public class CaveModel extends GridWorldModel {
    // 0:clean; 2:agent; 4:obstacle
    public static final int DEPOT = 1 << 3;
    public static final int EXCAV = 1 << 4;

    Location depot;
    Location startpoz;
    public final List<Location> excavators = new ArrayList<>();

    private Logger logger = Logger.getLogger("drones.mas2j." + this.getClass().getSimpleName());

    private String id = "WorldModel";

    // singleton
    protected static CaveModel model = null;

    private CaveModel(int w, int h, int nbAgs) { super(w, h, nbAgs); }

    synchronized public static CaveModel create(int w, int h, int nbAgs) {
        if (model == null) {
            model = new CaveModel(w, h, nbAgs);
        }
        return model;
    }

    public static CaveModel get() { return model; }

    public static void destroy() { model = null; }

    public void setObject(int type, int x, int y) {
        depot = new Location(x, y);
        data[x][y] |= type;
    }

    public void removeObject(int type, int x, int y) { data[x][y] &= ~type; }

    public Location getDepot() { return depot; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int getDroneX() { return startpoz.x; }

    public int getDroneY() { return startpoz.y; }

    public Location getExcavator(int i) {
        if (i < 0 || excavators.size() <= i)
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
    static CaveModel world() throws Exception {
        int x = 35;
        int y = 35;
        CaveModel model = CaveModel.create(x, y, 3);
        model.setId("Scenario 5");

        Generator gen = new Generator(x, y);

        boolean[][] blueprint = gen.getMatrix();
        int[] startloc = gen.getStart();
        int[] depotloc = gen.getDepot();
        model.startpoz = new Location(startloc[0], startloc[1]);

        int[][] excavator_loc = gen.generate_excavator(4);

        model.setExcavator(excavator_loc);
        model.setAgPos(0, startloc[0], startloc[1]);
        model.setAgPos(1, startloc[0], startloc[1]);
        // nem rajzolódik meg
        model.setAgPos(2, depotloc[0], depotloc[1]);
        model.setObject(DEPOT, depotloc[0], depotloc[1]);
        for (final var exc : excavator_loc) {
            model.setObject(EXCAV, exc[0], exc[1]);
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (!blueprint[i][j]) {
                    model.add(CaveModel.OBSTACLE, i, j);
                }
            }
        }
        return model;
    }


    @Override
    public String toString() { return id; }
}
