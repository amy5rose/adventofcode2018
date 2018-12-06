package day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.ReadInput;

public class day1 {
	
	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
//		List<String> inputList = ReadInput.readInput(input);
		List<String> inputList = read.readFile("input.txt");
		System.out.println("input size: " + inputList.size());

		day1 day = new day1();
		int sum = day.findFrequency(inputList);
		System.out.println("answer:" + sum);
		
		//part1: 484
		//part2: 367
		
	}
	
	

	public int findFrequency(List<String> list) {
		int target = 0; 
		List<Integer> map = new ArrayList<Integer>();
		
		while(true) {
			for(String str : list) {
				int value = Integer.valueOf(str);
				target +=value;
				if(map.contains(target)) {
					return target;
				}
				else {
					map.add(target);
				}
			}
		}
	}
}
