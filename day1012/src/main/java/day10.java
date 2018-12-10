import common.DayBase;
import common.MatrixHelper;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class day10 extends DayBase {

	public static void main(String[] args) throws IOException {
        day10 day = new day10();
        day.run();
	}

    @Override
    public String getSampleInputString() {
        return "10input.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "3";
    }

    @Override
    public String getRealInputString() {
        return "10input2.txt";
    }

    public String findAnswer(List<String> list) {
	    ArrayList<Star> stars = new ArrayList<>();

	    int max = 0;
	    for(String input : list ) {
            String[] values = input.split("[ @,:x#<>]+");
            Star s = new Star(values[2], values[1], values[5],values[4]);
	        stars.add(s);
        }

        int i = 0;
        int testX = Integer.MAX_VALUE;
        for(i = 0; i < 100000000; i ++) {
            for (Star star : stars) {
               star.increment();
            }
            int currentX = stars.stream().map(s -> s.point.x).max(Comparator.naturalOrder()).get();
            int cMinX = stars.stream().map(s -> s.point.x).min(Comparator.naturalOrder()).get();
            currentX = currentX - cMinX;

            if (currentX < testX) {
                testX = currentX;
            } else {
                printSolution(stars);
                break;
            }

        }
	    return i + "";
    }

    private void printSolution(ArrayList<Star> stars) {
        stars.stream().forEach(s -> {s.reverse();} );

        int minX = stars.stream().map(s -> s.point.x).min(Comparator.naturalOrder()).get();
        int minY = stars.stream().map(s -> s.point.y).min(Comparator.naturalOrder()).get();
        int maxX = stars.stream().map(s -> s.point.x).max(Comparator.naturalOrder()).get();
        int maxY = stars.stream().map(s -> s.point.y).max(Comparator.naturalOrder()).get();

        String[][] grid = new String[maxX-minX+1][maxY-minY+1];
        MatrixHelper.inititalizeGrid(grid, ".");
        stars.stream().forEach(s -> {s.point.x -= minX; s.point.y -= minY; s.updateGrid(grid);} );

        MatrixHelper.printGrid(grid, true);
    }

    class Star {
	    Point pointOriginal;
        Point point;
	    Point speed;

        public Star(String x, String y, String xVel, String yVel) {
            this(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(xVel), Integer.valueOf(yVel));
        }

        public Star(int x, int y, int xVel, int yVel) {
            this.pointOriginal = new Point(x,y);
            this.point = new Point(x,y);
            this.speed = new Point(xVel,yVel);
        }

        void increment() {
            point.x += speed.x;
            point.y += speed.y;
        }

        void reverse() {
            point.x -= speed.x;
            point.y -= speed.y;
        }

        public boolean updateGrid(String[][] grid) {
            if(point.x >= 0  && point.y >= 0 ) {
                if(point.x < grid.length && point.y < grid[0].length) {
                    grid[point.x][point.y] = "X";
                    return true;
                }
                System.out.println("error: " + point);
            }
            System.out.println("error: " + point);

            return false;
        }

        @Override
        public String toString() {
            return "Star{" +
                    "org=" + pointOriginal +
                    "current=" + point +
                    ", speed=" + speed +
                    '}';
        }
    }
}
