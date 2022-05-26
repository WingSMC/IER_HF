import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class Generator extends GridWorldModel {


    Location                  depot;
    Set<Integer>              agWithGold;  // which agent is carrying gold
    int                       goldsInDepot   = 0;
    int                       initialNbGolds = 0;

    private Logger            logger   = Logger.getLogger("jasonTeamSimLocal.mas2j." + Generator.class.getName());

    private String            id = "WorldModel";

    // singleton pattern
    protected static Generator model = null;

    //width, heigth, number of agents
    synchronized public static Generator create(int w, int h, int nbAgs) {
        if (model == null) {
            model = new Generator(w, h, nbAgs);
        }
        return model;
    }

    public static Generator get() {
        return model;
    }

    public static void destroy() {
        model = null;
    }

    private Generator(int w, int h, int nbAgs) {
        super(w, h, nbAgs);
        agWithGold = new HashSet<Integer>();
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

    //change to picck poz data?
    boolean pick(int ag) {
        return false;
    }

    //change to share poz data?
    boolean drop(int ag) {
        return false;
    }

    /** world with gold and obstacles */
    static Generator generateworld(int x, int y, int n) throws Exception {
        Generator model = Generator.create(x, y, n);
        tester gen = new tester(x, y);
        boolean[][] blueprint= gen.getMatrix();

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(!blueprint[i][j]){
                    model.add(Generator.OBSTACLE, i, j);
                }
            }
        }

        return model;
    }

}
