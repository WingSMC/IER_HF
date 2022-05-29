package model;



import jason.environment.grid.Location;

public class Cell {
	CellType type;
	Location loc;
	Cell[][] map;
	Actor agent;

	public Cell(CellType type, int x, int y, Cell[][] map) {
		this.type = type;
		this.loc = new Location(x, y);
		this.map = map;
	}

	public boolean isEmpty() { return type == CellType.EMPTY; }

	public boolean hasEmptyNeighbor() {
		int x = loc.x;
		int y = loc.y;
		return map[x - 1][y].isEmpty() || map[x][y - 1].isEmpty() || map[x][y + 1].isEmpty() || map[x + 1][y].isEmpty();
	}

	public boolean isReachable() { return (type != CellType.WALL) && hasEmptyNeighbor(); }

	public boolean enter(Actor ag) {
		if (agent != null)
			return false;
		agent = ag;
		return true;
	}

	public void leave(Actor drone) { agent = null; }

	public Cell getNeighbour(Direction dir) {
		int x = loc.x;
		int y = loc.y;
		return switch (dir) {
		case NORTH -> map[x][y - 1];
		case SOUTH -> map[x][y + 1];
		case EAST -> map[x + 1][y];
		case WEST -> map[x - 1][y];
		};
	}
}



enum CellType {
	EMPTY, WALL, UNKNOWN;
}
