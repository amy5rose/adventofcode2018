import common.DayBase;
import common.MatrixHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class day18 extends DayBase {

  public static final String WOODED = "|";
  public static final String LUMBER = "#";
  public static final String OPEN = ".";

  public static void main(String[] args) throws IOException {
    day18 day = new day18();
    day.run();
  }

  @Override
  public String getSampleInputString() {
    return "18input.txt";
  }

  @Override
  public String getSampleAnswer() {
    return "1147"; //37 * 31 = 1147.
  }

  @Override
  public String getRealInputString() {
    return "18input2.txt";
  }

  public String findAnswer(List<String> list, boolean isSample) {
    //create gridFirst in array
    String[][] gridFirst;
    String[][] gridNew;
    String[][] gridTemp;

    HashMap<String, String> answers = new HashMap<>();

    if (isSample) {
      gridFirst = new String[10][10];
      gridNew = new String[10][10];
    } else {
      gridFirst = new String[50][50];
      gridNew = new String[50][50];
    }

    String current;
    StringBuffer next = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      String row = list.get(i);
      for (int index = 0; index < gridFirst.length && index < row.length(); index++) {
        String piece = String.valueOf(row.charAt(index));
        gridFirst[i][index] = piece;
        next.append(piece);
      }
    }

    MatrixHelper.printGrid(gridFirst);

    current = next.toString();
    for (int minute = 1; minute <= (isSample? 10 : 1000000000 ) ; minute++) {
      if(answers.containsKey(current)) {
        current = answers.get(current);
        gridFirst = null;
      } else {
        for (int i = 0; i < list.size(); i++) {
          for (int index = 0; index < gridFirst.length; index++) {
            String piece = getNextState(gridFirst, i, index);
            gridNew[i][index] = piece;
            next.append(piece);
          }
        }
        //MatrixHelper.printGrid(gridNew, false);
        answers.put(current, next.toString());
        current = next.toString();
        gridTemp = gridNew;
        gridNew = gridFirst;
        gridFirst = gridTemp;
      }
    }

    int wooded = 0;
    int lumber = 0;
    if(gridFirst == null) {
      for( int i = 0; i < current.length(); i++) {
        char piece = current.charAt(i);
        if (WOODED.equals(piece)) {
          wooded++;
        } else if (LUMBER.equals(piece)) {
          lumber++;
        }
      }
    } else {

      MatrixHelper.printGrid(gridFirst);

      for (int i = 0; i < list.size(); i++) {
        for (int index = 0; index < gridFirst.length; index++) {
          if (WOODED.equals(gridFirst[i][index])) {
            wooded++;
          } else if (LUMBER.equals(gridFirst[i][index])) {
            lumber++;
          }
        }
      }
    }
    return (wooded * lumber) + "";
  }

  /*
    An open acre will become filled with trees if three or more adjacent acres contained trees. Otherwise, nothing happens.
    An acre filled with trees will become a lumberyard if three or more adjacent acres were lumberyards. Otherwise, nothing happens.
    An acre containing a lumberyard will remain a lumberyard if it was adjacent to at least one other lumberyard and at least one acre containing trees. Otherwise, it becomes open.
 */
  String getNextState(String[][] grid, int x, int y) {

    int wooded = 0;
    int lumber = 0;
    for (int i = (x == 0 ? 0 : -1); i <= (x == grid.length - 1 ? 0 : 1); i++) {
      for (int j = (y == 0 ? 0 : -1); j <= (y == grid.length - 1 ? 0 : 1); j++) {
        if (i == 0 && j == 0) {
          continue; //skip the middle node.
        }

        if (WOODED.equals(grid[x + i][y + j])) {
          wooded++;
        }
        if (LUMBER.equals(grid[x + i][y + j])) {
          lumber++;
        }


      }
    }


    if (OPEN.equals(grid[x][y]) && wooded >= 3) {
      return WOODED;
    }
    if (WOODED.equals(grid[x][y]) && lumber >= 3) {
      return LUMBER;
    }

    if (LUMBER.equals(grid[x][y])) {
      if (lumber >= 1 && wooded >= 1) {
        return LUMBER;
      } else {
        return OPEN;
      }
    }

    return grid[x][y];
  }
}
