import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import common.ReadInput;

public class day4 {
	
	public class guard {
		int year;
		int month;
		int day;
		int hour;
		int minute;
		String text;
		Integer id;
		boolean fallAsleep;
		boolean wakeUp;
		
		public Integer getDayStamp() {
			return Integer.valueOf(""+ year + month + day);
		}
		
		public String getTimeStamp() {
			return ""+ year + month + day + hour + minute;
		}

		@Override
		public String toString() {
			return "g:" + id + " [" + getTimeStamp() + ": fallAsleep=" + fallAsleep + ", wakeUp=" + wakeUp + "]";
		}
		
	}

	public static void main(String[] args) throws IOException {
		String input = 
				"[1518-11-01 00:00] Guard #10 begins shift\r\n" + 
				"[1518-11-01 00:05] falls asleep\r\n" + 
				"[1518-11-01 00:55] wakes up\r\n" + 
				"[1518-11-01 23:58] Guard #99 begins shift\r\n" + 
				"[1518-11-02 00:40] falls asleep\r\n" + 
				"[1518-11-02 00:50] wakes up\r\n" +
				"[1518-11-01 00:25] wakes up\r\n" + 
				"[1518-11-01 00:30] falls asleep\r\n" ;

		ReadInput read = new ReadInput();
//		List<String> inputList = read.readInput(input);
		List<String> inputList = read.readFile("4input.txt");
		System.out.println("size: " + inputList.size());


		day4 day = new day4();
		List<guard> guardList = day.parseInput(inputList); 
	}
	
	public List<guard> parseInput(List<String> list) {
		List<guard> guards = new ArrayList<guard>();

		Comparator<guard> valueComparator = new Comparator<guard>() {
			public int compare(guard g1, guard g2) {
				if (g1.year - g2.year != 0) {
					return g1.year - g2.year;
				}
				if (g1.month - g2.month != 0) {
					return g1.month - g2.month;
				}
				if (g1.day - g2.day != 0) {
					return g1.day - g2.day;
				}
				if (g1.hour - g2.hour != 0) {
					return g1.hour - g2.hour;
				}
				if (g1.minute - g2.minute != 0) {
					return g1.minute - g2.minute;
				}
				return 0;
			}};
	    Multimap<Integer, guard> timeMap = HashMultimap.create();

		for(String item: list) {
			guard g = new guard();
			String[] values = item.split("[ @,:x#\\[\\]-]+");
			g.year = Integer.valueOf(values[1].trim());
			g.month = Integer.valueOf(values[2].trim());
			g.day = Integer.valueOf(values[3].trim());
			g.hour = Integer.valueOf(values[4].trim());
			g.minute = Integer.valueOf(values[5].trim());
			if(values[6].contains("Guard")) {
				g.id =  Integer.valueOf(values[7].trim());
			}
			if(values[6].contains("falls")) {
				g.fallAsleep = true;			
			}
			if(values[6].contains("wakes")) {
				g.wakeUp = true;
			}
			guards.add(g);
			timeMap.put(g.getDayStamp(), g);
		}
		
		Collections.sort(guards, valueComparator);

		System.out.println("Ordered guard list: "+  guards);
		int gID = -1;
		for (guard g: guards) {
			if(g.id != null) {
				gID = g.id;
			} else {
				g.id = gID;
			}
		}
		System.out.println("List with all IDS:"+ guards);
		

		HashMap<Integer, Integer> timeAsleep = new HashMap<>();
		gID=-1;
		int start = -1;
		for (guard g: guards) {
			if (g.fallAsleep) {
				gID = g.id;
				start = g.minute;
			}
			if(g.wakeUp) {
				int sleep = g.minute - start;
				if(timeAsleep.containsKey(gID)) {
					sleep += timeAsleep.get(gID);
				}
				timeAsleep.put(gID, sleep);
			}
		}
		
		System.out.println("<guard, minutes sleep>:"+ timeAsleep);
		
		int maxGID = -1;
		int maxSleep = -1;
		for (Entry<Integer, Integer> g: timeAsleep.entrySet()) {
			if (maxSleep < g.getValue()) {
				maxGID = g.getKey();
				maxSleep = g.getValue();
			}
		}
		System.out.println("guard asleep most:" + maxGID);

		start = -1;
		int maxMinuteIndex = 0;
		ArrayList<Integer> sleepTime = new ArrayList<>(70);
		for(int i = 0; i < 60; i ++) {
			sleepTime.add(0);
		}
		
		for (guard g: guards) {
			if (g.id == maxGID) {
				if (g.fallAsleep) {
					start = g.minute;
				}	
				if(g.wakeUp) {
					for(int i = start; i < g.minute; i ++) {
						sleepTime.set(i, sleepTime.get(i)+1);
						if(sleepTime.get(maxMinuteIndex) < sleepTime.get(i)) {
							maxMinuteIndex = i;
						}
					}
				}
			}
		}

		System.out.println("sleepTime:" + sleepTime);
		System.out.println("maxMinuteIndex:" + maxMinuteIndex);

		System.out.println("solution:" + maxMinuteIndex * maxGID);	
		
		return guards;
	}
}
