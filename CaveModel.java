import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import model.Cell;
import model.Drone;
import model.MapGenerator;

import java.util.ArrayList;
import java.util.List;

/** Map */
public class CaveModel extends GridWorldModel {
    public static final int DRONE = 16;
    public Cell[][] map;
    public List<Drone> drones = new ArrayList<>();

    public CaveModel(int size, int density) {
        super(size, size, size * size);
        map = MapGenerator.generate(size, density);

        try {
            // drones
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location getAgPos(int ag) { return new Location(ag / getWidth(), ag % getWidth()); }

    void alive(int ag) { add(DRONE, getAgPos(ag)); }

    void dead(int ag) { remove(DRONE, getAgPos(ag)); }
}
