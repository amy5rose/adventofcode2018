import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import common.ReadInput;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class day9 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		ReadInput read = new ReadInput();
//        List<String> inputList = read.readInput("9 players; last marble is worth 25 points");
        List<String> inputList = read.readInput("430 players; last marble is worth 71588 points");
	
		day9 day = new day9();
		System.out.println("inputList:" + inputList.size());
        String answer = day.findFrequency(inputList);
		System.out.println("answer:" + answer);
		
		long endTime = System.currentTimeMillis();
		System.out.println("program running time:" + (endTime-startTime));
	}

	public String findFrequency(List<String> list) {
        String[] listS = list.get(0).split(" ");
        int players = Integer.valueOf(listS[0]);
        int marbles = Integer.valueOf(listS[6]) * 100;
//        System.out.println("players:" + players + " marbles:" + marbles);

        HashMap<Integer, Integer> scores = new HashMap<>();
        LinkedList<Integer> game = new LinkedList<Integer>();
        int currentIndex = 0;
        int currentPlayer = 0;

        //first turn
        game.add(0); // begin
        game.add(2); //0
        game.add(1); //1
        currentPlayer = 2;
        currentIndex = 1;
        System.out.println("game:" + game);

        for(int currentMarble = 3; currentMarble <= marbles; currentMarble++, currentPlayer = (currentPlayer+1)% players ) {
            if(currentMarble % 23 == 0) {
                int score = currentMarble;
                currentIndex = (currentIndex - 7 + game.size()) % game.size();
                score = score + game.remove(currentIndex);
                scores.put(currentPlayer, scores.getOrDefault(currentPlayer, 0) + score);
//                System.out.println("scores:" + scores);
            } else {
                currentIndex = (currentIndex + 2) % (game.size());
               // if(currentIndex==game.size()) {
                 //   game.add(currentMarble);
                //} else {
                    game.add(currentIndex, currentMarble);
                //}
            }
            //System.out.println("game:" + game);
        }
        System.out.println("scores:" + scores);

        int max = scores.values().stream().max(Comparator.naturalOrder()).get();
        return max + "";
    }

//[-] (0)
//[1]  0 (1)
//[1]  0 0 0 0 0 0
//[2]  0 2 1 0 2 1
}
