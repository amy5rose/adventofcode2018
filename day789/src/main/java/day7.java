import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import common.ReadInput;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
		System.out.println("program running time:" + (endTime-startTime));
	}

	public String findFrequency(List<String> list) {
        //Step C must be finished before step A can begin.
        //C -> A
        Multimap<String, String> comesBefore = HashMultimap.create();

        //A -> C
        Multimap<String, String> comesAfter = HashMultimap.create();


        Set<String> allNodes = new HashSet<String>();

        for (String input : list) {
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

        int NumberofWorkers = 5;
        int delay = 60;

//        int NumberofWorkers = 2;
//        int delay = 0;

        ArrayList<Worker> workers = new ArrayList<Worker>();

        for (int i = 0; i < NumberofWorkers; i++) {
            Worker w = new Worker(0, null);
            workers.add(w);
        }

        int time = 0;

        StringBuffer path = new StringBuffer();
        Set<String> started = new HashSet<>();
        Set<String> finished = new HashSet<>();
        Set<String> nextUp = new HashSet<>();
        nextUp.addAll(first);
        while (!nextUp.isEmpty()) {
            //find next things to work on
            Set<String> dependenciesMeet = new HashSet<>();
            for (String possibleNext : nextUp) {
                if (finished.containsAll(comesAfter.get(possibleNext))) {
                    dependenciesMeet.add(possibleNext);
                }
            }

            List<String> next = dependenciesMeet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            //next.removeAll(seen);
            System.out.println("Current:" + next + "   dependenciesMeet:" + dependenciesMeet + " started:" + started + " finished:" + finished);
            System.out.println("workers:"+ workers);
            //find free workers based on current time, and start work
            for (Worker worker : workers) {
                if (worker.time <= time) {
                    if (!next.isEmpty()) {
                        String nextS = next.remove(0);

                        started.add(nextS);
                        nextUp.remove(nextS);
                        int duration = getDuration(nextS, delay);
                        worker.time = time + duration;
                        worker.object = nextS;
                        System.out.println("Started: " + workers.indexOf(worker) + "->" + worker);
                    }

                }
            }
            System.out.println("path:" + path.toString());


            //find finished workers, and complete the work
            List<Integer> finishedTime = workers.stream()
                    .map(w -> w.time).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

            boolean updated = false;
            for (Integer nextTime : finishedTime) {
                for (Worker worker : workers) {
                    if (worker.time <= nextTime && worker.object != null) {
                        path.append(worker.object);

                        nextUp.addAll(comesBefore.get(worker.object));
                        nextUp.removeAll(started);

                        finished.add(worker.object);
                        System.out.println("Finished: " + workers.indexOf(worker) + "->" + worker);
                        time = nextTime;
                        worker.object = null;
                        updated = true;
                    }
                }
                if(updated)
                    break;
            }
            System.out.println("time: " + time);
        }
        System.out.println("total time: " + time);

        //968


        return path.toString();
	}

	int getDuration(String c, int delay){
	    return delay + c.charAt(0) - 'A' + 1;
    }

    class Worker {
	    int time;
	    String object;

        public Worker(int time, String obj) {
            this.time = time;
            this.object = obj;
        }

        @Override
        public String toString() {
            return "W{" +
                    "WorkTill=" + time +
                    ", on object='" + object + '\'' +
                    '}';
        }
    }

}
