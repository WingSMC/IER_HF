package model;

public class Generator {
	static Cell[][] generate(int x, int y) {
		Cell[][] map = new Cell[x][y];
		int xBound = x - 1;
		int yBound = y - 1;
		// Wall around the map
		for (int i = 0; i < x; i++) {
			map[i][0].setType(CellType.WALL);
			map[i][yBound].setType(CellType.WALL);
		}
		for (int j = 1; j < yBound; j++) {
			map[0][j].setType(CellType.WALL);
			map[xBound][j].setType(CellType.WALL);
		}
		// Fill in the inside
		for (int i = 1; i < xBound; i++) {
			for (int j = 1; j < yBound; j++) {
				map[i][j] = new Cell(CellType.EMPTY, i, j, map);
			}
		}
		return map;
	}

	static CellType[][] generateUnknown() {
		CellType[][] map = new CellType[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = CellType.UNKNOWN;
			}
		}
		return map;
	}
}
