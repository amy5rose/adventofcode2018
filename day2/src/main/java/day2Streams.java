import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import common.ReadInput;

public class day2Streams {
	
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
		
		day2Streams day = new day2Streams();
		day.findFrequency(inputList);
	}

	int doubleLetter = 0;
	int tripleLetter = 0;
	
	public int findFrequency(List<String> list) {
		for(String item: list) {
			Map<Integer, Long> map = item.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			
			if(map.containsValue(2L)) {
				doubleLetter += 1;
			} 
			if (map.containsValue(3L)) {
				tripleLetter += 1;
			}
		}
		
		System.out.println("Double: " + doubleLetter + " triple: " + tripleLetter + " checksum:" + doubleLetter*tripleLetter);
		return doubleLetter * tripleLetter;
	}
	
}
