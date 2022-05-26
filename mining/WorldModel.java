package mining;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import mining.MiningPlanet.Move;

public class WorldModel extends GridWorldModel {

    // 2^n value for each agent
    // didn't delete to leave as an example
    public static final int   GOLD  = 16;
    public static final int   ENEMY = 64;

    private Logger            logger   = Logger.getLogger("jasonTeamSimLocal.mas2j." + WorldModel.class.getName());

    private String            id = "WorldModel";

    // singleton pattern
    protected static WorldModel model = null;

    //width, heigth, number of agents
    synchronized public static WorldModel create(int w, int h, int nbAgs) {
        if (model == null) {
            model = new WorldModel(w, h, nbAgs);
        }
        return model;
    }

    public static WorldModel get() {
        return model;
    }

    public static void destroy() {
        model = null;
    }

    private WorldModel(int w, int h, int nbAgs) {
        super(w, h, nbAgs);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String toString() {
        return id;
    }

    /***
     * Obvious?
     * gets a direction and agent id
     * gets agent's current location
     * moves the agent by 1 block
     */
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

    boolean pick(int ag) {
        return false;
    }

    boolean drop(int ag) {
        return false;
    }
    

    /***
     * Basily what generates the map
     * 
     * 
     */
    static WorldModel world3() throws Exception {
        WorldModel model = WorldModel.create(35, 35, 4);
        model.setId("Scenario 5");

        int x = 35;
        int y = 35;
        tester gen = new tester(x, y);

        boolean[][] blueprint= gen.getMatrix();
        // opening block of cave + 1 more block inside
        int[] startloc = gen.getStart();

        // values: id, x coord, y coord
        model.setAgPos(0, startloc[0]+1, startloc[1]+1);
        model.setAgPos(1, startloc[0]+1, startloc[1]-1);
        model.setAgPos(2, startloc[0]-1, startloc[1]+1);
        model.setAgPos(3, startloc[0]-1, startloc[1]-1);

        //don't touch, sets up obstacles according to my generation
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(!blueprint[i][j]){
                    model.add(WorldModel.OBSTACLE, i, j);
                }
            }
        }
        return model;
    }

}
