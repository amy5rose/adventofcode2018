import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import common.ReadInput;

public class day5star2 {

	class FilteredList {
		public FilteredList(String input, String filter) {
			super();
			this.input = input;
			this.filter = filter;
		}

		String input;
		String filter;
		int index = 0; 
		
		public String getNext() {
			while(index < input.length()) {
				String current =  String.valueOf(input.charAt(index));
				if(!filter.equalsIgnoreCase(current) ) {
					index++;
					return current;
				} else {
					index++;
				}
			}
			return null;
		}
	}
	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("5input.txt");

		day5star2 day = new day5star2();

		int sum = day.findFrequency(inputList);
		System.out.println("answer:" + sum);
	}

	public int findFrequency(List<String> list) {
		ArrayDeque<String> stack = new ArrayDeque<String>();
		ArrayList<String> toTry = new ArrayList<String>();
		HashMap<String, Integer> answers = new HashMap<String, Integer>();
		String input = list.get(0);
	
		String minFilterTarget = String.valueOf(input.charAt(0)).toLowerCase();; 
		
		for(int i = 0; i < input.length(); i++) {	
			String current = String.valueOf(input.charAt(i)).toLowerCase();
			if (!toTry.contains(current)) {
			 toTry.add(current);	
			}
		}
		System.out.println("toTry: " + toTry );
		
		
		while(!toTry.isEmpty()) {
			String filterTarget =  toTry.remove(0);
			FilteredList inputList = new FilteredList(input, filterTarget);
			stack.clear();
			String current = "start";
			

			System.out.println("filterTarget: " + filterTarget  + " :: " +  input);
			while(current != null) {
				current = inputList.getNext();

				while(current != null && isUnitOpposite(stack.peek(), current)) {
					String remove = stack.pop();
					current = inputList.getNext();
//					System.out.println(remove + " <- " + stack);
				}
				
				if (current != null) {
					stack.push(current);
//					System.out.println(stack);
				}
			}
			
			System.out.println("filterTarget: " + filterTarget  + " :: " +  stack.size() + " :: " + stack);
			
			answers.put(filterTarget, stack.size());
			if(stack.size() < answers.get(minFilterTarget)) {
				minFilterTarget = filterTarget;
			}
		}
		System.out.println("answers:" + answers);
		System.out.println("minFilterTarget:" + minFilterTarget + "->" + answers.get(minFilterTarget));
		return answers.get(minFilterTarget);
	}
	
	
	
	
	boolean isUnitOpposite(String first, String second) {
		if (first == null) {
			return false;
		}
		if(first.equalsIgnoreCase(second)) {
			return !first.equals(second);
		} else {
			return false; 
		}
			
	}

	boolean isUnitOpposite(Character first, Character second) {
		return isUnitOpposite(String.valueOf(first), String.valueOf(second));	
	}
}
