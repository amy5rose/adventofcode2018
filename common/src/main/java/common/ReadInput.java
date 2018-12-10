package common;

import java.util.*;
import java.util.stream.Collectors;
import java.net.*;
import java.io.*;


public class ReadInput {

	boolean isFile(String input) {
		return input.contains(".txt");
	}


	public List<String> readInput(String input) throws IOException {
		if(isFile(input)) {
			return readFile(input);
		}
		else {
			return readInputString(input);
		}
	}

	public List<String> readInputString(String input) {
		List<String> list = new ArrayList<String>(); 
		for (String item : input.split("\r\n")) {
			list.add(String.valueOf(item));
		}
		return list;
	}
	
	public List<String> readFile(String fileString) throws IOException {
		List<String> input = new ArrayList<String>();
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileString).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				input.add(scanner.nextLine());
			}

		}
        return input;
	}


}
