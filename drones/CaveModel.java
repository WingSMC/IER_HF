package drones;



import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.ArrayList;
import java.util.List;

import drones.CaveEnvironment.Move;
import drones.actor.Excavator;

public class CaveModel extends GridWorldModel {
    // 0:clean; 2(<<1):agent; (<<2)4:obstacle
    public static final int DEPOT = 1 << 3;
    public static final int EXCAV = 1 << 4;
    Location startpoz;
    Location depotpoz;
    private final List<Excavator> excavators = new ArrayList<>();
    private String id = "Cave";

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

    public void setObject(int type, int x, int y) { data[x][y] |= type; }

    public void removeObject(int type, int x, int y) { data[x][y] &= ~type; }

    public String getId() { return id; }

    public int getDroneX() { return startpoz.x; }

    public int getDroneY() { return startpoz.y; }

    public int getMechaX() { return depotpoz.x; }

    public int getMechaY() { return depotpoz.y; }

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

    public Excavator getExcavator(int i) {
        if (i < 0 || excavators.size() <= i)
            return null;
        return excavators.get(i);
    }

    public Excavator getExcavator(int x, int y) {
        for (Excavator e : excavators) {
            if (x == e.loc.x && y == e.loc.y) {
                return e;
            }
        }
        return null;
    }

    private void setExcavator(int[][] excavator_loc) {
        for (int[] is : excavator_loc) {
            excavators.add(new Excavator(new Location(is[0], is[1])));
        }
    }

    @Override
    public String toString() { return id; }


    /** world setup */
    static CaveModel world(int size) throws Exception {
        CaveModel model = CaveModel.create(size, size, 2);
        Generator gen = new Generator(size, size);
        boolean[][] blueprint = gen.getMatrix();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!blueprint[i][j]) {
                    model.add(CaveModel.OBSTACLE, i, j);
                }
            }
        }

        int[] startloc = gen.getStart();
        int[] depotloc = gen.getDepot();
        int[][] excavator_loc = gen.generate_excavator(4);
        model.startpoz = new Location(startloc[0], startloc[1]);
        model.depotpoz = new Location(depotloc[0], depotloc[1]);

        model.setExcavator(excavator_loc);
        model.setObject(DEPOT, depotloc[0], depotloc[1]);
        model.setAgPos(0, startloc[0], startloc[1]);
        model.setAgPos(1, depotloc[0], depotloc[1]);

        for (final var exc : excavator_loc) {
            model.setObject(EXCAV, exc[0], exc[1]);
        }
        return model;
    }
}
