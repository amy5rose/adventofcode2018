import common.DayBase;
import common.MatrixHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class day11 extends DayBase {

	public static void main(String[] args) throws IOException {
        day11 day = new day11();
        day.run();
	}

    @Override
    public String getSampleInputString() {
        return "18";
    }

    @Override
    public String getSampleAnswer() {
        return "90,269,16";
    }

    @Override
    public String getRealInputString() {
        return "1133";
    }

    public String findAnswer(List<String> list, boolean isSample) {
	    int size = 300;
	    int[][] grid = new int[size+1][size+1];

	    int serial = Integer.valueOf(list.get(0));
	    for( int i = 1; i < size+1; i++) {
            for (int j = 1; j < 300+1; j++) {
                int rackID = i + 10;
                int power = rackID * j;
                int increase = power + serial;
                int powerCell = increase * rackID;
                int digit = 0;
                if (powerCell >= 100) {
                    digit = (powerCell  / 100) % 10;
                }
                int answer = digit - 5;
                grid[i][j] = answer;
            }
        }
//        System.out.println("grid[3][5]:" + grid[3][5]);
//
//        System.out.println("grid[122][79]:" + grid[122][79]);
//        System.out.println("grid[217][196]:" + grid[217][196]);

        //MatrixHelper.printGrid(grid, true, 15);
        int finalMax = 0;
	    int x=0,y=0;
	    int finalFuelCellSize = 0;

        int[][] fuelAnswers = new int[size+1][size+1];

        ///use a pattern of growing the cells in the tiny loops:
        //x123
        //1123
        //2223
        //3333

	    for( int fuelCellSize = 1; fuelCellSize < 300; fuelCellSize ++) {
            for (int i = 1; i < 300 - fuelCellSize + 1; i++) {
                for (int j = 1; j < 300 - fuelCellSize + 1; j++) {
                    int max = fuelAnswers[i][j];
                    //for both tinyloops, in the calc use (fuelCellSize-1) because fuelcell starts at 1...
                    for (int tinyX = 0; tinyX < fuelCellSize; tinyX++) {
                        max += grid[i + tinyX][j+fuelCellSize-1];
                    }

                    /// use limit with (fuelCellSize - 1) to not double count the point
                    for (int tinyY = 0; tinyY < fuelCellSize - 1 ; tinyY++) {
                        max += grid[i + fuelCellSize-1][j + tinyY];
                    }

                    fuelAnswers[i][j] = max;

                    if (finalMax < max) {
                        finalMax = max;
                        x = i;
                        y = j;
                        finalFuelCellSize = fuelCellSize;
                    }

                    if( i == 1 && j ==1 ){
                        //MatrixHelper.printGrid(grid, true, 15);
                    }
                }
            }
        }

        return x + "," + y + "," + finalFuelCellSize;
	}
}
