import com.google.common.collect.ComparisonChain;
import common.DayBase;
import common.MatrixHelper;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class day13star2 extends DayBase {

	public static void main(String[] args) throws IOException {
        day13star2 day = new day13star2();
        day.run();
	}

    @Override
    public String getSampleInputString() {
        return "13input4.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "6,4";
    }

    @Override
    public String getRealInputString() {
        return  "13input3.txt";
    }

    public String findAnswer(List<String> list, boolean isSample) {
	    //create grid in array, identify the trains and put them in an ordered array list
        String[][] grid;
        if (isSample) {
            grid = new String[14][14];
        } else {
            grid = new String[160][160];
        }

        HashSet<Point> trainPoints = new HashSet();

        MatrixHelper.inititalizeGrid(grid, "");
        TreeSet<Train> trainsToMove = new TreeSet();
        for(int i = 0; i < list.size(); i++){
            String row = list.get(i);
            for(int index = 0 ; index<grid.length && index < row.length(); index++) {
                String piece = String.valueOf(row.charAt(index));
                grid[i][index] = piece;
                if(isTrain(piece)) {
                    Train train = new Train(Direction.getDirection(piece), i, index);
                    trainsToMove.add(train);
                    grid[i][index] = train.direction.getPath();
                    trainPoints.add(new Point(i, index));
                }
            }
        }

        System.out.println("trains:" + trainsToMove.size() + " :: " + trainsToMove);
        MatrixHelper.printGrid(grid, true);

        if (!isSample) {
            //printGrid(trainsToMove, grid);
        }

        TreeSet<Train> movedTrains = new TreeSet();
        HashSet<Point> removedPoints = new HashSet();

        while (trainsToMove.size() > 1) {
            for (Train next : trainsToMove) {
                if (removedPoints.contains(new Point(next.x, next.y))) {
                    //skip
                    continue;
                }
                trainPoints.remove(new Point(next.x, next.y));
                System.out.println("next:" + next);
                next.move(grid);
                if (trainPoints.contains(new Point(next.x, next.y))) {
                    System.out.println("collide:" + next);
                    //System.out.println("collide trainsToMove:" + trainsToMove.removeIf(train -> train.x == next.x && train.y == next.y));
                    System.out.println("collide movedTrains:" + movedTrains.removeIf(train -> train.x == next.x && train.y == next.y));
                    System.out.println("collide points:" + trainPoints.removeIf(point -> point.x == next.x && point.y == next.y));
                    removedPoints.add(new Point(next.x, next.y));

                } else {
                    trainPoints.add(new Point(next.x, next.y));
                    movedTrains.add(next);
                }
                if (!isSample) {
                    //printGrid(trainsToMove, grid);
                }
            }
            System.out.println("movedTrains:" + movedTrains.size());

            trainsToMove = movedTrains;
            movedTrains = new TreeSet();
            removedPoints.clear();
        }

        //go through each train, move it forward.
        //iff collision, return answer
        //add train to moved list.

        //swap moved to toMove
	    return trainsToMove.first().y + "," + trainsToMove.first().x;
    }

    private void printGrid(TreeSet<Train> movedTrains, String[][] grid) {
	    String[][] gridClone = grid.clone();
	    for(Train train : movedTrains) {
	        gridClone[train.x][train.y] = train.direction.getTrainPiece();
        }
        MatrixHelper.printGrid(gridClone, true);
    }

    public boolean isTrain(String piece) {
        return "<>^v".contains(piece);
    }

    public class Train implements Comparable<Train> {
        Direction direction;
	    int x;
	    int y;
	    int crossingChoice;

        public Train(Direction direction, int x, int y) {
            this.direction = direction;
            this.x = x;
            this.y = y;
            this.crossingChoice = 0; //0 = left, 1 == straight, 2 = right
        }

        public void move(String[][] grid ) {
            String piece = grid[x][y];
            if(direction == Direction.North) {
                x--;
            } else if(direction == Direction.South) {
                x++;
            } else if(direction == Direction.East) {
                y++;
            } else {
                y--;
            }
            piece = grid[x][y];

            if(isTurn(piece)) {
                if (direction == Direction.East && "/".equals(piece)) {
                    direction = Direction.North;
                } else  if (direction == Direction.East && "\\".equals(piece)) {
                    direction = Direction.South;
                } else if (direction == Direction.West && "/".equals(piece)) {
                    direction = Direction.South;
                } else if (direction == Direction.West && "\\".equals(piece)) {
                    direction = Direction.North;
                } else if (direction == Direction.North && "/".equals(piece)) {
                    direction = Direction.East;
                } else if (direction == Direction.North && "\\".equals(piece)) {
                    direction = Direction.West;
                } else if (direction == Direction.South && "/".equals(piece)) {
                    direction = Direction.West;
                } else if (direction == Direction.South && "\\".equals(piece)) {
                    direction = Direction.East;
                } else {
                    System.out.println("OH NO: " + piece +  " dir:" + direction);
                }
            } else if (isCrossing(piece)) {

                if (crossingChoice == 1) {
                    //do nothing go straight
                } else if (crossingChoice == 0) { // go left
                    if (direction == Direction.South) {
                        direction = Direction.East;
                    } else if (direction == Direction.North) {
                        direction = Direction.West;
                    } else if (direction == Direction.East) {
                        direction = Direction.North;
                    } else { // <
                        direction = Direction.South;
                    }
                } else {// go right!
                    if (direction == Direction.South) {
                        direction = Direction.West;
                    } else if (direction == Direction.North) {
                        direction = Direction.East;
                    } else if (direction == Direction.East) {
                        direction = Direction.South;
                    } else { // <
                        direction = Direction.North;
                    }
                }
                crossingChoice = (crossingChoice + 1) % 3;
            }
        }

        public boolean isTurn(String piece) {
            return "/\\".contains(piece);
        }

        public boolean isCrossing(String piece) {
            return "+".contains(piece);
        }

        @Override
        public int compareTo(Train other) {
            return ComparisonChain.start()
                    .compare( this.x, other.x )
                    .compare( this.y, other.y )
                    .result();
        }


        @Override
        public String toString() {
            return "Train{" +
                    "direction=" + direction +
                    ", x=" + x +
                    ", y=" + y +
                    ", crossingChoice=" + crossingChoice +
                    '}';
        }
    }

    public enum Direction {
	    North, South, East, West;

	    public static Direction getDirection(String str) {
	        if ("v".equals(str)) {
	            return South;
            } else if ("^".equals(str)) {
                return North;
            } if (">".equals(str)) {
                return East;
            } else { // <
                return West;
            }
        }

        public String getTrainPiece() {
            if (this == South) {
                return "v";
            } else if (this == North) {
                return "^";
            } if (this == East) {
                return ">";
            } else { // <
                return "<";
            }
        }

        public String getPath() {
            if (this == North || this == South) {
                return "|";
            } else { //east/west
                return "-";
            }
        }
    }
}
