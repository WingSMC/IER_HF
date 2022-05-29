package model;



import model.generator.MatrixGenerator;

public class MapGenerator {
	public static Cell[][] generate(int x, int y) {
		var blueprint = new MatrixGenerator(x, y).getMatrix();
		Cell[][] map = new Cell[x][y];

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (blueprint[i][j])
					map[i][j].type = CellType.EMPTY;
				else
					map[i][j].type = CellType.EMPTY;
			}
		}
		return map;
	}

	public static CellType[][] generateUnknown() {
		CellType[][] map = new CellType[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = CellType.UNKNOWN;
			}
		}
		return map;
	}
}
