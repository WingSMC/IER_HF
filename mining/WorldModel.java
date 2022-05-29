package mining;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import mining.MiningPlanet.Move;

public class WorldModel extends GridWorldModel {

    public static final int   DEPOT = 8;
    public static final int   GOLD  = 16;
    public static final int   EXCAVATOR = 32;
    public static final int   ENEMY = 64;

    Location                  depot;
    Location                  startpoz;
    Location                  depotpoz;
    int                       goldsInDepot   = 0;
    int                       initialNbGolds = 0;
    Location				  Excavator1;
    Location				  Excavator2;
    Location				  Excavator3;
    Location				  Excavator4;

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

    public void setDepot(int x, int y) {
        depot = new Location(x, y);
        data[x][y] = DEPOT;
    }

    public Location getDepot() {
        return depot;
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

    public int getDroneX(){
        return startpoz.x;
    }

    public int getDroneY(){
        return startpoz.y;
    }

    public int getMechaX(){
        return depotpoz.x;
    }

    public int getMechaY(){
        return depotpoz.y;
    }

    public Location getExcavator(int i) {
    	logger.info("called");
    	switch (i) {
    	case 1: return Excavator1; 
    	case 2: return Excavator2;
    	case 3: return Excavator3;
    	case 4: return Excavator4;
    	}
    	return null; 	
    }

    
    public void setExcavator(int[][] excavator_loc) {
        Excavator1 = new Location(excavator_loc[0][0], excavator_loc[0][1]);
    	Excavator2 = new Location(excavator_loc[1][0], excavator_loc[1][1]);
    	Excavator3 = new Location(excavator_loc[2][0], excavator_loc[2][1]);
    	Excavator4 = new Location(excavator_loc[3][0], excavator_loc[3][1]);
    }


    public boolean isAllGoldsCollected() {
        return goldsInDepot == initialNbGolds;
    }

    public void setInitialNbGolds(int i) {
        initialNbGolds = i;
    }

    public int getInitialNbGolds() {
        return initialNbGolds;
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

    boolean pick(int ag) {
        return false;
    }

    boolean drop(int ag) {
        return false;
    }
    

    /** world with gold and obstacles */
    static WorldModel world3() throws Exception {
        int excavator_num = 4;
        int x = 35;
        int y = 35;
        WorldModel model = WorldModel.create(x, y, 2);
        model.setId("Scenario 5");

        tester gen = new tester(x, y);

        boolean[][] blueprint= gen.getMatrix();
        int[] startloc = gen.getStart();
        int[] depotloc = gen.getDepot();
        model.startpoz = new Location(startloc[0], startloc[1]);
        model.depotpoz = new Location(depotloc[0], depotloc[1]);

        int[][] excavator_loc = gen.generate_excavator(4);
        for(int i = 0; i < 4; i++){
            System.out.println("excavator: " + excavator_loc[i][0] + " " + excavator_loc[i][1]);
        }

        model.setExcavator(excavator_loc);

                
        model.setDepot(depotloc[0], depotloc[1]);
        model.setAgPos(0, startloc[0], startloc[1]);
        model.setAgPos(1, depotloc[0], depotloc[1]);

        
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(!blueprint[i][j]){
                    model.add(WorldModel.OBSTACLE, i, j);
                }
            }
        }

        model.setInitialNbGolds(model.countObjects(WorldModel.GOLD));
        return model;
    }

}
