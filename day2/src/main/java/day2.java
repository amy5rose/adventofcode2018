import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import common.ReadInput;

public class day2 {
	
	public static void main(String[] args) throws IOException {
		String input = "abcdefg\r\n" + 
				"bababc\r\n" +
				"abbcde\r\n" + 
				"abcccd\r\n"+
				"aabcdd\r\n" + 
				"abcdee\r\n" +
				"ababab";

		ReadInput read = new ReadInput();
//		List<String> inputList = ReadInput.readInput(input);
		List<String> inputList = read.readFile("input.txt");

		System.out.println(inputList);
		day2 day = new day2();
		day.findFrequency(inputList);	
	}

	public int findFrequency(List<String> list) {
		int doubleLetter = 0;
		int tripleLetter = 0;
		
		for(String item: list) {
			int[] map = new int[26];
			char[] chars = item.toCharArray();
			int value = processWord(chars);
			if(value == 1 || value == 3) {
				doubleLetter += 1;
			} 
			if (value == 2 || value ==3 ) {
				tripleLetter +=1 ;
			}
		}
		
		System.out.println("Double: " + doubleLetter + " triple: " + tripleLetter + " checksum:" + doubleLetter*tripleLetter);
		return doubleLetter * tripleLetter;
	}
	
	int processWord(char[] chars) {
		int[] map = new int[26];
		for (char c: chars) {
			map[c - 'a'] =  map[c - 'a'] + 1;
		}
		
		
		int doubleLetter = 0;
		int tripleLetter = 0;
		
		for (int values: map) {
			if(values == 2) {
				doubleLetter = 1;
			} 
			if (values == 3) {
				tripleLetter = 2;
			}
		}
		return doubleLetter+tripleLetter;
	}
	
}
