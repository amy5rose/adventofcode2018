import common.ReadInput;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class day9star2 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		ReadInput read = new ReadInput();
//        List<String> inputList = read.readInput("9 players; last marble is worth 25 points");
        List<String> inputList = read.readInput("430 players; last marble is worth 71588 points");
	
		day9star2 day = new day9star2();
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

        HashMap<Integer, Long> scores = new HashMap<>();
        int currentPlayer = 0;

        //begin
        Node game = new Node(0);
        Node head = game;

        game.next = game;
        game.previous = game;

        currentPlayer = 0;

        for(int currentMarble = 1; currentMarble <= marbles; currentMarble++, currentPlayer = (currentPlayer+1)% players ) {
            if(currentMarble % 23 == 0) {
                int score = currentMarble;
                game = game.previous.previous.previous.previous.previous.previous.previous;
                score = score + game.value;
                game = game.remove();
                scores.put(currentPlayer, scores.getOrDefault(currentPlayer, 0l) + score);
//                System.out.println("scores:" + scores);
            } else {
                game = game.addNextNext(currentMarble);
            }
            //head.print();
        }
        System.out.println("scores:" + scores);

        long max = scores.values().stream().max(Comparator.naturalOrder()).get();
        return max + "";
    }

    class Node {
	    Node next;
	    Node previous;
	    int value;

	    public Node (int value) {
	        this.value = value;
        }

        public Node addNextNext(int value) {
	        Node newNode = new Node(value);
            Node currentNext = next;

            newNode.next = currentNext.next;
            newNode.previous = currentNext;

            newNode.next.previous = newNode;
            currentNext.next = newNode;
            return newNode;
        }

        public Node remove() {
	        previous.next = next;
	        next.previous = previous;
	        return next;
        }

        @Override
        public String toString() {
            return value + "";
        }

        void print(){
	        Node start = this;
	        Node next = this.next;
            System.out.print(value + " ");
            while (next != start) {
	            System.out.print(next.value + " ");
	            next = next.next;
            }
            System.out.println(" ");

        }
    }
//[-] (0)
//[1]  0 (1)
//[1]  0 0 0 0 0 0
//[2]  0 2 1 0 2 1
}
