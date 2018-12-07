import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import common.ReadInput;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class day7 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		ReadInput read = new ReadInput();
//        List<String> inputList = read.readFile("7input.txt");
        List<String> inputList = read.readFile("7input2.txt");
	
		day7 day = new day7();

		System.out.println("inputList:" + inputList.size());
        String answer = day.findFrequency(inputList);
		System.out.println("answer:" + answer);
		
		long endTime = System.currentTimeMillis();
		System.out.println("time:" + (endTime-startTime));
	}

	public String findFrequency(List<String> list) {
	    //Step C must be finished before step A can begin.
        //C -> A
        Multimap<String, String> comesBefore = HashMultimap.create();

        //A -> C
        Multimap<String, String> comesAfter = HashMultimap.create();


        Set<String> allNodes = new HashSet<String>();

        for(String input: list) {
            String[] split = input.split(" ");
            comesBefore.put(split[1], split[7]);
            comesAfter.put(split[7], split[1]);
            allNodes.add(split[1]);
            allNodes.add(split[7]);
        }

        HashSet<String> first = new HashSet<String>();
        first.addAll(allNodes);
        first.removeAll(comesAfter.keySet());
        System.out.println("comesBefore:" + comesBefore + "\ncomesAfter:" + comesAfter);
        System.out.println("first:" + first + "  allnodes:" + allNodes);


        StringBuffer path = new StringBuffer();

        Set<String> seen = new HashSet<>();
        Set<String> nextUp = new HashSet<>();
        nextUp.addAll(first);
        while(!nextUp.isEmpty())  {

            Set<String> dependenciesMeet = new HashSet<>();
            for(String possibleNext: nextUp) {
                if(seen.containsAll(comesAfter.get(possibleNext))) {

                    dependenciesMeet.add(possibleNext);
                }
            }

            Optional<String> next = dependenciesMeet.stream().min(Comparator.naturalOrder());

            System.out.println("Current:" + next + "   dependenciesMeet:" + dependenciesMeet);
            if(next.isPresent()) {
                String nextS = next.get();
                path.append(nextS);
                nextUp.addAll(comesBefore.get(nextS));
                seen.add(nextS);
                nextUp.remove(nextS);
            }
            System.out.println(path.toString());
        }


		return path.toString();
	}

}
