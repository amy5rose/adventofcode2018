import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import common.ReadInput;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class day8star2 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		ReadInput read = new ReadInput();
//        List<String> inputList = read.readInput("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2");
        List<String> inputList = read.readFile("8input.txt");
	
		day8star2 day = new day8star2();
		System.out.println("inputList:" + inputList.size());
        String answer = day.findFrequency(inputList);

		long endTime = System.currentTimeMillis();
		System.out.println("program running time:" + (endTime-startTime));
	}

	public String findFrequency(List<String> list) {
        String[] input = list.get(0).split(" ");
        ArrayListMultimap<Integer, Integer> children = ArrayListMultimap.create();
        HashMap<Integer, Integer> values = new HashMap();

        int lastIndex = findNextIndex(children, values, input, 0, -1);
        System.out.println("lastIndex:" + lastIndex);
        System.out.println("root answer:" + values.get(0));
        return "";
	}

    int sum = 0;
	int nextChildName = 0;
	private int findNextIndex(ArrayListMultimap<Integer, Integer> childrenMap,
                              HashMap<Integer, Integer> valuesMap, String[] input, int index, int parent) {
	    int name = nextChildName;
        nextChildName++;
	    System.out.println("index:" + index + " sum:" + sum + " name:" + name);
	    if(index < input.length) {
            int children = Integer.valueOf(input[index]);
            int metadata = Integer.valueOf(input[index + 1]);
            if (children == 0) {//children
                sum = 0;
                int nextIndex = countMetaData(input, index+2, metadata);
                valuesMap.put(name, sum);
                childrenMap.put(parent, name);
                return nextIndex;
            } else {
                int childIndex = index + 2;

                for(int i = 0; i < children; i++) {
                    childIndex = findNextIndex(childrenMap, valuesMap, input, childIndex, name);
                }

                int nextIndex =  childIndex + metadata;

                sum = 0;
                for (int i = 0; i < metadata; i++) {
                    int childReference = Integer.valueOf(input[childIndex + i]) - 1;
                    if (childReference < children ){
                       int childID = childrenMap.get(name).get(childReference);
                       sum = sum + valuesMap.get(childID);
                    }
                }

                valuesMap.put(name, sum);
                childrenMap.put(parent, name);
                return nextIndex;
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
0 1 2 3 4  5  6  7 8 9 10 11 12 13 14 15
2 3 0 3 10 11 12 1 1 0 1  99 2  1  1  2
0---------------------------------------
    1----------- 2------------
                     3------
A---------------------------------------
    B----------- C------------
                     D------
0 1  2 3 4 5 6 7 8 9 10 11 12 13 14            21
0    1   2   3   4   5
8 11 7 3 5 5 3 4 1 6 0  9  6  5  1 6 2 4 1 4 1 3  1 1 2 3 1 1 7 0 9 1 3 1
0---------------------------------------
     1---------------------------
         2------
             3------
                 4------
                     5------

     */
}
