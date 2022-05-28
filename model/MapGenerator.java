package model;

public class MapGenerator {
	static Cell[][] generate(int x, int y) {
		var blueprint = new MatrixGenerator(x, y).getMatrix();
		Cell[][] map = new Cell[x][y];

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (blueprint[i][j])
					map[i][j].setType(CellType.EMPTY);
				else
					map[i][j].setType(CellType.WALL);
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
