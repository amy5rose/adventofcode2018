import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import common.ReadInput;

public class day5 {

	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("5input.txt");

		day5 day = new day5();

		int sum = day.findFrequency(inputList);
		System.out.println("answer:" + sum);
	}

	public int findFrequency(List<String> list) {
		ArrayDeque<Character> stack = new ArrayDeque<Character>();
		String input = list.get(0);
		stack.push(input.charAt(0));

		System.out.println(input.length() + ": " + stack );
		for(int i = 1; i < input.length(); i++) {
			
			while(i < input.length() && isUnitOpposite(stack.peek(), input.charAt(i))) {
				Character remove = stack.pop();
				i++;
				//System.out.println(remove + " <- " + stack);
			}
			if (i < input.length()) {
				stack.push(input.charAt(i));
				//System.out.println(stack);
			}
		}
		return stack.size();
	}

	boolean isUnitOpposite(Character first, Character second) {
		if(String.valueOf(first).equalsIgnoreCase(String.valueOf(second))) {
			return !String.valueOf(first).equals(String.valueOf(second));
		} else {
			return false; 
		}
			
		
	}
}
