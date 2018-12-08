import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import common.ReadInput;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day8 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		ReadInput read = new ReadInput();
        List<String> inputList = read.readInput("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2");
//        List<String> inputList = read.readFile("8input.txt");
	
		day8 day = new day8();
		System.out.println("inputList:" + inputList.size());
        String answer = day.findFrequency(inputList);
		System.out.println("answer:" + answer);
		
		long endTime = System.currentTimeMillis();
		System.out.println("program running time:" + (endTime-startTime));
	}

	public String findFrequency(List<String> list) {
        String[] input = list.get(0).split(" ");
        Multimap<String, String> children = HashMultimap.create();

        int nodes = 0;
        for (int index = 0; index < input.length; index++) {
            int childrenNum = Integer.valueOf(input[index]);
            //int metadataNum = Integer.valueOf(input[index+1]);
        }
        int lastIndex = findNextIndex(input, 0, 0);
        System.out.println("lastIndex:" + lastIndex);

        System.out.println("sum:" + sum);
        return "";
	}

    int sum = 0;
	private int findNextIndex(String[] input, int index, int name) {
	    System.out.println("index:" + index + " sum:" + sum + " name:" + name);
	    if(index < input.length) {
            int children = Integer.valueOf(input[index]);
            int metadata = Integer.valueOf(input[index + 1]);
            if (children == 0) {//children
                return countMetaData(input, index+2, metadata);
            } else {
                int childIndex = index + 2;

                for(int i = 0; i < children; i++) {
                    childIndex = findNextIndex(input, childIndex, name+1);
                }

                return countMetaData(input, childIndex, metadata);
            }
        }
	    return -1;
    }

    int countMetaData(String[] input, int index, int count) {
        for (int i = 0; i < count; i++) {
            sum += Integer.valueOf(input[index + i]);
        }

        return index + count;
    }

    /*
0 1 2 3 4  5  6  7 8 9 0 10 11 12 13 14
2 3 0 3 10 11 12 1 1 0 1 99 2  1  1  2
A---------------------------------------
    B----------- C------------
                     D------
     */
}
