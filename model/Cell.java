package model;



import java.util.List;
import java.util.LinkedList;

public class Cell {
	CellType type;
	int x;
	int y;
	Cell[][] map;
	List<Drone> drones = new LinkedList<>();

	public Cell(CellType type, int x, int y, Cell[][] map) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.map = map;
	}

	public CellType getType() { return type; }

	public void setType(CellType type) { this.type = type; }

	public boolean isWall() { return type == CellType.WALL; }

	public boolean isEmpty() { return type == CellType.EMPTY; }

	public boolean isUnknown() { return type == CellType.UNKNOWN; }

	public boolean hasEmptyNeighbor() {
		if (map[x - 1][y].isEmpty() || map[x][y - 1].isEmpty() || map[x][y + 1].isEmpty() || map[x + 1][y].isEmpty())
			return true;
		return false;
	}

	public int getX() { return x; }

	public int getY() { return y; }

	public Cell[][] getMap() { return map; }

	public boolean isReachable() { return type != CellType.WALL && hasEmptyNeighbor(); }

	public void enter(Drone drone) { drones.add(drone); }

	public void leave(Drone drone) { drones.remove(drone); }
}
