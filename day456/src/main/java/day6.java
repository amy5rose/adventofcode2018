import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import common.MatrixHelper;
import common.ReadInput;

public class day6 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		String input = "1, 1\r\n" + 
				"1, 6\r\n" + 
				"8, 3\r\n" + 
				"3, 4\r\n" + 
				"5, 5\r\n" + 
				"8, 9";
		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("6input.txt");
	
		day6 day = new day6();


		System.out.println("inputList:" + inputList.size());
		int sum = day.findFrequency(inputList);
		System.out.println("answer:" + sum);
		
		long endTime = System.currentTimeMillis();
		System.out.println("time:" + (endTime-startTime));
	}

	public int findFrequency(List<String> list) {
		String[][] grid;
		if(list.size() < 10 ) {
			grid = new String[10][10];
		} else {
			grid = new String[400][400];
		}
		
		HashMap<String, Point> map = new HashMap<>();
		MatrixHelper.inititalizeGrid(grid, ".");
		char c = 'A';
		
		for(String pointS : list) {
			String[] p = pointS.split(",");
			
			String loc = String.valueOf(c);
			Point point = new Point(Integer.valueOf(p[1].trim()), Integer.valueOf(p[0].trim()));
			map.put(loc, point);
			
			grid[point.x][point.y] = loc;
			c = (char) (c + 1);
		}
		MatrixHelper.printGrid(grid, false);
		
		HashMap<String, Integer> size = new HashMap<>();
		Set<String> edgePieces = new HashSet<> ();
		int part2Area = 0;
		for (int i = 0; i < grid.length  ; i ++) {
			for (int j = 0; j < grid.length; j ++) {

				int min = Integer.MAX_VALUE;
				String minPoint = null;
				int part2Distance = 0;
				for(Entry<String, Point> point : map.entrySet()) {
					int dist = distance(point.getValue(), i, j);
					if (dist == 0) {
						minPoint = "@";
					}

					if (dist == min) {
						min = dist;
						minPoint = null;
					}
					if (dist < min) {
						min = dist;
						minPoint = point.getKey();
					}
					part2Distance += dist;
				}

				if(grid.length == 10  && part2Distance < 32 ) {
					part2Area++;
				}
				if(grid.length == 400  && part2Distance < 10000 ) {
					part2Area++;
				}

				grid[i][j] = minPoint == null? "." : minPoint.toLowerCase();
				MatrixHelper.printGrid(grid, false);

				size.put(minPoint, size.getOrDefault(minPoint, 0)+1);
//				System.out.println( "<" + i + "," + j + ">" + " : " + min + " ::: " +  minPoint + " : " + size.get(minPoint));

				if (isEdge(i,j, grid.length)) {
					edgePieces.add(minPoint);
				}
			}
		}
//		System.out.println("edgePieces:" + edgePieces);
//		System.out.println(size.size() + " :: size:" + size);
		Optional<Integer> smallestsize = size.entrySet().stream()
				.filter(e -> e == null || !edgePieces.contains(e.getKey()))
				.map(Entry::getValue)
				.max(Comparator.naturalOrder());
		
		System.out.println("Part 2 Area:" + part2Area);
		return smallestsize.get();
	}
	
	int distance(Point p, int x, int y) {
		return Math.abs(p.x - x) + Math.abs(p.y-y); 
	}
	
	
	boolean isEdge(int x, int y, int edge) {
		if (x == 0 || x == edge-1 ) {
			return true;
		}
		return y == 0 || y == edge - 1;
	}
}
