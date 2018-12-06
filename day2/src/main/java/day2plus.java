import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import common.ReadInput;

public class day2plus {
	
	public static void main(String[] args) throws IOException {
		String input = "abcdefg\r\n" + 
				"bababc\r\n" +
				"abbcde\r\n" + 
				"abcccd\r\n"+
				"aabcdd\r\n" + 
				"abcdee\r\n" +
				"ababab";

		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("input.txt");

		
		System.out.println(inputList);
		day2plus day = new day2plus();
		day.findFrequency(inputList);
	}
	
	public int findFrequency(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String first = list.get(i);
			for (int j = i+1; j < list.size();  j++) {
				String second = list.get(j);
				processWord(first, second);
				
			}
		}
		return 0;
	}
	
	boolean processWord(String first, String second) {
		char[] charsFirst = first.toCharArray();
		char[] charsSecond = second.toCharArray();

		boolean diff = false;
		int index = -1;
		for (int i = 0; i < first.length(); i ++) {
			if (charsFirst[i] == charsSecond[i]) {
				continue;
			} else {
				if (!diff) {
					diff = true;
					index = i;
				} 
				else {
					return false; 
				}
			}
		}
		
		System.out.println("f: "+  first);
		System.out.println("s: "+  second);
		System.out.println("index: "+  index);

		return true;
	}

}
