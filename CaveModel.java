import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.Random;

/** Map */
public class CaveModel extends GridWorldModel {
    public static final int DRONE = 16;

    public CaveModel(int size, int density) {
        super(size, size, size * size);

        // initial agents' state (alive or dead)
        try {
            for (int i = 0; i < size; i++) {
                int ag = getAgId(i, j);
                setAgPos(ag, i, j);
                alive(ag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location getAgPos(int ag) { return new Location(ag / getWidth(), ag % getWidth()); }

    int getAgId(int x, int y) { return x * getWidth() + y; }

    void alive(int ag) { add(DRONE, getAgPos(ag)); }

    boolean isAlive(int ag) { return hasObject(DRONE, getAgPos(ag)); }

    boolean isAlive(int x, int y) { return hasObject(DRONE, x, y); }

    void dead(int ag) { remove(DRONE, getAgPos(ag)); }
}
