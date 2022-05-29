package drones;



import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    BlockType[][] matrix;
    int[] start;
    int[] depot;

    public Generator(int x, int y) {
        if (x < 3)
            x = 3;
        if (y < 3)
            y = 3;
        matrix = new BlockType[x][y];
        init();
        // at least 20% cave
        while (getcaveblocknum() < (x * y / 5)) {
            init();
            create_tunnels();
        }
    }

    public void init() {
        int x = matrix.length;
        int y = matrix[0].length;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                matrix[i][j] = new BlockType();
            }
        }

        // random start point, shouldn't be a corner
        int randomNum = ThreadLocalRandom.current().nextInt(0, (x + y) * 2);
        while (randomNum == 0 || randomNum == y - 1 || randomNum == y || randomNum == x + y - 1 || randomNum == x + y || randomNum == x + 2 * y - 1 || randomNum == x + 2 * y
                || randomNum == 2 * x + 2 * y - 1) {
            randomNum = ThreadLocalRandom.current().nextInt(0, (x + y) * 2);
        }

        // set up entrance
        if (randomNum < y) {
            matrix[x - 1][randomNum].setIscave(true);
            matrix[x - 2][randomNum].setIscave(true);
            depot = new int[] { x - 1, randomNum };
            start = new int[] { x - 2, randomNum };
        } else if (randomNum < x + y) {
            randomNum = randomNum - y;
            matrix[randomNum][y - 1].setIscave(true);
            matrix[randomNum][y - 2].setIscave(true);
            depot = new int[] { randomNum, y - 1 };
            start = new int[] { randomNum, y - 2 };
            randomNum = randomNum + y;
        } else if (randomNum < x + 2 * y) {
            randomNum = randomNum - x - y;
            matrix[0][randomNum].setIscave(true);
            matrix[1][randomNum].setIscave(true);
            depot = new int[] { 0, randomNum };
            start = new int[] { 1, randomNum };
            randomNum = randomNum + x + y;
        } else {
            randomNum = randomNum - x - 2 * y;
            matrix[randomNum][0].setIscave(true);
            matrix[randomNum][1].setIscave(true);
            depot = new int[] { randomNum, 0 };
            start = new int[] { randomNum, 1 };
            randomNum = randomNum + x + 2 * y;
        }

        // set up outer walls
        for (int i = 0; i < y; i++) {
            matrix[x - 1][i].setIswall(true);
        }

        for (int i = 0; i < x; i++) {
            matrix[i][y - 1].setIswall(true);
        }

        for (int i = 0; i < y; i++) {
            matrix[0][i].setIswall(true);
        }

        for (int i = 0; i < x; i++) {
            matrix[i][0].setIswall(true);
        }
    }

    public void create_tunnels() {
        int p_tunnel = 50;

        int x = matrix.length;
        int y = matrix[0].length;

        while (checkloop()) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (matrix[i][j].isIscave() && !matrix[i][j].isChecked() && !matrix[i][j].iswall) {
                        if (!matrix[i - 1][j].isIscave() && !matrix[i - 1][j].isIswall())
                            matrix[i - 1][j].setIscave(myrand(p_tunnel));
                        if (!matrix[i + 1][j].isIscave() && !matrix[i + 1][j].isIswall())
                            matrix[i + 1][j].setIscave(myrand(p_tunnel));
                        if (!matrix[i][j - 1].isIscave() && !matrix[i][j - 1].isIswall())
                            matrix[i][j - 1].setIscave(myrand(p_tunnel));
                        if (!matrix[i][j + 1].isIscave() && !matrix[i][j + 1].isIswall())
                            matrix[i][j + 1].setIscave(myrand(p_tunnel));
                        matrix[i][j].setChecked(true);
                    }
                }
            }
        }
    }

    public int[][] generate_excavator(int num) {
        int[][] tmp = new int[num][2];
        int x = matrix.length;
        int y = matrix[0].length;
        for (int i = 0; i < num; i++) {
            int randomX = ThreadLocalRandom.current().nextInt(0, x);
            int randomY = ThreadLocalRandom.current().nextInt(0, y);
            if (matrix[randomX][randomY].isIscave() && (oneedge(randomX, randomY) || quarter(randomX, randomY))) {
                tmp[i][0] = randomX;
                tmp[i][1] = randomY;
            } else
                i--;
        }
        return tmp;
    }

    public boolean oneedge(int x, int y){
        int tmp = 0;
        if (matrix[x + 1][y].isIscave())
            tmp++;
        if (matrix[x - 1][y].isIscave())
            tmp++;
        if (matrix[x][y + 1].isIscave())
            tmp++;
        if (matrix[x][y - 1].isIscave())
            tmp++;
        if (tmp == 3) return true;
        return false;
    }

    public boolean quarter(int x, int y){
        if (matrix[x + 1][y].isIscave() && matrix[x + 1][y + 1].isIscave() && matrix[x][y + 1].isIscave())
            return true;
        if (matrix[x + 1][y].isIscave() && matrix[x + 1][y - 1].isIscave() && matrix[x][y - 1].isIscave())
            return true;
        if (matrix[x - 1][y].isIscave() && matrix[x - 1][y + 1].isIscave() && matrix[x][y + 1].isIscave())
            return true;
        if (matrix[x - 1][y].isIscave() && matrix[x - 1][y - 1].isIscave() && matrix[x][y - 1].isIscave())
            return true;

        return false;
    }


    public boolean myrand(int p) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 101);
        return randomNum < p;
    }

    public boolean checkloop() {
        int x = matrix.length;
        int y = matrix[0].length;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (matrix[i][j].isIscave() && !matrix[i][j].isChecked() && !matrix[i][j].iswall)
                    return true;
            }
        }
        return false;
    }

    public int getcaveblocknum() {
        int x = matrix.length;
        int y = matrix[0].length;
        int num = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (matrix[i][j].isIscave())
                    num++;
            }
        }
        return num;
    }

    public int[] getStart() { return start; }

    public int[] getDepot() { return depot; }

    public boolean[][] getMatrix() {
        int x = matrix.length;
        int y = matrix[0].length;
        boolean[][] retmat = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                retmat[i][j] = matrix[i][j].isIscave();
            }
        }
        return retmat;
    }
}
