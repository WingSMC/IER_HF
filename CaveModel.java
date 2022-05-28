import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.Random;

/** Map */
public class CaveModel extends GridWorldModel {
    public static final int LIFE = 16;

    Random random = new Random();

    public CaveModel(int size, int density) {
        super(size, size, size * size);
        var maEmpty = (int) (size * size * (density / 100.0));

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

    void alive(int ag) { add(LIFE, getAgPos(ag)); }

    boolean isAlive(int ag) { return hasObject(LIFE, getAgPos(ag)); }

    boolean isAlive(int x, int y) { return hasObject(LIFE, x, y); }

    void dead(int ag) { remove(LIFE, getAgPos(ag)); }
}
