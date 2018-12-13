import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import common.DayBase;
import common.ReadInput;

public class day4star2 extends DayBase {

	public static void main(String[] args) throws IOException {
		day4star2 day = new day4star2();
		day.run();
	}

	@Override
	public String getSampleInputString() {
		return "[1518-11-01 00:00] Guard #10 begins shift\r\n" +
				"[1518-11-01 00:05] falls asleep\r\n" +
				"[1518-11-01 00:55] wakes up\r\n" +
				"[1518-11-01 23:58] Guard #99 begins shift\r\n" +
				"[1518-11-02 00:40] falls asleep\r\n" +
				"[1518-11-02 00:50] wakes up\r\n" +
				"[1518-11-01 00:25] wakes up\r\n" +
				"[1518-11-01 00:30] falls asleep\r\n";
	}

	@Override
	public String getSampleAnswer() {
		return "3960";
	}

	@Override
	public String getRealInputString() {
		return "4input2.txt";
	}


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

	@Override
	public String findAnswer(List<String> list, boolean isSample) {
		List<guard> guards = new ArrayList<guard>();
		Collections.sort(list);

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

		System.out.println("Ordered guard List: " + guards);
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
		
		int maxMaxSleepMinute = -1;
		int answer = -1;
		for(int sleepingG : timeAsleep.keySet()) {
			maxMinuteIndex = 0;
			start = -1;
			sleepTime = new ArrayList<>(70);
			for(int i = 0; i < 60; i ++) {
				sleepTime.add(0);
			}
			
			for (guard g: guards) {
				if (g.id == sleepingG) {
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

			if(maxMaxSleepMinute < sleepTime.get(maxMinuteIndex) ) {
				maxMaxSleepMinute = sleepTime.get(maxMinuteIndex);
				answer = sleepingG*maxMinuteIndex;
			}
			System.out.println("GID, maxMinuteIndex, #times asleep, Answer:::" + sleepingG + " : " + maxMinuteIndex + " : " + sleepTime.get(maxMinuteIndex) + " = " + (sleepingG*maxMinuteIndex));
		}
	

		System.out.println("sleepTime:" + sleepTime);
		System.out.println("maxMinuteIndex, #times asleep:" + maxMinuteIndex + " : " + sleepTime.get(maxMinuteIndex));

		System.out.println("solution part 1:" + maxMinuteIndex * maxGID);	
		System.out.println("solution part 2:" + answer);	
		return answer+"";
	}
}
