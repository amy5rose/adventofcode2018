import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.ReadInput;

public class day3 {

	class suit {
		String id;
		int left;
		int top;
		int width;
		int height;
		public suit(String input) {			
			String[] values = input.split("[ @,:x#]+");
			id = values[1].trim();
			left = Integer.valueOf(values[2].trim());
			top = Integer.valueOf(values[3].trim());
			width = Integer.valueOf(values[4].trim());
			height = Integer.valueOf(values[5].trim());
					
		}
	}
	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("input.txt");
	
		day3 day = new day3();
		List<suit> suitList = day.processInput(inputList);
		System.out.println(inputList);

		int sum = day.findFrequency(suitList);
		System.out.println("overlapping squares:" + sum);
	}
	
	private List<suit> processInput(List<String> input) {
		List<suit> list = new ArrayList<suit>(); 
		for (String item : input) {
			list.add(new suit(String.valueOf(item)));
		}
		return list;
	}

	public int findFrequency(List<suit> list) {
		String[][] grid = new String[1000][1000];
//		String[][] grid = new String[8][8];
		printGrid(grid);
		
		int count = 0;
		boolean overlap = false;
		Set<String> overlapSet = new HashSet<String>();
		for(suit item: list) {
			overlap = false;
			for (int i = item.top; i < item.height+item.top; i ++) {
				for (int j = item.left; j < item.left+item.width; j ++) {
					if (grid[i][j] == ".") {
						grid[i][j] = item.id;
					} 
					else {
						if (grid[i][j].contains("*")){
							//we are overlapping something ... 
							overlap = true;
						}
						else {
							if (!( (grid[i][j] == ".") && (grid[i][j].contains("*")))) {
		
								overlapSet.remove(grid[i][j]);
								grid[i][j] = grid[i][j]+ "*";
								count++;
								overlap = true;
							}
						}
					}
					
				}
			}
			if (!overlap) {
				overlapSet.add(item.id);
			}
			printGrid(grid);

			System.out.println(overlapSet);
		}
		

		System.out.println("unique item id:" + overlapSet);
		return count;
	}
	
	void printGrid(String[][] grid) {
		for (int i = 0; i < grid.length  ; i ++) {
			for (int j = 0; j < grid.length; j ++) {
				if(grid[i][j] == null) {
					grid[i][j] = "."; //side-effect, initialize to '*'
				}
				if(grid.length < 10) {
					System.out.print(grid[i][j]);
				}
			}

			if(grid.length < 10) {
				System.out.println("");
			}
		}
	}

}
