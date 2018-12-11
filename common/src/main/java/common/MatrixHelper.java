package common;

public class MatrixHelper {

    public static void inititalizeGrid(String[][] grid, String init) {
        for (int i = 0; i < grid.length  ; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                grid[i][j] = init;
            }
        }
    }

    public static void printGrid(String[][] grid) {
        printGrid(grid, false);

    }

    public static void printGrid(String[][] grid, boolean force) {
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < grid.length  ; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                if((force && i < 50 && j <50) || grid.length < 15) {
                    System.out.print(grid[i][j]);
                }
            }

            if((force && i < 50) || grid.length < 15) {
                System.out.println("");
            }
        }
    }

    public static void printGrid(int[][] grid, boolean force, int max) {
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < grid.length  ; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                if((force && i < max && j <max) || grid.length < max) {
                    System.out.print(grid[i][j] + " ");
                }
            }

            if((force && i < max) || grid.length < max) {
                System.out.println("");
            }
        }
    }
}
